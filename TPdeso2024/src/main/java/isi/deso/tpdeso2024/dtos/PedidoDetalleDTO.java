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
    private ArrayList<ItemMenuDTO> items;

    public PedidoDetalleDTO(ArrayList<ItemMenuDTO> items) {
        this.items = items;
    }

    public ArrayList<ItemMenuDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemMenuDTO> items) {
        this.items = items;
    }
    
    
}
