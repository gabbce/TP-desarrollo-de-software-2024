/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package isi.deso.tpdeso2024.utils.paneles;

import isi.deso.tpdeso2024.EstadoPedido;
import isi.deso.tpdeso2024.PagoType;
import isi.deso.tpdeso2024.controllers.ItemMenuController;
import isi.deso.tpdeso2024.controllers.PedidoController;
import isi.deso.tpdeso2024.dtos.BebidaDTO;
import isi.deso.tpdeso2024.dtos.CategoriaDTO;
import isi.deso.tpdeso2024.dtos.ClienteDTO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.dtos.PedidoDTO;
import isi.deso.tpdeso2024.dtos.PedidoDetalleDTO;
import isi.deso.tpdeso2024.dtos.PlatoDTO;
import isi.deso.tpdeso2024.excepciones.ClienteNoEncontradoException;
import isi.deso.tpdeso2024.excepciones.ItemNoEncontradoExcepcion;
import isi.deso.tpdeso2024.excepciones.ItemsNoSonDelMismoVendedorException;
import isi.deso.tpdeso2024.excepciones.PedidoNoEncontradoException;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import isi.deso.tpdeso2024.utils.modelosTablas.ModeloTablaItemMenu;
import isi.deso.tpdeso2024.utils.modelosTablas.ModeloTablaItemMenuPedido;
import isi.deso.tpdeso2024.utils.modelosTablas.ModeloTablaPedido;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class PanelPedido extends javax.swing.JPanel implements PanelInformacion{
    private final PedidoController pedidoController = PedidoController.getInstance();
    private final ItemMenuController itemMenuController = ItemMenuController.getInstance();
    
    ModeloTablaPedido modeloPedido;
    
    ModeloTablaItemMenuPedido modeloItemMenu;
    
    /**
     * Creates new form PanelPedido
     */
    public PanelPedido() {
        initComponents();
        
        inicializarTabla();
        modeloPedido = new ModeloTablaPedido();
        modeloPedido.setNombreColumnas(List.of("Id", "Tipo Pago", "Estado", "Id cliente"));
        tabla.setModel(modeloPedido);
        
        modeloItemMenu = new ModeloTablaItemMenuPedido();
        tabla_item.setModel(modeloItemMenu);
        tabla_ver_items.setModel(modeloItemMenu);
        
        cargarCategorias();
        cargarEstadosPedido();
        cargarPagoTypes();
    }
    
    public void inicializarTabla(){
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(false);  
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        tabla.setSelectionBackground(Color.LIGHT_GRAY);
        tabla.setSelectionForeground(Color.BLUE);
        tabla_item.setRowSelectionAllowed(true);
        tabla_item.setColumnSelectionAllowed(false);  
       tabla_item.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        tabla_item.setSelectionBackground(Color.LIGHT_GRAY);
        tabla_item.setSelectionForeground(Color.BLUE); 
       tabla_ver_items.setRowSelectionAllowed(true);
        tabla_ver_items.setColumnSelectionAllowed(false);  
        tabla_ver_items.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        tabla_ver_items.setSelectionBackground(Color.LIGHT_GRAY);
        tabla_ver_items.setSelectionForeground(Color.BLUE); 
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

    
    private void cargarEstadosPedido() {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        List<EstadoPedido> estados = pedidoController.getEstadosPedido();
        
        for (EstadoPedido estado : estados) {
            modelo.addElement(String.valueOf(estado));
        }
        combo_box_estado_pedido.setModel(modelo);
    }
    
    private void cargarPagoTypes() {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        List<PagoType> pagos = pedidoController.getPagoTypes();
        
        for (PagoType pago : pagos) {
            modelo.addElement(String.valueOf(pago));
        }
        combo_box_tipo_pago.setModel(modelo);
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
       modal_ver_items.dispose();
       modal_tabla_items.dispose();
    }
    
     public void ActivarFiltrosItem(Boolean b){
        filtro_tipo.setEnabled(b);
        filtro_nombre.setEnabled(b);
        filtro_vendedor.setEnabled(b);
        filtro_categoria.setEnabled(b);
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
        cerrarModales();
        int id = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        String pago = String.valueOf(tabla.getValueAt(filaSeleccionada, 1));
        String estado = String.valueOf(tabla.getValueAt(filaSeleccionada, 2));
        String cliente = String.valueOf(tabla.getValueAt(filaSeleccionada, 3));

        
        text_field_cliente.setText(cliente);
        combo_box_tipo_pago.setSelectedItem(pago);
        combo_box_estado_pedido.setEnabled(true);
        combo_box_estado_pedido.setSelectedItem(estado);

        boton_confirmar.putClientProperty("tipoAccion", "editar");
        //boton_confirmar.putClientProperty("fila", filaSeleccionada);
        boton_confirmar.putClientProperty("id", id);
        boton_confirmar.setText("Siguiente");
        titulo_modal.setText("Editar datos del pedido " + id);
        
        mostrarModal(modal);
        
        List<PedidoDetalleDTO> detalles =  ((ModeloTablaPedido) tabla.getModel()).getRow(filaSeleccionada).getPedidoDetalle();
        ((ModeloTablaItemMenuPedido) tabla_item.getModel()).actualizarListaItemsMenuYCantidades(detalles);
        ((AbstractTableModel) tabla_item.getModel()).fireTableChanged(null);
    }
    
    public void mostrarEditarItems(int id){
        
        
        confirmar_pedido.putClientProperty("tipoAccion", "editar");
        confirmar_pedido.setText("Editar pedido");
        titulo_modal_items.setText("Modifique los items del pedido");
        confirmar_pedido.putClientProperty("id", (Integer) tabla.getValueAt(id, 0));
        ActivarFiltrosItem(false);
        
        modal_tabla_items.setSize(815, 440);
        modal_tabla_items.setLocationRelativeTo(this);
        modal_tabla_items.setVisible(true);
    }
    
    
    
    @Override
    public void editar(int id) {
        PedidoDTO pedidoDTO = new PedidoDTO(id, null, null, EstadoPedido.valueOf(String.valueOf(combo_box_estado_pedido.getSelectedItem())), new ClienteDTO(Integer.parseInt(text_field_cliente.getText())));
        
        LinkedList<PedidoDetalleDTO> detalle = new LinkedList<>();
        
        List<ItemMenuDTO> items = ((ModeloTablaItemMenuPedido) tabla_item.getModel()).getItemsSeleccionados();
        
        Map<Integer, Integer> cantidades = ((ModeloTablaItemMenuPedido) tabla_item.getModel()).getCantidadesSeleccionadas();
        
        for(ItemMenuDTO i : items){
            detalle.add(new PedidoDetalleDTO(pedidoDTO, i, cantidades.get(i.getId())));
        }
        
        if(detalle.isEmpty()){
            JOptionPane.showMessageDialog(modal_tabla_items, "Debe seleccionar al menos 1 item");
            return;
        }
        
        pedidoDTO.setPedidoDetalle(detalle);
        
        try {
           pedidoController.actualizar(pedidoDTO);
            ((ModeloTablaPedido) tabla.getModel()).resetListaPedidos();
            ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
            JOptionPane.showMessageDialog(modal, "Pedido actualizado exitosamente.");
        }  catch (ClienteNoEncontradoException ex) {
             JOptionPane.showMessageDialog(modal, "<html>Error al actualizar el pedido.<br>No existe cliente con id " + id + "</html>");
            Logger.getLogger(PanelPedido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ItemsNoSonDelMismoVendedorException ex) {
             JOptionPane.showMessageDialog(modal, "<html>Error al actualizar el pedido.<br>Todos los items deben ser del mismo vendedor</html>");
            Logger.getLogger(PanelPedido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal, "Error al actualizar el pedido.");
        }
        
        modal_tabla_items.dispose();
    }

    //@Override
    public void buscar(String id) {
        if("".equals(id)){
            ((ModeloTablaPedido) tabla.getModel()).resetListaPedidos();
        }
        else{
            List<PedidoDTO> pedido = new ArrayList<>();
            PedidoDTO p = pedidoController.buscarPorID(Integer.parseInt(id));
            if(p != null) pedido.add(p);
            ((ModeloTablaPedido) tabla.getModel()).actualizarListaPedidos(pedido);
        }
        
        ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
        
    }
    

    @Override
    public void mostrarCrear() {
        cerrarModales();
      
       text_field_cliente.setText("");
       combo_box_estado_pedido.setEnabled(false);
       combo_box_estado_pedido.setSelectedItem("PENDIENTE");
        
       boton_confirmar.putClientProperty("tipoAccion", "crear");
       
       boton_confirmar.setText("Siguiente");
       titulo_modal.setText("Completar datos del nuevo pedido");
       
       mostrarModal(modal);
    }
    
    public void mostrarCrearItems(){
        
        ((ModeloTablaItemMenuPedido) tabla_item.getModel()).actualizarListaItemsMenu(itemMenuController.listar());
        ((ModeloTablaItemMenuPedido) tabla_item.getModel()).reiniciarCantidades();
        ((AbstractTableModel) tabla_item.getModel()).fireTableChanged(null);
        
        ActivarFiltrosItem(true);
        
        confirmar_pedido.putClientProperty("tipoAccion", "crear");
        confirmar_pedido.setText("Crear pedido");
        titulo_modal_items.setText("Seleccione los items del pedido");
        modal_tabla_items.setSize(815, 455);
        modal_tabla_items.setLocationRelativeTo(this);
        modal_tabla_items.setVisible(true);
    }
    
    public void mostrarItems(int filaSeleccionada){
        cerrarModales();
        
        titulo_ver_items.setText("Items del pedido " +  ((ModeloTablaPedido) tabla.getModel()).getValueAt(filaSeleccionada, 0));
        
        List<PedidoDetalleDTO> detalles =  ((ModeloTablaPedido) tabla.getModel()).getRow(filaSeleccionada).getPedidoDetalle();
        ((ModeloTablaItemMenuPedido) tabla_ver_items.getModel()).actualizarListaItemsMenuYCantidades(detalles);
        ((AbstractTableModel) tabla_ver_items.getModel()).fireTableChanged(null);
        
        modal_ver_items.setSize(815, 385);
        modal_ver_items.setLocationRelativeTo(this);
        modal_ver_items.setVisible(true);
    }
    
    @Override
    public void crear() {
        
        PedidoDTO pedidoDTO = new PedidoDTO(null, EstadoPedido.PENDIENTE, new ClienteDTO(Integer.parseInt(text_field_cliente.getText())));
        
        LinkedList<PedidoDetalleDTO> detalle = new LinkedList<>();
        
        List<ItemMenuDTO> items = ((ModeloTablaItemMenuPedido) tabla_item.getModel()).getItemsSeleccionados();
        
        Map<Integer, Integer> cantidades = ((ModeloTablaItemMenuPedido) tabla_item.getModel()).getCantidadesSeleccionadas();
        
        for(ItemMenuDTO i : items){
            detalle.add(new PedidoDetalleDTO(pedidoDTO, i, cantidades.get(i.getId())));
        }
        
        if(detalle.isEmpty()){
            JOptionPane.showMessageDialog(modal_tabla_items, "Debe seleccionar al menos 1 item");
            return;
        }
        
        pedidoDTO.setPedidoDetalle(detalle);
        
        try {
            pedidoController.crear(pedidoDTO);
            ((ModeloTablaPedido) tabla.getModel()).resetListaPedidos();
            ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
            JOptionPane.showMessageDialog(modal_tabla_items, "Pedido creado exitosamente.");
        } 
        catch (HeadlessException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(modal_tabla_items, "Error al crear el pedido.");
        } catch (ClienteNoEncontradoException | ItemsNoSonDelMismoVendedorException ex) {
            Logger.getLogger(PanelPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        modal_tabla_items.dispose();
    }
    
    public void filtrarItem(javax.swing.JTable tabla){
        
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
            ((ModeloTablaItemMenuPedido) tabla.getModel()).actualizarListaItemsMenu(itemMenuController.filtrar(esComida, nombre, vendedor, categoria));
        } catch(VendedorNoEncontradoException e){
            ((ModeloTablaItemMenuPedido) tabla.getModel()).actualizarListaItemsMenu(new ArrayList());
            System.out.println(e.getMessage());
        }
        
        ((AbstractTableModel) tabla.getModel()).fireTableChanged(null);
        
        
    }
    
    public void mostrarDetallesItem(int filaSeleccionada, javax.swing.JTable tabla){
        
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
        jLabel21 = new javax.swing.JLabel();
        text_field_cliente = new javax.swing.JTextField();
        label_buscar6 = new javax.swing.JLabel();
        combo_box_tipo_pago = new javax.swing.JComboBox<>();
        combo_box_estado_pedido = new javax.swing.JComboBox<>();
        label_buscar12 = new javax.swing.JLabel();
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
        modal_tabla_items = new javax.swing.JDialog();
        panel_tabla_item = new javax.swing.JPanel();
        titulo_modal_items = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_item = new javax.swing.JTable();
        panel_filtros = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        label_buscar3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        filtro_tipo = new javax.swing.JComboBox<>();
        label_buscar4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        filtro_nombre = new javax.swing.JTextField();
        label_buscar1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_buscar2 = new javax.swing.JLabel();
        filtro_vendedor = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        filtro_categoria = new javax.swing.JComboBox<>();
        label_buscar5 = new javax.swing.JLabel();
        boton_detalles = new javax.swing.JButton();
        confirmar_pedido = new javax.swing.JButton();
        ver_items_seleccionados = new javax.swing.JButton();
        boton_detalles1 = new javax.swing.JButton();
        modal_ver_items = new javax.swing.JDialog();
        panel_ver_items = new javax.swing.JPanel();
        titulo_ver_items = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_ver_items = new javax.swing.JTable();
        boton_detalles3 = new javax.swing.JButton();
        boton_detalles4 = new javax.swing.JButton();
        panel_info = new javax.swing.JPanel();
        panel_info_titulo = new javax.swing.JLabel();
        boton_crear = new javax.swing.JButton();
        label_buscar = new javax.swing.JLabel();
        text_field_buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        boton_editar = new javax.swing.JButton();
        boton_eliminar = new javax.swing.JButton();
        boton_detalles2 = new javax.swing.JButton();

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

        modal.setBackground(new java.awt.Color(0, 0, 0));

        panel_modal.setBackground(new java.awt.Color(255, 204, 204));

        boton_confirmar.setBackground(new java.awt.Color(0, 102, 0));
        boton_confirmar.setForeground(new java.awt.Color(204, 204, 204));
        boton_confirmar.setText("Siguiente");
        boton_confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confirmarActionPerformed(evt);
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
        titulo_modal.setText("Completar datos del nuevo pedido");

        jLabel21.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel21.setText("Id cliente");

        text_field_cliente.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        text_field_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_clienteActionPerformed(evt);
            }
        });

        label_buscar6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar6.setText("Tipo de pago");

        combo_box_tipo_pago.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        combo_box_tipo_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_tipo_pagoActionPerformed(evt);
            }
        });

        combo_box_estado_pedido.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        combo_box_estado_pedido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PENDIENTE" }));
        combo_box_estado_pedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_estado_pedidoActionPerformed(evt);
            }
        });

        label_buscar12.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar12.setText("Estado del envio");

        javax.swing.GroupLayout panel_modalLayout = new javax.swing.GroupLayout(panel_modal);
        panel_modal.setLayout(panel_modalLayout);
        panel_modalLayout.setHorizontalGroup(
            panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo_modal, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
            .addGroup(panel_modalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_modalLayout.createSequentialGroup()
                        .addComponent(boton_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boton_confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_modalLayout.createSequentialGroup()
                        .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_buscar12)
                            .addComponent(jLabel21)
                            .addComponent(label_buscar6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(text_field_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_box_estado_pedido, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_box_tipo_pago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_modalLayout.setVerticalGroup(
            panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modalLayout.createSequentialGroup()
                .addComponent(titulo_modal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_buscar12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_box_estado_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(text_field_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_buscar6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_box_tipo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(panel_modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_cancelar)
                    .addComponent(boton_confirmar))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout modalLayout = new javax.swing.GroupLayout(modal.getContentPane());
        modal.getContentPane().setLayout(modalLayout);
        modalLayout.setHorizontalGroup(
            modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 559, Short.MAX_VALUE)
            .addGroup(modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modalLayout.setVerticalGroup(
            modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 311, Short.MAX_VALUE)
            .addGroup(modalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_modal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(panel_modal3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_modal3Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(boton_continuar_detalle_bebida, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_modal3Layout.setVerticalGroup(
            panel_modal3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal3Layout.createSequentialGroup()
                .addComponent(titulo_modal_detalle_bebida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(boton_continuar_detalle_bebida)
                .addGap(31, 31, 31))
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
            .addGap(0, 323, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_modal4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_continuar_detalle_comida, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
        );
        panel_modal4Layout.setVerticalGroup(
            panel_modal4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modal4Layout.createSequentialGroup()
                .addComponent(titulo_modal_detalle_comida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(boton_continuar_detalle_comida)
                .addGap(43, 43, 43))
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

        panel_tabla_item.setBackground(new java.awt.Color(255, 255, 255));
        panel_tabla_item.setPreferredSize(new java.awt.Dimension(743, 382));

        titulo_modal_items.setBackground(new java.awt.Color(255, 255, 153));
        titulo_modal_items.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        titulo_modal_items.setForeground(new java.awt.Color(102, 0, 102));
        titulo_modal_items.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_modal_items.setText("Elija los items menu del pedido");
        titulo_modal_items.setOpaque(true);

        tabla_item.setBackground(new java.awt.Color(255, 102, 102));
        tabla_item.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        tabla_item.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_item.setRowHeight(30);
        jScrollPane2.setViewportView(tabla_item);

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

        label_buscar1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar1.setText("Nombre:");
        label_buscar1.setMinimumSize(new java.awt.Dimension(75, 42));
        label_buscar1.setPreferredSize(new java.awt.Dimension(75, 42));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtro_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(filtro_nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap())
        );

        panel_filtros.add(jPanel1, new java.awt.GridBagConstraints());

        label_buscar2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar2.setText("ID vendedor:");

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
                .addComponent(label_buscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtro_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_buscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtro_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_filtros.add(jPanel2, new java.awt.GridBagConstraints());

        filtro_categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas" }));
        filtro_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtro_categoriaActionPerformed(evt);
            }
        });

        label_buscar5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar5.setText("Categoria:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_buscar5)
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
                    .addComponent(label_buscar5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_filtros.add(jPanel3, new java.awt.GridBagConstraints());

        boton_detalles.setBackground(new java.awt.Color(255, 255, 153));
        boton_detalles.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_detalles.setText("Mostrar detalles item");
        boton_detalles.setPreferredSize(new java.awt.Dimension(150, 28));
        boton_detalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_detallesActionPerformed(evt);
            }
        });

        confirmar_pedido.setBackground(new java.awt.Color(255, 102, 153));
        confirmar_pedido.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        confirmar_pedido.setText("Crear pedido");
        confirmar_pedido.setPreferredSize(new java.awt.Dimension(150, 28));
        confirmar_pedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmar_pedidoActionPerformed(evt);
            }
        });

        ver_items_seleccionados.setBackground(new java.awt.Color(255, 255, 153));
        ver_items_seleccionados.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        ver_items_seleccionados.setText("Ver items seleccionados");
        ver_items_seleccionados.setPreferredSize(new java.awt.Dimension(150, 28));
        ver_items_seleccionados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ver_items_seleccionadosActionPerformed(evt);
            }
        });

        boton_detalles1.setBackground(new java.awt.Color(204, 0, 0));
        boton_detalles1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_detalles1.setForeground(new java.awt.Color(255, 255, 255));
        boton_detalles1.setText("Cancelar");
        boton_detalles1.setPreferredSize(new java.awt.Dimension(150, 28));
        boton_detalles1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_detalles1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tabla_itemLayout = new javax.swing.GroupLayout(panel_tabla_item);
        panel_tabla_item.setLayout(panel_tabla_itemLayout);
        panel_tabla_itemLayout.setHorizontalGroup(
            panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boton_detalles1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_detalles, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ver_items_seleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmar_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane2)
            .addComponent(panel_filtros, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addComponent(titulo_modal_items, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_tabla_itemLayout.setVerticalGroup(
            panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_itemLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(titulo_modal_items, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panel_filtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_detalles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ver_items_seleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmar_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_detalles1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout modal_tabla_itemsLayout = new javax.swing.GroupLayout(modal_tabla_items.getContentPane());
        modal_tabla_items.getContentPane().setLayout(modal_tabla_itemsLayout);
        modal_tabla_itemsLayout.setHorizontalGroup(
            modal_tabla_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_item, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        modal_tabla_itemsLayout.setVerticalGroup(
            modal_tabla_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_item, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        panel_ver_items.setBackground(new java.awt.Color(255, 255, 255));
        panel_ver_items.setPreferredSize(new java.awt.Dimension(800, 400));

        titulo_ver_items.setBackground(new java.awt.Color(255, 255, 153));
        titulo_ver_items.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        titulo_ver_items.setForeground(new java.awt.Color(102, 0, 102));
        titulo_ver_items.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_ver_items.setText("Items del pedido x");
        titulo_ver_items.setOpaque(true);

        tabla_ver_items.setBackground(new java.awt.Color(255, 102, 102));
        tabla_ver_items.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        tabla_ver_items.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_ver_items.setRowHeight(30);
        jScrollPane3.setViewportView(tabla_ver_items);

        boton_detalles3.setBackground(new java.awt.Color(255, 255, 153));
        boton_detalles3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_detalles3.setText("Mostrar detalles item");
        boton_detalles3.setPreferredSize(new java.awt.Dimension(150, 28));
        boton_detalles3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_detalles3ActionPerformed(evt);
            }
        });

        boton_detalles4.setBackground(new java.awt.Color(0, 102, 0));
        boton_detalles4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_detalles4.setForeground(new java.awt.Color(255, 255, 255));
        boton_detalles4.setText("Continuar");
        boton_detalles4.setPreferredSize(new java.awt.Dimension(150, 28));
        boton_detalles4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_detalles4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_ver_itemsLayout = new javax.swing.GroupLayout(panel_ver_items);
        panel_ver_items.setLayout(panel_ver_itemsLayout);
        panel_ver_itemsLayout.setHorizontalGroup(
            panel_ver_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo_ver_items, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(panel_ver_itemsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boton_detalles3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_detalles4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_ver_itemsLayout.setVerticalGroup(
            panel_ver_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ver_itemsLayout.createSequentialGroup()
                .addComponent(titulo_ver_items, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_ver_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boton_detalles3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_detalles4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout modal_ver_itemsLayout = new javax.swing.GroupLayout(modal_ver_items.getContentPane());
        modal_ver_items.getContentPane().setLayout(modal_ver_itemsLayout);
        modal_ver_itemsLayout.setHorizontalGroup(
            modal_ver_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_ver_items, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        modal_ver_itemsLayout.setVerticalGroup(
            modal_ver_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_ver_items, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setPreferredSize(new java.awt.Dimension(800, 610));

        panel_info.setBackground(new java.awt.Color(255, 255, 255));

        panel_info_titulo.setBackground(new java.awt.Color(255, 255, 153));
        panel_info_titulo.setFont(new java.awt.Font("Comic Sans MS", 1, 36)); // NOI18N
        panel_info_titulo.setForeground(new java.awt.Color(102, 0, 102));
        panel_info_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel_info_titulo.setText("Lista de pedidos");
        panel_info_titulo.setOpaque(true);

        boton_crear.setBackground(new java.awt.Color(255, 102, 153));
        boton_crear.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_crear.setText("Crear nuevo pedido");
        boton_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_crearActionPerformed(evt);
            }
        });

        label_buscar.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        label_buscar.setText("Buscar por ID:");

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

        boton_detalles2.setBackground(new java.awt.Color(255, 255, 153));
        boton_detalles2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        boton_detalles2.setText("Mostrar items");
        boton_detalles2.setPreferredSize(new java.awt.Dimension(150, 28));
        boton_detalles2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_detalles2ActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                        .addComponent(boton_detalles2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(boton_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(boton_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(panel_infoLayout.createSequentialGroup()
                        .addComponent(label_buscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_field_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_infoLayout.setVerticalGroup(
            panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_infoLayout.createSequentialGroup()
                .addComponent(panel_info_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(text_field_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boton_detalles2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(boton_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boton_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boton_crear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7))
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
        switch((String) boton_confirmar.getClientProperty("tipoAccion")){
            case "crear":
            mostrarCrearItems();
            break;
            case "editar":
                editar((Integer) boton_confirmar.getClientProperty("id"));
            //mostrarEditarItems((Integer) boton_confirmar.getClientProperty("fila"));
            break;
        }

        modal.dispose();
    }//GEN-LAST:event_boton_confirmarActionPerformed

    private void boton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelarActionPerformed
        modal.dispose();
    }//GEN-LAST:event_boton_cancelarActionPerformed

    private void boton_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_crearActionPerformed
        mostrarCrear();
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

    private void filtro_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtro_tipoActionPerformed
        filtrarItem(tabla_item);
    }//GEN-LAST:event_filtro_tipoActionPerformed

    private void filtro_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtro_nombreActionPerformed
        filtrarItem(tabla_item);
    }//GEN-LAST:event_filtro_nombreActionPerformed

    private void filtro_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filtro_nombreKeyReleased
        filtrarItem(tabla_item);
    }//GEN-LAST:event_filtro_nombreKeyReleased

    private void filtro_vendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtro_vendedorActionPerformed
        filtrarItem(tabla_item);
    }//GEN-LAST:event_filtro_vendedorActionPerformed

    private void filtro_vendedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filtro_vendedorKeyReleased
        filtrarItem(tabla_item);
    }//GEN-LAST:event_filtro_vendedorKeyReleased

    private void filtro_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtro_categoriaActionPerformed
        filtrarItem(tabla_item);
    }//GEN-LAST:event_filtro_categoriaActionPerformed

    private void boton_detallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_detallesActionPerformed
        int filaSeleccionada = tabla_item.getSelectedRow();

        if (filaSeleccionada != -1) {  // Verifica si hay una fila seleccionada

            mostrarDetallesItem(tabla_item.getSelectedRow(), tabla_item);

        } else {
            JOptionPane.showMessageDialog(panel_tabla_item, "Por favor, selecciona una fila para ver.");
        }
    }//GEN-LAST:event_boton_detallesActionPerformed

    private void combo_box_tipo_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_tipo_pagoActionPerformed
        
    }//GEN-LAST:event_combo_box_tipo_pagoActionPerformed

    private void boton_detalles1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_detalles1ActionPerformed
        
    }//GEN-LAST:event_boton_detalles1ActionPerformed

    private void boton_continuar_detalle_bebidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_continuar_detalle_bebidaActionPerformed
        modal_detalles_bebida.dispose();
    }//GEN-LAST:event_boton_continuar_detalle_bebidaActionPerformed

    private void detalle_graduacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detalle_graduacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_graduacionActionPerformed

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

    private void boton_detalles2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_detalles2ActionPerformed
         int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {  // Verifica si hay una fila seleccionada

            mostrarItems(tabla.getSelectedRow());

        } else {
            JOptionPane.showMessageDialog(panel_info, "Por favor, selecciona una fila para ver.");
        }
    }//GEN-LAST:event_boton_detalles2ActionPerformed

    private void boton_detalles3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_detalles3ActionPerformed
        int filaSeleccionada = tabla_ver_items.getSelectedRow();

        if (filaSeleccionada != -1) {  // Verifica si hay una fila seleccionada

            mostrarDetallesItem(tabla_ver_items.getSelectedRow(), tabla_ver_items);

        } else {
            JOptionPane.showMessageDialog(panel_ver_items, "Por favor, selecciona una fila para ver.");
        }
    }//GEN-LAST:event_boton_detalles3ActionPerformed

    private void boton_detalles4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_detalles4ActionPerformed
       modal_ver_items.dispose();
    }//GEN-LAST:event_boton_detalles4ActionPerformed

    private void confirmar_pedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmar_pedidoActionPerformed
        switch((String) confirmar_pedido.getClientProperty("tipoAccion")){
            case "crear":
            crear();
            break;
            case "editar":
            editar((Integer) confirmar_pedido.getClientProperty("id"));
            break;
        }
    }//GEN-LAST:event_confirmar_pedidoActionPerformed

    private void ver_items_seleccionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ver_items_seleccionadosActionPerformed

        ((ModeloTablaItemMenuPedido)tabla_item.getModel()).actualizarListaItemsMenu(((ModeloTablaItemMenuPedido)tabla_item.getModel()).getItemsSeleccionados());
    }//GEN-LAST:event_ver_items_seleccionadosActionPerformed

    private void combo_box_estado_pedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_estado_pedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_box_estado_pedidoActionPerformed

    private void text_field_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_field_clienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_field_clienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_cancelar;
    private javax.swing.JButton boton_cancelar_eliminar;
    private javax.swing.JButton boton_confirmar;
    private javax.swing.JButton boton_confirmar_eliminar;
    private javax.swing.JButton boton_continuar_detalle_bebida;
    private javax.swing.JButton boton_continuar_detalle_comida;
    private javax.swing.JButton boton_crear;
    private javax.swing.JButton boton_detalles;
    private javax.swing.JButton boton_detalles1;
    private javax.swing.JButton boton_detalles2;
    private javax.swing.JButton boton_detalles3;
    private javax.swing.JButton boton_detalles4;
    private javax.swing.JButton boton_editar;
    private javax.swing.JButton boton_eliminar;
    private javax.swing.JComboBox<String> combo_box_estado_pedido;
    private javax.swing.JComboBox<String> combo_box_tipo_pago;
    private javax.swing.JButton confirmar_pedido;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel label_buscar;
    private javax.swing.JLabel label_buscar1;
    private javax.swing.JLabel label_buscar12;
    private javax.swing.JLabel label_buscar2;
    private javax.swing.JLabel label_buscar3;
    private javax.swing.JLabel label_buscar4;
    private javax.swing.JLabel label_buscar5;
    private javax.swing.JLabel label_buscar6;
    private javax.swing.JDialog modal;
    private javax.swing.JDialog modal_detalles_bebida;
    private javax.swing.JDialog modal_detalles_comida;
    private javax.swing.JDialog modal_eliminar;
    private javax.swing.JDialog modal_tabla_items;
    private javax.swing.JDialog modal_ver_items;
    private javax.swing.JPanel panel_filtros;
    private javax.swing.JPanel panel_info;
    private javax.swing.JLabel panel_info_titulo;
    private javax.swing.JPanel panel_modal;
    private javax.swing.JPanel panel_modal3;
    private javax.swing.JPanel panel_modal4;
    private javax.swing.JPanel panel_modal_eliminar;
    private javax.swing.JPanel panel_tabla_item;
    private javax.swing.JPanel panel_ver_items;
    private javax.swing.JTable tabla;
    private javax.swing.JTable tabla_item;
    private javax.swing.JTable tabla_ver_items;
    private javax.swing.JTextField text_field_buscar;
    private javax.swing.JTextField text_field_cliente;
    private javax.swing.JLabel titulo_modal;
    private javax.swing.JLabel titulo_modal_detalle_bebida;
    private javax.swing.JLabel titulo_modal_detalle_comida;
    private javax.swing.JLabel titulo_modal_eliminar;
    private javax.swing.JLabel titulo_modal_items;
    private javax.swing.JLabel titulo_ver_items;
    private javax.swing.JButton ver_items_seleccionados;
    // End of variables declaration//GEN-END:variables
}
