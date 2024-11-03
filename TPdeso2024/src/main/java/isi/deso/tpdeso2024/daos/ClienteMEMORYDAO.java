/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.Cliente;
import isi.deso.tpdeso2024.dtos.ClienteDTO;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author augus
 */
public class ClienteMEMORYDAO implements ClienteDAO {

    private List<Cliente> clientes; //mantiene orden por id
    private int ultimaID; //se auto incrementa
    // Constructor para inicializar la lista
    public ClienteMEMORYDAO() {
        this.clientes = new LinkedList<>();
        ultimaID = 1; 
    }

    @Override
    public boolean crear(Cliente v) {
        v.setId(ultimaID);
        ultimaID++;
        clientes.add(v);
        return true;
    }

    @Override
    public boolean eliminar(int id) {
        //ahora mismo estan ordenados por id
        //int indice = this.buscarPorID(id);
        clientes.remove(id-1);
        return true;  
    }


    @Override
    public List<Cliente> buscar(String nombre) {
        ArrayList<Cliente> res = new ArrayList<>();
        for(Cliente v:clientes) {
            /*String s = v.getNombre().substring(0,nombre.length());
            if(s.equalsIgnoreCase(nombre)) res.add(v);*/
        }
        return res;
    }

    @Override
    public List<Cliente> listar() {
        return this.clientes;
    }

    @Override
    public boolean actualizar(ClienteDTO vdto) {
        //ahora mismo estan ordenados por id
        //int indice = this.buscarPorID(vdto.getId());
        Cliente v = clientes.get(vdto.getId()-1);
        
        /*v.setNombre(vdto.getNombre());
        v.setDireccion(vdto.getDireccion());
        Coordenada c = new Coordenada(
                vdto.getCoordenada().getLatitud(),
                vdto.getCoordenada().getLongitud()
        );
        
        v.setCoordenadas(c);*/
        
        
        
        return true;
    }
}
