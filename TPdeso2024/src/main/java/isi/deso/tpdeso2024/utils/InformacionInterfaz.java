/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.tpdeso2024.utils;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author gabic
 */
public interface InformacionInterfaz {
    
    
    public void cambiarPanel();
    public void mostrarEliminar(int filaSeleccionada);
    public void eliminar(int id);
    public void mostrarEditar(int filaSeleccionada);
    public void editar(int id);
    public void mostrarCrear();
    public void crear();
    public void buscar(String nombre);
    public Component buscarComponente(Container container, String nombre);
    
    
}
