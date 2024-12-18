/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Entidades;

/**
 *
 * @author Miguel
 */
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author Miguel
 */
@Getter @Setter
@Entity
public class ItemMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Generación automática del id
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Long idCategoria;
    private Long idVendedor;
    private boolean esComida;
    private float graduacionAlcoholica;
    private float tam; 
    private float peso;
    private float calorias;
    private boolean aptoCeliaco;
    private boolean aptoVegano;

    
}
