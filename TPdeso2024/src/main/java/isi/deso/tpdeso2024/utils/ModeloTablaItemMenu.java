/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.utils;

import isi.deso.tpdeso2024.controllers.ItemMenuController;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class ModeloTablaItemMenu extends AbstractTableModel {
    private List<String> nombreColumnas;
    private final ItemMenuController itemMenuController;
    private List<ItemMenuDTO> listaItemsMenu;

    public ModeloTablaItemMenu() {
        this.itemMenuController = ItemMenuController.getInstance();
        this.nombreColumnas = new ArrayList<>();
        this.listaItemsMenu = new ArrayList<>(itemMenuController.listar());
        
    }

    public void actualizarListaItemsMenu(List<ItemMenuDTO> nuevaLista) {
        this.listaItemsMenu = nuevaLista;
    }

    public void resetListaItemsMenu() {
        this.listaItemsMenu = new ArrayList<>(itemMenuController.listar());
    }

    public void setNombreColumnas(List<String> nombreColumnas) {
        this.nombreColumnas = nombreColumnas;
    }
    

    @Override
    public int getRowCount() {
        return this.listaItemsMenu.size();
    }

    @Override
    public int getColumnCount() {
        return this.nombreColumnas.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemMenuDTO itemMenu = listaItemsMenu.get(rowIndex);
        String tipo;
        if(itemMenu.EsComida()) tipo = "Comida";
        else tipo = "Bebida";
        return switch (columnIndex) {
            case 0 -> itemMenu.getId();
            case 1 -> tipo;
            case 2 -> itemMenu.getNombre();
            case 3 -> itemMenu.getDescripcion();
            case 4 -> itemMenu.getPrecio();
            case 5 -> itemMenu.getCategoria().getTipo();
            case 6 -> itemMenu.getVendedor().getId();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return this.nombreColumnas.get(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 6 -> Integer.class;
            case 1, 2, 3, 5 -> String.class;
            case 4 -> Double.class;
            default -> Object.class;
        };
    }
}
