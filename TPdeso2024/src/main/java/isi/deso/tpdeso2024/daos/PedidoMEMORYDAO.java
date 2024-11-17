/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.Pedido;
import isi.deso.tpdeso2024.dtos.PedidoDTO;
import isi.deso.tpdeso2024.excepciones.PedidoNoEncontradoException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author augus
 */
public class PedidoMEMORYDAO implements PedidoDAO {

    private List<Pedido> pedidos; //mantiene orden por id
    private int ultimaID; //se auto incrementa
    // Constructor para inicializar la lista
    public PedidoMEMORYDAO() {
        this.pedidos = new LinkedList<>();
        ultimaID = 1; 
    }

    @Override
    public boolean crear(Pedido v) {
        v.setId(ultimaID);
        ultimaID++;
        pedidos.add(v);
        return true;
    }

    @Override
    public boolean eliminar(int id) {
        //ahora mismo estan ordenados por id
        //int indice = this.buscarPorID(id);
        pedidos.remove(id-1);
        return true;  
    }


    public List<Pedido> buscar(String nombre) {
        ArrayList<Pedido> res = new ArrayList<>();
        for(Pedido v:pedidos) {
           /* String s = v.getNombre().substring(0,nombre.length());
            if(s.equalsIgnoreCase(nombre)) res.add(v);*/
        }
        return res;
    }

    @Override
    public List<Pedido> listar() {
        return this.pedidos;
    }

    public boolean actualizar(PedidoDTO vdto) {
        //ahora mismo estan ordenados por id
        //int indice = this.buscarPorID(vdto.getId());
        Pedido v = pedidos.get(vdto.getId()-1);
        /*
        v.setNombre(vdto.getNombre());
        v.setDireccion(vdto.getDireccion());
        Coordenada c = new Coordenada(
                vdto.getCoordenada().getLatitud(),
                vdto.getCoordenada().getLongitud()
        );
        
        v.setCoordenadas(c);*/
        
        
        
        return true;
    }

    @Override
    public boolean actualizar(Pedido v) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pedido buscarPorID(int id) throws PedidoNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
