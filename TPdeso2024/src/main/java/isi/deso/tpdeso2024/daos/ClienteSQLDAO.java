/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.Cliente;
import isi.deso.tpdeso2024.dtos.ClienteDTO;
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
    public boolean crear(Cliente v) {
    try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
                                                                            INSERT INTO Cliente (cuit,mail,direccion,latitud,longitud) 
                                                                            VALUES (?,?,?,?,?);""");
        
        //setear valores
        preparedStatement.setInt(1, v.getCuit());
        preparedStatement.setString(2, v.getMail());
        preparedStatement.setString(3, v.getDireccion());
        preparedStatement.setFloat(4, v.getCoordenadas().getLatitud());
        preparedStatement.setFloat(5, v.getCoordenadas().getLongitud());

        
        preparedStatement.executeUpdate();
        preparedStatement.close();
        this.conector.cerrar();
        }
        catch(Exception e){
            System.out.println("excepcion en "+ Class.getSimpleName() + ".create() " + e.getMessage());
            return false;
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
            System.out.println("excepcion en "+ Class.getSimpleName() + ".eliminar() " + e.getMessage());
            return false;
        } 
        return true;  
    }


    @Override
	public List<Cliente> buscar(int cuit) {
		System.out.println("no tengo ganas de ir a la interfaz");
		return null;
	}
	
    public List<ClienteDTO> buscar(int cuit) {
		try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT FROM Cliente WHERE cuit = ?;								
		""");
        
        //setear valores
        preparedStatement.setString(1, cuit);
        
		
		ArrayList<ClienteDTO> ret = new ArrayList<>();
        ResultSet resultados = preparedStatement.executeQuery();
		while(resultados.next()){
			ClienteDTO tmp = new ClienteDTO(
				resultados.getInt(1),
				resultados.getInt(2),
				resultados.getString(3),
				resultados.getString(4),
				new CoordenadaDTO(
					resultados.getFloat(5),
					resultados.getFloat(6),
				)
			);
			ret.add(tmp);
		}

		
		
        preparedStatement.close();
        this.conector.cerrar();
        }
        catch(Exception e){
            System.out.println("excepcion en "+ Class.getSimpleName() + ".buscar() " + e.getMessage());
            return null;
        } 
        return ret;
    }

    @Override
	public List<Cliente> listar() {
		System.out.println("no tengo ganas de ir a la interfaz");
		return null;
	}

    public List<ClienteDTO> listar() {
		
		try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT FROM Cliente WHERE;								
		""");
		
		ArrayList<ClienteDTO> ret = new ArrayList<>();
        ResultSet resultados = preparedStatement.executeQuery();
		while(resultados.next()){
			ClienteDTO tmp = new ClienteDTO(
				resultados.getInt(1),
				resultados.getInt(2),
				resultados.getString(3),
				resultados.getString(4),
				new CoordenadaDTO(
					resultados.getFloat(5),
					resultados.getFloat(6),
				)
			);
			ret.add(tmp);
		}

		
		
        preparedStatement.close();
        this.conector.cerrar();
        }
        catch(Exception e){	
            System.out.println("excepcion en "+ Class.getSimpleName() + ".listar() " + e.getMessage());
            return null;
        } 
        return ret;
    }

    @Override
    public boolean actualizar(ClienteDTO v) {
		try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
			UPDATE Cliente
			SET cuit = ?, mail = ?, direccion = ?, latitud= ?, longitud = ?;
			WHERE id = ?
			
			""");
        
        //setear valores
        preparedStatement.setInt(1, v.getCuit());
        preparedStatement.setString(2, v.getMail());
        preparedStatement.setString(3, v.getDireccion());
        preparedStatement.setFloat(4, v.getCoordenadas().getLatitud());
        preparedStatement.setFloat(5, v.getCoordenadas().getLongitud());
        preparedStatement.setInt(6, v.getId());
        
        preparedStatement.executeUpdate();
        preparedStatement.close();
        this.conector.cerrar();
        }
        catch(Exception e){
            System.out.println("excepcion en "+ Class.getSimpleName() + ".actualizar() " + e.getMessage());
            return false;
        } 
        
        return true;
    }
}
