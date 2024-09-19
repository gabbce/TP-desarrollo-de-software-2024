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
public interface ItemsPedidoDao {

    public ArrayList<ItemMenu> Filtrado()throws ItemNoEncontradoExcepcion;
    public ArrayList<ItemMenu> OrdenarPorCriterios()throws ItemNoEncontradoExcepcion;
    public ArrayList<ItemMenu> BuscarPorRangoDePrecios()throws ItemNoEncontradoExcepcion;
    public ArrayList<ItemMenu> BuscarPorRestaurante()throws ItemNoEncontradoExcepcion;
}
