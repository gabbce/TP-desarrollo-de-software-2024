/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.utils;

import isi.deso.tpdeso2024.controllers.ItemMenuController;
import isi.deso.tpdeso2024.dtos.CoordenadaDTO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.excepciones.CategoriaNoEncontradoException;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import java.awt.HeadlessException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class InterfazItemMenu implements InformacionInterfaz{
    private final ItemMenuController itemMenuController = ItemMenuController.getInstance();
    
    //JPanel panel_info;
    JDialog modal;
    JDialog modal_eliminar;
    
    ModeloTablaItemMenu modeloItemMenu;
    JTable tabla;
    JTextField completar_nombre;
    JTextArea completar_descripcion;
    JTextField completar_vendedor;
    JTextField completar_precio;
    JTextField completar_categoria;
    JLabel titulo_modal;
    JButton boton_confirmar;
    JLabel titulo_modal_eliminar;
    JButton boton_confirmar_eliminar;
    JButton boton_crear;
    JLabel panel_info_titulo;
    //JTextField text_field_buscar;
    JLabel label_buscar;

    public InterfazItemMenu(JDialog modal, JDialog modal_eliminar, JTable tabla, JTextField completar_nombre, JTextArea completar_descripcion, JTextField completar_vendedor, JTextField completar_precio, JTextField completar_categoria, JLabel titulo_modal, JButton boton_confirmar, JLabel titulo_modal_eliminar, JButton boton_confirmar_eliminar, JButton boton_crear, JLabel panel_info_titulo, JLabel label_buscar) {
        this.modal = modal;
        this.modal_eliminar = modal_eliminar;
        this.tabla = tabla;
        this.completar_nombre = completar_nombre;
        this.completar_descripcion = completar_descripcion;
        this.completar_vendedor = completar_vendedor;
        this.completar_precio = completar_precio;
        this.completar_categoria = completar_categoria;
        this.titulo_modal = titulo_modal;
        this.boton_confirmar = boton_confirmar;
        this.titulo_modal_eliminar = titulo_modal_eliminar;
        this.boton_confirmar_eliminar = boton_confirmar_eliminar;
        this.boton_crear = boton_crear;
        this.panel_info_titulo = panel_info_titulo;
        this.label_buscar = label_buscar;
        
        modeloItemMenu = new ModeloTablaItemMenu();
        modeloItemMenu.setNombreColumnas(List.of("Id", "Nombre", "Descripcion", "Precio", "Id Categoria", "Id Vendedor"));

    }
    
    
    @Override
    public void cambiarPanel() {
        boton_crear.setText("Agregar item Menu");
        panel_info_titulo.setText("Lista de items Menu");
        label_buscar.setText("buscar por nombre:");
        
        tabla.setModel(modeloItemMenu);
        ((AbstractTableModel)tabla.getModel()).fireTableChanged(null);
    }

    @Override
    public void mostrarEliminar(int filaSeleccionada) {
        int id = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        titulo_modal_eliminar.setText("Se eliminará el item menu " + id);
        boton_confirmar_eliminar.putClientProperty("id", id);
    }
    
    @Override
    public void eliminar(int id){
        try {
            itemMenuController.eliminar(id);
            ((ModeloTablaItemMenu) tabla.getModel()).resetListaItemsMenu();
            ((AbstractTableModel)tabla.getModel()).fireTableChanged(null);
            
             JOptionPane.showMessageDialog(modal_eliminar, "Eliminado exitosamente.");
             
        } catch(HeadlessException e){
             JOptionPane.showMessageDialog(modal_eliminar, "No se pudo eliminar.");
             
        }
        
    }

    @Override
    public void mostrarEditar(int filaSeleccionada) {
        
        int id = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) tabla.getValueAt(filaSeleccionada, 1);
        String descripcion = (String) tabla.getValueAt(filaSeleccionada, 2);
        String precio = String.valueOf(tabla.getValueAt(filaSeleccionada, 3));
        String categoria = String.valueOf(tabla.getValueAt(filaSeleccionada, 4));
        String vendedor = String.valueOf(tabla.getValueAt(filaSeleccionada, 5));

        
        completar_nombre.setText(nombre);
        completar_descripcion.setText(descripcion);
        completar_precio.setText(precio);
        completar_categoria.setText(categoria);
        completar_vendedor.setText(vendedor);

        boton_confirmar.putClientProperty("tipoAccion", "editar");
        boton_confirmar.putClientProperty("id", id);
        boton_confirmar.setText("Confirmar");
        titulo_modal.setText("Editar datos del itemMenu " + id);
    }
    
    @Override
    public void editar(int id) {
        ItemMenuDTO itemMenuDTO = new ItemMenuDTO(id, completar_nombre.getText(),
                completar_descripcion.getText(), Float.parseFloat(completar_precio.getText()), 
                Integer.parseInt(completar_categoria.getText()), Integer.parseInt(completar_vendedor.getText())
        );
        

        try {
            itemMenuController.actualizar(itemMenuDTO);
            ((ModeloTablaItemMenu) tabla.getModel()).resetListaItemsMenu();
            ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
            JOptionPane.showMessageDialog(modal, "ItemMenu actualizado exitosamente.");
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal, "Error al actualizar el itemMenu.");
       //CATCH para las nuevas excepciones
        } catch (VendedorNoEncontradoException ex) {
            Logger.getLogger(InterfazItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CategoriaNoEncontradoException ex) {
            Logger.getLogger(InterfazItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void buscar(String nombre) {
        if("".equals(nombre)){
            ((ModeloTablaItemMenu) tabla.getModel()).resetListaItemsMenu();
        }
        else{
            ((ModeloTablaItemMenu) tabla.getModel()).actualizarListaItemsMenu(itemMenuController.buscar(nombre));
            System.out.println(itemMenuController.buscar(nombre));
        }
        
        ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
    }

    @Override
    public void mostrarCrear() {
       
        completar_nombre.setText("");
        completar_descripcion.setText("");
        completar_precio.setText("");
        completar_categoria.setText("");
        completar_vendedor.setText("");
        
       boton_confirmar.putClientProperty("tipoAccion", "crear");
       
       boton_confirmar.setText("Crear");
       titulo_modal.setText("Completar datos del nuevo itemMenu");
       
    }
    
    @Override
    public void crear() {
        
        
        ItemMenuDTO itemMenuDTO = new ItemMenuDTO(completar_nombre.getText(),
                completar_descripcion.getText(), Float.parseFloat(completar_precio.getText()), 
                Integer.parseInt(completar_categoria.getText()), Integer.parseInt(completar_vendedor.getText())
        );

        try {
            itemMenuController.crear(itemMenuDTO);
            ((ModeloTablaItemMenu) tabla.getModel()).resetListaItemsMenu();
            ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
            JOptionPane.showMessageDialog(modal, "Item menu creado exitosamente.");
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal, "Error al crear el item menu.");
        } catch (VendedorNoEncontradoException ex) {
            Logger.getLogger(InterfazItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CategoriaNoEncontradoException ex) {
            Logger.getLogger(InterfazItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
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
