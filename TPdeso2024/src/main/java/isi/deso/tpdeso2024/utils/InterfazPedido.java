/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.utils;

import isi.deso.tpdeso2024.EstadoPedido;
import isi.deso.tpdeso2024.Pago;
import isi.deso.tpdeso2024.PagoType;
import isi.deso.tpdeso2024.controllers.PedidoController;
import isi.deso.tpdeso2024.dtos.PedidoDTO;
import isi.deso.tpdeso2024.dtos.CoordenadaDTO;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class InterfazPedido implements InformacionInterfaz{
    private final PedidoController pedidoController = PedidoController.getInstance();
    
 
    
    //JPanel panel_info;
    JDialog modal;
    JDialog modal_eliminar;
    
    ModeloTablaPedido modeloPedido;
    
    JTable tabla;
    JTextField text_field_cliente;
    JRadioButton rbutton_mp;
    JRadioButton rbutton_transferencia;
    JLabel titulo_modal;
    JButton boton_confirmar;
    JLabel titulo_modal_eliminar;
    JButton boton_confirmar_eliminar;
    JButton boton_crear;
    JLabel panel_info_titulo;
    //JTextField text_field_buscar;
    JLabel label_buscar;

    public InterfazPedido(JDialog modal, JDialog modal_eliminar, JTable tabla, JTextField text_field_cliente, JRadioButton rbutton_mp, JRadioButton rbutton_transferencia, JLabel titulo_modal, JButton boton_confirmar, JLabel titulo_modal_eliminar, JButton boton_confirmar_eliminar, JButton boton_crear, JLabel panel_info_titulo, JLabel label_buscar) {
        this.modal = modal;
        this.modal_eliminar = modal_eliminar;
        this.tabla = tabla;
        this.text_field_cliente = text_field_cliente;
        this.rbutton_mp = rbutton_mp;
        this.rbutton_transferencia = rbutton_transferencia;
        this.titulo_modal = titulo_modal;
        this.boton_confirmar = boton_confirmar;
        this.titulo_modal_eliminar = titulo_modal_eliminar;
        this.boton_confirmar_eliminar = boton_confirmar_eliminar;
        this.boton_crear = boton_crear;
        this.panel_info_titulo = panel_info_titulo;
        this.label_buscar = label_buscar;
        
        modeloPedido = new ModeloTablaPedido();
        modeloPedido.setNombreColumnas(List.of("Id", "Tipo Pago", "Estado", "Id cliente"));
    }
    

    
    
    //@Override
    public void cambiarPanel() {
        boton_crear.setText("Agregar pedido");
        panel_info_titulo.setText("Lista de pedidos");
        label_buscar.setText("buscar por nombre:");
        
        tabla.setModel(modeloPedido);
        ((AbstractTableModel)tabla.getModel()).fireTableChanged(null);
    }

    @Override
    public void mostrarEliminar(int filaSeleccionada) {
        int id = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        titulo_modal_eliminar.setText("Se eliminará el pedido " + id);
        boton_confirmar_eliminar.putClientProperty("id", id);
    }
    
    @Override
    public void eliminar(int id){
        try {
            pedidoController.eliminar(id);
            ((ModeloTablaPedido) tabla.getModel()).resetListaPedidos();
            ((AbstractTableModel)tabla.getModel()).fireTableChanged(null);
            
             JOptionPane.showMessageDialog(modal_eliminar, "Eliminado exitosamente.");
             
        } catch(HeadlessException e){
             JOptionPane.showMessageDialog(modal_eliminar, "No se pudo eliminar.");
             
        }
        
    }

    @Override
    public void mostrarEditar(int filaSeleccionada) {
        
        int id = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        PagoType pago = (PagoType) tabla.getValueAt(filaSeleccionada, 1);
        //EstadoPedido estado = (EstadoPedido) tabla.getValueAt(filaSeleccionada, 2);
        String cliente = String.valueOf(tabla.getValueAt(filaSeleccionada, 3));

        text_field_cliente.setText(cliente);
        
        if(pago == PagoType.MERCADO_PAGO){
            rbutton_mp.setSelected(true);
            rbutton_transferencia.setSelected(false);
        }
        else {
            rbutton_mp.setSelected(false);
            rbutton_transferencia.setSelected(true);
        }

        boton_confirmar.putClientProperty("tipoAccion", "editar");
        boton_confirmar.putClientProperty("id", id);
        boton_confirmar.setText("Confirmar");
        titulo_modal.setText("Editar datos del pedido " + id);
    }
    
    @Override
    public void editar(int id) {
        /*Pago pago; if(rbutton_mp.isSelected()) pago = 
        PedidoDTO pedidoDTO = new PedidoDTO(id, )
        );

        try {
           pedidoController.actualizar(pedidoDTO);
            ((ModeloTablaPedido) tabla.getModel()).resetListaPedidos();
            ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
            JOptionPane.showMessageDialog(modal, "Pedido actualizado exitosamente.");
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal, "Error al actualizar el pedido.");
        }*/
        
        JOptionPane.showMessageDialog(modal, "Error al actualizar el pedido.");
    }

    @Override
    public void buscar(String nombre) {
        /*if("".equals(nombre)){
            ((ModeloTablaPedido) tabla.getModel()).resetListaPedidos();
        }
        else{
            ((ModeloTablaPedido) tabla.getModel()).actualizarListaPedidos(pedidoController.buscar(nombre));
            System.out.println(pedidoController.buscar(nombre));
        }
        
        ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);*/
        
        
    }

    @Override
    public void mostrarCrear() {
       
        text_field_cliente.setText("");
        rbutton_mp.setSelected(false);
        rbutton_transferencia.setSelected(false);
        
       boton_confirmar.putClientProperty("tipoAccion", "crear");
       
       boton_confirmar.setText("Crear");
       titulo_modal.setText("Completar datos del nuevo pedido");
       
    }
    
    @Override
    public void crear() {
        /*PedidoDTO pedidoDTO = new PedidoDTO(Integer.parseInt(text_field_cuit.getText()), text_field_email.getText(),
                text_field_direccion.getText(),
                new CoordenadaDTO(Double.parseDouble(text_field_latitud.getText()), Double.parseDouble(text_field_longitud.getText()))
        );

        try {
            pedidoController.crear(pedidoDTO);
            ((ModeloTablaPedido) tabla.getModel()).resetListaPedidos();
            ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
            JOptionPane.showMessageDialog(modal, "Pedido creado exitosamente.");
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal, "Error al crear el pedido.");
        }*/
        
        JOptionPane.showMessageDialog(modal, "Error al crear el pedido.");
    }

    /*@Override
    public Component buscarComponente(Container container, String nombre) {
        if (nombre.equals(container.getName())) {
            return container;
        }

        for (Component component : container.getComponents()) {
            if (nombre.equals(component.getName())) {
                return component;
            } else if (component instanceof Container container1) {
                // Llamada recursiva si el componente es un contenedor
                Component child = buscarComponente(container1, nombre);
                if (child != null) {
                    return child;
                }
            }
        }
        return null;
    }*/
    
}
