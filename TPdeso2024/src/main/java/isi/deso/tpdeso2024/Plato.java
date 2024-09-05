/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

/**
 *
 * @author augus
 */

//supongo que Plato == Comida
public class Plato extends ItemMenu {
    private float peso;
    private float calorias;
    private boolean aptoCeliaco;
    private boolean aptoVegano;

    public Plato(float peso, float calorias, boolean aptoCeliaco, boolean aptoVegano, int id, String nombre, String descripcion, float precio, Categoria categoria) {
        super(id, nombre, descripcion, precio, categoria);
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

    public boolean isAptoVegano() {
        return aptoVegano;
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

    public boolean esAptoCeliaco() {
        return aptoCeliaco;
    }

    public void setAptoCeliaco(boolean aptoCeliaco) {
        this.aptoCeliaco = aptoCeliaco;
    }

    public boolean esVegano() {
        return aptoVegano;
    }

    public void setAptoVegetariano(boolean aptoVegano) {
        this.aptoVegano = aptoVegano;
    }
    
    public boolean esComida(){
        return true;
    }
    
    public boolean esBebida(){
        return false;
    }
    
    public float peso(){return (float) (1.10 * this.peso);}
    
}
