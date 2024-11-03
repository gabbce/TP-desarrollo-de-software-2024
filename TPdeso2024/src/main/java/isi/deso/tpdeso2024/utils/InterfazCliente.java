/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.utils;

import isi.deso.tpdeso2024.controllers.ClienteController;
import isi.deso.tpdeso2024.dtos.CoordenadaDTO;
import isi.deso.tpdeso2024.dtos.ClienteDTO;
import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class InterfazCliente implements InformacionInterfaz{
    private final ClienteController clienteController = ClienteController.getInstance();
    
 
    
    //JPanel panel_info;
    JDialog modal;
    JDialog modal_eliminar;
    
    ModeloTablaCliente modeloCliente;
    JTable tabla;
    JTextField completar_cuit;
    JTextField completar_email;
    JTextField completar_direccion;
    JTextField completar_latitud;
    JTextField completar_longitud;
    JLabel titulo_modal;
    JButton boton_confirmar;
    JLabel titulo_modal_eliminar;
    JButton boton_confirmar_eliminar;
    JButton boton_crear;
    JLabel panel_info_titulo;
    //JTextField text_field_buscar;
    JLabel label_buscar;
    
    
    // armar constructor con todos los componentes usados!!!

    public InterfazCliente(JPanel panel_info, JDialog modal, JDialog modal_eliminar) {
        
        //this.panel_info = panel_info;
        this.modal = modal; 
        this.modal_eliminar = modal_eliminar;
        
        tabla = (JTable) buscarComponente(panel_info, "tabla");
        completar_cuit = (JTextField) buscarComponente(modal, "text_field_cuit");
        completar_email = (JTextField) buscarComponente(modal, "text_field_email");
        completar_direccion = (JTextField) buscarComponente(modal, "text_field_direccion");
        completar_latitud = (JTextField) buscarComponente(modal, "text_field_latitud");
        completar_longitud = (JTextField) buscarComponente(modal, "text_field_longitud");
        titulo_modal = (JLabel) buscarComponente(modal, "label_titulo_modal_cliente");
        boton_confirmar = (JButton) buscarComponente(modal, "boton_confirmar_cliente");
        titulo_modal_eliminar = (JLabel)  buscarComponente(modal_eliminar, "label_titulo_modal_eliminar");
        boton_confirmar_eliminar = (JButton) buscarComponente(modal_eliminar, "boton_confirmar_eliminar");
        
        boton_crear = (JButton) buscarComponente(panel_info, "boton_crear");
        panel_info_titulo  = (JLabel) buscarComponente(panel_info, "panel_info_titulo");
        //text_field_buscar  = (JTextField) buscarComponente(panel_info, "text_field_buscar");
        label_buscar = (JLabel) buscarComponente(panel_info, "label_buscar");
        
        modeloCliente = new ModeloTablaCliente();
        modeloCliente.setNombreColumnas(List.of("Id", "Cuit", "Email", "Latitud", "Longitud"));

    }
    
    
    @Override
    public void cambiarPanel() {
        boton_crear.setText("Agregar cliente");
        panel_info_titulo.setText("Lista de clientes");
        label_buscar.setText("buscar por CUIT:");
        
        tabla.setModel(modeloCliente);
        ((AbstractTableModel)tabla.getModel()).fireTableChanged(null);
    }

    @Override
    public void mostrarEliminar(int filaSeleccionada) {
        int id = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        titulo_modal_eliminar.setText("Se eliminará el cliente " + id);
        boton_confirmar_eliminar.putClientProperty("id", id);
    }
    
    @Override
    public void eliminar(int id){
        try {
            clienteController.eliminar(id);
            ((ModeloTablaCliente) tabla.getModel()).resetListaClientes();
            ((AbstractTableModel)tabla.getModel()).fireTableChanged(null);
            
             JOptionPane.showMessageDialog(modal_eliminar, "Eliminado exitosamente.");
             
        } catch(HeadlessException e){
             JOptionPane.showMessageDialog(modal_eliminar, "No se pudo eliminar.");
             
        }
        
    }

    @Override
    public void mostrarEditar(int filaSeleccionada) {
        
        int id = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        String cuit = (String) tabla.getValueAt(filaSeleccionada, 1);
        String email = (String) tabla.getValueAt(filaSeleccionada, 2);
        String direccion = (String) tabla.getValueAt(filaSeleccionada, 3);
        String latitud = String.valueOf(tabla.getValueAt(filaSeleccionada, 4));
        String longitud = String.valueOf(tabla.getValueAt(filaSeleccionada, 5));

        completar_cuit.setText(cuit);
        completar_email.setText(email);
        completar_direccion.setText(direccion);
        completar_latitud.setText(latitud);
        completar_longitud.setText(longitud);

        boton_confirmar.putClientProperty("tipoAccion", "editar");
        boton_confirmar.putClientProperty("id", id);
        boton_confirmar.setText("Confirmar");
        titulo_modal.setText("Editar datos del cliente " + id);
    }
    
    @Override
    public void editar(int id) {
        ClienteDTO clienteDTO = new ClienteDTO(id, Integer.parseInt(completar_cuit.getText()), completar_email.getText(),
                completar_direccion.getText(),
                new CoordenadaDTO(Double.parseDouble(completar_latitud.getText()), Double.parseDouble(completar_longitud.getText()))
        );

        try {
            clienteController.actualizar(clienteDTO);
            ((ModeloTablaCliente) tabla.getModel()).resetListaClientes();
            ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
            JOptionPane.showMessageDialog(modal, "Cliente actualizado exitosamente.");
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal, "Error al actualizar el cliente.");
        }
    }

    @Override
    public void buscar(String nombre) {
        if("".equals(nombre)){
            ((ModeloTablaCliente) tabla.getModel()).resetListaClientes();
        }
        else{
            ((ModeloTablaCliente) tabla.getModel()).actualizarListaClientes(clienteController.buscar(nombre));
            System.out.println(clienteController.buscar(nombre));
        }
        
        ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
    }

    @Override
    public void mostrarCrear() {
       
        completar_cuit.setText("");
        completar_email.setText("");
        completar_direccion.setText("");
        completar_latitud.setText("");
        completar_longitud.setText("");
        
       boton_confirmar.putClientProperty("tipoAccion", "crear");
       
       boton_confirmar.setText("Crear");
       titulo_modal.setText("Completar datos del nuevo cliente");
       
    }
    
    @Override
    public void crear() {
        ClienteDTO clienteDTO = new ClienteDTO(Integer.parseInt(completar_cuit.getText()), completar_email.getText(),
                completar_direccion.getText(),
                new CoordenadaDTO(Double.parseDouble(completar_latitud.getText()), Double.parseDouble(completar_longitud.getText()))
        );

        try {
            clienteController.crear(clienteDTO);
            ((ModeloTablaCliente) tabla.getModel()).resetListaClientes();
            ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
            JOptionPane.showMessageDialog(modal, "Cliente creado exitosamente.");
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal, "Error al crear el cliente.");
        }
    }

    @Override
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
    }
}
