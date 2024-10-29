/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.controllers;

import isi.deso.tpdeso2024.daos.FactoryDAO;

/**
 *
 * @author augus
 */
public class ItemMenuController {
    //metodos listar, buscar, crear, eliminar, actualizar
    
    public ItemMenu buscar(ItemMenuDTO dto){
        ItemMenuDAO dao = FactoryDAO.getFactory(FactoryDAO.MEMORY).getItemMenuDAO();
        
        //crear objeto
        ItemMenu objeto = new ItemMenu(
        dto.getId(),
        
        );
        
        
        dao.buscar(...)
    
    }
    
    //metodos  buscar, crear, eliminar, actualizar
}
