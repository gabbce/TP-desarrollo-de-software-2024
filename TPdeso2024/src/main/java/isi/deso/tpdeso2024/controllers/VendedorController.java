/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.controllers;


import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.Vendedor;
import isi.deso.tpdeso2024.daos.FactoryDAO;
import isi.deso.tpdeso2024.daos.VendedorDAO;
import isi.deso.tpdeso2024.dtos.CoordenadaDTO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.dtos.VendedorDTO;
import java.util.*;

/**
 *
 * @author augus
 */
public class VendedorController {
    //metodos  listar, buscar, crear, eliminar, actualizar
    
    
    public List<VendedorDTO> listar(){
        VendedorDAO dao = FactoryDAO.getFactory(FactoryDAO.MEMORY).getVendedorDAO();
        
        List<Vendedor> lista = dao.listar();
        
        List<VendedorDTO> resultado = new ArrayList<>();
        
        for(Vendedor v:lista) resultado.add(this.convertirADTO(v));
        
        return resultado;
    }
    
    private VendedorDTO convertirADTO(Vendedor v){
        ArrayList<ItemMenuDTO> listaItems  = new ArrayList<>();
        for(ItemMenu it:v.getItemsMenu()){
            listaItems.add(this.convertirItemADTO(it));
        }
        
        return new VendedorDTO(
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
    it.getCategoria(),
            null
    );
    
    }
    
}
