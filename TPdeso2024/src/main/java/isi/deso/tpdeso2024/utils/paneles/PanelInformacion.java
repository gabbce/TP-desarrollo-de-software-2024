/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.tpdeso2024.utils.paneles;

import javax.swing.JPanel;

/**
 *
 * @author gabic
 */
public interface PanelInformacion{
    public void cerrarModales();
    public void abrirPanel();
    public void mostrarEliminar(int filaSeleccionada);
    public void eliminar(int id);
    public void mostrarEditar(int filaSeleccionada);
    public void editar(int id);
    public void mostrarCrear();
    public void crear();
    //public void buscar(String nombre);
}
