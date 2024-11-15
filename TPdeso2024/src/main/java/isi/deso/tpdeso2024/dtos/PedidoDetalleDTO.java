/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.dtos;

import java.util.ArrayList;

/**
 *
 * @author gabic
 */
public class PedidoDetalleDTO {
    private PedidoDTO pedido;
    private ItemMenuDTO item;
    private int cantidad;

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }

    public ItemMenuDTO getItem() {
        return item;
    }

    public void setItem(ItemMenuDTO item) {
        this.item = item;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public PedidoDetalleDTO(PedidoDTO pedido, ItemMenuDTO item, int cantidad) {
        this.pedido = pedido;
        this.item = item;
        this.cantidad = cantidad;
    }
    
}
