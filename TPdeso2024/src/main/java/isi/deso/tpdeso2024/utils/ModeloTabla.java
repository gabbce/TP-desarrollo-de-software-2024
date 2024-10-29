/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.utils;

import isi.deso.tpdeso2024.dtos.*;
import isi.deso.tpdeso2024.controllers.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class ModeloTabla extends AbstractTableModel{
    private List<String> nombreColumnas;
    private VendedorController vendedorController;

    public ModeloTabla() {
        this.nombreColumnas = new ArrayList<>();        
    }

    public ModeloTabla(VendedorController vendedorController) {
        this.vendedorController = vendedorController;
    }

    public VendedorController getVendedorController() {
        return vendedorController;
    }

    public void setVendedorController(VendedorController vendedorController) {
        this.vendedorController = vendedorController;
    }
    
    public List<String> getNombreColumnas() {
        return nombreColumnas;
    }

    public void setNombreColumnas(List<String> nombreColumnas) {
        this.nombreColumnas = nombreColumnas;
    }

    public List<VendedorDTO> getListaVendedors() {
        return this.vendedorController.listar();
    }    
    

    @Override
    public int getRowCount() {
        return this.vendedorController.listar().size();
    }

    @Override
    public int getColumnCount() {
        return this.nombreColumnas.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<VendedorDTO> listaVendedores = vendedorController.listar();
        return switch (columnIndex) {
            case 0 -> listaVendedores.get(rowIndex).getNombre(); //retorna nombre 
            case 1 -> listaVendedores.get(rowIndex).getDireccion(); // retorna apellido
            case 2 -> listaVendedores.get(rowIndex).getCoordenada().getLatitud(); // retorna dni
            case 3 -> listaVendedores.get(rowIndex).getCoordenada().getLongitud(); // retorna dni
            default -> null;
        };
    }
    
    @Override
    public String getColumnName(int column) {
        return this.getNombreColumnas().get(column);
    }
}
