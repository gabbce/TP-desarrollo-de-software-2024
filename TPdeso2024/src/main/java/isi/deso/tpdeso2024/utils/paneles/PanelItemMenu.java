/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package isi.deso.tpdeso2024.utils.paneles;

import isi.deso.tpdeso2024.controllers.ItemMenuController;
import isi.deso.tpdeso2024.dtos.BebidaDTO;
import isi.deso.tpdeso2024.dtos.CategoriaDTO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.dtos.PlatoDTO;
import isi.deso.tpdeso2024.dtos.VendedorDTO;
import isi.deso.tpdeso2024.excepciones.CategoriaNoEncontradoException;
import isi.deso.tpdeso2024.excepciones.ItemNoEncontradoExcepcion;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import isi.deso.tpdeso2024.utils.modelosTablas.ModeloTablaItemMenu;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class PanelItemMenu extends javax.swing.JPanel implements PanelInformacion{
    private final ItemMenuController itemMenuController = ItemMenuController.getInstance();

    ModeloTablaItemMenu modeloItemMenu;
    //InterfazItemMenu interfaz;
    
    /**
     * Creates new form PanelItemMenu
     */
    public PanelItemMenu() {
        initComponents();
        
        inicializarTabla();
        modeloItemMenu = new ModeloTablaItemMenu();
        modeloItemMenu.setNombreColumnas(List.of("Id", "Tipo", "Nombre", "Descripcion", "Precio", "Categoria", "Id Vendedor"));
        tabla.setModel(modeloItemMenu);
        cargarCategorias();
        
    }
    
    public void inicializarTabla(){
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(false);  
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        tabla.setSelectionBackground(Color.LIGHT_GRAY);
        tabla.setSelectionForeground(Color.BLUE); 
    }

    private void mostrarModal(JDialog modal) {
        modal.setSize(520, 350);
        modal.setLocationRelativeTo(this);
        modal.setVisible(true);
    }
    
    private void mostrarModalEliminar(JDialog modal) {
        modal.setSize(300, 150);
        modal.setLocationRelativeTo(this);
        modal.setVisible(true);
    }
    
    @Override
    public void abrirPanel() {
        
        resetFiltros();
        
    }
    
    public void resetFiltros(){
        filtro_tipo.setSelectedIndex(0);
        filtro_categoria.setSelectedIndex(0);
        filtro_nombre.setText("");
        filtro_vendedor.setText("");
       ((ModeloTablaItemMenu) tabla.getModel()).resetListaItemsMenu();
       ((AbstractTableModel)tabla.getModel()).fireTableChanged(null);
    }
    
    @Override
    public void cerrarModales() {
       modal.dispose();
       modal_eliminar.dispose();
       modal_comida.dispose();
       modal_bebida.dispose();
       modal_detalles_comida.dispose();
       modal_detalles_bebida.dispose();
    }
    
    private void cargarCategorias() {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        modelo.addElement("Todas");

        List<CategoriaDTO> categorias = itemMenuController.listarCategorias();
        for (CategoriaDTO categoria : categorias) {
            modelo.addElement(categoria.getTipo());
        }

        filtro_categoria.setModel(modelo);
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
            resetFiltros();
            
            JOptionPane.showMessageDialog(modal_eliminar, "Eliminado exitosamente.");
             
        } catch(HeadlessException e){
             JOptionPane.showMessageDialog(modal_eliminar, "No se pudo eliminar.");
             
        }
        
    }
    
    public void getValoresTabla(int fila){
        
        String tipo = (String) tabla.getValueAt(fila, 1);
        String nombre = (String) tabla.getValueAt(fila, 2);
        String descripcion = (String) tabla.getValueAt(fila, 3);
        String precio = String.valueOf(tabla.getValueAt(fila, 4));
        String categoria = (String) tabla.getValueAt(fila, 5);
        String vendedor = String.valueOf(tabla.getValueAt(fila, 6));

        
        text_field_nombre.setText(nombre);
        text_area_descripcion.setText(descripcion);
        text_field_precio.setText(precio);
        combo_box_categoria.setSelectedItem(categoria);
        combo_box_tipo.setSelectedItem(tipo);
        text_field_vendedor.setText(vendedor);
    }
    
    public void mostrarDetalles(int filaSeleccionada){
        int id = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        try {
            ItemMenuDTO i = itemMenuController.buscarPorID(id);
            
            if(! i.EsComida()) {
                BebidaDTO p = (BebidaDTO) i;
                detalle_graduacion.setText(String.valueOf(p.getGraduacionAlcoholica()));
                detalle_tam.setText(String.valueOf(p.getTam()));
                
                titulo_modal_detalle_bebida.setText("Detalles item menu " + id + " - BEBIDA");
                mostrarModal(modal_detalles_bebida);
                
            }
            else {
                PlatoDTO p = (PlatoDTO) i;
                detalle_calorias.setText(String.valueOf(p.getCalorias()));
                detalle_peso.setText(String.valueOf(p.getPeso()));
                detalle_vegano.setSelected(p.aptoVegano());
                detalle_celiaco.setSelected(p.aptoCeliaco());
                
                titulo_modal_detalle_comida.setText("Detalles item menu " + id + " - COMIDA");
                mostrarModal(modal_detalles_comida);
            }
            
        } catch (ItemNoEncontradoExcepcion ex) {
            Logger.getLogger(PanelItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mostrarEditar(int filaSeleccionada) {
        
        getValoresTabla(filaSeleccionada);
        int id = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        
        titulo_modal.setText("Editar datos del item menu " + id);
        boton_siguiente.putClientProperty("tipoAccion", "editar");
        boton_siguiente.putClientProperty("id", id);
        mostrarModal(modal);
    }
    
    public void mostrarEditarComida(int id) {
        
        try {
            PlatoDTO p = (PlatoDTO) itemMenuController.buscarPorID(id);
            
            text_field_calorias.setText(String.valueOf(p.getCalorias()));
            text_field_peso.setText(String.valueOf(p.getPeso()));
            apto_vegano.setSelected(p.aptoVegano());
            apto_celiaco.setSelected(p.aptoCeliaco());
            
            titulo_modal_comida.setText("Editar datos del item menu " + id);
            boton_confirmar_comida.putClientProperty("tipoAccion", "editar");
            boton_confirmar_comida.putClientProperty("id", id);
            boton_confirmar_comida.setText("Confirmar");
            
            mostrarModal(modal_comida);
        } catch (ItemNoEncontradoExcepcion ex) {
            Logger.getLogger(PanelItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarEditarBebida(int id) {

        try {
            BebidaDTO p = (BebidaDTO) itemMenuController.buscarPorID(id);

            text_field_graduacion.setText(String.valueOf(p.getGraduacionAlcoholica()));
            text_field_tam.setText(String.valueOf(p.getTam()));
            
            titulo_modal_bebida.setText("Editar datos del item menu " + id);
            boton_confirmar_bebida.putClientProperty("tipoAccion", "editar");
            boton_confirmar_bebida.putClientProperty("id", id);
            boton_confirmar_bebida.setText("Confirmar");
            mostrarModal(modal_bebida);
            
        } catch (ItemNoEncontradoExcepcion ex) {
            Logger.getLogger(PanelItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    @Override
    public void editar(int id) {
    }
    
    public void editarComida(int id) {
        
        PlatoDTO platoDTO = new PlatoDTO(id, text_field_nombre.getText(),
                text_area_descripcion.getText(), new VendedorDTO(Integer.parseInt(text_field_vendedor.getText()), null, null, null), 
                new CategoriaDTO((String)combo_box_categoria.getSelectedItem()), Float.parseFloat(text_field_precio.getText()),
                Float.parseFloat(text_field_peso.getText()), Float.parseFloat(text_field_calorias.getText()),
                apto_celiaco.isSelected(), apto_vegano.isSelected()
        );

        try {
            itemMenuController.actualizar((ItemMenuDTO)platoDTO, true);
            resetFiltros();
            JOptionPane.showMessageDialog(modal_comida, "Item menu " + id + " editado exitosamente.");
        }   catch (VendedorNoEncontradoException ex) {
            JOptionPane.showMessageDialog(modal_comida, "<html>Error al editar el item menu<br>No existe el vendedor</html>");
            Logger.getLogger(PanelItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CategoriaNoEncontradoException ex) {
            JOptionPane.showMessageDialog(modal_comida, "<html>Error al editar el item menu<br>No existe la categoria</html>");
            Logger.getLogger(PanelItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal_comida, "Error al editar el item menu.");
        } 
        
        modal_comida.dispose();
    }
    
    public void editarBebida(int id) {
        
        BebidaDTO bebidaDTO = new BebidaDTO(id, text_field_nombre.getText(),
                text_area_descripcion.getText(), new VendedorDTO(Integer.parseInt(text_field_vendedor.getText()), null, null, null), 
                new CategoriaDTO((String)combo_box_categoria.getSelectedItem()), Float.parseFloat(text_field_precio.getText()),
                Float.parseFloat(text_field_graduacion.getText()), Float.parseFloat(text_field_tam.getText())
        );

         try {
            itemMenuController.actualizar((ItemMenuDTO)bebidaDTO, false);
            resetFiltros();
            JOptionPane.showMessageDialog(modal_bebida, "Item menu " + id + " editado exitosamente.");
        } catch (VendedorNoEncontradoException ex) {
            JOptionPane.showMessageDialog(modal_bebida, "<html>Error al editar el item menu<br>No existe el vendedor</html>");
            Logger.getLogger(PanelItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CategoriaNoEncontradoException ex) {
            JOptionPane.showMessageDialog(modal_bebida, "<html>Error al editar el item menu<br>No existe la categoria</html>");
            Logger.getLogger(PanelItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal_bebida, "Error al editar el item menu.");
        } 
         
         modal_bebida.dispose();
    }


    @Override
    public void mostrarCrear() {
       
        text_field_nombre.setText("");
        text_area_descripcion.setText("");
        text_field_precio.setText("");
        combo_box_categoria.setSelectedIndex(0);
        combo_box_tipo.setSelectedIndex(0);
        text_field_vendedor.setText("");
        
       
       titulo_modal.setText("Completar datos del nuevo item menu");
       boton_siguiente.putClientProperty("tipoAccion", "crear");
       boton_siguiente.setText("Siguiente");
       mostrarModal(modal);
    }
    
    public void mostrarCrearComida(){
        text_field_calorias.setText("");
        text_field_peso.setText("");
        apto_vegano.setSelected(false);
        apto_celiaco.setSelected(false);
        
        boton_confirmar_comida.putClientProperty("tipoAccion", "crear");
        boton_confirmar_comida.setText("Crear");
        titulo_modal_comida.setText("Completar datos del nuevo itemMenu - COMIDA");
        mostrarModal(modal_comida);
        
    }
    
     public void mostrarCrearBebida(){
        text_field_graduacion.setText("");
        text_field_tam.setText("");
        
       boton_confirmar_bebida.putClientProperty("tipoAccion", "crear");
       boton_confirmar_bebida.setText("Crear");
       titulo_modal_bebida.setText("Completar datos del nuevo item menu - BEBIDA");
        mostrarModal(modal_bebida);
    }
    
    @Override
    public void crear(){
        
    }
    
    public void crearComida() {
        
        PlatoDTO platoDTO = new PlatoDTO(0, text_field_nombre.getText(),
                text_area_descripcion.getText(), new VendedorDTO(Integer.parseInt(text_field_vendedor.getText()), null, null, null), 
                new CategoriaDTO((String)combo_box_categoria.getSelectedItem()), Float.parseFloat(text_field_precio.getText()),
                Float.parseFloat(text_field_peso.getText()), Float.parseFloat(text_field_calorias.getText()),
                apto_celiaco.isSelected(), apto_vegano.isSelected()
        );

        try {
            itemMenuController.crear((ItemMenuDTO)platoDTO, true);
            resetFiltros();
            JOptionPane.showMessageDialog(modal_comida, "Item menu creado exitosamente.");
        }   catch (VendedorNoEncontradoException ex) {
            JOptionPane.showMessageDialog(modal_comida, "<html>Error al crear el item menu<br>No existe el vendedor</html>");
            //Logger.getLogger(InterfazItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CategoriaNoEncontradoException ex) {
            JOptionPane.showMessageDialog(modal_comida, "<html>Error al crear el item menu<br>No existe la categoria</html>");
            //Logger.getLogger(InterfazItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal_comida, "Error al crear el item menu.");
        } 
        
        modal_comida.dispose();
    }
    
    public void crearBebida() {
        
        BebidaDTO bebidaDTO = new BebidaDTO(0, text_field_nombre.getText(),
                text_area_descripcion.getText(), new VendedorDTO(Integer.parseInt(text_field_vendedor.getText()), null, null, null), 
                new CategoriaDTO((String)combo_box_categoria.getSelectedItem()), Float.parseFloat(text_field_precio.getText()),
                Float.parseFloat(text_field_graduacion.getText()), Float.parseFloat(text_field_tam.getText())
        );

         try {
            itemMenuController.crear((ItemMenuDTO)bebidaDTO, false);
            resetFiltros();
            JOptionPane.showMessageDialog(modal_bebida, "Item menu creado exitosamente.");
        } catch (VendedorNoEncontradoException ex) {
            JOptionPane.showMessageDialog(modal_bebida, "<html>Error al crear el item menu<br>No existe el vendedor</html>");
            Logger.getLogger(PanelItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CategoriaNoEncontradoException ex) {
            JOptionPane.showMessageDialog(modal_bebida, "<html>Error al crear el item menu<br>No existe la categoria</html>");
            Logger.getLogger(PanelItemMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal_bebida, "Error al crear el item menu.");
        } 
         
         modal_bebida.dispose();
    }
    
    public void filtrar(){
        
        String nombre = filtro_nombre.getText();
        String categoria = (String) filtro_categoria.getSelectedItem();
        String tipo = (String) filtro_tipo.getSelectedItem();
        String vendedor = filtro_vendedor.getText();
        
        Boolean esComida = null;
        
        if(tipo.equals("Comida")){
            esComida = true;
        } else if(tipo.equals("Bebida")) {
            esComida = false;
        }
        
        try {
            ((ModeloTablaItemMenu) tabla.getModel()).actualizarListaItemsMenu(itemMenuController.filtrar(esComida, nombre, vendedor, categoria));
        } catch(Exception e){
            ((ModeloTablaItemMenu) tabla.getModel()).actualizarListaItemsMenu(new ArrayList());
            System.out.println(e.getMessage());
        }
        
        ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
        
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modal = new javax.swing.JDialog();
        panel_modal = new javax.swing.JPanel();
        boton_siguiente = new javax.swing.JButton();
        boton_cancelar = new javax.swing.JButton();
        titulo_modal = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        text_field_vendedor = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        text_field_nombre = new javax.swing.JTextField();
        text_field_precio = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        text_area_descripcion = new javax.swing.JTextArea();
        combo_box_categoria = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        combo_box_tipo = new javax.swing.JComboBox<>();
        modal_eliminar = new javax.swing.JDialog();
        panel_modal_eliminar = new javax.swing.JPanel();
        boton_confirmar_eliminar = new javax.swing.JButton();
        boton_cancelar_eliminar = new javax.swing.JButton();
        titulo_modal_eliminar = new javax.swing.JLabel();
        modal_bebida = new javax.swing.JDialog();
        panel_modal1 = new javax.swing.JPanel();
        boton_confirmar_bebida = new javax.swing.JButton();
        boton_cancelar_bebida = new javax.swing.JButton();
        titulo_modal_bebida = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        text_field_graduacion = new javax.swing.JTextField();
        text_field_tam = new javax.swing.JTextField();
        modal_comida = new javax.swing.JDialog();
        panel_modal2 = new javax.swing.JPanel();
        boton_confirmar_comida = new javax.swing.JButton();
        boton_cancelar_comida = new javax.swing.JButton();
        titulo_modal_comida = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        apto_celiaco = new javax.swing.JCheckBox();
        apto_vegano = new javax.swing.JCheckBox();
        text_field_peso = new javax.swing.JTextField();
        text_field_calorias = new javax.swing.JTextField();
        modal_detalles_bebida = new javax.swing.JDialog();
        panel_modal3 = new javax.swing.JPanel();
        boton_continuar_detalle_bebida = new javax.swing.JButton();
        titulo_modal_detalle_bebida = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        detalle_graduacion = new javax.swing.JTextField();
        detalle_tam = new javax.swing.JTextField();
        modal_detalles_comida = new javax.swing.JDialog();
        panel_modal4 = new javax.swing.JPanel();
        boton_continuar_detalle_comida = new javax.swing.JButton();
        titulo_modal_detalle_comida = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        detalle_celiaco = new javax.swing.JCheckBox();
        detalle_vegano = new javax.swing.JCheckBox();
        detalle_peso = new javax.swing.JTextField();
        detalle_calorias = new javax.swing.JTextField();
        panel_info = new javax.swing.JPanel();
        panel_info_titulo = new javax.swing.JLabel();
        boton_crear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        boton_editar = new javax.swing.JButton();
        boton_eliminar = new javax.swing.JButton();
        panel_filtros = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        label_buscar3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        filtro_tipo = new javax.swing.JComboBox<>();
        label_buscar4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        filtro_nombre = new javax.swing.JTextField();
        label_buscar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_buscar1 = new javax.swing.JLabel();
        filtro_vendedor = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        filtro_categoria = new javax.swing.JComboBox<>();
        label_buscar2 = new javax.swing.JLabel();
        boton_detalles = new javax.swing.JButton();

        modal.setBackground(new java.awt.Color(0, 0, 0));

        panel_modal.setBackground(new java.awt.Color(255, 204, 204));

        boton_siguiente.setBackground(new java.awt.Color(0, 102, 0));
        boton_siguiente.setForeground(new java.awt.Color(204, 204, 204));
        boton_siguiente.setText("Siguiente");
        boton_siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_siguienteActionPerformed(evt);
            }
        });

        boton_cancelar.setBackground(new java.awt.Color(102, 0, 0));
        boton_cancelar.setForeground(new java.awt.Color(204, 204, 204));
        boton_cancelar.setText("Cancelar");
        boton_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelarActionPerformed(evt);
            }
        });

        titulo_modal.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        titulo_modal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_modal.setText("Completar datos del nuevo item menu");

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Descripcion");

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Id vendedor");

        text_field_vendedor.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_vendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_vendedorActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Categoria");

        jLabel16.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Nombre");

        text_field_nombre.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        text_field_precio.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Precio");

        text_area_descripcion.setColumns(20);
        text_area_descripcion.setRows(5);
        jScrollPane2.setViewportView(text_area_descripcion);

        combo_box_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_categoriaActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Tipo");

        combo_box_tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Comida", "Bebida" }));
        combo_box_tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_tipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_modalLayout = new javax.swing.GroupLayout(panel_modal);
        panel_modal.setLayout(panel_modalLayout);
        panel_modalLayout.setHorizontalGroup(
            panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo_modal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_modalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_modalLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(boton_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_modalLayout.createSequentialGroup()
                        .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_modalLayout.createSequentialGroup()
                                .addComponent(text_field_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel18)
                                        .addGroup(panel_modalLayout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(combo_box_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(boton_siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(combo_box_tipo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panel_modalLayout.createSequentialGroup()
                                .addComponent(text_field_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(text_field_nombre)
                            .addComponent(jScrollPane2))
                        .addGap(15, 15, 15))))
        );
        panel_modalLayout.setVerticalGroup(
            panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modalLayout.createSequentialGroup()
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_modalLayout.createSequentialGroup()
                        .addComponent(titulo_modal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(text_field_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(combo_box_categoria))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_modalLayout.createSequentialGroup()
                        .addGap(0, 155, Short.MAX_VALUE)
                        .addComponent(text_field_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_modalLayout.createSequentialGroup()
                        .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(combo_box_tipo))
                        .addGap(5, 5, 5))
                    .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(text_field_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_cancelar)
                    .addComponent(boton_siguiente))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout modalLayout = new javax.swing.GroupLayout(modal.getContentPane());
        modal.getContentPane().setLayout(modalLayout);
        modalLayout.setHorizontalGroup(
            modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
            .addGroup(modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modalLayout.setVerticalGroup(
            modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
            .addGroup(modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        modal_eliminar.setBackground(new java.awt.Color(51, 0, 51));

        panel_modal_eliminar.setBackground(new java.awt.Color(204, 204, 204));

        boton_confirmar_eliminar.setBackground(new java.awt.Color(0, 102, 0));
        boton_confirmar_eliminar.setForeground(new java.awt.Color(255, 255, 255));
        boton_confirmar_eliminar.setText("Confirmar");
        boton_confirmar_eliminar.setMaximumSize(new java.awt.Dimension(76, 23));
        boton_confirmar_eliminar.setMinimumSize(new java.awt.Dimension(76, 23));
        boton_confirmar_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confirmar_eliminarActionPerformed(evt);
            }
        });

        boton_cancelar_eliminar.setBackground(new java.awt.Color(102, 0, 0));
        boton_cancelar_eliminar.setForeground(new java.awt.Color(255, 255, 255));
        boton_cancelar_eliminar.setText("Cancelar");
        boton_cancelar_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelar_eliminarActionPerformed(evt);
            }
        });

        titulo_modal_eliminar.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        titulo_modal_eliminar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_modal_eliminar.setText("Se eliminará el *objeto* *id*, seguro?");

        javax.swing.GroupLayout panel_modal_eliminarLayout = new javax.swing.GroupLayout(panel_modal_eliminar);
        panel_modal_eliminar.setLayout(panel_modal_eliminarLayout);
        panel_modal_eliminarLayout.setHorizontalGroup(
            panel_modal_eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo_modal_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_modal_eliminarLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(boton_cancelar_eliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_confirmar_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        panel_modal_eliminarLayout.setVerticalGroup(
            panel_modal_eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal_eliminarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo_modal_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel_modal_eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_cancelar_eliminar)
                    .addComponent(boton_confirmar_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout modal_eliminarLayout = new javax.swing.GroupLayout(modal_eliminar.getContentPane());
        modal_eliminar.getContentPane().setLayout(modal_eliminarLayout);
        modal_eliminarLayout.setHorizontalGroup(
            modal_eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
            .addGroup(modal_eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal_eliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modal_eliminarLayout.setVerticalGroup(
            modal_eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(modal_eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        modal_bebida.setBackground(new java.awt.Color(0, 0, 0));

        panel_modal1.setBackground(new java.awt.Color(255, 204, 204));

        boton_confirmar_bebida.setBackground(new java.awt.Color(0, 102, 0));
        boton_confirmar_bebida.setForeground(new java.awt.Color(204, 204, 204));
        boton_confirmar_bebida.setText("Confirmar");
        boton_confirmar_bebida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confirmar_bebidaActionPerformed(evt);
            }
        });

        boton_cancelar_bebida.setBackground(new java.awt.Color(102, 0, 0));
        boton_cancelar_bebida.setForeground(new java.awt.Color(204, 204, 204));
        boton_cancelar_bebida.setText("Cancelar");
        boton_cancelar_bebida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelar_bebidaActionPerformed(evt);
            }
        });

        titulo_modal_bebida.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        titulo_modal_bebida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_modal_bebida.setText("Completar datos del nuevo item menu - BEBIDA");

        jPanel10.setOpaque(false);

        jLabel20.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Graduación alcoholica");

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Tamaño (ml)");

        text_field_graduacion.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_graduacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_graduacionActionPerformed(evt);
            }
        });

        text_field_tam.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_field_graduacion, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(text_field_tam))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_field_graduacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_field_tam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_modal1Layout = new javax.swing.GroupLayout(panel_modal1);
        panel_modal1.setLayout(panel_modal1Layout);
        panel_modal1Layout.setHorizontalGroup(
            panel_modal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo_modal_bebida, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
            .addGroup(panel_modal1Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(boton_cancelar_bebida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_confirmar_bebida)
                .addGap(109, 109, 109))
            .addGroup(panel_modal1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_modal1Layout.setVerticalGroup(
            panel_modal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal1Layout.createSequentialGroup()
                .addComponent(titulo_modal_bebida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addGroup(panel_modal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_cancelar_bebida)
                    .addComponent(boton_confirmar_bebida))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout modal_bebidaLayout = new javax.swing.GroupLayout(modal_bebida.getContentPane());
        modal_bebida.getContentPane().setLayout(modal_bebidaLayout);
        modal_bebidaLayout.setHorizontalGroup(
            modal_bebidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 608, Short.MAX_VALUE)
            .addGroup(modal_bebidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modal_bebidaLayout.setVerticalGroup(
            modal_bebidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
            .addGroup(modal_bebidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        modal_comida.setBackground(new java.awt.Color(0, 0, 0));

        panel_modal2.setBackground(new java.awt.Color(255, 204, 204));
        panel_modal2.setPreferredSize(new java.awt.Dimension(520, 320));

        boton_confirmar_comida.setBackground(new java.awt.Color(0, 102, 0));
        boton_confirmar_comida.setForeground(new java.awt.Color(204, 204, 204));
        boton_confirmar_comida.setText("Confirmar");
        boton_confirmar_comida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confirmar_comidaActionPerformed(evt);
            }
        });

        boton_cancelar_comida.setBackground(new java.awt.Color(102, 0, 0));
        boton_cancelar_comida.setForeground(new java.awt.Color(204, 204, 204));
        boton_cancelar_comida.setText("Cancelar");
        boton_cancelar_comida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelar_comidaActionPerformed(evt);
            }
        });

        titulo_modal_comida.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        titulo_modal_comida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_modal_comida.setText("Completar datos del nuevo item menu - COMIDA");

        jPanel11.setOpaque(false);

        jLabel21.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Peso (gr)");

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Calorias (kcal)");

        apto_celiaco.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        apto_celiaco.setText("Apto celiaco");
        apto_celiaco.setContentAreaFilled(false);
        apto_celiaco.setHideActionText(true);
        apto_celiaco.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        apto_vegano.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        apto_vegano.setText("Apto vegano");
        apto_vegano.setContentAreaFilled(false);
        apto_vegano.setHideActionText(true);
        apto_vegano.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        apto_vegano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apto_veganoActionPerformed(evt);
            }
        });

        text_field_peso.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_peso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_pesoActionPerformed(evt);
            }
        });

        text_field_calorias.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_calorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_caloriasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(12, 12, 12)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(text_field_peso, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(text_field_calorias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(apto_celiaco)
                    .addComponent(apto_vegano))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_field_peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apto_vegano))
                .addGap(37, 37, 37)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_field_calorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(apto_celiaco))
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout panel_modal2Layout = new javax.swing.GroupLayout(panel_modal2);
        panel_modal2.setLayout(panel_modal2Layout);
        panel_modal2Layout.setHorizontalGroup(
            panel_modal2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo_modal_comida, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
            .addGroup(panel_modal2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_modal2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_modal2Layout.createSequentialGroup()
                        .addComponent(boton_cancelar_comida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boton_confirmar_comida))
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_modal2Layout.setVerticalGroup(
            panel_modal2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal2Layout.createSequentialGroup()
                .addComponent(titulo_modal_comida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addGroup(panel_modal2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_cancelar_comida)
                    .addComponent(boton_confirmar_comida))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout modal_comidaLayout = new javax.swing.GroupLayout(modal_comida.getContentPane());
        modal_comida.getContentPane().setLayout(modal_comidaLayout);
        modal_comidaLayout.setHorizontalGroup(
            modal_comidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
            .addGroup(modal_comidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modal_comidaLayout.setVerticalGroup(
            modal_comidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
            .addGroup(modal_comidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
        );

        modal_detalles_bebida.setBackground(new java.awt.Color(0, 0, 0));

        panel_modal3.setBackground(new java.awt.Color(255, 204, 204));

        boton_continuar_detalle_bebida.setBackground(new java.awt.Color(0, 102, 0));
        boton_continuar_detalle_bebida.setForeground(new java.awt.Color(204, 204, 204));
        boton_continuar_detalle_bebida.setText("Continuar");
        boton_continuar_detalle_bebida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_continuar_detalle_bebidaActionPerformed(evt);
            }
        });

        titulo_modal_detalle_bebida.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        titulo_modal_detalle_bebida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_modal_detalle_bebida.setText("Detalles item menu - BEBIDA");

        jPanel12.setOpaque(false);

        jLabel22.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Graduación alcoholica");

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Tamaño (ml)");

        detalle_graduacion.setEditable(false);
        detalle_graduacion.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        detalle_graduacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detalle_graduacionActionPerformed(evt);
            }
        });

        detalle_tam.setEditable(false);
        detalle_tam.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(detalle_graduacion, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(detalle_tam))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(detalle_graduacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(detalle_tam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_modal3Layout = new javax.swing.GroupLayout(panel_modal3);
        panel_modal3.setLayout(panel_modal3Layout);
        panel_modal3Layout.setHorizontalGroup(
            panel_modal3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo_modal_detalle_bebida, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
            .addGroup(panel_modal3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_modal3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_continuar_detalle_bebida, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_modal3Layout.setVerticalGroup(
            panel_modal3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal3Layout.createSequentialGroup()
                .addComponent(titulo_modal_detalle_bebida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(boton_continuar_detalle_bebida)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout modal_detalles_bebidaLayout = new javax.swing.GroupLayout(modal_detalles_bebida.getContentPane());
        modal_detalles_bebida.getContentPane().setLayout(modal_detalles_bebidaLayout);
        modal_detalles_bebidaLayout.setHorizontalGroup(
            modal_detalles_bebidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 608, Short.MAX_VALUE)
            .addGroup(modal_detalles_bebidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modal_detalles_bebidaLayout.setVerticalGroup(
            modal_detalles_bebidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
            .addGroup(modal_detalles_bebidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        modal_detalles_comida.setBackground(new java.awt.Color(0, 0, 0));

        panel_modal4.setBackground(new java.awt.Color(255, 204, 204));
        panel_modal4.setPreferredSize(new java.awt.Dimension(520, 320));

        boton_continuar_detalle_comida.setBackground(new java.awt.Color(0, 102, 0));
        boton_continuar_detalle_comida.setForeground(new java.awt.Color(204, 204, 204));
        boton_continuar_detalle_comida.setText("Continuar");
        boton_continuar_detalle_comida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_continuar_detalle_comidaActionPerformed(evt);
            }
        });

        titulo_modal_detalle_comida.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        titulo_modal_detalle_comida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_modal_detalle_comida.setText("Detalles item menu - COMIDA");

        jPanel13.setOpaque(false);

        jLabel23.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Peso (gr)");

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Calorias (kcal)");

        detalle_celiaco.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        detalle_celiaco.setText("Apto celiaco");
        detalle_celiaco.setContentAreaFilled(false);
        detalle_celiaco.setEnabled(false);
        detalle_celiaco.setHideActionText(true);
        detalle_celiaco.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        detalle_vegano.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        detalle_vegano.setText("Apto vegano");
        detalle_vegano.setContentAreaFilled(false);
        detalle_vegano.setEnabled(false);
        detalle_vegano.setHideActionText(true);
        detalle_vegano.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        detalle_vegano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detalle_veganoActionPerformed(evt);
            }
        });

        detalle_peso.setEditable(false);
        detalle_peso.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        detalle_peso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detalle_pesoActionPerformed(evt);
            }
        });

        detalle_calorias.setEditable(false);
        detalle_calorias.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        detalle_calorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detalle_caloriasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(12, 12, 12)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(detalle_peso, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(detalle_calorias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(detalle_celiaco)
                    .addComponent(detalle_vegano))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(detalle_peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(detalle_vegano))
                .addGap(37, 37, 37)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(detalle_calorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(detalle_celiaco))
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout panel_modal4Layout = new javax.swing.GroupLayout(panel_modal4);
        panel_modal4.setLayout(panel_modal4Layout);
        panel_modal4Layout.setHorizontalGroup(
            panel_modal4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo_modal_detalle_comida, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
            .addGroup(panel_modal4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_modal4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_continuar_detalle_comida, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_modal4Layout.setVerticalGroup(
            panel_modal4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal4Layout.createSequentialGroup()
                .addComponent(titulo_modal_detalle_comida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(boton_continuar_detalle_comida)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout modal_detalles_comidaLayout = new javax.swing.GroupLayout(modal_detalles_comida.getContentPane());
        modal_detalles_comida.getContentPane().setLayout(modal_detalles_comidaLayout);
        modal_detalles_comidaLayout.setHorizontalGroup(
            modal_detalles_comidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
            .addGroup(modal_detalles_comidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modal_detalles_comidaLayout.setVerticalGroup(
            modal_detalles_comidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
            .addGroup(modal_detalles_comidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
        );

        setPreferredSize(new java.awt.Dimension(800, 610));

        panel_info.setBackground(new java.awt.Color(255, 255, 255));

        panel_info_titulo.setBackground(new java.awt.Color(255, 255, 153));
        panel_info_titulo.setFont(new java.awt.Font("Comic Sans MS", 1, 36)); // NOI18N
        panel_info_titulo.setForeground(new java.awt.Color(102, 0, 102));
        panel_info_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel_info_titulo.setText("Lista de items menu");
        panel_info_titulo.setOpaque(true);

        boton_crear.setBackground(new java.awt.Color(255, 102, 153));
        boton_crear.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_crear.setText("Crear nuevo item menu");
        boton_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_crearActionPerformed(evt);
            }
        });

        tabla.setBackground(new java.awt.Color(255, 102, 102));
        tabla.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Dirección", "Coordenadas", "Acciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setRowHeight(30);
        jScrollPane1.setViewportView(tabla);

        boton_editar.setBackground(new java.awt.Color(255, 102, 153));
        boton_editar.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_editar.setText("Editar");
        boton_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_editarActionPerformed(evt);
            }
        });

        boton_eliminar.setBackground(new java.awt.Color(255, 102, 153));
        boton_eliminar.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_eliminar.setText("Eliminar");
        boton_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_eliminarActionPerformed(evt);
            }
        });

        panel_filtros.setLayout(new java.awt.GridBagLayout());

        label_buscar3.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_buscar3.setText("FILTROS");
        label_buscar3.setMinimumSize(new java.awt.Dimension(75, 42));
        label_buscar3.setPreferredSize(new java.awt.Dimension(75, 42));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_buscar3, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_buscar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_filtros.add(jPanel4, new java.awt.GridBagConstraints());

        filtro_tipo.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        filtro_tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Comida", "Bebida" }));
        filtro_tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtro_tipoActionPerformed(evt);
            }
        });

        label_buscar4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar4.setText("Tipo:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_buscar4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtro_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filtro_tipo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_buscar4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_filtros.add(jPanel5, new java.awt.GridBagConstraints());

        filtro_nombre.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        filtro_nombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        filtro_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtro_nombreActionPerformed(evt);
            }
        });
        filtro_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtro_nombreKeyReleased(evt);
            }
        });

        label_buscar.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar.setText("Nombre:");
        label_buscar.setMinimumSize(new java.awt.Dimension(75, 42));
        label_buscar.setPreferredSize(new java.awt.Dimension(75, 42));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtro_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(filtro_nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap())
        );

        panel_filtros.add(jPanel1, new java.awt.GridBagConstraints());

        label_buscar1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar1.setText("ID vendedor:");

        filtro_vendedor.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        filtro_vendedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        filtro_vendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtro_vendedorActionPerformed(evt);
            }
        });
        filtro_vendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtro_vendedorKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtro_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtro_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_filtros.add(jPanel2, new java.awt.GridBagConstraints());

        filtro_categoria.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        filtro_categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas" }));
        filtro_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtro_categoriaActionPerformed(evt);
            }
        });

        label_buscar2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar2.setText("Categoria:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_buscar2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtro_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filtro_categoria, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_buscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_filtros.add(jPanel3, new java.awt.GridBagConstraints());

        boton_detalles.setBackground(new java.awt.Color(255, 255, 153));
        boton_detalles.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_detalles.setText("Mostrar detalles");
        boton_detalles.setPreferredSize(new java.awt.Dimension(150, 28));
        boton_detalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_detallesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_infoLayout = new javax.swing.GroupLayout(panel_info);
        panel_info.setLayout(panel_infoLayout);
        panel_infoLayout.setHorizontalGroup(
            panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_info_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_infoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_infoLayout.createSequentialGroup()
                        .addComponent(boton_crear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boton_detalles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(boton_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(boton_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(panel_infoLayout.createSequentialGroup()
                        .addComponent(panel_filtros, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_infoLayout.setVerticalGroup(
            panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_infoLayout.createSequentialGroup()
                .addComponent(panel_info_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_filtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_crear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_detalles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_info, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void boton_siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_siguienteActionPerformed
        switch((String) combo_box_tipo.getSelectedItem()){
            case "Comida":
                if(boton_siguiente.getClientProperty("tipoAccion") == "crear") mostrarCrearComida();
                else mostrarEditarComida((int) boton_siguiente.getClientProperty("id"));
            break;
            case "Bebida":
                if(boton_siguiente.getClientProperty("tipoAccion") == "crear") mostrarCrearBebida();
                else mostrarEditarBebida((int) boton_siguiente.getClientProperty("id"));
            break;
        }

        modal.dispose();
    }//GEN-LAST:event_boton_siguienteActionPerformed

    private void boton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelarActionPerformed
        modal.dispose();
    }//GEN-LAST:event_boton_cancelarActionPerformed

    private void boton_confirmar_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confirmar_eliminarActionPerformed

        int id = (Integer) boton_confirmar_eliminar.getClientProperty("id");

        eliminar(id);
        modal_eliminar.dispose();
    }//GEN-LAST:event_boton_confirmar_eliminarActionPerformed

    private void boton_cancelar_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelar_eliminarActionPerformed
        modal_eliminar.dispose();
    }//GEN-LAST:event_boton_cancelar_eliminarActionPerformed

    private void boton_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_crearActionPerformed
        mostrarCrear();
    }//GEN-LAST:event_boton_crearActionPerformed

    private void filtro_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filtro_nombreKeyReleased
        filtrar();
    }//GEN-LAST:event_filtro_nombreKeyReleased

    private void boton_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_editarActionPerformed
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {  // Verifica si hay una fila seleccionada

            mostrarEditar(filaSeleccionada);

            mostrarModal(modal);

        } else {
            JOptionPane.showMessageDialog(panel_info, "Por favor, selecciona una fila para editar.");
        }
    }//GEN-LAST:event_boton_editarActionPerformed

    private void boton_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_eliminarActionPerformed
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {

            mostrarEliminar(filaSeleccionada);

            mostrarModalEliminar(modal_eliminar);
        } else {
            JOptionPane.showMessageDialog(panel_info, "Por favor, selecciona una fila para eliminar.");
        }
    }//GEN-LAST:event_boton_eliminarActionPerformed

    private void filtro_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtro_nombreActionPerformed
        filtrar();
    }//GEN-LAST:event_filtro_nombreActionPerformed

    private void filtro_vendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtro_vendedorActionPerformed
        filtrar();
    }//GEN-LAST:event_filtro_vendedorActionPerformed

    private void filtro_vendedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filtro_vendedorKeyReleased
        filtrar();
    }//GEN-LAST:event_filtro_vendedorKeyReleased

    private void boton_confirmar_bebidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confirmar_bebidaActionPerformed
       switch((String) boton_confirmar_bebida.getClientProperty("tipoAccion")){
            case "crear":
            crearBebida();
            break;
            case "editar":
            editarBebida((Integer) boton_confirmar_bebida.getClientProperty("id"));
            break;
        }
       modal_bebida.dispose();
    }//GEN-LAST:event_boton_confirmar_bebidaActionPerformed

    private void combo_box_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_categoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_box_categoriaActionPerformed

    private void text_field_vendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_field_vendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_field_vendedorActionPerformed

    private void text_field_graduacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_field_graduacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_field_graduacionActionPerformed

    private void combo_box_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_tipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_box_tipoActionPerformed

    private void boton_confirmar_comidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confirmar_comidaActionPerformed
        switch((String) boton_confirmar_comida.getClientProperty("tipoAccion")){
            case "crear":
            crearComida();
            break;
            case "editar":
            editarComida((Integer) boton_confirmar_comida.getClientProperty("id"));
            break;
        }
        modal_comida.dispose();
    }//GEN-LAST:event_boton_confirmar_comidaActionPerformed

    private void boton_cancelar_comidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelar_comidaActionPerformed
        modal_comida.dispose();
    }//GEN-LAST:event_boton_cancelar_comidaActionPerformed

    private void apto_veganoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apto_veganoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apto_veganoActionPerformed

    private void text_field_pesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_field_pesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_field_pesoActionPerformed

    private void filtro_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtro_categoriaActionPerformed
        filtrar();
    }//GEN-LAST:event_filtro_categoriaActionPerformed

    private void filtro_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtro_tipoActionPerformed
        filtrar();
    }//GEN-LAST:event_filtro_tipoActionPerformed

    private void text_field_caloriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_field_caloriasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_field_caloriasActionPerformed

    private void boton_detallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_detallesActionPerformed
        
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {  // Verifica si hay una fila seleccionada

            mostrarDetalles(tabla.getSelectedRow());

        } else {
            JOptionPane.showMessageDialog(panel_info, "Por favor, selecciona una fila para editar.");
        }
    }//GEN-LAST:event_boton_detallesActionPerformed

    private void boton_continuar_detalle_bebidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_continuar_detalle_bebidaActionPerformed
        modal_detalles_bebida.dispose();
    }//GEN-LAST:event_boton_continuar_detalle_bebidaActionPerformed

    private void detalle_graduacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detalle_graduacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_graduacionActionPerformed

    private void boton_cancelar_bebidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelar_bebidaActionPerformed
        modal_bebida.dispose();
    }//GEN-LAST:event_boton_cancelar_bebidaActionPerformed

    private void boton_continuar_detalle_comidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_continuar_detalle_comidaActionPerformed
        modal_detalles_comida.dispose();
    }//GEN-LAST:event_boton_continuar_detalle_comidaActionPerformed

    private void detalle_veganoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detalle_veganoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_veganoActionPerformed

    private void detalle_pesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detalle_pesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_pesoActionPerformed

    private void detalle_caloriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detalle_caloriasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_caloriasActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox apto_celiaco;
    private javax.swing.JCheckBox apto_vegano;
    private javax.swing.JButton boton_cancelar;
    private javax.swing.JButton boton_cancelar_bebida;
    private javax.swing.JButton boton_cancelar_comida;
    private javax.swing.JButton boton_cancelar_eliminar;
    private javax.swing.JButton boton_confirmar_bebida;
    private javax.swing.JButton boton_confirmar_comida;
    private javax.swing.JButton boton_confirmar_eliminar;
    private javax.swing.JButton boton_continuar_detalle_bebida;
    private javax.swing.JButton boton_continuar_detalle_comida;
    private javax.swing.JButton boton_crear;
    private javax.swing.JButton boton_detalles;
    private javax.swing.JButton boton_editar;
    private javax.swing.JButton boton_eliminar;
    private javax.swing.JButton boton_siguiente;
    private javax.swing.JComboBox<String> combo_box_categoria;
    private javax.swing.JComboBox<String> combo_box_tipo;
    private javax.swing.JTextField detalle_calorias;
    private javax.swing.JCheckBox detalle_celiaco;
    private javax.swing.JTextField detalle_graduacion;
    private javax.swing.JTextField detalle_peso;
    private javax.swing.JTextField detalle_tam;
    private javax.swing.JCheckBox detalle_vegano;
    private javax.swing.JComboBox<String> filtro_categoria;
    private javax.swing.JTextField filtro_nombre;
    private javax.swing.JComboBox<String> filtro_tipo;
    private javax.swing.JTextField filtro_vendedor;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label_buscar;
    private javax.swing.JLabel label_buscar1;
    private javax.swing.JLabel label_buscar2;
    private javax.swing.JLabel label_buscar3;
    private javax.swing.JLabel label_buscar4;
    private javax.swing.JDialog modal;
    private javax.swing.JDialog modal_bebida;
    private javax.swing.JDialog modal_comida;
    private javax.swing.JDialog modal_detalles_bebida;
    private javax.swing.JDialog modal_detalles_comida;
    private javax.swing.JDialog modal_eliminar;
    private javax.swing.JPanel panel_filtros;
    private javax.swing.JPanel panel_info;
    private javax.swing.JLabel panel_info_titulo;
    private javax.swing.JPanel panel_modal;
    private javax.swing.JPanel panel_modal1;
    private javax.swing.JPanel panel_modal2;
    private javax.swing.JPanel panel_modal3;
    private javax.swing.JPanel panel_modal4;
    private javax.swing.JPanel panel_modal_eliminar;
    private javax.swing.JTable tabla;
    private javax.swing.JTextArea text_area_descripcion;
    private javax.swing.JTextField text_field_calorias;
    private javax.swing.JTextField text_field_graduacion;
    private javax.swing.JTextField text_field_nombre;
    private javax.swing.JTextField text_field_peso;
    private javax.swing.JTextField text_field_precio;
    private javax.swing.JTextField text_field_tam;
    private javax.swing.JTextField text_field_vendedor;
    private javax.swing.JLabel titulo_modal;
    private javax.swing.JLabel titulo_modal_bebida;
    private javax.swing.JLabel titulo_modal_comida;
    private javax.swing.JLabel titulo_modal_detalle_bebida;
    private javax.swing.JLabel titulo_modal_detalle_comida;
    private javax.swing.JLabel titulo_modal_eliminar;
    // End of variables declaration//GEN-END:variables
}
