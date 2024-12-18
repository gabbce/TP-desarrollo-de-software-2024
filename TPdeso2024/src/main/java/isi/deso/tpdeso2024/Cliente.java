/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;

/**
 *
 * @author gabic
 */
public class Cliente{
    private int id;
    private String cuit;
    private String email;
    private String direccion;
    private Coordenada coordenadas;

    public Cliente(int id, String cuit, String email, String direccion, Coordenada coordenadas) {
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

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
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
    
    
    public Pedido crearPedido(ArrayList<ItemMenu> listaItems) throws IOException{ //el argumento seria el carro de compra 
        if(listaItems.isEmpty()){
            System.out.println("Error: lista vacia");
            return null;
        }
        //deben ser todos del mismo vendedor
        Vendedor vend = listaItems.get(0).getVendedor();
        for(ItemMenu it:listaItems)if(it.getVendedor().getId() != vend.getId()){
            System.out.println("Error: los items deben ser del mismo vendedor");
            return null;
        }
        
        float precioTotal = 0;
        for(ItemMenu it:listaItems)precioTotal+=it.getPrecio();
        
        System.out.println("Elija la forma de pago");
     
        /*
        creamos ambos objetos
        para mostrar opciones, .precio() de cada uno
        
        */
        
        PagoMercadoPago pMP = new PagoMercadoPago(); 
        PagoTransferencia pTr = new PagoTransferencia(); 
        
        float precioMP,precioTransferencia;
        precioMP  = pMP.precioFinal(precioTotal);
        precioTransferencia = pTr.precioFinal(precioTotal);
        
        System.out.println("1 - MercadoPago. Precio: "+precioMP);
        System.out.println("2 - Transferencia. Precio: "+precioTransferencia);
        Scanner scan = new Scanner(System.in);
        int formaDePago = scan.nextInt();
        while(formaDePago<1 || formaDePago>2){
            System.out.println("Elija una opcion valida");
            formaDePago = scan.nextInt();
            }
        
        
        Pago pagoSeleccionado = pMP; //A netbeans no le gusta que no inicialice
        Float precioFinal = 0f;
        switch (formaDePago){
            case 1 -> {pagoSeleccionado = pMP; precioFinal = precioMP;}
            case 2 -> {pagoSeleccionado = pTr; precioFinal = precioTransferencia;}
        }
        
        pagoSeleccionado.tomarDatos();
        Pedido p = new Pedido(/*new PedidoDetalle(listaItems),*/ pagoSeleccionado, this, precioFinal);
        for(ItemMenu i : listaItems){
            PedidoDetalle pd = new PedidoDetalle(p, i, 1);
        }
        
        p.setEstado(EstadoPedido.RECIBIDO);
        //aca tendria que ir a DB
        
        return p;
    }
    
    public void update(Pedido pedido){
        if(pedido.getEstado().equals(EstadoPedido.EN_ENVIO)){
            
            pedido.getPago().setFechaPago(LocalDateTime.now());
        }
        System.out.println("El pedido ha cambiado al estado " + pedido.getEstado());
    }
    
    public void mostrar(){
        
        System.out.println("ID: "+this.getId());
        System.out.println("Cuit: "+this.getCuit());
        System.out.println("Email: "+this.getEmail());
        System.out.println("Direccion: "+this.getDireccion());
        System.out.println("Coordenadas, longitud: "+this.getCoordenadas().getLongitud() + ", latitud:" + this.getCoordenadas().getLatitud());
        
    }
}
