/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.util.ArrayList;

/**
 *
 * @author augus
 */
public class PedidoDetalle {
    private ArrayList<ItemMenu> items;
    private int cantidad;
    
    public float peso(){
        float suma = 0;
        for(ItemMenu i: items){
            suma+=i.peso();
        
        }
        return suma;
    }

    public ArrayList<ItemMenu> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemMenu> items) {
        this.items = items;
    }

    public PedidoDetalle(ArrayList<ItemMenu> items, int cantidad) {
        this.items = items;
        this.cantidad = cantidad;
    }
    

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
