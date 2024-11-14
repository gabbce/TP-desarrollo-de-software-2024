/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.utils;

import isi.deso.tpdeso2024.controllers.ClienteController;
import isi.deso.tpdeso2024.dtos.ClienteDTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class ModeloTablaCliente extends AbstractTableModel {
    private List<String> nombreColumnas;
    private final ClienteController clienteController;
    private List<ClienteDTO> listaClientes;

    public ModeloTablaCliente() {
        this.clienteController = ClienteController.getInstance();
        this.nombreColumnas = new ArrayList<>();
        this.listaClientes = new ArrayList<>(clienteController.listar());
        
    }

    public void actualizarListaClientes(List<ClienteDTO> nuevaLista) {
        this.listaClientes = nuevaLista;
    }

    public void resetListaClientes() {
        this.listaClientes = new ArrayList<>(clienteController.listar());
    }

    public void setNombreColumnas(List<String> nombreColumnas) {
        this.nombreColumnas = nombreColumnas;
    }
    

    @Override
    public int getRowCount() {
        return this.listaClientes.size();
    }

    @Override
    public int getColumnCount() {
        return this.nombreColumnas.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClienteDTO cliente = listaClientes.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> cliente.getId();
            case 1 -> cliente.getCuit();
            case 2 -> cliente.getEmail();
            case 3 -> cliente.getDireccion();
            case 4 -> "(" + String.valueOf(cliente.getCoordenadas().getLatitud()) + "; " + String.valueOf(cliente.getCoordenadas().getLongitud()) + ")";
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
            case 0, 1 -> Integer.class;
            case 2, 3, 4 -> String.class;
            default -> Object.class;
        };
    }
}
