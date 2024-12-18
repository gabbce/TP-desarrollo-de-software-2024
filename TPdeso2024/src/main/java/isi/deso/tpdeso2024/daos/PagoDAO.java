package isi.deso.tpdeso2024.daos;


import isi.deso.tpdeso2024.Pago;
import isi.deso.tpdeso2024.PagoType;
import java.sql.SQLException;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author gabic
 */
public interface PagoDAO {
    public boolean crear(Pago p) throws SQLException;
    public boolean eliminar(int id_pedido) throws SQLException;
    public boolean actualizar(Pago p) throws SQLException;
    public Pago buscarPorIdPedido(int id_pedido);
    public List<PagoType> getPagoTypes();
}
