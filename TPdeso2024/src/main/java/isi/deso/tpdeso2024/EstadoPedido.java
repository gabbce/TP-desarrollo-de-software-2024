/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package isi.deso.tpdeso2024;

/**
 *
 * @author augus
 */
public enum EstadoPedido {
    PENDIENTE,// no lo recibio el vendedor
    RECIBIDO, // el vendedor lo recibio
    EN_ENVIO, // el vendedor envio el pedido, se crea un pago
    RECIBIDO_CLIENTE
}
