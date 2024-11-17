/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.utils.modelosTablas;

import isi.deso.tpdeso2024.dtos.*;
import isi.deso.tpdeso2024.controllers.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class ModeloTablaVendedor extends AbstractTableModel {
    private List<String> nombreColumnas;
    private final VendedorController vendedorController;
    private List<VendedorDTO> listaVendedores;

    public ModeloTablaVendedor() {
        this.vendedorController = VendedorController.getInstance();
        this.nombreColumnas = new ArrayList<>();
        this.listaVendedores = new ArrayList<>(vendedorController.listar());
        
    }

    public void actualizarListaVendedores(List<VendedorDTO> nuevaLista) {
        this.listaVendedores = nuevaLista;
    }

    public void resetListaVendedores() {
        this.listaVendedores = new ArrayList<>(vendedorController.listar());
    }

    public void setNombreColumnas(List<String> nombreColumnas) {
        this.nombreColumnas = nombreColumnas;
    }
    

    @Override
    public int getRowCount() {
        return this.listaVendedores.size();
    }

    @Override
    public int getColumnCount() {
        return this.nombreColumnas.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VendedorDTO vendedor = listaVendedores.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> vendedor.getId();
            case 1 -> vendedor.getNombre();
            case 2 -> vendedor.getDireccion();
            case 3 -> "(" + String.valueOf(vendedor.getCoordenada().getLatitud()) + "; " + String.valueOf(vendedor.getCoordenada().getLongitud()) + ")";
            /*case 3 -> vendedor.getCoordenada().getLatitud();
            case 4 -> vendedor.getCoordenada().getLongitud();*/
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
            case 0 -> Integer.class;
            case 1, 2, 3 -> String.class;
            //case 3, 4 -> Double.class;
            default -> Object.class;
        };
    }
}

