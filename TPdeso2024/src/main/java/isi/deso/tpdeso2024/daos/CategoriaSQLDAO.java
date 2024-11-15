/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Categoria;
import isi.deso.tpdeso2024.excepciones.CategoriaNoEncontradoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabic
 */
public class CategoriaSQLDAO implements CategoriaDAO {

    private DataBaseConnection conector;

    public CategoriaSQLDAO() {
        conector = new DataBaseConnection();
    }

    @Override
    public boolean crear(Categoria c) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //listar, buscar por tipo, buscar por id
    @Override
    public List<Categoria> listar() {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Categoria;								
		""");

            ArrayList<Categoria> ret = new ArrayList<>();
            ResultSet resultados = preparedStatement.executeQuery();
            while (resultados.next()) {
                Categoria tmp = new Categoria(
                        resultados.getInt(1),
                        resultados.getString(2),
                        resultados.getString(3)
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
    public Categoria buscarPorTipo(String tipo) throws CategoriaNoEncontradoException {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Categoria WHERE tipo = ?;								
		""");

            //setear valores
            preparedStatement.setString(1, tipo);

            Categoria ret = null;
            ResultSet resultados = preparedStatement.executeQuery();
            int contador = 0;
            while (resultados.next()) {
                ret = new Categoria(
                        resultados.getInt(1),
                        resultados.getString(2),
                        resultados.getString(3)
                );
                contador++;
            }
            if (contador == 0) {
                throw new CategoriaNoEncontradoException("No existe Categoria con tipo " + tipo);
            }

            preparedStatement.close();
            this.conector.cerrar();

            return ret;

        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".buscarPorTipo() " + e.getMessage());
            return null;
        }

    }

    @Override
    public Categoria buscarPorID(int id) throws CategoriaNoEncontradoException {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM Categoria WHERE id = ?;								
		""");

            //setear valores
            preparedStatement.setInt(1, id);

            Categoria ret = null;
            ResultSet resultados = preparedStatement.executeQuery();
            int contador = 0;
            while (resultados.next()) {
                ret = new Categoria(
                        resultados.getInt(1),
                        resultados.getString(2),
                        resultados.getString(3)
                );
                contador++;
            }
            if (contador == 0) {
                throw new CategoriaNoEncontradoException("No existe Categoria con id " + id);
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
