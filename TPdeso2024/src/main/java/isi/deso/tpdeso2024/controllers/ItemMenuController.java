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
import isi.deso.tpdeso2024.daos.FactoryDAO;
import isi.deso.tpdeso2024.daos.ItemMenuDAO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.excepciones.CategoriaNoEncontradoException;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import java.util.ArrayList;
import java.util.List;

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
    
    private CategoriaDAO catdao;
    private ItemMenuDAO itemdao;
    private ItemMenuController(){
        this.catdao = FactoryDAO.getFactory(1).getCategoriaDAO();
        this.itemdao = FactoryDAO.getFactory(1).getItemMenuDAO();
    }
    
    public void eliminar(int id){
        
    }
    
    public void actualizar(ItemMenuDTO dto) throws VendedorNoEncontradoException, CategoriaNoEncontradoException{
        
    }
    
    public List<ItemMenuDTO> listar(){
        
        return new ArrayList<>();
    }
    
    public List<ItemMenuDTO> buscar(String nombre){
        /*ItemMenuDAO dao = FactoryDAO.getFactory(FactoryDAO.MEMORY).getItemMenuDAO();
        
        //crear objeto
        ItemMenu objeto = new ItemMenu(
        dto.getId(),
        
        );
        
        
        dao.buscar(...)*/
    
        return new ArrayList<>();
    }
    
    //metodos  buscar, crear, eliminar, actualizar
    
    public void crear (ItemMenuDTO dto) throws VendedorNoEncontradoException, CategoriaNoEncontradoException{
        int idVendedor = dto.getVendedor().getId();
        Vendedor v = FactoryDAO.getFactory(1).getVendedorDAO().buscarPorID(idVendedor);
        Categoria c = this.catdao.buscarPorID(dto.getCategoria().getId());
        
        if(dto.isEsComida()){
        Plato it = new Plato(
                0,//arbitrario
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getPrecio(),
                c,
                v
        )
        }
        else{
            Bebida it = new Bebida(idVendedor, nombre, descripcion, idVendedor, c, idVendedor, idVendedor, v)
        
        }
        
        
        this.itemdao.crear(it);
        
    }
}
