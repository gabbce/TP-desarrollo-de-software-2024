/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Categoria;
import isi.deso.tpdeso2024.excepciones.CategoriaNoEncontradoException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author augus
 */
public class CategoriaMEMORYDAO implements CategoriaDAO{

    private static List<Categoria> categorias;
    private int ultimaID; //se auto incrementa
    // Constructor para inicializar la lista
    public CategoriaMEMORYDAO() {
        CategoriaMEMORYDAO.categorias = new LinkedList<>();
        ultimaID = 1; 
        //harcodear categorias aca usando this.crear
    }
    
    @Override
    public Categoria buscarPorID(int id) throws CategoriaNoEncontradoException {
        for (int i = 0; i < categorias.size(); i++) if(categorias.get(i).getId()==id)return categorias.get(i);
        throw new CategoriaNoEncontradoException("");
     }

    @Override
    public boolean crear(Categoria c) {
        c.setId(ultimaID);
        ultimaID++;
        categorias.add(c);
        return true;
    }

    @Override
    public List<Categoria> listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Categoria buscarPorTipo(String tipo) throws CategoriaNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}
