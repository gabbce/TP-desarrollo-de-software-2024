/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Cliente;
import isi.deso.tpdeso2024.dtos.ClienteDTO;
import isi.deso.tpdeso2024.excepciones.ClienteNoEncontradoException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author augus
 */
public interface ClienteDAO {
    public boolean crear(Cliente v) throws SQLException;
    public boolean eliminar(int id);
    public boolean actualizar(Cliente v) throws SQLException;
    public List<Cliente> listar();
    public List<Cliente> buscarPorCuit(String cuit);
    public Cliente buscarPorID(int id) throws ClienteNoEncontradoException;
}
