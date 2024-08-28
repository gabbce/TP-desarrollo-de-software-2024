/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;


/**
 *
 * @author augus
 */
public class Vendedor {
    private int id;
    private String nombre;
    private String direccion;
    private Coordenada coordenada;

    public Vendedor() {
    }

    public Vendedor(int id, String nombre, String direccion, Coordenada coordenada) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenada = coordenada;
    }

    public Vendedor(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCoordenadas(Coordenada coordenada) {
        this.coordenada = coordenada;
    }
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Coordenada getCoordenadas() {
        return coordenada;
    }
    
    public double distancia(Cliente c){
        
        
        
        double radioTierra = 6371.0;
        
        double lat1 = Math.toRadians(this.coordenada.getLatitud());
        double long1 = Math.toRadians(this.coordenada.getLongitud());
       
        double lat2 = Math.toRadians(c.getCoordenadas().getLatitud());
        double long2 = Math.toRadians(c.getCoordenadas().getLongitud());
        
        //Consigo las deltas para la formula final
        
        double deltaLat = lat2-lat1;
        double deltaLong = long2-long1;
        
        //Aplico formula 
        
        double a = Math.sin(deltaLat / 2) *  Math.sin(deltaLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLong / 2) * Math.sin(deltaLong / 2);
                
        double arc = 2 * Math.atan(Math.sqrt(a));
        
        return radioTierra*arc;
        
    }
    
}
