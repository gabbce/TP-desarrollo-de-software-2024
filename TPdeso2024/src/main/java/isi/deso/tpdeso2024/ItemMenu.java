/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

/**
 *
 * @author augus
 */
public abstract class ItemMenu {
    private int id; 
    private String nombre;
    private String descripcion;
    
    private Vendedor vendedor;
    private Categoria categoria;
    private float precio;

    public ItemMenu(int id, String nombre, String descripcion, Vendedor vendedor, Categoria categoria, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.vendedor = vendedor;
        this.categoria = categoria;
        this.precio = precio;
    }
    

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
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

    public String getDescripcion() {
        return descripcion;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    abstract public boolean esComida();
    
    abstract public boolean esBebida();
    
    abstract public boolean aptoVegano();
    
    abstract public float peso();
    
     
    
}
