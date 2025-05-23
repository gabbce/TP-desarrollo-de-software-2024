/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Pedido implements Observable {

    private int id;

    //private PedidoDetalle detalle;
    private Pago pago;
    private EstadoPedido estado;
    private Cliente cliente;
    boolean changed = false;
    private LinkedList<PedidoDetalle> pedidoDetalle;
    private Float precioFinal;

    public LinkedList<PedidoDetalle> getPedidoDetalle() {
        return pedidoDetalle;
    }

    public void setPedidoDetalle(LinkedList<PedidoDetalle> pedidoDetalle) {
        this.pedidoDetalle = pedidoDetalle;
    }

    public Pedido(int id, Pago pago, EstadoPedido estado, Cliente cliente, Float precioFinal) {
        this.id = id;
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
        this.pedidoDetalle = new LinkedList<>();
        this.precioFinal = precioFinal;
    }
    
    public Pedido(Pago pago, Cliente cliente, Float precioFinal) {
        this.pago = pago;
        this.cliente = cliente;
        this.estado = EstadoPedido.PENDIENTE;
        this.precioFinal = precioFinal;
    }

    public boolean itemsSonDelMismoVendedor(){
    if(pedidoDetalle.isEmpty()) return true;
    //Vendedor v = pedidoDetalle.get(0).getItem().getVendedor();
    int idV = pedidoDetalle.get(0).getItem().getVendedor().getId();
    for(PedidoDetalle pd : pedidoDetalle){
        /*Vendedor v2 = pd.getItem().getVendedor();
        if(v!=v2)return false;*/
        
        int idVAux = pd.getItem().getVendedor().getId();
        
        if(idV != idVAux) return false;
    }
    return true;
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

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Float getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Float precioFinal) {
        this.precioFinal = precioFinal;
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

    /*public float peso(){
        float suma = 0;
        for(ItemMenu i: items){
            suma+=i.peso();
        
        }
        return suma;
    }*/
}
