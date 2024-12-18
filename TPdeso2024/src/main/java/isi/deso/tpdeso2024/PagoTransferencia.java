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
public class PagoTransferencia implements Pago{
    private Integer id;
    private Integer id_pedido;
    private String cbu;
    private String cuit;
    private LocalDateTime fechaPago;

    

    public PagoTransferencia(Integer id, Integer id_pedido, String cbu, String cuit, LocalDateTime fechaPago) {
        this.id = id;
        this.id_pedido = id_pedido;
        this.cbu = cbu;
        this.cuit = cuit;
        this.fechaPago = fechaPago;
    }

    public PagoTransferencia() {
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    @Override
    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    @Override
    public void setFechaPago(LocalDateTime fecha) {
        this.fechaPago = fecha;
    }

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
    
    @Override
    public float precioFinal(float precioTotal) {
        return (float) (1.02*precioTotal);
    }

    @Override
    public void tomarDatos() {
        String cbuAux,cuitAux;
        Scanner scan = new Scanner(System.in);
        System.out.println("Ingrese su CBU y CUIT");
        System.out.print("CBU: ");
        cbuAux = scan.nextLine();
        System.out.print("CUIT: ");
        cuitAux = scan.nextLine();
        
        this.setCbu(cbuAux);
        this.setCuit(cuitAux);
    }
    
    @Override
    public PagoType getStrategyType() {
        return PagoType.TRANSFERENCIA;
    }
}
