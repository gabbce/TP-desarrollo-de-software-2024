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
        v3 = new Vendedor(3,"Carlos","Lavaise 802", new Coordenada(2,1))
        ;

List<Vendedor> vendedores = new ArrayList<>(v1,v2,v3);

// Iterar sobre el arreglo para buscar vendedores por nombre o por id.

for(Vendedor v:vendedores){
    Vendedor vAux = Vendedor.PorNombre(vendedores,v.getNombre());
    vAux.mostrar();
    vAux = Vendedor.PorId(vendedores,v.getId());
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
    Cliente cAux = Cliente.PorCuit(clientes,c.getCuit());
    cAux.mostrar();
    cAux = Cliente.PorId(clientes,c.getId());
    cAux.mostrar();
}

// Eliminar un cliente del arreglo.
clientes.remove(1);
    }
}
