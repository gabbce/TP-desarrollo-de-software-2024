/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.excepciones.ItemNoEncontradoExcepcion;
import java.util.*;

/**
 *
 * @author augus
 */
public interface ItemMenuDAO {

    public ArrayList<ItemMenu> Filtrado(String criterio, String valor)throws ItemNoEncontradoExcepcion;
    public ArrayList<ItemMenu> OrdenarPorCriterios(Comparator<ItemMenu> criterio)throws ItemNoEncontradoExcepcion;
    public ArrayList<ItemMenu> BuscarPorRangoDePrecios(float precioMin, float precioMax)throws ItemNoEncontradoExcepcion;
    public ArrayList<ItemMenu> BuscarPorRestaurante(int idVendedor)throws ItemNoEncontradoExcepcion;
    public ItemMenu buscarPorID(int id) throws ItemNoEncontradoExcepcion;
    
    public List<ItemMenu> buscar(Boolean esComida, String nombre, String idVendedor, String tipoCategoria);
    
    public boolean crear(ItemMenu it);
    public boolean eliminar(int id);
    public boolean actualizar(ItemMenu it);
    public List<ItemMenu> listar();
    public List<ItemMenu> buscar(String nombre) ;

}
