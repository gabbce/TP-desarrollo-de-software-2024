/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.excepciones.ItemNoEncontradoExcepcion;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author augus
 */

public class ItemMenuMEMORYDAO implements ItemMenuDAO {
//muy probable que no se use esta clase
    private List<ItemMenu> itemsPedido;
    // Constructor para inicializar la lista
    public ItemMenuMEMORYDAO(List<ItemMenu> itemsPedido) {
        this.itemsPedido = itemsPedido;
    }

    public ItemMenuMEMORYDAO() {
        this.itemsPedido = new LinkedList<>();
    }

    @Override
    public ArrayList<ItemMenu> Filtrado(String criterio, String valor) throws ItemNoEncontradoExcepcion {
        // Filtrado por criterio (ejemplo: por apto vegano, por categoría, etc.)
        List<ItemMenu> resultados = itemsPedido.stream()
            .filter(item -> {
                switch (criterio.toLowerCase()) {
                    case "vegano":
                        return item.aptoVegano();
                    case "comida":
                        return item.esComida();
                    case "bebida":
                        return item.esBebida();
                    case "categoria":
                        return item.getCategoria().getId() == Integer.parseInt(valor);

                    default:
                        return false;
                }
            })
            .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoExcepcion("No se encontraron ítems para el criterio: " + criterio + " con valor: " + valor);
        }

        return new ArrayList<>(resultados);
    }

    @Override
    public ArrayList<ItemMenu> OrdenarPorCriterios(Comparator<ItemMenu> criterio) throws ItemNoEncontradoExcepcion {
        List<ItemMenu> resultados = itemsPedido.stream()
            .sorted(criterio)
            .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoExcepcion("No hay ítems para ordenar.");
        }

        return new ArrayList<>(resultados);
    }

    @Override
    public ArrayList<ItemMenu> BuscarPorRangoDePrecios(float precioMin, float precioMax) throws ItemNoEncontradoExcepcion {
        List<ItemMenu> resultados = itemsPedido.stream()
            .filter(item -> item.getPrecio() >= precioMin && item.getPrecio() <= precioMax)
            .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoExcepcion("No se encontraron ítems en el rango de precios: " + precioMin + " - " + precioMax);
        }

        return new ArrayList<>(resultados);
    }

    @Override
    public ArrayList<ItemMenu> BuscarPorRestaurante(int idVendedor) throws ItemNoEncontradoExcepcion {
        List<ItemMenu> resultados = itemsPedido.stream()
            .filter(item -> {
                // Aquí asumo que ItemMenu tiene un método getVendedor() que devuelve el Vendedor asociado
                return item.getVendedor().getId() == idVendedor;  // Comparamos por ID del vendedor
            })
            .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoExcepcion("No se encontraron ítems para el vendedor con ID: " + idVendedor);
        }

        return new ArrayList<>(resultados);
    }

    @Override
    public boolean crear(ItemMenu it) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean actualizar(ItemMenu it) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemMenu> listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemMenu> buscar(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemMenu> buscar(Boolean esComida, String nombre, String idVendedor, String tipoCategoria) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ItemMenu buscarPorID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
