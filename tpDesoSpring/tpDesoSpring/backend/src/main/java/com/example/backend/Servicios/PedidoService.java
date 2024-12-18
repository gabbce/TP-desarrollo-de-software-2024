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
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemMenuRepository itemMenuRepository;
    
    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;
    
    // Obtener todos los pedidos
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    // Obtener un pedido por ID
    public Optional<Pedido> getPedidoById(Long id) {
        return pedidoRepository.findById(id);
    }

    // Crear un nuevo pedido con sus detalles
    public Pedido createPedido(Pedido pedido, List<PedidoDetalle> detalles) {
         // Guardar pedido
            pedidoRepository.save(pedido);

            // Guardar detalles de pedido
            for (PedidoDetalle detalle : detalles) {
                detalle.setPedido(pedido);
                pedidoDetalleRepository.save(detalle);
            }

    return pedido;
    }

    // Actualizar un pedido existente junto con sus detalles
    public Pedido updatePedido(Long id, Pedido pedidoDetails) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();

            // Actualizar los datos básicos del pedido
            pedido.setTipoPago(pedidoDetails.getTipoPago());
            pedido.setEstado(pedidoDetails.getEstado());
            pedido.setIdCliente(pedidoDetails.getIdCliente());

            // Limpiar y reasignar los detalles
            if (pedido.getDetalles() != null) {
                pedido.getDetalles().clear();
            }

            if (pedidoDetails.getDetalles() != null) {
                for (PedidoDetalle detalle : pedidoDetails.getDetalles()) {
                    // Obtener el ItemMenu correspondiente
                    ItemMenu itemMenu = itemMenuRepository.findById(detalle.getItem().getId())
                        .orElseThrow(() -> new RuntimeException("ItemMenu no encontrado: " + detalle.getItem().getId()));
                    detalle.setItem(itemMenu);
                    detalle.setPedido(pedido);
                    pedido.getDetalles().add(detalle);
                }
            }

            // Guardar los cambios
            return pedidoRepository.save(pedido);
        }

        return null; // Si no se encuentra el pedido
    }

    // Eliminar un pedido por ID
    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
