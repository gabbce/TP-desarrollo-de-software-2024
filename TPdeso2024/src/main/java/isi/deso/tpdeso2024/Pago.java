/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.time.LocalDateTime;

/**
 *
 * @author Usuario
 */
public interface Pago{
    //Pago strategy
    public void setFechaPago(LocalDateTime fecha);
    public LocalDateTime getFechaPago();
    public float precioFinal(float precioTotal);
    public void tomarDatos();

    public PagoType getStrategyType();
}
