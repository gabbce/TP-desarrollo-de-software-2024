/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.util.List;

/**
 *
 * @author gabic
 */
public class Cliente {
    private int id;
    private int cuit;
    private String email;
    private String direccion;
    private Coordenada coordenadas;

    public Cliente(int id, int cuit, String email, String direccion, Coordenada coordenadas) {
        this.id = id;
        this.cuit = cuit;
        this.email = email;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCuit() {
        return cuit;
    }

    public void setCuit(int cuit) {
        this.cuit = cuit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Coordenada getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenada coordenadas) {
        this.coordenadas = coordenadas;
    }
    
    //Es la funcion por nombre, pero como cliente no tiene nombre, lo hacemos por cuit
    public static Cliente BuscadorClienteByCuit(List<Cliente> lista, Integer cuit){
        for(Cliente c: lista){
            if(cuit.equals(c.cuit)){
                return c;
            }
        }
        return null;
    }
    
    public static Cliente BuscadorClienteByID(List<Cliente> lista, Integer id){
        for(Cliente c: lista){
            if(id.equals(c.getId())){
                return c;
            }
        }
        return null;
    }
    
    
    public void mostrar(){
        
        System.out.println("ID: "+this.getId());
        System.out.println("Cuit: "+this.getCuit());
        System.out.println("Email: "+this.getEmail());
        System.out.println("Direccion: "+this.getDireccion());
        System.out.println("Coordenadas, longitud: "+this.getCoordenadas().getLongitud() + ", latitud:" + this.getCoordenadas().getLatitud());
        
    }
}
