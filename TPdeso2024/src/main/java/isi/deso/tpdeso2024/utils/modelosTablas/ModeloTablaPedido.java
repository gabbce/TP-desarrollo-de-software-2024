/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.utils.modelosTablas;

import isi.deso.tpdeso2024.EstadoPedido;
import isi.deso.tpdeso2024.PagoType;
import isi.deso.tpdeso2024.controllers.PedidoController;
import isi.deso.tpdeso2024.dtos.PedidoDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class ModeloTablaPedido extends AbstractTableModel {
    private List<String> nombreColumnas;
    private final PedidoController pedidoController;
    private List<PedidoDTO> listaPedidos;

    public ModeloTablaPedido() {
        this.pedidoController = PedidoController.getInstance();
        this.nombreColumnas = new ArrayList<>();
        this.listaPedidos = new ArrayList<>(pedidoController.listar());
        
    }

    public void actualizarListaPedidos(List<PedidoDTO> nuevaLista) {
        this.listaPedidos = nuevaLista;
    }

    public void resetListaPedidos() {
        this.listaPedidos = new ArrayList<>(pedidoController.listar());
    }

    public void setNombreColumnas(List<String> nombreColumnas) {
        this.nombreColumnas = nombreColumnas;
    }
    
    public PedidoDTO getRow(int fila){
        return listaPedidos.get(fila);
    }

    @Override
    public int getRowCount() {
        return this.listaPedidos.size();
    }

    @Override
    public int getColumnCount() {
        return this.nombreColumnas.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PedidoDTO pedido = listaPedidos.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> pedido.getId();
            case 1 -> pedido.getEstado();
            case 2 -> pedido.getPrecioFinal();
            case 3 -> pedido.getPago().getPagoType();
            case 4 -> pedido.getPago().getAlias_cbu();
            case 5 -> pedido.getPago().getFechaPago();
            case 6 -> pedido.getCliente().getId();
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
            case 1 -> EstadoPedido.class;
            case 2 -> Float.class;
            case 3 -> PagoType.class;
            case 4 -> String.class;
            case 5 -> LocalDateTime.class;
            
            default -> Object.class;
        };
    }
}
