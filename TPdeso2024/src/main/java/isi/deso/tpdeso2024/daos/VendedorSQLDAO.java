/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.Vendedor;
import isi.deso.tpdeso2024.dtos.VendedorDTO;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author augus
 */
public class VendedorSQLDAO implements VendedorDAO {


	private DataBaseConnection conector;
    public VendedorSQLDAO() {
		conector = new DataBaseConnection();
    }
	

    @Override
    public boolean crear(Vendedor v) {
    try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
                                                                            INSERT INTO Vendedor (nombre,direccion,latitud,longitud) 
                                                                            VALUES (?,?,?,?);""");
        
        //setear valores
        preparedStatement.setString(1, v.getNombre());
        preparedStatement.setString(2, v.getDireccion());
        preparedStatement.setFloat(3, v.getCoordenadas().getLatitud());
        preparedStatement.setFloat(4, v.getCoordenadas().getLongitud());

        
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
		DELETE FROM Vendedor WHERE id = ?;
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
    public List<Vendedor> buscar(String nombre) {
		System.out.println("no tengo ganas de ir a la interfaz");
		return null;
	}
		
	public List<VendedorDTO> buscar(String nombre) {
    	try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT FROM Vendedor WHERE nombre = ?;								
		""");
        
        //setear valores
        preparedStatement.setString(1, nombre);
        
		
		ArrayList<VendedorDTO> ret = new ArrayList<>();
        ResultSet resultados = preparedStatement.executeQuery();
		while(resultados.next()){
			VendedorDTO tmp = new VendedorDTO(
				resultados.getInt(1),
				resultados.getString(2),
				resultados.getString(3),
				new CoordenadaDTO(
					resultados.getFloat(4),
					resultados.getFloat(5),
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
	public List<Vendedor> listar() {
		System.out.println("no tengo ganas de ir a la interfaz");
		return null;
	}
	
    public List<VendedorDTO> listar() {
    	try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT FROM Vendedor;								
		""");
        
        //setear valores
        preparedStatement.setString(1, nombre);
        
		
		ArrayList<VendedorDTO> ret = new ArrayList<>();
        ResultSet resultados = preparedStatement.executeQuery();
		while(resultados.next()){
			VendedorDTO tmp = new VendedorDTO(
				resultados.getInt(1),
				resultados.getString(2),
				resultados.getString(3),
				new CoordenadaDTO(
					resultados.getFloat(4),
					resultados.getFloat(5),
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
        return ret;    }

    @Override
    public boolean actualizar(VendedorDTO vdto) {
    try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
			UPDATE Vendedor
			SET nombre = ?, direccion = ?, latitud= ?, longitud = ?;
			WHERE id = ?
			
			""");
        
        //setear valores
        preparedStatement.setString(1, v.getNombre());
        preparedStatement.setString(2, v.getDireccion());
        preparedStatement.setFloat(3, v.getCoordenadas().getLatitud());
        preparedStatement.setFloat(4, v.getCoordenadas().getLongitud());
        preparedStatement.setInt(5, v.getId());
        
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
    
    @Override
	public Vendedor buscarPorID(int id) throws VendedorNoEncontradoException{
		throw new VendedorNoEncontradoException("No se ejecuta el metodo correcto");
	}
		
    public VendedorDTO buscarPorID(int id) throws VendedorNoEncontradoException{
    	try{
        this.conector.conectar();
        
       
        PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT FROM Vendedor WHERE id = ?;								
		""");
        
        //setear valores
        preparedStatement.setInt(1, id);
        
		
		ArrayList<VendedorDTO> ret = new ArrayList<>();
        ResultSet resultados = preparedStatement.executeQuery();
		int contador = 0;
		while(resultados.next()){
			VendedorDTO tmp = new VendedorDTO(
				resultados.getInt(1),
				resultados.getString(2),
				resultados.getString(3),
				new CoordenadaDTO(
					resultados.getFloat(4),
					resultados.getFloat(5),
				)
			);
			ret.add(tmp);
			contador++;
		}
		if(contador == 0) throw new VendedorNoEncontradoException("");

		
		
        preparedStatement.close();
        this.conector.cerrar();
        }
        catch(Exception e){
            System.out.println("excepcion en "+ Class.getSimpleName() + ".buscarPorID() " + e.getMessage());
            return null;
        } 
        return ret;
    }
}
