/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.dtos;

import isi.deso.tpdeso2024.EstadoPedido;
import isi.deso.tpdeso2024.Pago;
import isi.deso.tpdeso2024.PedidoDetalle;

/**
 *
 * @author gabic
 */
public class PedidoDTO {
    private int id;
    
    private PedidoDetalle detalle; // lista de items menu
    private Pago pago; // se consigue el tipo con getStrategyType();
    private EstadoPedido estado; // ENUM
    private ClienteDTO cliente;
    boolean changed;

    public PedidoDTO(int id, PedidoDetalle detalle, Pago pago, EstadoPedido estado, ClienteDTO cliente) {
        this.id = id;
        this.detalle = detalle;
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
        this.changed = false;
    }
    
    public PedidoDTO(PedidoDetalle detalle, Pago pago, EstadoPedido estado, ClienteDTO cliente) {
        this.detalle = detalle;
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
        this.changed = false;
    }
    
    public PedidoDTO(int id, PedidoDetalle detalle, Pago pago, EstadoPedido estado, ClienteDTO cliente, boolean changed) {
        this.id = id;
        this.detalle = detalle;
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
        this.changed = changed;
    }

    public int getId() {
        return id;
    }
    
    public PedidoDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(PedidoDetalle detalle) {
        this.detalle = detalle;
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

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    
    
    
}
