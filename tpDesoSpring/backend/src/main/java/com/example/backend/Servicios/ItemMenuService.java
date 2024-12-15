/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Servicios;

/**
 *
 * @author Miguel
 */
import com.example.backend.Entidades.ItemMenu;
import com.example.backend.Repositorios.ItemMenuRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Miguel
 */
@Service
public class ItemMenuService {

    @Autowired
    private ItemMenuRepository itemMenuRepository;

    // Obtener todos los elementos de menú
    public List<ItemMenu> getAllItemMenus() {
        return itemMenuRepository.findAll();
    }

    // Obtener un elemento de menú por id
    public Optional<ItemMenu> getItemMenuById(Long id) {
        return itemMenuRepository.findById(id);
    }

    // Crear un nuevo elemento de menú
    public ItemMenu createItemMenu(ItemMenu itemMenu) {
        return itemMenuRepository.save(itemMenu);
    }

    // Actualizar un elemento de menú existente
    public ItemMenu updateItemMenu(Long id, ItemMenu itemMenuDetails) {
        Optional<ItemMenu> itemMenu = itemMenuRepository.findById(id);
        if (itemMenu.isPresent()) {
            ItemMenu updatedItemMenu = itemMenu.get();
            updatedItemMenu.setNombre(itemMenuDetails.getNombre());
            updatedItemMenu.setDescripcion(itemMenuDetails.getDescripcion());
            updatedItemMenu.setPrecio(itemMenuDetails.getPrecio());
            updatedItemMenu.setIdCategoria(itemMenuDetails.getIdCategoria());
            updatedItemMenu.setIdVendedor(itemMenuDetails.getIdVendedor());
            updatedItemMenu.setEsComida(itemMenuDetails.getEsComida());
            return itemMenuRepository.save(updatedItemMenu);
        }
        return null;
    }

    // Eliminar un elemento de menú
    public void deleteItemMenu(Long id) {
        itemMenuRepository.deleteById(id);
    }
}

