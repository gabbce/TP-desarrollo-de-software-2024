/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.controllers;


import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.Pedido;
import isi.deso.tpdeso2024.daos.FactoryDAO;
import isi.deso.tpdeso2024.daos.PedidoDAO;
import isi.deso.tpdeso2024.dtos.CategoriaDTO;
import isi.deso.tpdeso2024.dtos.CoordenadaDTO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.dtos.PedidoDTO;
import java.util.*;

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
    
    
    private PedidoDAO dao = FactoryDAO.getFactory(FactoryDAO.MEMORY).getPedidoDAO();
    private PedidoController(){
    }
    
    
    public void crear(PedidoDTO vdto){
        //crear el objeto y mandarlo a db
		/*
		 this.detalle = detalle;
        this.pago = pago;
        this.estado = estado;
        this.cliente = cliente;
        this.changed = false;*/
        Pedido v = new Pedido(
       0, //implementado identity increment en this.dao
                vdto.getDetalle(),
                vdto.getPago(),
				vdto.getEstado(),
				vdto.getCliente(),
				false
		);

        
        
        this.dao.crear(v);
    }
    
    
    public List<PedidoDTO> listar(){
        
        List<Pedido> lista = this.dao.listar();
        
        List<PedidoDTO> resultado = new ArrayList<>();
        
        for(Pedido v:lista) resultado.add(this.convertirADTO(v));
        
        return resultado;
        
        /*List<PedidoDTO> listaPedidoes = new ArrayList<>();
        listaPedidoes.add( new PedidoDTO("Agustin","Lavaise 800", new CoordenadaDTO(1,1)));
        
        return listaPedidoes;*/
    }
    
    
    
    public void eliminar(int id){
        
        this.dao.eliminar(id);
    
    }
    
    public List<PedidoDTO> buscar(String nombreSubstring){
        //nombre es substring de nombre. Ignore case
        
        
        List<Pedido> l = this.dao.buscar(nombreSubstring);
        
        List<PedidoDTO> resultado = new ArrayList<>();
        for(Pedido v: l)resultado.add(convertirADTO(v));
        
        return resultado;
    }
    
    public void actualizar(PedidoDTO vdto){
        //buscar el id del dto y actualizar con los otros datos
        
        this.dao.actualizar(vdto);
    }
    
    private PedidoDTO convertirADTO(Pedido v){
        ArrayList<ItemMenuDTO> listaItems  = new ArrayList<>();
        for(ItemMenu it:v.getItemsMenu()){
            listaItems.add(this.convertirItemADTO(it));
        }
        
        return new PedidoDTO(
                v.getId(),
                v.getNombre(),
                v.getDireccion(),
                new CoordenadaDTO(
                        v.getCoordenadas().getLongitud(),
                        v.getCoordenadas().getLatitud()
                ),
                listaItems
        );

    }
    
    private ItemMenuDTO convertirItemADTO(ItemMenu it){
    return new ItemMenuDTO(
    it.getId(),
    it.getNombre(),
    it.getDescripcion(),
    it.getPrecio(),
    new CategoriaDTO(it.getCategoria().getId(), it.getCategoria().getDescripcion(), it.getCategoria().getTipo()),
            null
    );
    
}
