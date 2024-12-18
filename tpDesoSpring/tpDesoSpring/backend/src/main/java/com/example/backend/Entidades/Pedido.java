/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Entidades;

/**
 *
 * @author Miguel
 */
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Miguel
 */
@Getter @Setter
@Entity
public class Pedido {
       @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Generación automática del id
    private Long id;
    private String tipoPago;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente; // Relación con Cliente
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoDetalle> detalles= new ArrayList<>(); // Detalles del pedido

    // Getters and Setters
    
}

