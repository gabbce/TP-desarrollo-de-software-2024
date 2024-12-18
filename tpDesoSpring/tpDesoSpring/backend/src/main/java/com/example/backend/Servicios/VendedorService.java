/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Servicios;

/**
 *
 * @author Miguel
 */
import com.example.backend.Repositorios.VendedorRepository;
import com.example.backend.entidades.Vendedor;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Miguel
 */
@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> getAllVendedores() {
        return vendedorRepository.findAll();
    }

    public Optional<Vendedor> getVendedorById(Long id) {
        return vendedorRepository.findById(id);
    }

    public Vendedor createVendedor(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public Vendedor updateVendedor(Long id, Vendedor vendedorDetails) {
        Optional<Vendedor> vendedor = vendedorRepository.findById(id);
        if (vendedor.isPresent()) {
            Vendedor updatedVendedor = vendedor.get();
            updatedVendedor.setNombre(vendedorDetails.getNombre());
            updatedVendedor.setDireccion(vendedorDetails.getDireccion());
            updatedVendedor.setLatitud(vendedorDetails.getLatitud());
            updatedVendedor.setLongitud(vendedorDetails.getLongitud());
            return vendedorRepository.save(updatedVendedor);
        }
        return null;
    }

    public void deleteVendedor(Long id) {
        vendedorRepository.deleteById(id);
    }
}
