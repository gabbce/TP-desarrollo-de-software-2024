/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package isi.deso.tpdeso2024;

import java.awt.BorderLayout;
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
        Plato p1 = new Plato(1,124f,3000f,false,true,"Pizza rica","Pizza con piña",17999f,new Categoria(10, "Pizza", "Comida"));
        System.out.println(b1.getNombre()+" - es Bebida?: "+b1.esBebida()+" - es Plato?: "+b1.esComida());
        System.out.println(p1.getNombre()+" - es Bebida?: "+p1.esBebida()+" - es Plato?: "+p1.esComida());
        
        v1.agregarItemMenu(p1);
        v1.agregarItemMenu(b1);
        
        List<ItemMenu> listaItemsMenu = v1.getItemsMenu();
        List<Bebida> listaBebida = v1.getBebidas();
        List<Plato> listaComidas = v1.getComidas();
        
        System.out.println("Lista de ItemsMenu de v1:");
        for(ItemMenu item : listaItemsMenu){
            System.out.println(item.getNombre());
        }
        
        System.out.println("Lista de bebidas de v1:");
        for(Bebida bebida: listaBebida){
            System.out.println(bebida.getNombre());
        }
        
        System.out.println("Lista de platos de v1:");
        for(Plato comida: listaComidas){
            System.out.println(comida.getNombre());
        }
        
    }
    
    
    
}

