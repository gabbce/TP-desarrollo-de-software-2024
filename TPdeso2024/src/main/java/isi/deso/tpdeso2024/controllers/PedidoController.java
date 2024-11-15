/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.controllers;


import isi.deso.tpdeso2024.Cliente;
import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.EstadoPedido;
import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.Pago;
import isi.deso.tpdeso2024.Pedido;
import isi.deso.tpdeso2024.PedidoDetalle;
import isi.deso.tpdeso2024.daos.FactoryDAO;
import isi.deso.tpdeso2024.daos.PedidoDAO;
import isi.deso.tpdeso2024.dtos.CategoriaDTO;
import isi.deso.tpdeso2024.dtos.ClienteDTO;
import isi.deso.tpdeso2024.dtos.CoordenadaDTO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.dtos.PedidoDTO;
import isi.deso.tpdeso2024.dtos.PedidoDetalleDTO;
import isi.deso.tpdeso2024.excepciones.ClienteNoEncontradoException;
import isi.deso.tpdeso2024.excepciones.ItemNoEncontradoExcepcion;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author augus
 */
public class PedidoController {
    //metodos  listar, buscar, crear, eliminar, actualizar
    

//singleton
    private static PedidoController instance;
    public static PedidoController getInstance(){
        if(PedidoController.instance == null)PedidoController.instance =  new PedidoController();
        return PedidoController.instance;
    }
    
    
    //private PedidoDAO dao = FactoryDAO.getFactory(FactoryDAO.MEMORY).getPedidoDAO();
    private PedidoController(){
    }
    
    
    public void crear(PedidoDTO vdto) throws ClienteNoEncontradoException {
        Cliente c = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO().buscarPorID(vdto.getCliente().getId());
        
        Pedido v = convertirAModelo(vdto,c);
        
        FactoryDAO.getFactory(FactoryDAO.SQL).getPedidoDAO().crear(v);
    }
    
    
    public List<PedidoDTO> listar(){
        
        /*List<Pedido> lista = this.dao.listar();
        
        List<PedidoDTO> resultado = new ArrayList<>();
        
        for(Pedido v:lista) resultado.add(this.convertirADTO(v));
        
        return resultado;*/
        
        return new ArrayList<>();
        
        /*List<PedidoDTO> listaPedidoes = new ArrayList<>();
        listaPedidoes.add( new PedidoDTO("Agustin","Lavaise 800", new CoordenadaDTO(1,1)));
        
        return listaPedidoes;*/
    }
    
    
    
    public void eliminar(int id){
        
        //this.dao.eliminar(id);
    
    }
    
    public List<PedidoDTO> buscar(String nombreSubstring){
        //nombre es substring de nombre. Ignore case
        
        /*
        List<Pedido> l = this.dao.buscar(nombreSubstring);
        
        List<PedidoDTO> resultado = new ArrayList<>();
        for(Pedido v: l)resultado.add(convertirADTO(v));
        
        return resultado;*/
        
        return new ArrayList<>();
    }
    
    public void actualizar(PedidoDTO vdto){
        //buscar el id del dto y actualizar con los otros datos
        
       // this.dao.actualizar(vdto);
    }
    
    public PedidoDTO convertirADTO(Pedido v){
        
        LinkedList<PedidoDetalleDTO> pedidoDetalleDTO = new LinkedList<>();
        
        ClienteDTO clienteDTO = ClienteController.getInstance().convertirADTO(v.getCliente());
        
        PedidoDTO ret = new PedidoDTO(v.getId(),pedidoDetalleDTO,null, v.getEstado(),clienteDTO);
               
        for(PedidoDetalle pd:v.getPedidoDetalle())pedidoDetalleDTO.add(convertirADTO(pd,ret));
        
        
        return  ret;
    }
    
    public PedidoDetalleDTO convertirADTO(PedidoDetalle pd,PedidoDTO pdto){
        
    ItemMenuDTO itemdto = null;
        try {
            itemdto = ItemMenuController.getInstance().convertirADTO(pd.getItem(), pd.getItem().esComida());
        } catch (VendedorNoEncontradoException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    return new PedidoDetalleDTO(pdto, itemdto, pd.getCantidad());  
    }

    public Pedido convertirAModelo(PedidoDTO vdto,Cliente c) {
        Pedido p = new Pedido(vdto.getId(),null,EstadoPedido.PENDIENTE,c);
                
        for(PedidoDetalleDTO pdto: vdto.getPedidoDetalle()){
            p.getPedidoDetalle().add(
            convertirAModelo(pdto,p)
            );
        }
        return p;
    }

    public PedidoDetalle convertirAModelo(PedidoDetalleDTO pdto, Pedido p) {
        
        ItemMenu item;
        item = null;
        try {
            item = FactoryDAO.getFactory(FactoryDAO.SQL).getItemMenuDAO().buscarPorID(pdto.getItem().getId());
        } catch (ItemNoEncontradoExcepcion ex) {//no deberia suceder
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new PedidoDetalle(p, item, pdto.getCantidad());
    
    }
    
    
}
