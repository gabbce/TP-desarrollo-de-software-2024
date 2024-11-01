/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.Vendedor;
import isi.deso.tpdeso2024.dtos.VendedorDTO;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author augus
 */
public class VendedorMEMORYDAO implements VendedorDAO {

    private List<Vendedor> vendedores;
    private int ultimaID; //se auto incrementa
    // Constructor para inicializar la lista
    public VendedorMEMORYDAO() {
        this.vendedores = new LinkedList<>();
        ultimaID = 1; 
    }

    @Override
    public boolean create(Vendedor v) {
        v.setId(ultimaID);
        ultimaID++;
        vendedores.add(v);
        return true;
    }

    @Override
    public boolean delete(int id) {
        //ahora mismo estan ordenados por id
        int indice = this.buscarPorID(id);
        vendedores.remove(indice);
        return true;  
    }


    @Override
    public List<Vendedor> buscar(String nombre) {
        ArrayList<Vendedor> res = new ArrayList<>();
        for(Vendedor v:vendedores) if(v.getNombre().equals(nombre)) res.add(v);
        return res;
    }

    @Override
    public List<Vendedor> listar() {
        return this.vendedores;
    }

    @Override
    public boolean actualizar(VendedorDTO vdto) {
        //ahora mismo estan ordenados por id
        int indice = this.buscarPorID(vdto.getId());
        Vendedor v = vendedores.get(indice);
        
        v.setNombre(vdto.getNombre());
        v.setDireccion(vdto.getDireccion());
        Coordenada c = new Coordenada(
                vdto.getCoordenada().getLatitud(),
                vdto.getCoordenada().getLongitud()
        );
        
        v.setCoordenadas(c);
        
        
        
        return true;
    }
    private int buscarPorID(int id){
        for (int i = 0; i < vendedores.size(); i++) if(vendedores.get(i).getId()==id)return i;
        return -1;
    }
}
