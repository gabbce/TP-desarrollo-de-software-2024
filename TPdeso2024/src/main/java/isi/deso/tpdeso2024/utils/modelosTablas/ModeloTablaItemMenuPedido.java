/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.utils.modelosTablas;

import isi.deso.tpdeso2024.controllers.ItemMenuController;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.dtos.PedidoDetalleDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class ModeloTablaItemMenuPedido extends AbstractTableModel {
    private List<String> nombreColumnas;
    private final ItemMenuController itemMenuController;
    private List<ItemMenuDTO> listaItemsMenuPedido;
    private Map<Integer, Integer> cantidades; // Mapa para almacenar cantidades por ID
    private Integer vendedorSeleccionado; // ID del vendedor del primer ítem seleccionado

    public ModeloTablaItemMenuPedido() {
        this.itemMenuController = ItemMenuController.getInstance();
        this.nombreColumnas = new ArrayList<>(List.of("ID", "Tipo", "Nombre", "Descripción", "Precio", "Categoría", "Vendedor", "Cantidad"));
        this.listaItemsMenuPedido = new ArrayList<>(itemMenuController.listar());
        this.cantidades = new HashMap<>(); // Inicializa el mapa vacío
        this.vendedorSeleccionado = null;

        // Inicializar cantidades para los ítems actuales
        listaItemsMenuPedido.forEach(item -> cantidades.put(item.getId(), 0));
    }
    
    public void reiniciarCantidades() {
    // Reinicia todas las cantidades a 0
    cantidades.replaceAll((id, cantidad) -> 0);

    // Resetea el vendedor seleccionado
    vendedorSeleccionado = null;

    // Notifica a la tabla que se han actualizado los datos
    fireTableDataChanged();
    }
    

    @Override
    public int getRowCount() {
        return this.listaItemsMenuPedido.size();
    }

    @Override
    public int getColumnCount() {
        return this.nombreColumnas.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemMenuDTO itemMenu = listaItemsMenuPedido.get(rowIndex);
        String tipo = itemMenu.EsComida() ? "Comida" : "Bebida";

        return switch (columnIndex) {
            case 0 -> itemMenu.getId();
            case 1 -> tipo;
            case 2 -> itemMenu.getNombre();
            case 3 -> itemMenu.getDescripcion();
            case 4 -> itemMenu.getPrecio();
            case 5 -> itemMenu.getCategoria().getTipo();
            case 6 -> itemMenu.getVendedor().getId();
            case 7 -> cantidades.getOrDefault(itemMenu.getId(), 0); // Recupera la cantidad del mapa
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 7; // Solo la columna de Cantidad es editable
    }

    /*@Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 7) {
            try {
                int cantidad = Integer.parseInt(aValue.toString());
                if (cantidad >= 0) {
                    ItemMenuDTO itemMenu = listaItemsMenuPedido.get(rowIndex);
                    cantidades.put(itemMenu.getId(), cantidad); // Actualiza el mapa con la nueva cantidad
                    fireTableCellUpdated(rowIndex, columnIndex);
                }
            } catch (NumberFormatException e) {
                // Manejar valores no válidos
            }
        }
    }*/
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 7) {
            try {
                int cantidad = Integer.parseInt(aValue.toString());
                if (cantidad >= 0) {
                    ItemMenuDTO itemMenu = listaItemsMenuPedido.get(rowIndex);
                    int vendedorActual = itemMenu.getVendedor().getId();

                    // Validar que todos los ítems sean del mismo vendedor
                    if (cantidad > 0) {
                        if (vendedorSeleccionado == null) {
                            vendedorSeleccionado = vendedorActual; // Primer vendedor seleccionado
                        } else if (!vendedorSeleccionado.equals(vendedorActual)) {
                            // Mostrar error o rechazar selección
                            JOptionPane.showMessageDialog(null,
                                    "Todos los ítems seleccionados deben ser del mismo vendedor.",
                                    "Error de selección",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Si la cantidad es 0 y se está eliminando el último ítem, resetear vendedorSeleccionado
                    if (cantidad == 0 && cantidades.get(itemMenu.getId()) > 0) {
                        cantidades.put(itemMenu.getId(), 0);
                        if (cantidades.values().stream().allMatch(c -> c == 0)) {
                            vendedorSeleccionado = null; // Resetea el vendedor seleccionado
                        }
                    } else {
                        cantidades.put(itemMenu.getId(), cantidad);
                    }

                    fireTableCellUpdated(rowIndex, columnIndex);
                }
            } catch (NumberFormatException e) {
                // Manejar valores no válidos
                JOptionPane.showMessageDialog(null,
                        "Por favor, ingrese un valor numérico válido para la cantidad.",
                        "Error de entrada",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
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
            case 7 -> Integer.class; // Cantidad como entero
            default -> Object.class;
        };
    }

    // Método para filtrar ítems con cantidad > 0
    public List<ItemMenuDTO> getItemsSeleccionados() {
        List<ItemMenuDTO> seleccionados = new ArrayList<>();
        for (ItemMenuDTO item : listaItemsMenuPedido) {
            if (cantidades.getOrDefault(item.getId(), 0) > 0) {
                seleccionados.add(item);
            }
        }
        return seleccionados;
    }

    // Método para actualizar la lista sin perder las cantidades
    public void actualizarListaItemsMenu(List<ItemMenuDTO> nuevaLista) {
        this.listaItemsMenuPedido = nuevaLista;
        // Añade los nuevos ítems al mapa y mantiene las cantidades existentes
        listaItemsMenuPedido.forEach(item -> cantidades.putIfAbsent(item.getId(), 0));
        fireTableDataChanged();
    }
    
    public void actualizarListaItemsMenuYCantidades(List<PedidoDetalleDTO> listaDetalle) {
        
        listaItemsMenuPedido = new ArrayList();
        // Añade los nuevos ítems al mapa y mantiene las cantidades existentes
        listaDetalle.forEach(detalle -> listaItemsMenuPedido.add(detalle.getItem()));
        listaDetalle.forEach(detalle -> cantidades.put(detalle.getItem().getId(), detalle.getCantidad()));
        
        fireTableDataChanged();
    }
    
    public Map<Integer, Integer> getCantidadesSeleccionadas() {
    return cantidades.entrySet().stream()
            .filter(entry -> entry.getValue() > 0)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


}
