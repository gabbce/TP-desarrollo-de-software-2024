/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Pedido;
import isi.deso.tpdeso2024.dtos.PedidoDTO;
import java.util.List;

/**
 *
 * @author gabic
 */
public interface PedidoDAO {
    public boolean crear(Pedido v);
    public boolean eliminar(int id);
    public boolean actualizar(PedidoDTO vdto);
    public List<Pedido> listar();
    public List<Pedido> buscar(String nombre);
}
