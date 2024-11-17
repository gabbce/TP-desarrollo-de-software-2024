/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.dtos;

import isi.deso.tpdeso2024.EstadoPedido;
import isi.deso.tpdeso2024.Pago;
import isi.deso.tpdeso2024.PedidoDetalle;
import java.util.LinkedList;

/**
 *
 * @author gabic
 */
public class PedidoDTO {
    private int id;
    
    private LinkedList<PedidoDetalleDTO> pedidoDetalle; // lista de items menu

    private Pago pago; // se consigue el tipo con getStrategyType();
    private EstadoPedido estado; // ENUM
    private ClienteDTO cliente;
    //boolean changed;

    public PedidoDTO(int id, LinkedList<PedidoDetalleDTO> pedidoDetalle, Pago pago, EstadoPedido estado, ClienteDTO cliente) {
        this.id = id;
        this.pedidoDetalle = pedidoDetalle;
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
    }

    public PedidoDTO(LinkedList<PedidoDetalleDTO> pedidoDetalle, Pago pago, EstadoPedido estado, ClienteDTO cliente) {
        this.pedidoDetalle = pedidoDetalle;
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
    }

    public PedidoDTO(Pago pago, EstadoPedido estado, ClienteDTO cliente) {
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
    }

   
    
    
    public LinkedList<PedidoDetalleDTO> getPedidoDetalle() {
        return pedidoDetalle;
    }

    public void setPedidoDetalle(LinkedList<PedidoDetalleDTO> pedidoDetalle) {
        this.pedidoDetalle = pedidoDetalle;
    }

    public int getId() {
        return id;
    }
    

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    
    
    
}
