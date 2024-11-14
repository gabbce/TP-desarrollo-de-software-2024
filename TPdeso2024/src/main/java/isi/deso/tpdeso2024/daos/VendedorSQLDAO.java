/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.Vendedor;
import isi.deso.tpdeso2024.dtos.VendedorDTO;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
                                                                            INSERT INTO Vendedor (nombre,direccion,latitud,longitud) 
                                                                            VALUES (?,?,?,?);""");

            //setear valores
            preparedStatement.setString(1, v.getNombre());
            preparedStatement.setString(2, v.getDireccion());
            preparedStatement.setDouble(3, v.getCoordenadas().getLatitud());
            preparedStatement.setDouble(4, v.getCoordenadas().getLongitud());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.conector.cerrar();
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".create() " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean eliminar(int id) {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		DELETE FROM Vendedor WHERE id = ?;
		""");

            //setear valores
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.conector.cerrar();
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".eliminar() " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Vendedor> buscar(String nombre) {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Vendedor WHERE nombre LIKE ?;								
		""");

            //setear valores
            preparedStatement.setString(1, "%" + nombre + "%");

            ArrayList<Vendedor> ret = new ArrayList<>();
            ResultSet resultados = preparedStatement.executeQuery();
            while (resultados.next()) {
                Vendedor tmp = new Vendedor(
                        resultados.getInt(1),
                        resultados.getString(2),
                        resultados.getString(3),
                        new Coordenada(
                                resultados.getFloat(4),
                                resultados.getFloat(5)
                        )
                );
                ret.add(tmp);
            }

            preparedStatement.close();
            this.conector.cerrar();

            return ret;
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".buscar() " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Vendedor> listar() {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Vendedor;								
		""");

            ArrayList<Vendedor> ret = new ArrayList<>();
            ResultSet resultados = preparedStatement.executeQuery();
            while (resultados.next()) {
                Vendedor tmp = new Vendedor(
                        resultados.getInt(1),
                        resultados.getString(2),
                        resultados.getString(3),
                        new Coordenada(
                                resultados.getFloat(4),
                                resultados.getFloat(5)
                        )
                );
                ret.add(tmp);
            }

            preparedStatement.close();
            this.conector.cerrar();

            return ret;
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".listar() " + e.getMessage());
            return null;
        }

    }

    @Override
    public boolean actualizar(Vendedor v) {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
			UPDATE Vendedor
			SET nombre = ?, direccion = ?, latitud= ?, longitud = ?
			WHERE id = ?
			
			""");

            //setear valores
            preparedStatement.setString(1, v.getNombre());
            preparedStatement.setString(2, v.getDireccion());
            preparedStatement.setDouble(3, v.getCoordenadas().getLatitud());
            preparedStatement.setDouble(4, v.getCoordenadas().getLongitud());
            preparedStatement.setInt(5, v.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.conector.cerrar();
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".actualizar() " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Vendedor buscarPorID(int id) throws VendedorNoEncontradoException {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Vendedor WHERE id = ?;								
		""");

            //setear valores
            preparedStatement.setInt(1, id);

            Vendedor ret = null;
            ResultSet resultados = preparedStatement.executeQuery();
            int contador = 0;
            while (resultados.next()) {
                ret = new Vendedor(
                        resultados.getInt(1),
                        resultados.getString(2),
                        resultados.getString(3),
                        new Coordenada(
                                resultados.getFloat(4),
                                resultados.getFloat(5)
                        )
                );
                contador++;
            }
            if (contador == 0) {
                throw new VendedorNoEncontradoException("No existe vendedor con id " + id);
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
