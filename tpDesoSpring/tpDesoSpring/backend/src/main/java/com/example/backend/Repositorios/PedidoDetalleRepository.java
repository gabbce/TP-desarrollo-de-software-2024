/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Repositorios;

import com.example.backend.Entidades.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Miguel
 */
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {
    // Métodos personalizados pueden ser definidos aquí
}
