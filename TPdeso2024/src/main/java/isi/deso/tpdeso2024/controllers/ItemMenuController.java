/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.controllers;

import isi.deso.tpdeso2024.Bebida;
import isi.deso.tpdeso2024.Categoria;
import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.Plato;
import isi.deso.tpdeso2024.Vendedor;
import isi.deso.tpdeso2024.daos.CategoriaDAO;
import isi.deso.tpdeso2024.daos.CategoriaSQLDAO;
import isi.deso.tpdeso2024.daos.FactoryDAO;
import isi.deso.tpdeso2024.daos.ItemMenuDAO;
import isi.deso.tpdeso2024.dtos.BebidaDTO;
import isi.deso.tpdeso2024.dtos.CategoriaDTO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.dtos.PlatoDTO;
import isi.deso.tpdeso2024.excepciones.CategoriaNoEncontradoException;
import isi.deso.tpdeso2024.excepciones.ItemNoEncontradoExcepcion;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author augus
 */
public class ItemMenuController {
    //metodos listar, buscar, crear, eliminar, actualizar
    
    //singleton
    private static ItemMenuController instance;
    public static ItemMenuController getInstance(){
        if(ItemMenuController.instance == null)ItemMenuController.instance =  new ItemMenuController();
        return ItemMenuController.instance;
    }
    
	
    private ItemMenuController(){
        
    }
    
	
    public void eliminar(int id){
		//hay que eliminarlo del vendedor para que lo levante el 
        FactoryDAO.getFactory(FactoryDAO.SQL).getItemMenuDAO().eliminar(id);
    }
    
    public void actualizar(ItemMenuDTO dto, Boolean esComida) throws VendedorNoEncontradoException, CategoriaNoEncontradoException{
        int idVendedor = dto.getVendedor().getId();
        Vendedor v = FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO().buscarPorID(idVendedor);
        Categoria c = FactoryDAO.getFactory(FactoryDAO.SQL).getCategoriaDAO().buscarPorTipo(dto.getCategoria().getTipo());
        
        ItemMenu p = convertirAModelo(dto, esComida, v, c);
        
        FactoryDAO.getFactory(FactoryDAO.SQL).getItemMenuDAO().crear(p);
    }
    
    public List<ItemMenuDTO> listar(){
        ItemMenuDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getItemMenuDAO();
        
        
        List<ItemMenu> items = dao.listar();
    
        List<ItemMenuDTO> itemsdto = new ArrayList();
        
        for(ItemMenu i : items){
            try {
                itemsdto.add(convertirADTO(i, i.esComida()));
            } catch (VendedorNoEncontradoException ex) {
                Logger.getLogger(ItemMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        return itemsdto;
        
    }
    
    public ItemMenuDTO buscarPorID(int id) throws ItemNoEncontradoExcepcion{
        ItemMenuDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getItemMenuDAO();
        
        ItemMenu i = dao.buscarPorID(id);
        
        try {
            return convertirADTO(i, i.esComida());
        } catch (VendedorNoEncontradoException ex) {
            Logger.getLogger(ItemMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public List<ItemMenuDTO> filtrar(Boolean esComida, String nombre, String idVendedor, String categoria) throws VendedorNoEncontradoException{
        // esComida : null = no filtrar, true = filtrar comidas, false = filtrar bebidas;
        
        // SI SON TODOS NULOS, DEBE DEVOLVER LA LISTA COMPLETA
        
        ItemMenuDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getItemMenuDAO();
        
        
        List<ItemMenu> items = dao.buscar(esComida, nombre, idVendedor, categoria);
    
        List<ItemMenuDTO> itemsdto = new ArrayList();
        
        for(ItemMenu i : items){
            itemsdto.add(convertirADTO(i, i.esComida()));
        }
        
        
        return itemsdto;
    }
    
    //metodos  buscar, crear, eliminar, actualizar
    
    public void crear (ItemMenuDTO dto, Boolean esComida) throws VendedorNoEncontradoException, CategoriaNoEncontradoException{
        int idVendedor = dto.getVendedor().getId();
        
        
            Vendedor v = FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO().buscarPorID(idVendedor);
            Categoria c = FactoryDAO.getFactory(FactoryDAO.SQL).getCategoriaDAO().buscarPorTipo(dto.getCategoria().getTipo());
        
        
            ItemMenu p = convertirAModelo(dto, esComida, v, c);
        
            FactoryDAO.getFactory(FactoryDAO.SQL).getItemMenuDAO().crear(p);
        
        
        
    }
    
    public List<CategoriaDTO> listarCategorias(){
        List<Categoria> categorias = FactoryDAO.getFactory(FactoryDAO.SQL).getCategoriaDAO().listar();
        
        List<CategoriaDTO> cdto = new ArrayList();
        
        for(Categoria c : categorias){
            cdto.add(new CategoriaDTO(c.getId(), c.getDescripcion(), c.getTipo()));
        }
        
        return cdto;
    }
    
    public CategoriaDTO buscarCategoriaPorID(int id) throws CategoriaNoEncontradoException{
       Categoria c = FactoryDAO.getFactory(FactoryDAO.SQL).getCategoriaDAO().buscarPorID(id);
       
       return new CategoriaDTO(c.getId(), c.getDescripcion(), c.getTipo());
    }
    
    public CategoriaDTO buscarCategoriaPorTipo(String tipo) throws CategoriaNoEncontradoException{
       Categoria c = FactoryDAO.getFactory(FactoryDAO.SQL).getCategoriaDAO().buscarPorTipo(tipo);
       
       return new CategoriaDTO(c.getId(), c.getDescripcion(), c.getTipo());
    }
    
    
    public ItemMenuDTO convertirADTO(ItemMenu v, Boolean esComida) throws VendedorNoEncontradoException{
        if(esComida) {
            Plato p = (Plato) v;
            return new PlatoDTO(
                p.getId(),
                    p.getNombre(),
                    p.getDescripcion(),
                    VendedorController.getInstance().convertirADTO(FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO().buscarPorID(v.getVendedor().getId())),
                    new CategoriaDTO(p.getCategoria().getId(), p.getCategoria().getDescripcion(), p.getCategoria().getTipo()),
                    p.getPrecio(),
                    p.getPeso(),
                p.getCalorias(),
                p.aptoCeliaco(),
                    p.aptoVegano()
                
        );
        }
            Bebida p = (Bebida) v;
            return new BebidaDTO(
                p.getId(),
                    p.getNombre(),
                    p.getDescripcion(),
                    VendedorController.getInstance().convertirADTO(p.getVendedor()),
                    new CategoriaDTO(p.getCategoria().getId(), p.getCategoria().getDescripcion(), p.getCategoria().getTipo()),
                    p.getPrecio(),
                    p.getGraduacionAlcoholica(),
                    p.getTam()
        );

    }
    
    public ItemMenu convertirAModelo(ItemMenuDTO vdto, Boolean esComida, Vendedor v, Categoria c){
        
        if(esComida) {
            PlatoDTO p = (PlatoDTO) vdto;
            return new Plato(
                p.getId(),
                    p.getNombre(),
                    p.getDescripcion(),
                    v,
                    c,
                    p.getPrecio(),
                    p.getPeso(),
                p.getCalorias(),
                p.aptoCeliaco(),
                    p.aptoVegano()
                
        );
        }
            BebidaDTO p = (BebidaDTO) vdto;
            return new Bebida(
                p.getId(),
                    p.getNombre(),
                    p.getDescripcion(),
                    v,
                    c,
                    p.getPrecio(),
                    p.getGraduacionAlcoholica(),
                    p.getTam()
        );
        
    }
    
    
}
