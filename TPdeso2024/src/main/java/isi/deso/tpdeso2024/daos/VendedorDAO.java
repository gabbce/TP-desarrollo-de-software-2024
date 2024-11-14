/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Vendedor;
import isi.deso.tpdeso2024.dtos.VendedorDTO;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import java.util.List;

/**
 *
 * @author augus
 */
public interface VendedorDAO {
    public boolean crear(Vendedor v);
    public boolean eliminar(int id);
    public boolean actualizar(Vendedor vdto);
    public List<Vendedor> listar();
    public List<Vendedor> buscar(String nombre) ;

    public Vendedor buscarPorID(int id) throws VendedorNoEncontradoException;
}
