/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Cliente;
import isi.deso.tpdeso2024.dtos.ClienteDTO;
import java.util.List;

/**
 *
 * @author augus
 */
public interface ClienteDAO {
    public boolean crear(Cliente v);
    public boolean eliminar(int id);
    public boolean actualizar(ClienteDTO vdto);
    public List<Cliente> listar();
    public List<Cliente> buscar(int cuit);
}
