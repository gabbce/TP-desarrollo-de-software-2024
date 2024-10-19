/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author augus
 */
public class PagoMercadoPago implements Pago{
    private String alias;
    private LocalDateTime fechaPago;

    @Override
    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    @Override
    public void setFechaPago(LocalDateTime fecha) {
        this.fechaPago = fecha;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    @Override
    public float precioFinal(float precioTotal) {
        return (float) (1.04*precioTotal);
    }

    @Override
    public void tomarDatos() {
       Scanner scan = new Scanner(System.in);
       String Aux;
       System.out.println("Ingrese el alias");
       Aux = scan.nextLine();
       this.setAlias(Aux);
    }

    @Override
    public PagoType getStrategyType() {
        return PagoType.MERCADO_PAGO;
    }
    
}
