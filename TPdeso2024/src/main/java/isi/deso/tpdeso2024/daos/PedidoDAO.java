/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Pedido;
import isi.deso.tpdeso2024.dtos.PedidoDTO;
import isi.deso.tpdeso2024.excepciones.PedidoNoEncontradoException;
import java.util.List;

/**
 *
 * @author gabic
 */
public interface PedidoDAO {
    public boolean crear(Pedido v);
    public boolean eliminar(int id);
    public boolean actualizar(Pedido v);
    public List<Pedido> listar();
    public Pedido buscarPorID(int id) throws PedidoNoEncontradoException;
}
