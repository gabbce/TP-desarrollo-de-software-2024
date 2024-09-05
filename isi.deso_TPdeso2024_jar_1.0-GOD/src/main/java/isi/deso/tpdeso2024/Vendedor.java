/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author augus
 */
public class Vendedor {
    private int id;
    private String nombre;
    private String direccion;
    private Coordenada coordenada;
    private List<ItemMenu> itemsMenu;

    public Vendedor() {
        this.itemsMenu = new ArrayList<>();
    }

    public Vendedor(int id, String nombre, String direccion, Coordenada coordenada) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenada = coordenada;
        this.itemsMenu = new ArrayList<>();
    }

    public Vendedor(int id) {
        this.id = id;
        this.itemsMenu = new ArrayList<>(); // Inicializamos la lista
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
    
    public List<ItemMenu> getItemsMenu() {
        return itemsMenu;
    }
    
    public void agregarItemMenu(ItemMenu item) {
        this.itemsMenu.add(item);
    }
    
    public static Vendedor BuscadorVendedor(List<Vendedor> lista, String vendedor){
        
        for(Vendedor v: lista){
            if(vendedor.equals(v.getNombre())){
                return v;
            }
        }
        return null;
        
    }
    
    public static Vendedor BuscadorVendedor(List<Vendedor> lista, Integer vendedor){
        
        for(Vendedor v: lista){
            if(vendedor.equals(v.getId())){
                return v;
            }
        }
        return null;
        
    }
    
    public void mostrar(){
        System.out.println("ID: "+this.getId());
        System.out.println("Nombre: "+this.getNombre());
        System.out.println("Direccion : "+this.getDireccion());
        System.out.println("Coordenadas, longitud: "+this.getCoordenadas().getLongitud() + ", latitud:" + this.getCoordenadas().getLatitud());
    }
    
    public List<Bebida> getBebidas() {
        List<Bebida> bebidas = new ArrayList<>();
        for (ItemMenu item : itemsMenu) {
            if (item.esBebida()) {
                bebidas.add((Bebida) item);
            }
        }
        return bebidas;
    }
    
    public List<Plato> getComidas() {
        List<Plato> comidas = new ArrayList<>();
        for (ItemMenu item : itemsMenu) {
            if (item.esComida()) {
                comidas.add((Plato) item);
            }
        }
        return comidas;
    }
    
     
    public List<Plato> getComidasVeganas() {
        List<Plato> comidasVeganas = new ArrayList<>();
        for (ItemMenu item : itemsMenu) {
            if (item instanceof Plato && ((Plato) item).esVegano()) {
                comidasVeganas.add((Plato) item);
            }
        }
        return comidasVeganas;
    }
    public List<Bebida> getBebidasSinAlcohol() {
        List<Bebida> bebidasSinAlcohol = new ArrayList<>();
        for (ItemMenu item : itemsMenu) {
            if (item instanceof Bebida && ((Bebida) item).getGraduacionAlcoholica() == 0.0F) {
                bebidasSinAlcohol.add((Bebida) item);
            }
        }
        return bebidasSinAlcohol;
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
