/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.dtos;

/**
 *
 * @author augus
 */
public class ItemMenuDTO {
    private int id; 
    private String nombre;
    private String descripcion;
    private boolean esComida;

    
    private CategoriaDTO categoria;
    private VendedorDTO vendedor; //null desde lado de vendedor
    private float precio;
    
    public int getId() {
        return id;
    }

    public ItemMenuDTO(int id, String nombre, String descripcion,  Boolean esComida, VendedorDTO vendedor, CategoriaDTO categoria, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esComida = esComida;
        this.precio = precio;
        this.categoria = categoria;
        this.vendedor = vendedor;
    }
    
    public ItemMenuDTO(int id, String nombre, String descripcion, Boolean esComida, String categoria, int vendedor, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esComida = esComida;
        this.precio = precio;
        this.categoria = new CategoriaDTO(categoria);
        this.vendedor = new VendedorDTO(vendedor, null, null, null);
    }
    
    public ItemMenuDTO(String nombre, String descripcion, Boolean esComida, String categoria, int vendedor, float precio) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esComida = esComida;
        this.precio = precio;
        this.categoria = new CategoriaDTO(categoria);
        this.vendedor = new VendedorDTO(vendedor, null, null, null);
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

    public String getDescripcion() {
        return descripcion;
    }

    public boolean EsComida() {
        return esComida;
    }

    public void setEsComida(boolean esComida) {
        this.esComida = esComida;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public VendedorDTO getVendedor() {
        return vendedor;
    }

    public void setVendedor(VendedorDTO vendedor) {
        this.vendedor = vendedor;
    }
}
