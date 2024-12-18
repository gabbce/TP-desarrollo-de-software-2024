/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package isi.deso.tpdeso2024.utils.paneles;

import isi.deso.tpdeso2024.controllers.VendedorController;
import isi.deso.tpdeso2024.dtos.CoordenadaDTO;
import isi.deso.tpdeso2024.dtos.VendedorDTO;
import isi.deso.tpdeso2024.utils.modelosTablas.ModeloTablaVendedor;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabic
 */
public class PanelVendedor extends javax.swing.JPanel implements PanelInformacion{
    private final VendedorController vendedorController = VendedorController.getInstance();
    
    ModeloTablaVendedor modeloVendedor;
    
    
    /**
     * Creates new form PanelVendedor
     */
    public PanelVendedor() {
        initComponents();
        
        inicializarTabla();
        this.modeloVendedor = new ModeloTablaVendedor();
        this.modeloVendedor.setNombreColumnas(List.of("Id", "Nombre", "Dirección", "Coordenadas"/*, "Latitud", "Longitud"*/));
        tabla.setModel(modeloVendedor);
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
        ((AbstractTableModel)tabla.getModel()).fireTableChanged(null);
    }
    
    @Override
    public void cerrarModales() {
       modal.dispose();
       modal_eliminar.dispose();
    }
    
    @Override
    public Boolean validarDatos() {
        try {
            // Validar el nombre
            String nombre = text_field_nombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(modal, "El nombre no puede estar vacío.");
                return false;
            }
            if (nombre.length() > 30) {
                JOptionPane.showMessageDialog(modal, "El nombre no puede exceder los 30 caracteres.");
                return false;
            }
            if (!nombre.matches("[a-zA-ZÁÉÍÓÚÑáéíóúñ\\s]+")) {
                JOptionPane.showMessageDialog(modal, "El nombre solo puede contener letras");
                return false;
            }

            // Validar la dirección
            String direccion = text_field_direccion.getText().trim();
            if (direccion.isEmpty()) {
                JOptionPane.showMessageDialog(modal, "La dirección no puede estar vacía.");
                return false;
            }
            if (direccion.length() > 50) {
                JOptionPane.showMessageDialog(modal, "La dirección no puede exceder los 50 caracteres.");
                return false;
            }
            if (!direccion.matches("[a-zA-ZÁÉÍÓÚÑáéíóúñ0-9\\s]+")) {
                JOptionPane.showMessageDialog(modal, "La dirección solo puede contener letras y números");
                return false;
            }

            // Validar la latitud
            String latitudTexto = text_field_latitud.getText().trim();
            if (latitudTexto.isEmpty()) {
                JOptionPane.showMessageDialog(modal, "La latitud no puede estar vacía.");
                return false;
            }

            Float latitud;
            try {
                latitud = Float.parseFloat(latitudTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(modal, "La latitud debe ser un número válido.");
                return false;
            }

            if (latitud < -90 || latitud > 90) {
                JOptionPane.showMessageDialog(modal, "La latitud debe estar entre -90 y 90 grados.");
                return false;
            }
            if (latitudTexto.contains(".") && latitudTexto.substring(latitudTexto.indexOf(".") + 1).length() > 2) {
                JOptionPane.showMessageDialog(modal, "La latitud no puede tener más de dos decimales.");
                return false;
            }

            // Validar la longitud
            String longitudTexto = text_field_longitud.getText().trim();
            if (longitudTexto.isEmpty()) {
                JOptionPane.showMessageDialog(modal, "La longitud no puede estar vacía.");
                return false;
            }

            Float longitud;
            try {
                longitud = Float.parseFloat(longitudTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(modal, "La longitud debe ser un número válido.");
                return false;
            }

            if (longitud < -180 || longitud > 180) {
                JOptionPane.showMessageDialog(modal, "La longitud debe estar entre -180 y 180 grados.");
                return false;
            }
            if (longitudTexto.contains(".") && longitudTexto.substring(longitudTexto.indexOf(".") + 1).length() > 2) {
                JOptionPane.showMessageDialog(modal, "La longitud no puede tener más de dos decimales.");
                return false;
            }

            // Si todos los datos son válidos
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(modal, "Ocurrió un error inesperado al validar los datos.");
            return false;
        }
    }

    
    @Override
    public void mostrarEliminar(int filaSeleccionada) {
        int id = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        titulo_modal_eliminar.setText("Se eliminará el vendedor " + id);
        boton_confirmar_eliminar.putClientProperty("id", id);
    }
    
    @Override
    public void eliminar(int id){
        try {
            vendedorController.eliminar(id);
            ((ModeloTablaVendedor) tabla.getModel()).resetListaVendedores();
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
        String direccion = (String) tabla.getValueAt(filaSeleccionada, 2);
        String coordenada = (String) tabla.getValueAt(filaSeleccionada, 3);
        
        // Quitar paréntesis y dividir por el punto y coma
        String[] partes = coordenada.replace("(", "").replace(")", "").split(";");
        
        String latitud = partes[0].trim();
        String longitud = partes[1].trim();

        text_field_nombre.setText(nombre);
        text_field_direccion.setText(direccion);
        text_field_latitud.setText(latitud);
        text_field_longitud.setText(longitud);

        boton_confirmar.putClientProperty("tipoAccion", "editar");
        boton_confirmar.putClientProperty("id", id);
        boton_confirmar.setText("Confirmar");
        titulo_modal.setText("Editar datos del vendedor " + id);
    }
    
    @Override
    public void editar(int id) {
        VendedorDTO vendedorDTO = new VendedorDTO(id, text_field_nombre.getText(),
                text_field_direccion.getText(),
                new CoordenadaDTO(Double.valueOf(text_field_latitud.getText()), Double.valueOf(text_field_longitud.getText()))
        );

        try {
            vendedorController.actualizar(vendedorDTO);
            ((ModeloTablaVendedor) tabla.getModel()).resetListaVendedores();
            ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
            JOptionPane.showMessageDialog(modal, "Vendedor actualizado exitosamente.");
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal, "Error al actualizar el vendedor.");
        }
    }

    //@Override
    public void buscar(String nombre) {
        if("".equals(nombre)){
            ((ModeloTablaVendedor) tabla.getModel()).resetListaVendedores();
        }
        else{
            ((ModeloTablaVendedor) tabla.getModel()).actualizarListaVendedores(vendedorController.buscar(nombre));
            System.out.println(vendedorController.buscar(nombre));
        }
        
        ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
    }

    @Override
    public void mostrarCrear() {
       
        text_field_nombre.setText("");
        text_field_direccion.setText("");
        text_field_latitud.setText("");
        text_field_longitud.setText("");
        
       boton_confirmar.putClientProperty("tipoAccion", "crear");
       
       boton_confirmar.setText("Crear");
       titulo_modal.setText("Completar datos del nuevo vendedor");
       
    }
    
    @Override
    public void crear() {
        VendedorDTO vendedorDTO = new VendedorDTO(text_field_nombre.getText(),
                text_field_direccion.getText(),
                new CoordenadaDTO(Double.valueOf(text_field_latitud.getText()), Double.valueOf(text_field_longitud.getText()))
        );

        try {
            vendedorController.crear(vendedorDTO);
            ((ModeloTablaVendedor) tabla.getModel()).resetListaVendedores();
            ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
            JOptionPane.showMessageDialog(modal, "Vendedor creado exitosamente.");
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal, "Error al crear el vendedor.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modal_eliminar = new javax.swing.JDialog();
        panel_modal_eliminar = new javax.swing.JPanel();
        boton_confirmar_eliminar = new javax.swing.JButton();
        boton_cancelar_eliminar = new javax.swing.JButton();
        titulo_modal_eliminar = new javax.swing.JLabel();
        modal = new javax.swing.JDialog();
        panel_modal = new javax.swing.JPanel();
        boton_confirmar = new javax.swing.JButton();
        boton_cancelar = new javax.swing.JButton();
        titulo_modal = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        text_field_direccion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        text_field_latitud = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        text_field_longitud = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        text_field_nombre = new javax.swing.JTextField();
        panel_info = new javax.swing.JPanel();
        panel_info_titulo = new javax.swing.JLabel();
        boton_crear = new javax.swing.JButton();
        label_buscar = new javax.swing.JLabel();
        text_field_buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        boton_editar = new javax.swing.JButton();
        boton_eliminar = new javax.swing.JButton();

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

        panel_modal.setBackground(new java.awt.Color(255, 204, 204));

        boton_confirmar.setBackground(new java.awt.Color(0, 102, 0));
        boton_confirmar.setForeground(new java.awt.Color(255, 255, 255));
        boton_confirmar.setText("Crear");
        boton_confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confirmarActionPerformed(evt);
            }
        });

        boton_cancelar.setBackground(new java.awt.Color(102, 0, 0));
        boton_cancelar.setForeground(new java.awt.Color(255, 255, 255));
        boton_cancelar.setText("Cancelar");
        boton_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelarActionPerformed(evt);
            }
        });

        titulo_modal.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        titulo_modal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_modal.setText("Completar datos del nuevo vendedor");

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Dirección");

        text_field_direccion.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_direccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_direccionActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Latitud");

        text_field_latitud.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Longitud");

        text_field_longitud.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Nombre");

        text_field_nombre.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        javax.swing.GroupLayout panel_modalLayout = new javax.swing.GroupLayout(panel_modal);
        panel_modal.setLayout(panel_modalLayout);
        panel_modalLayout.setHorizontalGroup(
            panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo_modal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_modalLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(boton_cancelar)
                .addGap(90, 90, 90)
                .addComponent(boton_confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_modalLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(text_field_direccion, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_field_latitud, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_field_longitud, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_field_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        panel_modalLayout.setVerticalGroup(
            panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modalLayout.createSequentialGroup()
                .addComponent(titulo_modal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(text_field_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(text_field_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(text_field_latitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(text_field_longitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_cancelar)
                    .addComponent(boton_confirmar))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout modalLayout = new javax.swing.GroupLayout(modal.getContentPane());
        modal.getContentPane().setLayout(modalLayout);
        modalLayout.setHorizontalGroup(
            modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(modalLayout.createSequentialGroup()
                    .addComponent(panel_modal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        modalLayout.setVerticalGroup(
            modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setPreferredSize(new java.awt.Dimension(800, 610));
        addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                formHierarchyChanged(evt);
            }
        });

        panel_info.setBackground(new java.awt.Color(255, 255, 255));

        panel_info_titulo.setBackground(new java.awt.Color(255, 255, 153));
        panel_info_titulo.setFont(new java.awt.Font("Comic Sans MS", 1, 36)); // NOI18N
        panel_info_titulo.setForeground(new java.awt.Color(102, 0, 102));
        panel_info_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel_info_titulo.setText("Lista de vendedores");
        panel_info_titulo.setOpaque(true);

        boton_crear.setBackground(new java.awt.Color(255, 102, 153));
        boton_crear.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_crear.setText("Crear nuevo vendedor");
        boton_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_crearActionPerformed(evt);
            }
        });

        label_buscar.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar.setText("Buscar por nombre:");

        text_field_buscar.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_buscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        text_field_buscar.setPreferredSize(new java.awt.Dimension(50, 29));
        text_field_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_field_buscarKeyReleased(evt);
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

        javax.swing.GroupLayout panel_infoLayout = new javax.swing.GroupLayout(panel_info);
        panel_info.setLayout(panel_infoLayout);
        panel_infoLayout.setHorizontalGroup(
            panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_info_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_infoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_infoLayout.createSequentialGroup()
                        .addComponent(label_buscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_field_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_infoLayout.createSequentialGroup()
                        .addComponent(boton_crear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 317, Short.MAX_VALUE)
                        .addComponent(boton_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boton_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_infoLayout.setVerticalGroup(
            panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_infoLayout.createSequentialGroup()
                .addComponent(panel_info_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_field_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_crear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
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

    private void boton_confirmar_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confirmar_eliminarActionPerformed

        int id = (Integer) boton_confirmar_eliminar.getClientProperty("id");

        eliminar(id);
        modal_eliminar.dispose();
    }//GEN-LAST:event_boton_confirmar_eliminarActionPerformed

    private void boton_cancelar_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelar_eliminarActionPerformed
        modal_eliminar.dispose();
    }//GEN-LAST:event_boton_cancelar_eliminarActionPerformed

    private void boton_confirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confirmarActionPerformed

        if(validarDatos()){
            switch((String) boton_confirmar.getClientProperty("tipoAccion")){
            case "crear":
            crear();
            break;
            case "editar":
            editar((Integer) boton_confirmar.getClientProperty("id"));
            break;
        }

            modal.dispose();
        }
        
        
    }//GEN-LAST:event_boton_confirmarActionPerformed

    private void boton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelarActionPerformed
        modal.dispose();
    }//GEN-LAST:event_boton_cancelarActionPerformed

    private void text_field_direccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_field_direccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_field_direccionActionPerformed

    private void boton_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_crearActionPerformed
        mostrarCrear();
        mostrarModal(modal);
    }//GEN-LAST:event_boton_crearActionPerformed

    private void text_field_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_field_buscarKeyReleased
        buscar(text_field_buscar.getText());
    }//GEN-LAST:event_text_field_buscarKeyReleased

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

    private void formHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_formHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_formHierarchyChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_cancelar;
    private javax.swing.JButton boton_cancelar_eliminar;
    private javax.swing.JButton boton_confirmar;
    private javax.swing.JButton boton_confirmar_eliminar;
    private javax.swing.JButton boton_crear;
    private javax.swing.JButton boton_editar;
    private javax.swing.JButton boton_eliminar;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_buscar;
    private javax.swing.JDialog modal;
    private javax.swing.JDialog modal_eliminar;
    private javax.swing.JPanel panel_info;
    private javax.swing.JLabel panel_info_titulo;
    private javax.swing.JPanel panel_modal;
    private javax.swing.JPanel panel_modal_eliminar;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField text_field_buscar;
    private javax.swing.JTextField text_field_direccion;
    private javax.swing.JTextField text_field_latitud;
    private javax.swing.JTextField text_field_longitud;
    private javax.swing.JTextField text_field_nombre;
    private javax.swing.JLabel titulo_modal;
    private javax.swing.JLabel titulo_modal_eliminar;
    // End of variables declaration//GEN-END:variables
}
