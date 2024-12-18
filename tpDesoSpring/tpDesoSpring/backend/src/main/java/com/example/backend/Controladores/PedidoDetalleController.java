/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Controladores;

import com.example.backend.Entidades.PedidoDetalle;
import com.example.backend.Servicios.PedidoDetalleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Miguel
 */
@RestController
@RequestMapping("/api/pedido-detalles")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoDetalleController {

    @Autowired
    private PedidoDetalleService pedidoDetalleService;

    // Obtener todos los detalles de pedidos
    @GetMapping
    public List<PedidoDetalle> getAllPedidoDetalles() {
        return pedidoDetalleService.getAllPedidoDetalles();
    }

    // Obtener un detalle de pedido por su ID
    @GetMapping("/{id}")
    public Optional<PedidoDetalle> getPedidoDetalleById(@PathVariable Long id) {
        return pedidoDetalleService.getPedidoDetalleById(id);
    }

    // Crear un nuevo detalle de pedido
    @PostMapping
        public PedidoDetalle createPedidoDetalle(@RequestBody PedidoDetalle pedidoDetalle) {
            // Lógica para guardar el detalle del pedido
            return pedidoDetalleService.createPedidoDetalle(pedidoDetalle);
        }

    // Actualizar un detalle de pedido existente
    @PutMapping("/{id}")
    public PedidoDetalle updatePedidoDetalle(@PathVariable Long id, @RequestBody PedidoDetalle pedidoDetalleDetails) {
        return pedidoDetalleService.updatePedidoDetalle(id, pedidoDetalleDetails);
    }

    // Eliminar un detalle de pedido
    @DeleteMapping("/{id}")
    public void deletePedidoDetalle(@PathVariable Long id) {
        pedidoDetalleService.deletePedidoDetalle(id);
    }

    // Obtener los detalles de un pedido por su ID
    @GetMapping("/pedido/{pedidoId}")
    public List<PedidoDetalle> getDetallesByPedidoId(@PathVariable Long pedidoId) {
        return pedidoDetalleService.getDetallesByPedidoId(pedidoId);
    }
}
