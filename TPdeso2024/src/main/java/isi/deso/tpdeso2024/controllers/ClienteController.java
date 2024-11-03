/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.controllers;


import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.Cliente;
import isi.deso.tpdeso2024.daos.FactoryDAO;
import isi.deso.tpdeso2024.daos.ClienteDAO;
import isi.deso.tpdeso2024.dtos.CategoriaDTO;
import isi.deso.tpdeso2024.dtos.CoordenadaDTO;
import isi.deso.tpdeso2024.dtos.ItemMenuDTO;
import isi.deso.tpdeso2024.dtos.ClienteDTO;
import java.util.*;

/**
 *
 * @author augus
 */
public class ClienteController {
    //metodos  listar, buscar, crear, eliminar, actualizar
    

//singleton
    private static ClienteController instance;
    public static ClienteController getInstance(){
        if(ClienteController.instance == null)ClienteController.instance =  new ClienteController();
        return ClienteController.instance;
    }
    
    
    //private ClienteDAO dao = FactoryDAO.getFactory(FactoryDAO.MEMORY).getClienteDAO();
    private ClienteController(){
    }
    
    
    public void crear(ClienteDTO dto){
        //crear el objeto y mandarlo a db
        Coordenada c = new Coordenada(dto.getCoordenadas().getLatitud(),dto.getCoordenadas().getLongitud());
        Cliente v = new Cliente(
       0, //implementado identity increment en this.dao
                dto.getCuit(),
                dto.getEmail(),
                dto.getDireccion(),
                 c
        );        
        //this.dao.crear(v);
    }
    
    
    public List<ClienteDTO> listar(){
        
        /*List<Cliente> lista = this.dao.listar();
        
        List<ClienteDTO> resultado = new ArrayList<>();
        
        for(Cliente c:lista) resultado.add(this.convertirADTO(c));
        
        return resultado;*/
        
        return new ArrayList<>();
    }
    
    
    
    public void eliminar(int id){
        
       // this.dao.eliminar(id);
    
    }
    
    public List<ClienteDTO> buscar(String nombreSubstring){
        //nombre es substring de nombre. Ignore case
        
        
       /* List<Cliente> l = this.dao.buscar(nombreSubstring);
        
        List<ClienteDTO> resultado = new ArrayList<>();
        for(Cliente v: l)resultado.add(convertirADTO(v));
        
        return resultado;*/ return new ArrayList<>();
    }
    
    public void actualizar(ClienteDTO dto){
        //buscar el id del dto y actualizar con los otros datos
        
        //this.dao.actualizar(dto);
    }
    
    private ClienteDTO convertirADTO(Cliente v){
        
        return new ClienteDTO(
                v.getId(),
                v.getCuit(),
                v.getEmail(),
                v.getDireccion(),
                new CoordenadaDTO(
                        v.getCoordenadas().getLongitud(),
                        v.getCoordenadas().getLatitud()
                )
        );

    }
    
}

