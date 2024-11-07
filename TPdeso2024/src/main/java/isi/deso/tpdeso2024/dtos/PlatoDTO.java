/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.dtos;

/**
 *
 * @author augus
 */

//supongo que Plato == Comida
public class PlatoDTO extends ItemMenuDTO {
    private float peso;
    private float calorias;
    private boolean aptoCeliaco;
    private boolean aptoVegano;

    public PlatoDTO(int id,float peso, float calorias, boolean aptoCeliaco, boolean aptoVegano, String nombre, String descripcion, float precio, CategoriaDTO categoria, VendedorDTO vendedor) {
       // super(id, nombre, descripcion, precio, categoria);
       super(id,nombre,descripcion,precio,categoria,vendedor); 
       this.peso = peso;
        this.calorias = calorias;
        this.aptoCeliaco = aptoCeliaco;
        this.aptoVegano = aptoVegano;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setAptoVegano(boolean aptoVegano) {
        this.aptoVegano = aptoVegano;
    }
    
    public float getCalorias() {
        return calorias;
    }

    public void setCalorias(float calorias) {
        this.calorias = calorias;
    }

    public void setAptoCeliaco(boolean aptoCeliaco) {
        this.aptoCeliaco = aptoCeliaco;
    }
    
    public boolean esComida(){
        return true;
    }
    
    public boolean esBebida(){
        return false;
    }
    
    public boolean aptoVegano() {
        return aptoVegano;
    }
    
    public boolean aptoCeliaco() {
        return aptoCeliaco;
    }
    
}
