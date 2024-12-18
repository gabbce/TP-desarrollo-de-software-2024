/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.dtos;

import isi.deso.tpdeso2024.PagoType;
import java.time.LocalDateTime;

/**
 *
 * @author gabic
 */
public class PagoDTO {
    Integer id;
    Integer id_pedido;
    PagoType pagoType;
    String alias_cbu;
    String cuit;
    LocalDateTime fechaPago;

    public PagoDTO(Integer id, Integer id_pedido, PagoType pagoType, String alias_cbu, String cuit, LocalDateTime fechaPago) {
        this.id = id;
        this.id_pedido = id_pedido;
        this.pagoType = pagoType;
        this.alias_cbu = alias_cbu;
        this.cuit = cuit;
        this.fechaPago = fechaPago;
    }


    public PagoDTO(PagoType pagoType, String alias_cbu, String cuit, LocalDateTime fechaPago) {
        
        this.pagoType = pagoType;
        this.alias_cbu = alias_cbu;
        this.fechaPago = fechaPago;
        this.cuit = cuit;
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

    public PagoType getPagoType() {
        return pagoType;
    }

    public void setPagoType(PagoType pagoType) {
        this.pagoType = pagoType;
    }

    public String getAlias_cbu() {
        return alias_cbu;
    }

    public void setAlias_cbu(String alias_cbu) {
        this.alias_cbu = alias_cbu;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    
}
