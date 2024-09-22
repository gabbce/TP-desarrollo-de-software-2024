/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.util.*;

/**
 *
 * @author augus
 */
public interface ItemsPedidoDao {

    public ArrayList<ItemMenu> Filtrado(String criterio, String valor)throws ItemNoEncontradoExcepcion;
    public ArrayList<ItemMenu> OrdenarPorCriterios(Comparator<ItemMenu> criterio)throws ItemNoEncontradoExcepcion;
    public ArrayList<ItemMenu> BuscarPorRangoDePrecios(float precioMin, float precioMax)throws ItemNoEncontradoExcepcion;
    public ArrayList<ItemMenu> BuscarPorRestaurante(int idVendedor)throws ItemNoEncontradoExcepcion;
}
