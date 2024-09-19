/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.util.ArrayList;

/**
 *
 * @author augus
 */
public class ItemsPedidoMemory implements ItemsPedidoDao{ //usando el API de Streams
//acá vas a cargar una lista de los elementos instancias de la clase ItemsPedido
    
    @Override
    public ArrayList<ItemMenu> Filtrado() throws ItemNoEncontradoExcepcion {
        //es según a sus criterios, pueden filtrar por nombre de cliente y retornar una lista de Items pedidos; 
        //pueden filtrar por vendedor; por cuit de vendedor, etc
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ItemMenu> OrdenarPorCriterios() throws ItemNoEncontradoExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ItemMenu> BuscarPorRangoDePrecios() throws ItemNoEncontradoExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ItemMenu> BuscarPorRestaurante() throws ItemNoEncontradoExcepcion {
        //por id o nombre del vendedor.
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
