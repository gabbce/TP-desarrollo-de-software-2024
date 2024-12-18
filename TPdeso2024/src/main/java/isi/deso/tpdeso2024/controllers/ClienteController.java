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
import isi.deso.tpdeso2024.excepciones.ClienteNoEncontradoException;
import java.sql.SQLException;
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
    
    
    //private ClienteDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO();
    private ClienteController(){
    }
    
    
    public void crear(ClienteDTO dto) throws SQLException{
        //crear el objeto y mandarlo a db
        Coordenada c = new Coordenada(dto.getCoordenadas().getLatitud(),dto.getCoordenadas().getLongitud());
        Cliente v = new Cliente(
       0, //implementado identity increment en this.dao
                dto.getCuit(),
                dto.getEmail(),
                dto.getDireccion(),
                c
        );        
		ClienteDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO();
		dao.crear(v);
        //this.dao.crear(v);
    }
    
    
    public List<ClienteDTO> listar(){
        ClienteDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO();
        List<Cliente> lista = dao.listar();
        
        List<ClienteDTO> resultado = new ArrayList<>();
        
        for(Cliente c:lista) resultado.add(this.convertirADTO(c));
        
        
        
        return resultado;
		}
    
    
    
    public void eliminar(int id){
       ClienteDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO();
       dao.eliminar(id);
    
    }
    
    public List<ClienteDTO> buscarPorCuit(String cuit){
        //nombre es substring de nombre. Ignore case
        
        ClienteDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO();
        
		List<Cliente> l = dao.buscarPorCuit(cuit);
        
        List<ClienteDTO> resultado = new ArrayList<>();
        for(Cliente v: l)resultado.add(convertirADTO(v));
        
        return resultado;// return new ArrayList<>();
    }
    
    public ClienteDTO buscarPorID(int id) throws ClienteNoEncontradoException{
        ClienteDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO();
        
        ClienteDTO c = convertirADTO(dao.buscarPorID(id));
        
        return c;
    }
    
    public void actualizar(ClienteDTO dto) throws SQLException{
        //buscar el id del dto y actualizar con los otros datos
        ClienteDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getClienteDAO();
        dao.actualizar(convertirAModelo(dto));
		
    }
    
    public ClienteDTO convertirADTO(Cliente v){
        
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
    
    public Cliente convertirAModelo(ClienteDTO vdto){
        
        return new Cliente(
                vdto.getId(),
                vdto.getCuit(),
                vdto.getEmail(),
                vdto.getDireccion(),
                new Coordenada(
                        vdto.getCoordenadas().getLongitud(),
                        vdto.getCoordenadas().getLatitud()
                )
        );

    }
    
}

