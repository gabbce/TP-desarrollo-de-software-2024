/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package isi.deso.tpdeso2024;

/**
 *
 * @author gabic
 */


import isi.deso.tpdeso2024.controllers.*;
import isi.deso.tpdeso2024.dtos.*;
import isi.deso.tpdeso2024.utils.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

import java.awt.CardLayout;
import java.util.Arrays;

public class InterfazGeneral extends javax.swing.JFrame {

    private final InterfazVendedor interfazVendedor;
    private final InterfazCliente interfazCliente;
    private final InterfazItemMenu interfazItemMenu;
    private final InterfazPedido interfazPedido;
    private InformacionInterfaz interfazActual;
    
    private JDialog modal_actual;
    private final VendedorController vendedorController;
    private CardLayout cardLayout;
    
    private ButtonGroup tipo_pago_group;
    
    
    /**
     * Creates new form VendedorInterface
     */
    public InterfazGeneral() {
        initComponents();
        vendedorController = VendedorController.getInstance();
        
        inicializarNombreComponentes();
        
        interfazVendedor = new InterfazVendedor(panel_info, modal_vendedor, modal_eliminar);
        interfazCliente = new InterfazCliente(panel_info, modal_cliente, modal_eliminar);
        interfazItemMenu = new InterfazItemMenu(panel_info, modal_item_menu, modal_eliminar);
        interfazPedido = new InterfazPedido(panel_info, modal_pedido, modal_eliminar);
        
        inicializarTabla(tabla);
        
        //interfazVendedor.setModelTabla();
        /*setModelTablaCliente();
        setModelTablaItemsMenu();
        setModelTablaPedido();*/
        
        
        /*this.cardLayout = new CardLayout(); // Crea el CardLayout
        panel_Informacion.setLayout(this.cardLayout); // Configura el layout

        // Añade los paneles con nombres para el CardLayout
        panel_Informacion.add(panel_info, "Vendedores");
        panel_Informacion.add(panel_clientes, "Clientes");
        panel_Informacion.add(panel_items_menu, "ItemMenus");
        panel_Informacion.add(panel_pedidos, "Pedidos");*/
        
        cambiarPanel(interfazVendedor, modal_vendedor);
        
    }
    
    public void inicializarNombreComponentes(){
        // Generales
        tabla.setName("tabla");
        label_titulo_modal_eliminar.setName("label_titulo_modal_eliminar");
        boton_confirmar_eliminar.setName("boton_confirmar_eliminar");
        boton_crear.setName("boton_crear");
        panel_info_titulo.setName("panel_info_titulo");
        text_field_buscar.setName("text_field_buscar");
        label_buscar.setName("label_buscar");
        
        // Vendedor
        text_field_nombre_v.setName("text_field_nombre_v");
        text_field_direccion_v.setName("text_field_direccion_v");
        text_field_latitud_v.setName("text_field_latitud_v");
        text_field_longitud_v.setName("text_field_longitud_v");
        label_titulo_modal_vendedor.setName("label_titulo_modal_vendedor");
        boton_confirmar_vendedor.setName("boton_confirmar_vendedor");
        
        //Cliente
        text_field_cuit_c.setName("text_field_cuit_c");
        text_field_email_c.setName("text_field_email_c");
        text_field_direccion_c.setName("text_field_direccion_c");
        text_field_latitud_c.setName("text_field_latitud_c");
        text_field_longitud_c.setName("text_field_longitud_c");
        label_titulo_modal_cliente.setName("label_titulo_modal_cliente");
        boton_confirmar_cliente.setName("boton_confirmar_cliente");
        
        // Pedido
        text_field_cliente_p.setName("text_field_cliente_p");
        rbutton_mp_p.setName("rbutton_mp_p");
        rbutton_transferencia_p.setName("rbutton_transferencia_p");
        label_titulo_modal_pedido.setName("label_titulo_modal_pedido");
        boton_confirmar_pedido.setName("boton_confirmar_pedido");
        
        
        tipo_pago_group = new ButtonGroup();
        tipo_pago_group.add(rbutton_mp_p);
        tipo_pago_group.add(rbutton_transferencia_p);
        
        // Item Menu
        text_field_nombre_i.setName("text_field_nombre_i");
        text_field_precio_i.setName("text_field_precio_i");
        text_area_descripcion_i.setName("text_area_descripcion_i");
        text_field_categoria_i.setName("text_field_categoria_i");
        text_field_vendedor_i.setName("text_field_vendedor_i");
        label_titulo_modal_item_menu.setName("label_titulo_modal_item_menu");
        boton_confirmar_item_menu.setName("boton_confirmar_item_menu");
        
    }
    
    public void inicializarTabla(JTable tabla){
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(false);  
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        tabla.setSelectionBackground(Color.LIGHT_GRAY);
        tabla.setSelectionForeground(Color.BLUE); 
    }

    /**
     * Creates new form NewJFrame
     */
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modal_vendedor = new javax.swing.JDialog();
        panel_modal_vendedor = new javax.swing.JPanel();
        boton_confirmar_vendedor = new javax.swing.JButton();
        boton_cancelar_vendedor = new javax.swing.JButton();
        label_titulo_modal_vendedor = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        text_field_direccion_v = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        text_field_latitud_v = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        text_field_longitud_v = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        text_field_nombre_v = new javax.swing.JTextField();
        modal_eliminar = new javax.swing.JDialog();
        panel_modal_eliminar = new javax.swing.JPanel();
        boton_confirmar_eliminar = new javax.swing.JButton();
        boton_cancelar_eliminar = new javax.swing.JButton();
        label_titulo_modal_eliminar = new javax.swing.JLabel();
        modal_cliente = new javax.swing.JDialog();
        panel_modal_cliente = new javax.swing.JPanel();
        boton_confirmar_cliente = new javax.swing.JButton();
        boton_cancelar_cliente = new javax.swing.JButton();
        label_titulo_modal_cliente = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        text_field_direccion_c = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        text_field_latitud_c = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        text_field_longitud_c = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        text_field_cuit_c = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        text_field_email_c = new javax.swing.JTextField();
        modal_item_menu = new javax.swing.JDialog();
        panel_modal_item_menu = new javax.swing.JPanel();
        boton_confirmar_item_menu = new javax.swing.JButton();
        boton_cancelar_item_menu = new javax.swing.JButton();
        label_titulo_modal_item_menu = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        text_field_vendedor_i = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        text_field_categoria_i = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        text_field_nombre_i = new javax.swing.JTextField();
        text_field_precio_i = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        text_area_descripcion_i = new javax.swing.JTextArea();
        modal_pedido = new javax.swing.JDialog();
        panel_modal_pedido = new javax.swing.JPanel();
        boton_confirmar_pedido = new javax.swing.JButton();
        boton_cancelar_pedido = new javax.swing.JButton();
        label_titulo_modal_pedido = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        text_field_cliente_p = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        rbutton_transferencia_p = new javax.swing.JRadioButton();
        rbutton_mp_p = new javax.swing.JRadioButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        panel_botones = new javax.swing.JPanel();
        botonListaVendedores = new javax.swing.JButton();
        botonListaClientes = new javax.swing.JButton();
        botonListaItemsMenu = new javax.swing.JButton();
        botonListaPedidos = new javax.swing.JButton();
        panel_Informacion = new javax.swing.JPanel();
        panel_info = new javax.swing.JPanel();
        panel_info_titulo = new javax.swing.JLabel();
        boton_crear = new javax.swing.JButton();
        label_buscar = new javax.swing.JLabel();
        text_field_buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        boton_editar = new javax.swing.JButton();
        boton_eliminar = new javax.swing.JButton();

        panel_modal_vendedor.setBackground(new java.awt.Color(255, 204, 204));

        boton_confirmar_vendedor.setBackground(new java.awt.Color(0, 102, 0));
        boton_confirmar_vendedor.setText("Crear");
        boton_confirmar_vendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confirmar_vendedorActionPerformed(evt);
            }
        });

        boton_cancelar_vendedor.setBackground(new java.awt.Color(102, 0, 0));
        boton_cancelar_vendedor.setText("Cancelar");
        boton_cancelar_vendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelar_vendedorActionPerformed(evt);
            }
        });

        label_titulo_modal_vendedor.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_titulo_modal_vendedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_titulo_modal_vendedor.setText("Completar datos del nuevo vendedor");

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setLabelFor(text_field_direccion_v);
        jLabel4.setText("Dirección");

        text_field_direccion_v.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_direccion_v.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_direccion_vActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setLabelFor(text_field_direccion_v);
        jLabel8.setText("Latitud");

        text_field_latitud_v.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setLabelFor(text_field_direccion_v);
        jLabel9.setText("Longitud");

        text_field_longitud_v.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setLabelFor(text_field_direccion_v);
        jLabel11.setText("Nombre");

        text_field_nombre_v.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        javax.swing.GroupLayout panel_modal_vendedorLayout = new javax.swing.GroupLayout(panel_modal_vendedor);
        panel_modal_vendedor.setLayout(panel_modal_vendedorLayout);
        panel_modal_vendedorLayout.setHorizontalGroup(
            panel_modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_modal_vendedorLayout.createSequentialGroup()
                .addContainerGap(115, Short.MAX_VALUE)
                .addComponent(boton_cancelar_vendedor)
                .addGap(97, 97, 97)
                .addComponent(boton_confirmar_vendedor)
                .addGap(101, 101, 101))
            .addComponent(label_titulo_modal_vendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_modal_vendedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_field_longitud_v)
                    .addComponent(text_field_latitud_v)
                    .addComponent(text_field_nombre_v)
                    .addComponent(text_field_direccion_v))
                .addContainerGap())
        );
        panel_modal_vendedorLayout.setVerticalGroup(
            panel_modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal_vendedorLayout.createSequentialGroup()
                .addComponent(label_titulo_modal_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(text_field_nombre_v, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(text_field_direccion_v, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(text_field_latitud_v, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(text_field_longitud_v, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(panel_modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_confirmar_vendedor)
                    .addComponent(boton_cancelar_vendedor))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout modal_vendedorLayout = new javax.swing.GroupLayout(modal_vendedor.getContentPane());
        modal_vendedor.getContentPane().setLayout(modal_vendedorLayout);
        modal_vendedorLayout.setHorizontalGroup(
            modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 467, Short.MAX_VALUE)
            .addGroup(modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal_vendedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modal_vendedorLayout.setVerticalGroup(
            modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
            .addGroup(modal_vendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal_vendedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        modal_eliminar.setBackground(new java.awt.Color(51, 0, 51));

        panel_modal_eliminar.setBackground(new java.awt.Color(204, 204, 204));

        boton_confirmar_eliminar.setBackground(new java.awt.Color(0, 102, 0));
        boton_confirmar_eliminar.setText("Confirmar");
        boton_confirmar_eliminar.setMaximumSize(new java.awt.Dimension(76, 23));
        boton_confirmar_eliminar.setMinimumSize(new java.awt.Dimension(76, 23));
        boton_confirmar_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confirmar_eliminarActionPerformed(evt);
            }
        });

        boton_cancelar_eliminar.setBackground(new java.awt.Color(102, 0, 0));
        boton_cancelar_eliminar.setText("Cancelar");
        boton_cancelar_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelar_eliminarActionPerformed(evt);
            }
        });

        label_titulo_modal_eliminar.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_titulo_modal_eliminar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_titulo_modal_eliminar.setText("Se eliminará el *objeto* *id*, seguro?");

        javax.swing.GroupLayout panel_modal_eliminarLayout = new javax.swing.GroupLayout(panel_modal_eliminar);
        panel_modal_eliminar.setLayout(panel_modal_eliminarLayout);
        panel_modal_eliminarLayout.setHorizontalGroup(
            panel_modal_eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_titulo_modal_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
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
                .addComponent(label_titulo_modal_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        panel_modal_cliente.setBackground(new java.awt.Color(255, 204, 204));

        boton_confirmar_cliente.setBackground(new java.awt.Color(0, 102, 0));
        boton_confirmar_cliente.setText("Crear");
        boton_confirmar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confirmar_clienteActionPerformed(evt);
            }
        });

        boton_cancelar_cliente.setBackground(new java.awt.Color(102, 0, 0));
        boton_cancelar_cliente.setText("Cancelar");
        boton_cancelar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelar_clienteActionPerformed(evt);
            }
        });

        label_titulo_modal_cliente.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_titulo_modal_cliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_titulo_modal_cliente.setText("Completar datos del nuevo cliente");

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setLabelFor(text_field_direccion_v);
        jLabel5.setText("Dirección");

        text_field_direccion_c.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_direccion_c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_direccion_cActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setLabelFor(text_field_direccion_v);
        jLabel10.setText("Latitud");

        text_field_latitud_c.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setLabelFor(text_field_direccion_v);
        jLabel12.setText("Longitud");

        text_field_longitud_c.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setLabelFor(text_field_direccion_v);
        jLabel13.setText("Cuit");

        text_field_cuit_c.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setLabelFor(text_field_direccion_v);
        jLabel6.setText("Email");

        text_field_email_c.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_email_c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_email_cActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_modal_clienteLayout = new javax.swing.GroupLayout(panel_modal_cliente);
        panel_modal_cliente.setLayout(panel_modal_clienteLayout);
        panel_modal_clienteLayout.setHorizontalGroup(
            panel_modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_modal_clienteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_cancelar_cliente)
                .addGap(97, 97, 97)
                .addComponent(boton_confirmar_cliente)
                .addGap(101, 101, 101))
            .addComponent(label_titulo_modal_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_modal_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_field_email_c)
                    .addComponent(text_field_longitud_c)
                    .addComponent(text_field_latitud_c)
                    .addComponent(text_field_cuit_c)
                    .addComponent(text_field_direccion_c, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_modal_clienteLayout.setVerticalGroup(
            panel_modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal_clienteLayout.createSequentialGroup()
                .addComponent(label_titulo_modal_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(text_field_cuit_c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(text_field_email_c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(text_field_direccion_c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(text_field_latitud_c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(text_field_longitud_c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(panel_modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_confirmar_cliente)
                    .addComponent(boton_cancelar_cliente))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout modal_clienteLayout = new javax.swing.GroupLayout(modal_cliente.getContentPane());
        modal_cliente.getContentPane().setLayout(modal_clienteLayout);
        modal_clienteLayout.setHorizontalGroup(
            modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
            .addGroup(modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(modal_clienteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_modal_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        modal_clienteLayout.setVerticalGroup(
            modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
            .addGroup(modal_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(modal_clienteLayout.createSequentialGroup()
                    .addGap(0, 3, Short.MAX_VALUE)
                    .addComponent(panel_modal_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 3, Short.MAX_VALUE)))
        );

        modal_item_menu.setBackground(new java.awt.Color(0, 0, 0));

        panel_modal_item_menu.setBackground(new java.awt.Color(255, 204, 204));

        boton_confirmar_item_menu.setBackground(new java.awt.Color(0, 102, 0));
        boton_confirmar_item_menu.setForeground(new java.awt.Color(204, 204, 204));
        boton_confirmar_item_menu.setText("Crear");
        boton_confirmar_item_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confirmar_item_menuActionPerformed(evt);
            }
        });

        boton_cancelar_item_menu.setBackground(new java.awt.Color(102, 0, 0));
        boton_cancelar_item_menu.setForeground(new java.awt.Color(204, 204, 204));
        boton_cancelar_item_menu.setText("Cancelar");
        boton_cancelar_item_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelar_item_menuActionPerformed(evt);
            }
        });

        label_titulo_modal_item_menu.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_titulo_modal_item_menu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_titulo_modal_item_menu.setText("Completar datos del nuevo item menu");

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setLabelFor(text_field_direccion_v);
        jLabel7.setText("Descripcion");

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setLabelFor(text_field_direccion_v);
        jLabel14.setText("Id vendedor");

        text_field_vendedor_i.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setLabelFor(text_field_direccion_v);
        jLabel15.setText("Id categoria");

        text_field_categoria_i.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setLabelFor(text_field_direccion_v);
        jLabel16.setText("Nombre");

        text_field_nombre_i.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        text_field_precio_i.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setLabelFor(text_field_direccion_v);
        jLabel17.setText("Precio");

        text_area_descripcion_i.setColumns(20);
        text_area_descripcion_i.setRows(5);
        jScrollPane2.setViewportView(text_area_descripcion_i);

        javax.swing.GroupLayout panel_modal_item_menuLayout = new javax.swing.GroupLayout(panel_modal_item_menu);
        panel_modal_item_menu.setLayout(panel_modal_item_menuLayout);
        panel_modal_item_menuLayout.setHorizontalGroup(
            panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_titulo_modal_item_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_modal_item_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_modal_item_menuLayout.createSequentialGroup()
                        .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel16))
                        .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_modal_item_menuLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(boton_cancelar_item_menu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(boton_confirmar_item_menu)
                                .addGap(107, 107, 107))
                            .addGroup(panel_modal_item_menuLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(text_field_nombre_i)
                                    .addComponent(jScrollPane2))
                                .addContainerGap())))
                    .addGroup(panel_modal_item_menuLayout.createSequentialGroup()
                        .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17))
                        .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_modal_item_menuLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(text_field_categoria_i, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(text_field_vendedor_i, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_modal_item_menuLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(text_field_precio_i)))
                        .addContainerGap())))
        );
        panel_modal_item_menuLayout.setVerticalGroup(
            panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal_item_menuLayout.createSequentialGroup()
                .addComponent(label_titulo_modal_item_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(text_field_nombre_i, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(text_field_precio_i, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(text_field_categoria_i, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(text_field_vendedor_i, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(panel_modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_confirmar_item_menu)
                    .addComponent(boton_cancelar_item_menu))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout modal_item_menuLayout = new javax.swing.GroupLayout(modal_item_menu.getContentPane());
        modal_item_menu.getContentPane().setLayout(modal_item_menuLayout);
        modal_item_menuLayout.setHorizontalGroup(
            modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
            .addGroup(modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal_item_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modal_item_menuLayout.setVerticalGroup(
            modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 301, Short.MAX_VALUE)
            .addGroup(modal_item_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal_item_menu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        modal_pedido.setBackground(new java.awt.Color(0, 0, 0));

        panel_modal_pedido.setBackground(new java.awt.Color(255, 204, 204));

        boton_confirmar_pedido.setBackground(new java.awt.Color(0, 102, 0));
        boton_confirmar_pedido.setForeground(new java.awt.Color(204, 204, 204));
        boton_confirmar_pedido.setText("Crear");
        boton_confirmar_pedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confirmar_pedidoActionPerformed(evt);
            }
        });

        boton_cancelar_pedido.setBackground(new java.awt.Color(102, 0, 0));
        boton_cancelar_pedido.setForeground(new java.awt.Color(204, 204, 204));
        boton_cancelar_pedido.setText("Cancelar");
        boton_cancelar_pedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelar_pedidoActionPerformed(evt);
            }
        });

        label_titulo_modal_pedido.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_titulo_modal_pedido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_titulo_modal_pedido.setText("Completar datos del nuevo pedido");

        jLabel21.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setLabelFor(text_field_direccion_v);
        jLabel21.setText("Id cliente");

        text_field_cliente_p.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setLabelFor(text_field_direccion_v);
        jLabel22.setText("Tipo de pago");

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));

        rbutton_transferencia_p.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        rbutton_transferencia_p.setText("Transferencia");

        rbutton_mp_p.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        rbutton_mp_p.setText("Mercado pago");
        rbutton_mp_p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbutton_mp_pActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbutton_transferencia_p)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbutton_mp_p)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rbutton_mp_p, rbutton_transferencia_p});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rbutton_transferencia_p)
                .addComponent(rbutton_mp_p))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {rbutton_mp_p, rbutton_transferencia_p});

        javax.swing.GroupLayout panel_modal_pedidoLayout = new javax.swing.GroupLayout(panel_modal_pedido);
        panel_modal_pedido.setLayout(panel_modal_pedidoLayout);
        panel_modal_pedidoLayout.setHorizontalGroup(
            panel_modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_titulo_modal_pedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_modal_pedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_modal_pedidoLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(21, 21, 21)
                        .addGroup(panel_modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_modal_pedidoLayout.createSequentialGroup()
                                .addComponent(boton_cancelar_pedido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                                .addComponent(boton_confirmar_pedido)
                                .addGap(107, 107, 107))
                            .addGroup(panel_modal_pedidoLayout.createSequentialGroup()
                                .addComponent(text_field_cliente_p, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panel_modal_pedidoLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(41, 41, 41)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(85, 85, 85))))
        );
        panel_modal_pedidoLayout.setVerticalGroup(
            panel_modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal_pedidoLayout.createSequentialGroup()
                .addComponent(label_titulo_modal_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(text_field_cliente_p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(panel_modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_confirmar_pedido)
                    .addComponent(boton_cancelar_pedido))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout modal_pedidoLayout = new javax.swing.GroupLayout(modal_pedido.getContentPane());
        modal_pedido.getContentPane().setLayout(modal_pedidoLayout);
        modal_pedidoLayout.setHorizontalGroup(
            modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
            .addGroup(modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal_pedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modal_pedidoLayout.setVerticalGroup(
            modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
            .addGroup(modal_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal_pedido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setBackground(new java.awt.Color(0, 0, 0));

        panel_botones.setBackground(new java.awt.Color(255, 255, 153));

        botonListaVendedores.setBackground(new java.awt.Color(255, 102, 153));
        botonListaVendedores.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        botonListaVendedores.setText("Vendedores");
        botonListaVendedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonListaVendedoresActionPerformed(evt);
            }
        });

        botonListaClientes.setBackground(new java.awt.Color(255, 102, 153));
        botonListaClientes.setText("Clientes");
        botonListaClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonListaClientesActionPerformed(evt);
            }
        });

        botonListaItemsMenu.setBackground(new java.awt.Color(255, 102, 153));
        botonListaItemsMenu.setText("ItemsMenu");
        botonListaItemsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonListaItemsMenuActionPerformed(evt);
            }
        });

        botonListaPedidos.setBackground(new java.awt.Color(255, 102, 153));
        botonListaPedidos.setText("Pedidos");
        botonListaPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonListaPedidosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_botonesLayout = new javax.swing.GroupLayout(panel_botones);
        panel_botones.setLayout(panel_botonesLayout);
        panel_botonesLayout.setHorizontalGroup(
            panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_botonesLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botonListaVendedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonListaClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonListaItemsMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonListaPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_botonesLayout.setVerticalGroup(
            panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_botonesLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(botonListaVendedores)
                .addGap(18, 18, 18)
                .addComponent(botonListaClientes)
                .addGap(18, 18, 18)
                .addComponent(botonListaItemsMenu)
                .addGap(18, 18, 18)
                .addComponent(botonListaPedidos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(panel_botones);

        panel_Informacion.setBackground(new java.awt.Color(153, 255, 153));
        panel_Informacion.setPreferredSize(new java.awt.Dimension(764, 610));

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
        label_buscar.setLabelFor(text_field_buscar);
        label_buscar.setText("Buscar por nombre:");

        text_field_buscar.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_buscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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
                .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_infoLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(boton_crear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label_buscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_field_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_infoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_infoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(boton_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(boton_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_infoLayout.setVerticalGroup(
            panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_infoLayout.createSequentialGroup()
                .addComponent(panel_info_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_crear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_field_buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout panel_InformacionLayout = new javax.swing.GroupLayout(panel_Informacion);
        panel_Informacion.setLayout(panel_InformacionLayout);
        panel_InformacionLayout.setHorizontalGroup(
            panel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_info, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_InformacionLayout.setVerticalGroup(
            panel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(panel_Informacion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Configuración en el constructor o inicialización de tu clase `Split`

    
    
    private void botonListaPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonListaPedidosActionPerformed
        if(interfazActual != interfazPedido) cambiarPanel(interfazPedido, modal_pedido);
    }//GEN-LAST:event_botonListaPedidosActionPerformed

    private void botonListaVendedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonListaVendedoresActionPerformed
        if(interfazActual != interfazVendedor) cambiarPanel(interfazVendedor, modal_vendedor);
    }//GEN-LAST:event_botonListaVendedoresActionPerformed

    private void boton_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_crearActionPerformed
       interfazActual.mostrarCrear();
       mostrarModal(modal_actual);
    }//GEN-LAST:event_boton_crearActionPerformed

    private void boton_confirmar_vendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confirmar_vendedorActionPerformed

        switch((String) boton_confirmar_vendedor.getClientProperty("tipoAccion")){
            case "crear":
                interfazActual.crear();
            break;
            case "editar":
               interfazActual.editar((Integer) boton_confirmar_vendedor.getClientProperty("id"));
            break;
        }
       
       modal_actual.dispose();
    }//GEN-LAST:event_boton_confirmar_vendedorActionPerformed

    private void boton_cancelar_vendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelar_vendedorActionPerformed
        modal_vendedor.dispose();
    }//GEN-LAST:event_boton_cancelar_vendedorActionPerformed

    private void text_field_direccion_vActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_field_direccion_vActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_field_direccion_vActionPerformed

    private void botonListaClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonListaClientesActionPerformed
        if(interfazActual != interfazCliente) cambiarPanel(interfazCliente, modal_cliente);
    }//GEN-LAST:event_botonListaClientesActionPerformed

    private void botonListaItemsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonListaItemsMenuActionPerformed
       if(interfazActual != interfazItemMenu) cambiarPanel(interfazItemMenu, modal_item_menu);
    }//GEN-LAST:event_botonListaItemsMenuActionPerformed

    private void boton_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_editarActionPerformed
        int filaSeleccionada = tabla.getSelectedRow();
        
        if (filaSeleccionada != -1) {  // Verifica si hay una fila seleccionada
            
            interfazActual.mostrarEditar(filaSeleccionada);
            
            mostrarModal(modal_actual);
            
        } else {
            JOptionPane.showMessageDialog(panel_info, "Por favor, selecciona una fila para editar.");
        }
    }//GEN-LAST:event_boton_editarActionPerformed

    private void boton_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_eliminarActionPerformed
        int filaSeleccionada = tabla.getSelectedRow();
        
        if (filaSeleccionada != -1) {  
            
            interfazActual.mostrarEliminar(filaSeleccionada);
            
            mostrarModalEliminar(modal_eliminar);
        } else {
            JOptionPane.showMessageDialog(panel_info, "Por favor, selecciona una fila para eliminar.");
        }
    }//GEN-LAST:event_boton_eliminarActionPerformed

    private void boton_confirmar_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confirmar_eliminarActionPerformed

        int id = (Integer) boton_confirmar_eliminar.getClientProperty("id");
        
        interfazActual.eliminar(id);
        modal_eliminar.dispose();
    }//GEN-LAST:event_boton_confirmar_eliminarActionPerformed

    private void boton_cancelar_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelar_eliminarActionPerformed
        modal_eliminar.dispose();
    }//GEN-LAST:event_boton_cancelar_eliminarActionPerformed

    private void text_field_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_field_buscarKeyReleased
        interfazActual.buscar(text_field_buscar.getText());
    }//GEN-LAST:event_text_field_buscarKeyReleased

    private void boton_confirmar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confirmar_clienteActionPerformed
        switch((String) boton_confirmar_cliente.getClientProperty("tipoAccion")){
            case "crear":
                interfazActual.crear();
            break;
            case "editar":
               interfazActual.editar((Integer) boton_confirmar_cliente.getClientProperty("id"));
            break;
        }
       
       modal_actual.dispose();
    }//GEN-LAST:event_boton_confirmar_clienteActionPerformed

    private void boton_cancelar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelar_clienteActionPerformed
         modal_cliente.dispose();
    }//GEN-LAST:event_boton_cancelar_clienteActionPerformed

    private void text_field_direccion_cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_field_direccion_cActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_field_direccion_cActionPerformed

    private void text_field_email_cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_field_email_cActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_field_email_cActionPerformed

    private void boton_confirmar_item_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confirmar_item_menuActionPerformed
        switch((String) boton_confirmar_item_menu.getClientProperty("tipoAccion")){
            case "crear":
                interfazActual.crear();
            break;
            case "editar":
               interfazActual.editar((Integer) boton_confirmar_item_menu.getClientProperty("id"));
            break;
        }
       
       modal_actual.dispose();
    }//GEN-LAST:event_boton_confirmar_item_menuActionPerformed

    private void boton_cancelar_item_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelar_item_menuActionPerformed
        modal_item_menu.dispose();
    }//GEN-LAST:event_boton_cancelar_item_menuActionPerformed

    private void boton_confirmar_pedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confirmar_pedidoActionPerformed
         switch((String) boton_confirmar_pedido.getClientProperty("tipoAccion")){
            case "crear":
                interfazActual.crear();
            break;
            case "editar":
               interfazActual.editar((Integer) boton_confirmar_pedido.getClientProperty("id"));
            break;
        }
       
       modal_actual.dispose();
    }//GEN-LAST:event_boton_confirmar_pedidoActionPerformed

    private void boton_cancelar_pedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelar_pedidoActionPerformed
       modal_pedido.dispose();
    }//GEN-LAST:event_boton_cancelar_pedidoActionPerformed

    private void rbutton_mp_pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbutton_mp_pActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbutton_mp_pActionPerformed

    private void mostrarModal(JDialog modal) {
        
        modal.setSize(500, 300);

        modal.setLocationRelativeTo(this);

        modal.setVisible(true);
    }
    
    private void mostrarModalEliminar(JDialog modal) {
        
        modal.setSize(300, 150);

        modal.setLocationRelativeTo(this);

        modal.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazGeneral().setVisible(true);
            }
        });
    }
    
    
    public void cambiarPanel(InformacionInterfaz nuevaInterfaz, JDialog nuevoModal){
        nuevaInterfaz.cambiarPanel();
        interfazActual = nuevaInterfaz;
        modal_actual = nuevoModal;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonListaClientes;
    private javax.swing.JButton botonListaItemsMenu;
    private javax.swing.JButton botonListaPedidos;
    private javax.swing.JButton botonListaVendedores;
    private javax.swing.JButton boton_cancelar_cliente;
    private javax.swing.JButton boton_cancelar_eliminar;
    private javax.swing.JButton boton_cancelar_item_menu;
    private javax.swing.JButton boton_cancelar_pedido;
    private javax.swing.JButton boton_cancelar_vendedor;
    private javax.swing.JButton boton_confirmar_cliente;
    private javax.swing.JButton boton_confirmar_eliminar;
    private javax.swing.JButton boton_confirmar_item_menu;
    private javax.swing.JButton boton_confirmar_pedido;
    private javax.swing.JButton boton_confirmar_vendedor;
    private javax.swing.JButton boton_crear;
    private javax.swing.JButton boton_editar;
    private javax.swing.JButton boton_eliminar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel label_buscar;
    private javax.swing.JLabel label_titulo_modal_cliente;
    private javax.swing.JLabel label_titulo_modal_eliminar;
    private javax.swing.JLabel label_titulo_modal_item_menu;
    private javax.swing.JLabel label_titulo_modal_pedido;
    private javax.swing.JLabel label_titulo_modal_vendedor;
    private javax.swing.JDialog modal_cliente;
    private javax.swing.JDialog modal_eliminar;
    private javax.swing.JDialog modal_item_menu;
    private javax.swing.JDialog modal_pedido;
    private javax.swing.JDialog modal_vendedor;
    private javax.swing.JPanel panel_Informacion;
    private javax.swing.JPanel panel_botones;
    private javax.swing.JPanel panel_info;
    private javax.swing.JLabel panel_info_titulo;
    private javax.swing.JPanel panel_modal_cliente;
    private javax.swing.JPanel panel_modal_eliminar;
    private javax.swing.JPanel panel_modal_item_menu;
    private javax.swing.JPanel panel_modal_pedido;
    private javax.swing.JPanel panel_modal_vendedor;
    private javax.swing.JRadioButton rbutton_mp_p;
    private javax.swing.JRadioButton rbutton_transferencia_p;
    private javax.swing.JTable tabla;
    private javax.swing.JTextArea text_area_descripcion_i;
    private javax.swing.JTextField text_field_buscar;
    private javax.swing.JTextField text_field_categoria_i;
    private javax.swing.JTextField text_field_cliente_p;
    private javax.swing.JTextField text_field_cuit_c;
    private javax.swing.JTextField text_field_direccion_c;
    private javax.swing.JTextField text_field_direccion_v;
    private javax.swing.JTextField text_field_email_c;
    private javax.swing.JTextField text_field_latitud_c;
    private javax.swing.JTextField text_field_latitud_v;
    private javax.swing.JTextField text_field_longitud_c;
    private javax.swing.JTextField text_field_longitud_v;
    private javax.swing.JTextField text_field_nombre_i;
    private javax.swing.JTextField text_field_nombre_v;
    private javax.swing.JTextField text_field_precio_i;
    private javax.swing.JTextField text_field_vendedor_i;
    // End of variables declaration//GEN-END:variables



}
