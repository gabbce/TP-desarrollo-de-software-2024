/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

/**
 *
 * @author augus
 */
public class Bebida extends ItemMenu{
    private float graduacionAlcoholica;
    private float tam; //mililitros?

    
    public Bebida(int id, String nombre, String descripcion, float precio,Categoria categoria, float graduacionAlcoholica, float tam){
    
        super(id,nombre,descripcion,precio,categoria);
        this.graduacionAlcoholica=graduacionAlcoholica;
        this.tam=tam;
    }
    
    public float getGraduacionAlcoholica() {
        return graduacionAlcoholica;
    }

    public void setGraduacionAlcoholica(float graduacionAlcoholica) {
        this.graduacionAlcoholica = graduacionAlcoholica;
    }

    public float getTam() {
        return tam;
    }

    public void setTam(float tam) {
        this.tam = tam;
    }
    
    public boolean esComida(){
        return false;
    }
    
    
    public boolean esBebida(){
        return true;
    }
    
    
}
