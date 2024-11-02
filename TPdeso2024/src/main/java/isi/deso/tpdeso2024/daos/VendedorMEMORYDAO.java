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

    private List<Vendedor> vendedores; //mantiene orden por id
    private int ultimaID; //se auto incrementa
    // Constructor para inicializar la lista
    public VendedorMEMORYDAO() {
        this.vendedores = new LinkedList<>();
        ultimaID = 1; 
    }

    @Override
    public boolean crear(Vendedor v) {
        v.setId(ultimaID);
        ultimaID++;
        vendedores.add(v);
        return true;
    }

    @Override
    public boolean eliminar(int id) {
        //ahora mismo estan ordenados por id
        //int indice = this.buscarPorID(id);
        vendedores.remove(id-1);
        return true;  
    }


    @Override
    public List<Vendedor> buscar(String nombre) {
        ArrayList<Vendedor> res = new ArrayList<>();
        for(Vendedor v:vendedores) {
            String s = v.getNombre().substring(0,nombre.length());
            if(s.equalsIgnoreCase(nombre)) res.add(v);
        }
        return res;
    }

    @Override
    public List<Vendedor> listar() {
        return this.vendedores;
    }

    @Override
    public boolean actualizar(VendedorDTO vdto) {
        //ahora mismo estan ordenados por id
        //int indice = this.buscarPorID(vdto.getId());
        Vendedor v = vendedores.get(vdto.getId()-1);
        
        v.setNombre(vdto.getNombre());
        v.setDireccion(vdto.getDireccion());
        Coordenada c = new Coordenada(
                vdto.getCoordenada().getLatitud(),
                vdto.getCoordenada().getLongitud()
        );
        
        v.setCoordenadas(c);
        
        
        
        return true;
    }
    private int buscarPorID(int id){ //no se usa xq mantiene orden por id
        for (int i = 0; i < vendedores.size(); i++) if(vendedores.get(i).getId()==id)return i;
        return -1;
    }
}
