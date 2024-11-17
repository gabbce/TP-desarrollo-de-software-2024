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
import isi.deso.tpdeso2024.PagoType;
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
import isi.deso.tpdeso2024.excepciones.ItemsNoSonDelMismoVendedorException;
import isi.deso.tpdeso2024.excepciones.PedidoNoEncontradoException;
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
    
    
    public void crear(PedidoDTO vdto) throws ClienteNoEncontradoException, ItemsNoSonDelMismoVendedorException {
        Cliente c = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO().buscarPorID(vdto.getCliente().getId());
        
        Pedido v = convertirAModelo(vdto,c);
        
        if(!v.itemsSonDelMismoVendedor())throw new ItemsNoSonDelMismoVendedorException("");
        //chequear existencia de los items?
        
        
        
        FactoryDAO.getFactory(FactoryDAO.SQL).getPedidoDAO().crear(v);
    }
    
    
    
    public void actualizar(PedidoDTO vdto) throws ClienteNoEncontradoException, ItemsNoSonDelMismoVendedorException{
        Cliente c = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO().buscarPorID(vdto.getCliente().getId());
        
        Pedido v = convertirAModelo(vdto,c);
        
        if(!v.itemsSonDelMismoVendedor())throw new ItemsNoSonDelMismoVendedorException("");
        //chequear existencia de los items?
        
        FactoryDAO.getFactory(FactoryDAO.SQL).getPedidoDAO().actualizar(v);
    }
    
    
    
    public void eliminar(int id){
        
       PedidoDAO pdao = FactoryDAO.getFactory(FactoryDAO.SQL).getPedidoDAO();
       pdao.eliminar(id);
    }
    
    public List<PedidoDTO> listar(){
       PedidoDAO pdao = FactoryDAO.getFactory(FactoryDAO.SQL).getPedidoDAO();
       
       List<Pedido> lista = pdao.listar();
       
       //buscar al cliente y los items y linkearlos
       for(Pedido p:lista)armarPedido(p);
       
       //convertir todo a dto, pedido e items
       List<PedidoDTO> ret = new ArrayList<>();
       for(Pedido p:lista)ret.add(convertirADTO(p));
       return ret;
    }

    public PedidoDTO buscarPorID(int id){
        try {
            Pedido v = FactoryDAO.getFactory(FactoryDAO.SQL).getPedidoDAO().buscarPorID(id);
            if(v == null) return null;
            armarPedido(v);
            return this.convertirADTO(v);
        } catch (PedidoNoEncontradoException e){
            return null;
        }
        
    }
    
    //utilidades
    
    public Pedido armarPedido(Pedido p){
           try{
               Cliente c = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO().buscarPorID(p.getCliente().getId());
               p.setCliente(c);
       
            for(PedidoDetalle pd:p.getPedidoDetalle()){
                ItemMenu it;
                   it = FactoryDAO.getFactory(FactoryDAO.SQL).getItemMenuDAO().buscarPorID(pd.getItem().getId());
                pd.setItem(it);
            }
           }
           catch(Exception e){
               System.out.println(e.getClass().getSimpleName()+" en isi.deso.tpdeso2024.controllers.PedidoController.armarPedido()");
               return null;
           }
           
           return p;
    }
    
    public List<EstadoPedido> getEstadosPedido(){
        return FactoryDAO.getFactory(FactoryDAO.SQL).getPedidoDAO().getEstadosPedido();
    }
    
    public List<PagoType> getPagoTypes(){
        return FactoryDAO.getFactory(FactoryDAO.SQL).getPedidoDAO().getPagoTypes();
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
        Pedido p = new Pedido(vdto.getId(),null,vdto.getEstado(),c);
                
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
