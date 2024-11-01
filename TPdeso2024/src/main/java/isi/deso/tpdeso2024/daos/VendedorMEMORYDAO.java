/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Vendedor;
import java.util.ArrayList;
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
        this.vendedores = new ArrayList();
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
    public boolean delete(Vendedor v) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Vendedor v) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean read(Vendedor v) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Vendedor> listar() {
        return this.vendedores;
    }
    
}
