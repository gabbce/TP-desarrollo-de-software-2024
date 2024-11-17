/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.dtos;

/**
 *
 * @author gabic
 */
public class ClienteDTO {
    private int id;
    private String cuit;
    private String email;
    private String direccion;
    private CoordenadaDTO coordenadas;

    public ClienteDTO(int id) {
        this.id = id;
    }

    public ClienteDTO(int id, String cuit, String email, String direccion, CoordenadaDTO coordenadas) {
        this.id = id;
        this.cuit = cuit;
        this.email = email;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
    }
    
    public ClienteDTO(String cuit, String email, String direccion, CoordenadaDTO coordenadas) {
        
        this.cuit = cuit;
        this.email = email;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
    }

    public int getId() {
        return id;
    }
    
    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CoordenadaDTO getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(CoordenadaDTO coordenadas) {
        this.coordenadas = coordenadas;
    }
    
    
}
