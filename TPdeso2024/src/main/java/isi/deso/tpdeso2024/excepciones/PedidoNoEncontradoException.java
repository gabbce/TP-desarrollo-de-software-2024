/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package isi.deso.tpdeso2024.excepciones;

/**
 *
 * @author augus
 */
public class PedidoNoEncontradoException extends Exception {

    /**
     * Creates a new instance of <code>PedidoNoEncontradoException</code>
     * without detail message.
     */
    public PedidoNoEncontradoException() {
    }

    /**
     * Constructs an instance of <code>PedidoNoEncontradoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PedidoNoEncontradoException(String msg) {
        super(msg);
    }
}
