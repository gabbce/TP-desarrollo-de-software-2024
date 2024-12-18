/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.Cliente;
import isi.deso.tpdeso2024.excepciones.ClienteNoEncontradoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author augus
 */
public class ClienteSQLDAO implements ClienteDAO {

	private DataBaseConnection conector;
    public ClienteSQLDAO() {
		conector = new DataBaseConnection();
    }

    @Override
    public boolean crear(Cliente v) throws SQLException{
    try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
                                                                            INSERT INTO Cliente (cuit,mail,direccion,latitud,longitud) 
                                                                            VALUES (?,?,?,?,?);""");
        
        //setear valores
        preparedStatement.setString(1, v.getCuit());
        preparedStatement.setString(2, v.getEmail());
        preparedStatement.setString(3, v.getDireccion());
        preparedStatement.setDouble(4, v.getCoordenadas().getLatitud());
        preparedStatement.setDouble(5, v.getCoordenadas().getLongitud());

        
        preparedStatement.executeUpdate();
        preparedStatement.close();
        this.conector.cerrar();
        }
        catch(SQLException e){
            System.out.println("excepcion en "+ this.getClass().getName() + ".create() " + e.getMessage());
            throw new SQLException(e.getMessage());
            //return false;
        } 
        return true;
    }

    @Override
    public boolean eliminar(int id) {
		try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
		DELETE FROM Cliente WHERE id = ?;
		""");
        
        //setear valores
        preparedStatement.setInt(1, id);
        
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
    public List<Cliente> buscarPorCuit(String cuit) {
		try{
        this.conector.conectar();
        
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
                SELECT * FROM Cliente WHERE CAST(cuit AS TEXT) LIKE ?;
            """);

                    preparedStatement.setString(1, cuit + "%");

        
        
		
		ArrayList<Cliente> ret = new ArrayList<>();
        ResultSet resultados = preparedStatement.executeQuery();
		while(resultados.next()){
			Cliente tmp = new Cliente(
				resultados.getInt(1),
				resultados.getString(2),
				resultados.getString(3),
				resultados.getString(4),
				new Coordenada(
					resultados.getFloat(5),
					resultados.getFloat(6)
				)
			);
			ret.add(tmp);
		}

		
		
        preparedStatement.close();
        this.conector.cerrar();
        
        return ret;
        }
        catch(Exception e){
            System.out.println("excepcion en "+ this.getClass().getName() + ".buscar() " + e.getMessage());
            return null;
        } 
        
    }

    @Override
    public List<Cliente> listar() {
		      ArrayList<Cliente> ret = new ArrayList<>();

        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Cliente;								
		""");

            ResultSet resultados = preparedStatement.executeQuery();
            while (resultados.next()) {
                Cliente tmp = new Cliente(
                        resultados.getInt(1),
                        resultados.getString(2),
                        resultados.getString(3),
                        resultados.getString(4),
                        new Coordenada(
                                resultados.getFloat(5),
                                resultados.getFloat(6)
                        )
                );
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
    public boolean actualizar(Cliente v) throws SQLException{
		try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
			UPDATE Cliente
			SET cuit = ?, mail = ?, direccion = ?, latitud= ?, longitud = ?
			WHERE id = ?
			
			""");
        
        //setear valores
        preparedStatement.setString(1, v.getCuit());
        preparedStatement.setString(2, v.getEmail());
        preparedStatement.setString(3, v.getDireccion());
        preparedStatement.setDouble(4, v.getCoordenadas().getLatitud());
        preparedStatement.setDouble(5, v.getCoordenadas().getLongitud());
        preparedStatement.setInt(6, v.getId());
        
        preparedStatement.executeUpdate();
        preparedStatement.close();
        this.conector.cerrar();
        }
        catch(SQLException e){
            System.out.println("excepcion en "+ this.getClass().getName() + ".actualizar() " + e.getMessage());
            throw new SQLException(e.getMessage());
            //return false;
        } 
        
        return true;
    }
    

    @Override
    public Cliente buscarPorID(int id) throws ClienteNoEncontradoException {
       try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Cliente WHERE id = ?;								
		""");

            //setear valores
            preparedStatement.setInt(1, id);

            Cliente ret = null;
            ResultSet resultados = preparedStatement.executeQuery();
            int contador = 0;
            while (resultados.next()) {
                ret = new Cliente(
                        resultados.getInt(1),
				resultados.getString(2),
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
                throw new ClienteNoEncontradoException("No existe Cliente con id " + id);
            }

            preparedStatement.close();
            this.conector.cerrar();
            
            return ret;
            
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".buscarPorID() " + e.getMessage());
            return null;
        }
    }
}
