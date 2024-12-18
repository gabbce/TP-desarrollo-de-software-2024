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

    private PagoDTO pago; // se consigue el tipo con getStrategyType();
    private EstadoPedido estado; // ENUM
    private ClienteDTO cliente;
    private Float precioFinal;
    //boolean changed;

    public PedidoDTO(int id, LinkedList<PedidoDetalleDTO> pedidoDetalle, PagoDTO pago, EstadoPedido estado, ClienteDTO cliente, Float precioFinal) {
        this.id = id;
        this.pedidoDetalle = pedidoDetalle;
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
        this.precioFinal = precioFinal;
    }

    public PedidoDTO(LinkedList<PedidoDetalleDTO> pedidoDetalle, PagoDTO pago, EstadoPedido estado, ClienteDTO cliente, Float precioFinal) {
        this.pedidoDetalle = pedidoDetalle;
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
        this.precioFinal = precioFinal;
    }

    public PedidoDTO(PagoDTO pago, EstadoPedido estado, ClienteDTO cliente, Float precioFinal) {
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
        this.precioFinal = precioFinal;
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
    

    public PagoDTO getPago() {
        return pago;
    }

    public void setPago(PagoDTO pago) {
        this.pago = pago;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Float getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Float precioFinal) {
        this.precioFinal = precioFinal;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    
    
    
}
