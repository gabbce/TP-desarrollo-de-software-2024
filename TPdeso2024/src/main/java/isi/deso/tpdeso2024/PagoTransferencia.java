/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.util.Scanner;

/**
 *
 * @author augus
 */
public class PagoTransferencia implements Pago{
    private String cbu;

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
    private String cuit;
    @Override
    public float precioFinal(float precioTotal) {
        return (float) (1.02*precioTotal);
    }

    @Override
    public void tomarDatos() {
        String cbuAux,cuitAux;
        Scanner scan = new Scanner(System.in);
        System.out.println("Ingrese el CBU y el CUIT");
        cbuAux = scan.nextLine();
        cuitAux = scan.nextLine();
        
        this.setCbu(cbuAux);
        this.setCuit(cuitAux);
    
    
    }
    @Override
    public PagoType getStrategyType() {
        return PagoType.TRANSFERENCIA;
    }
}
