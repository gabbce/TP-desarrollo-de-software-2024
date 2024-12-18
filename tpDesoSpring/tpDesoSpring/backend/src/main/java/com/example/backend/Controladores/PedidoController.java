/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Controladores;

import com.example.backend.Entidades.Pedido;
import com.example.backend.Entidades.PedidoDetalle;
import com.example.backend.Servicios.PedidoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 */@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Obtener todos los pedidos
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    // Obtener un pedido por su ID
    @GetMapping("/{id}")
    public Optional<Pedido> getPedidoById(@PathVariable Long id) {
        return pedidoService.getPedidoById(id);
    }

    @PostMapping("/pedidos")
        public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
            // Extraer detalles del pedido (ya están dentro del objeto 'pedido' en el frontend)
            List<PedidoDetalle> detalles = pedido.getDetalles();

            // Crear el pedido y sus detalles
            Pedido nuevoPedido = pedidoService.createPedido(pedido, detalles);

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
        }

    // Actualizar un pedido existente
    @PutMapping("/pedidos/{id}")
        public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails) {
            // Actualizar pedido con los nuevos detalles
            Pedido pedidoActualizado = pedidoService.updatePedido(id, pedidoDetails);

            if (pedidoActualizado != null) {
                return ResponseEntity.ok(pedidoActualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
    }
}