/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package isi.deso.tpdeso2024;

import java.util.*;
/**
 *
 * @author gabic
 */
public class TPdeso2024 {

    public static void main(String[] args) { 
       //Crear las clases Cliente, Vendedor y Coordenada.
        
        //Crear un método main que cree 3 instancias de vendedores y agregarlos a un arreglo.

        Vendedor v1 = new Vendedor(1,"Agustin","Lavaise 800", new Coordenada(1,1)),
                v2 = new Vendedor(2,"Bruno","Lavaise 801", new Coordenada(1,2)),
                v3 = new Vendedor(3,"Carlos","Lavaise 802", new Coordenada(2,1));

        List<Vendedor> vendedores = new ArrayList<>(Arrays.asList(v1,v2,v3));

        // Iterar sobre el arreglo para buscar vendedores por nombre o por id.

        for(Vendedor v:vendedores){
            Vendedor vAux;
            vAux = Vendedor.BuscadorVendedor(vendedores, v.getNombre());
            vAux.mostrar();
            vAux = Vendedor.BuscadorVendedor(vendedores,v.getId());
            vAux.mostrar();
        }

        // Eliminar un vendedor del arreglo.

        vendedores.remove(0);

        // Crear un método main que cree 3 instancias de Clientes y agregarlos a un arreglo.

        Cliente c1 = new Cliente(1,01,"pedro@gmail.com","Lavaise 800", new Coordenada(1,1)),
                c2 = new Cliente(2,02,"carlos@gmail.com","Lavaise 801", new Coordenada(1,2)),
                c3 = new Cliente(3,03,"pancracio@gmail.com","Lavaise 802", new Coordenada(2,1))
                ;

        List<Cliente> clientes = new ArrayList<>(Arrays.asList(c1,c2,c3));

        // Iterar sobre el arreglo para buscar Clientes por nombre o por id.

        for(Cliente c:clientes){
            Cliente cAux = Cliente.BuscadorClienteByCuit(clientes,c.getCuit());
            cAux.mostrar();
            cAux = Cliente.BuscadorClienteByID(clientes,c.getId());
            cAux.mostrar();
        }

        // Eliminar un cliente del arreglo.
        clientes.remove(1);
        
        
        
        //Funcion distancia
        System.out.println("Distancia entre " + 
                vendedores.get(0).getNombre() + " y Cliente con ID = " + 
                clientes.get(0).getId()+": "+
                vendedores.get(0).distancia(clientes.get(0))+
                " Kilometros");
        
        Bebida b1= new Bebida(11,"Coca Cola","Rica", 2500.00F,new Categoria(101,"Zarpada","Bebida"), 0.0F, 1500.0F);
        Plato p1 = new Plato(124,"Pizza","Con Muzzarella", 4550.00F,new Categoria(101,"Rico","Comida"),1000, true,false);
        System.out.println("Bebida: "+b1.esBebida()+" Plato: "+b1.esComida());
        
        v1.agregarItemMenu(p1);
        v1.agregarItemMenu(b1);
        v1.agregarItemMenu(p1);
        v1.agregarItemMenu(b1);
        
        System.out.println(v1.getItemsMenu());
        
        System.out.println(v1.getBebidas());
        System.out.println(v1.getComidas());
        
        
    }
    
    
    
}

