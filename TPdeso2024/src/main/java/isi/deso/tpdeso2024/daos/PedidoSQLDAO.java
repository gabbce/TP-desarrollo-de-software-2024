/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Bebida;
import isi.deso.tpdeso2024.Cliente;
import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.EstadoPedido;
import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.Pedido;
import isi.deso.tpdeso2024.PedidoDetalle;
import isi.deso.tpdeso2024.Plato;
import isi.deso.tpdeso2024.dtos.PedidoDTO;
import isi.deso.tpdeso2024.excepciones.ClienteNoEncontradoException;
import isi.deso.tpdeso2024.excepciones.PedidoNoEncontradoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author augus
 */
public class PedidoSQLDAO implements PedidoDAO {

    private DataBaseConnection conector;
    public PedidoSQLDAO() {
		conector = new DataBaseConnection();
    }

    @Override
    public boolean crear(Pedido v) {
    try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
                                                                            INSERT INTO Pedido (estado_pedido,id_cliente) 
                                                                            VALUES ('Pendiente',?);""",
                PreparedStatement.RETURN_GENERATED_KEYS);
        
        //setear valores
        preparedStatement.setInt(1, v.getCliente().getId());

        preparedStatement.executeUpdate();
            
        
        ResultSet claveGenerada = preparedStatement.getGeneratedKeys();
        claveGenerada.next();
        int parentId = claveGenerada.getInt(1);
        
        for(PedidoDetalle pd: v.getPedidoDetalle()){
        
        preparedStatement = conector.con.prepareStatement("INSERT INTO Pedido_detalle (id_pedido,id_item_menu,cantidad)  VALUES (?,?,?);");
        
        //setear valores
        preparedStatement.setInt(1, parentId);
        preparedStatement.setInt(2, pd.getItem().getId());
        preparedStatement.setInt(3, pd.getCantidad());
        
        preparedStatement.executeUpdate();        
        }
        preparedStatement.close();
        this.conector.cerrar();
        }
        catch(Exception e){
            System.out.println("excepcion en "+ this.getClass().getName() + ".create() " + e.getMessage());
            return false;
        } 
        return true;
    }



    @Override
    public boolean actualizar(Pedido v) {
		try{
        this.conector.conectar();
        
       
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
                                                                            UPDATE Pedido
                                                                            SET estado_pedido = ?, id_cliente = ?
                                                                            WHERE id = ?;""");
        
        //setear valores
        preparedStatement.setString(1, (v.getEstado().toString()));
        preparedStatement.setInt(2, v.getCliente().getId());
        preparedStatement.setInt(3, v.getId());

        preparedStatement.executeUpdate();
        
        for(PedidoDetalle pd: v.getPedidoDetalle()){
        
        preparedStatement = conector.con.prepareStatement("""
                                                              UPDATE Pedido_detalle
                                                              SET id_item_menu = ?, cantidad = ?
                                                              WHERE id_pedido = ?;""");
        //setear valores
        preparedStatement.setInt(1, pd.getItem().getId());
        preparedStatement.setInt(2, pd.getCantidad());
        preparedStatement.setInt(3, v.getId());
        
        preparedStatement.executeUpdate();        
        }
        this.conector.cerrar();
        }
        catch(Exception e){
            System.out.println("excepcion en "+ this.getClass().getName() + ".actualizar() " + e.getMessage());
            return false;
        } 
        
        return true;
    }
    
    
    @Override
    public boolean eliminar(int id) {
		try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
		DELETE FROM Pedido WHERE id = ?;
                DELETE FROM Pedido_detalle WHERE id_pedido = ?;
		""");
        
        //setear valores
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, id);
        
        preparedStatement.executeUpdate();
        preparedStatement.close();
        this.conector.cerrar();
        }
        catch(Exception e){
            System.out.println("excepcion en "+ this.getClass().getName() + ".eliminar() " + e.getMessage());
            return false;
        } 
        return true;  
    }


    @Override
    public List<Pedido> listar() {
	ArrayList<Pedido> ret = new ArrayList<>();
        
        
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Pedido;								
		""");

            ResultSet resultados = preparedStatement.executeQuery();
            while (resultados.next()) {
                Cliente aux = new Cliente(resultados.getInt(3), 0,null, null,null);
                Pedido tmp = new Pedido(resultados.getInt(1), null, EstadoPedido.valueOf(resultados.getString(2)),aux);
                ret.add(tmp);
            }

            preparedStatement.close();
            this.conector.cerrar();
            for(Pedido p:ret)armarPedidoDetalle(p);

        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".listar() " + e.getMessage());
        }

        return ret;
    }
    
    public void armarPedidoDetalle(Pedido p){
        armarPedidoDetalleAux(p, true);
        armarPedidoDetalleAux(p, false);
    }
    public void armarPedidoDetalleAux(Pedido p,boolean comida){
        try {
            
            
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT id_item_menu,cantidad
                FROM pedido_detalle JOIN item_menu it ON id_item_menu =  it.id
                WHERE id_pedido = ? AND es_comida = ?;								
		""");
            preparedStatement.setInt(1,p.getId());
            preparedStatement.setBoolean(2,comida);

            ResultSet resultados = preparedStatement.executeQuery();
            while (resultados.next()) {
                
                PedidoDetalle tmp;
                if(comida){
                    Plato aux = new Plato(resultados.getInt(1), null, null, null, null, 0, 0, 0, false, false);

                    tmp = new PedidoDetalle(p, aux, resultados.getInt(3));
                }
                else{
                
                    Bebida aux = new Bebida(resultados.getInt(1), null,null,null,null, 0, 0, 0);
                    tmp = new PedidoDetalle(p, aux, resultados.getInt(3));
                
                }
                p.getPedidoDetalle().add(tmp);
            }

            preparedStatement.close();
            this.conector.cerrar();
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".armarPedidoDetalle() " + e.getMessage());
        }
    
    
    }
    
    @Override
    public Pedido buscarPorID(int id) throws PedidoNoEncontradoException{
        Pedido ret=null;
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Pedido WHERE id = ?;								
		""");

            preparedStatement.setInt(1, id);
            
            
            ResultSet resultados = preparedStatement.executeQuery();
            int contador =0;
            while (resultados.next()) {
                Cliente aux = new Cliente(resultados.getInt(3), 0,null, null,null);
                Pedido tmp = new Pedido(resultados.getInt(1), null, EstadoPedido.valueOf(resultados.getString(2)),aux);
                contador++;
            }

            preparedStatement.close();
            this.conector.cerrar();
            
            if(contador==0)throw new PedidoNoEncontradoException();
            armarPedidoDetalle(ret);
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".listar() " + e.getMessage());
        }

        return ret;
    
    
    }
}
