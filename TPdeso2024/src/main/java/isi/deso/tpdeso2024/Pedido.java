/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Pedido implements Observable {

    private int id;

    private PedidoDetalle detalle;
    private Pago pago;
    private EstadoPedido estado;
    private Cliente cliente;
    boolean changed = false;

    public Pedido(int id, PedidoDetalle detalle, Pago pago, EstadoPedido estado, Cliente cliente) {
        this.id = id;
        this.detalle = detalle;
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
    }
    
    public Pedido(PedidoDetalle detalle, Pago pago, Cliente cliente) {
        this.detalle = detalle;
        this.pago = pago;
        this.cliente = cliente;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
        this.setChanged();
        this.notifyObservers();
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void setChanged() {
        this.changed = true;
    }

    @Override
    public void clearChanged() {
        this.changed = false;
    }

    @Override
    public void notifyObservers() {
        if (this.changed) {
            this.cliente.update(this);
        }
        this.clearChanged();
    }

}
