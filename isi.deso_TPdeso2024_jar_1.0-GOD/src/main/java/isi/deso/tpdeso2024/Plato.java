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
    private float calorias;
    private boolean aptoCeliaco;
    private boolean aptoVegano;

   
   public Plato(int id, String nombre, String descripcion, float precio,Categoria categoria, float calorias, boolean aptoCeliaco,boolean aptoVegano ){
    
        super(id,nombre,descripcion,precio,categoria);
        this.calorias=calorias;
        this.aptoCeliaco=aptoCeliaco;
        this.aptoVegano=aptoVegano;
        
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
    
}
