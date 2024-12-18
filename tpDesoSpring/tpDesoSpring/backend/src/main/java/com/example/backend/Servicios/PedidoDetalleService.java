/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Servicios;

import com.example.backend.Entidades.ItemMenu;
import com.example.backend.Entidades.Pedido;
import com.example.backend.Entidades.PedidoDetalle;
import com.example.backend.Repositorios.ItemMenuRepository;
import com.example.backend.Repositorios.PedidoDetalleRepository;
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
public class PedidoDetalleService {

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemMenuRepository itemMenuRepository;

    // Obtener todos los detalles de pedidos
    public List<PedidoDetalle> getAllPedidoDetalles() {
        return pedidoDetalleRepository.findAll();
    }

    // Obtener un detalle de pedido por ID
    public Optional<PedidoDetalle> getPedidoDetalleById(Long id) {
        return pedidoDetalleRepository.findById(id);
    }

    // Crear un nuevo detalle de pedido
    public PedidoDetalle createPedidoDetalle(PedidoDetalle pedidoDetalle) {
        // Verificar la existencia del Pedido asociado
        Pedido pedido = pedidoRepository.findById(pedidoDetalle.getPedido().getId())
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + pedidoDetalle.getPedido().getId()));

        // Verificar la existencia del ItemMenu asociado
        ItemMenu itemMenu = itemMenuRepository.findById(pedidoDetalle.getItem().getId())
            .orElseThrow(() -> new RuntimeException("ItemMenu no encontrado: " + pedidoDetalle.getItem().getId()));

        // Asignar las relaciones
        pedidoDetalle.setPedido(pedido);
        pedidoDetalle.setItem(itemMenu);

        // Guardar el detalle
        return pedidoDetalleRepository.save(pedidoDetalle);
    }

    // Actualizar un detalle de pedido existente
    public PedidoDetalle updatePedidoDetalle(Long id, PedidoDetalle pedidoDetalleDetails) {
        PedidoDetalle pedidoDetalle = pedidoDetalleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PedidoDetalle no encontrado: " + id));

        // Actualizar campos del detalle
        pedidoDetalle.setCantidad(pedidoDetalleDetails.getCantidad());

        // Verificar y asignar el Pedido si cambió
        if (pedidoDetalleDetails.getPedido() != null) {
            Pedido pedido = pedidoRepository.findById(pedidoDetalleDetails.getPedido().getId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + pedidoDetalleDetails.getPedido().getId()));
            pedidoDetalle.setPedido(pedido);
        }

        // Verificar y asignar el ItemMenu si cambió
        if (pedidoDetalleDetails.getItem() != null) {
            ItemMenu itemMenu = itemMenuRepository.findById(pedidoDetalleDetails.getItem().getId())
                .orElseThrow(() -> new RuntimeException("ItemMenu no encontrado: " + pedidoDetalleDetails.getItem().getId()));
            pedidoDetalle.setItem(itemMenu);
        }

        // Guardar los cambios
        return pedidoDetalleRepository.save(pedidoDetalle);
    }

    // Eliminar un detalle de pedido por ID
    public void deletePedidoDetalle(Long id) {
        pedidoDetalleRepository.deleteById(id);
    }

    // Obtener detalles de pedido por ID de pedido
    public List<PedidoDetalle> getDetallesByPedidoId(Long pedidoId) {
        return pedidoDetalleRepository.findAll()
            .stream()
            .filter(detalle -> detalle.getPedido().getId().equals(pedidoId))
            .toList();
    }
}