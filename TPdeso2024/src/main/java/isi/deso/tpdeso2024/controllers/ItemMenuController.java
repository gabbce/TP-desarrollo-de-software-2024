/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.controllers;

import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.daos.FactoryDAO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
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
    
    public void eliminar(int id){
        
    }
    
    public void actualizar(ItemMenuDTO dto){
        
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
    
    public void crear (ItemMenuDTO dto){
        
    }
}
