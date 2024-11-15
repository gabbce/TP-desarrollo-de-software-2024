/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Categoria;
import isi.deso.tpdeso2024.Vendedor;
import isi.deso.tpdeso2024.dtos.VendedorDTO;
import isi.deso.tpdeso2024.excepciones.CategoriaNoEncontradoException;
import java.util.List;

/**
 *
 * @author augus
 */
public interface CategoriaDAO {
    public boolean crear(Categoria c);
    public List<Categoria> listar();
    public Categoria buscarPorID(int id) throws CategoriaNoEncontradoException;
    public Categoria buscarPorTipo(String tipo) throws CategoriaNoEncontradoException;
}

