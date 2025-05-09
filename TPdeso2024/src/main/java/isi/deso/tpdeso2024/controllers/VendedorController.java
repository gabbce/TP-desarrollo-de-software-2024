/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.controllers;


import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.Vendedor;
import isi.deso.tpdeso2024.daos.FactoryDAO;
import isi.deso.tpdeso2024.daos.VendedorDAO;
import isi.deso.tpdeso2024.dtos.CategoriaDTO;
import isi.deso.tpdeso2024.dtos.CoordenadaDTO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.dtos.VendedorDTO;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import java.util.*;

/**
 *
 * @author augus
 */
public class VendedorController {
    //metodos  listar, buscar, crear, eliminar, actualizar
    

//singleton
    private static VendedorController instance;
    public static VendedorController getInstance(){
        if(VendedorController.instance == null)VendedorController.instance =  new VendedorController();
        return VendedorController.instance;
    }
    
    
    private VendedorDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO();
    private VendedorController(){
    }
    
    
    public void crear(VendedorDTO vdto){
        //crear el objeto y mandarlo a db
       CoordenadaDTO c = new CoordenadaDTO(vdto.getCoordenada().getLatitud(),vdto.getCoordenada().getLongitud());
        Vendedor v = new Vendedor(
       0, //implementado identity increment en this.dao
                vdto.getNombre(),
                vdto.getDireccion(),
                 new Coordenada(vdto.getCoordenada().getLatitud(),vdto.getCoordenada().getLongitud())
        );

        //arranca vacia v.itemsMenu, se le agregan items al crearlos en la interfaz de items
        
        
        
        this.dao.crear(v);
    }
    
    
    public List<VendedorDTO> listar(){
        
        List<Vendedor> lista = this.dao.listar();
        
        List<VendedorDTO> resultado = new ArrayList<>();
        
        for(Vendedor v:lista) resultado.add(this.convertirADTO(v));
        
        return resultado;
        
        /*List<VendedorDTO> listaVendedores = new ArrayList<>();
        listaVendedores.add( new VendedorDTO("Agustin","Lavaise 800", new CoordenadaDTO(1,1)));
        
        return listaVendedores;*/
    }
    
    
    
    public void eliminar(int id){
        
        this.dao.eliminar(id);
    
    }
    
    public List<VendedorDTO> buscar(String nombreSubstring){
        //nombre es substring de nombre. Ignore case
        
        
        List<Vendedor> l = this.dao.buscar(nombreSubstring);
        
        List<VendedorDTO> resultado = new ArrayList<>();
        for(Vendedor v: l)resultado.add(convertirADTO(v));
        
        return resultado;
    }
    
    public VendedorDTO buscarPorID(int id) throws VendedorNoEncontradoException{
        Vendedor v = this.dao.buscarPorID(id);
        return this.convertirADTO(v);
    }
    
    public void actualizar(VendedorDTO vdto){
        //buscar el id del dto y actualizar con los otros datos
        
        
        this.dao.actualizar(convertirAModelo(vdto));
    }
    
    public VendedorDTO convertirADTO(Vendedor v){
        ArrayList<ItemMenuDTO> listaItems  = new ArrayList<>();
        /*for(ItemMenuDTO it:v.getItemsMenu()){
            listaItems.add(this.convertirItemADTO(it));
        }*/
        
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
    
    public Vendedor convertirAModelo(VendedorDTO vdto){
        ArrayList<ItemMenuDTO> listaItems  = new ArrayList<>();
        /*for(ItemMenuDTO it:v.getItemsMenu()){
            listaItems.add(this.convertirItemADTO(it));
        }*/
        
        return new Vendedor(
                vdto.getId(),
                vdto.getNombre(),
                vdto.getDireccion(),
                new Coordenada(
                        vdto.getCoordenada().getLongitud(),
                        vdto.getCoordenada().getLatitud()
                )/*,
                listaItems*/
        );
    }
     
    
}
