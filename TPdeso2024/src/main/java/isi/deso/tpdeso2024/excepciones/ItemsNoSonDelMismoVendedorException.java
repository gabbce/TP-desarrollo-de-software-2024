/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package isi.deso.tpdeso2024.excepciones;

/**
 *
 * @author augus
 */
public class ItemsNoSonDelMismoVendedorException extends Exception{

    /**
     * Creates a new instance of
     * <code>ItemsNoSonDelMismoVendedorException</code> without detail message.
     */
    public ItemsNoSonDelMismoVendedorException() {
    }

    /**
     * Constructs an instance of
     * <code>ItemsNoSonDelMismoVendedorException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ItemsNoSonDelMismoVendedorException(String msg) {
        super(msg);
    }
}
