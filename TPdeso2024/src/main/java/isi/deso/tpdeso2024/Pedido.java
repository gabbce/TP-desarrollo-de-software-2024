/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

/**
 *
 * @author Usuario
 */
public class Pedido {
    private PedidoDetalle detalle;
    private Pago pago;

    public Pedido(PedidoDetalle detalle, Pago pago) {
        this.detalle = detalle;
        this.pago = pago;
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
    
    
}
