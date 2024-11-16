/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Cliente;
import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.EstadoPedido;
import isi.deso.tpdeso2024.Pedido;
import isi.deso.tpdeso2024.PedidoDetalle;
import isi.deso.tpdeso2024.dtos.PedidoDTO;
import isi.deso.tpdeso2024.excepciones.ClienteNoEncontradoException;
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
    public List<Pedido> listar() {
	ArrayList<Pedido> ret = new ArrayList<>();
        //armar la query completa y despues parsear todo, sino va a ser lerdo
        //porque hay que buscar el cliente y los items
        
        
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Pedido;								
		""");

            ResultSet resultados = preparedStatement.executeQuery();
            while (resultados.next()) { //CAMBIAR ORDEN EN CAPA DATOS
                Cliente cliente = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO().buscarPorID(resultados.getInt(3));
                Pedido tmp = new Pedido(resultados.getInt(1), null, EstadoPedido.valueOf(resultados.getString(2)), cliente);
                //subconsulta, agregar pedidodetalle
                
                
                
                
                ret.add(tmp);
            }

            preparedStatement.close();
            this.conector.cerrar();

        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".listar() " + e.getMessage());
        }

        return ret;
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

    
    
    /*public Pedido buscarPorID(int id) {
       try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Pedido WHERE id = ?;								
		""");

            //setear valores
            preparedStatement.setInt(1, id);

            Pedido ret = null;
            ResultSet resultados = preparedStatement.executeQuery();
            int contador = 0;
            while (resultados.next()) {
                ret = new Pedido(
                        resultados.getInt(1),
				resultados.getInt(2),
				resultados.getString(3),
				resultados.getString(4),
				new Coordenada(
					resultados.getFloat(5),
					resultados.getFloat(6)
				)
                );
                contador++;
            }
            if (contador == 0) {
                throw new PedidoNoEncontradoException("No existe Pedido con id " + id);
            }

            preparedStatement.close();
            this.conector.cerrar();
            
            return ret;
            
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".buscarPorID() " + e.getMessage());
            return null;
        }
    }
*/
    @Override
    public boolean actualizar(PedidoDTO vdto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Pedido> buscar(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
