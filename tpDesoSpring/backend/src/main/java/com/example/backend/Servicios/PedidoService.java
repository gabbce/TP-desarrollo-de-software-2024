/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Servicios;

import com.example.backend.Entidades.Pedido;
import com.example.backend.Repositorios.PedidoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Miguel
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Obtener todos los elementos de menú
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    // Obtener un elemento de menú por id
    public Optional<Pedido> getItemMenuById(Long id) {
        return pedidoRepository.findById(id);
    }

    // Crear un nuevo elemento de menú
    public Pedido createItemMenu(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Actualizar un elemento de menú existente
    public Pedido updateItemMenu(Long id, Pedido pedidoDetails) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            Pedido updatedPedido = pedido.get();
            updatedPedido.setTipoPago(pedidoDetails.getTipoPago());
            updatedPedido.setEstado(pedidoDetails.getEstado());
            updatedPedido.setIdCliente(pedidoDetails.getIdCliente());
            return pedidoRepository.save(updatedPedido);
        }
        return null;
    }

    // Eliminar un elemento de menú
    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}


