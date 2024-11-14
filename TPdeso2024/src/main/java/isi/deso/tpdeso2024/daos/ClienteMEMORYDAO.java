/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.Cliente;
import isi.deso.tpdeso2024.dtos.ClienteDTO;
import isi.deso.tpdeso2024.excepciones.ClienteNoEncontradoException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author augus
 */
public class ClienteMEMORYDAO implements ClienteDAO {

    private static List<Cliente> clientes = new LinkedList<>();; //mantiene orden por id
    private static int ultimaID; //se auto incrementa
    // Constructor para inicializar la lista
    public ClienteMEMORYDAO() {
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
        for(Cliente v:clientes) if(v.getId()==id)clientes.remove(v);
        return true;  
    }


    @Override
    public List<Cliente> buscarPorCuit(int cuit) {
        ArrayList<Cliente> res = new ArrayList<>();
        for(Cliente v:clientes) {
            if(v.getCuit()==cuit) res.add(v);
        }
        return res;
    }
    
    @Override
    public Cliente buscarPorID(int id) throws ClienteNoEncontradoException{
        for (int i = 0; i < clientes.size(); i++) if(clientes.get(i).getId()==id)return clientes.get(i);
        throw new ClienteNoEncontradoException("No existe el cliente con id " + id);
    }

    @Override
    public List<Cliente> listar() {
        return ClienteMEMORYDAO.clientes;
    }

    @Override
    public boolean actualizar(Cliente v) {
      /*  //ahora mismo estan ordenados por id
        //int indice = this.buscarPorID(vdto.getId());
	Cliente v = null;
        for(Cliente v1:clientes) {
            if(v1.getId()==vdto.getId()){
				v=v1;
			}
        }
        
        v.setCuit(vdto.getCuit());
        v.setDireccion(vdto.getDireccion());
        Coordenada c = new Coordenada(
                vdto.getCoordenadas().getLatitud(),
                vdto.getCoordenadas().getLongitud()
        );
        
        v.setCoordenadas(c);
        v.setEmail(vdto.getEmail());
        
        */
        return true;
    }
    
}

