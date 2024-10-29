/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.dtos;

import java.util.List;

/**
 *
 * @author augus
 */
public class VendedorDTO {

    public VendedorDTO(int id, String nombre, String direccion, CoordenadaDTO coordenada, List<ItemMenuDTO> itemsMenu) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenada = coordenada;
        this.itemsMenu = itemsMenu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CoordenadaDTO getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(CoordenadaDTO coordenada) {
        this.coordenada = coordenada;
    }

    public List<ItemMenuDTO> getItemsMenu() {
        return itemsMenu;
    }

    public void setItemsMenu(List<ItemMenuDTO> itemsMenu) {
        this.itemsMenu = itemsMenu;
    }
    private int id;
    private String nombre;
    private String direccion;
    private CoordenadaDTO coordenada;
    private List<ItemMenuDTO> itemsMenu; //null desde lado de itemMenu
    


}
