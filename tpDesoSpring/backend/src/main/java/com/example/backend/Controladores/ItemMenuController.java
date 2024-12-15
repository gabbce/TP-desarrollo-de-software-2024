/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.Controladores;

import com.example.backend.Entidades.ItemMenu;
import com.example.backend.Servicios.ItemMenuService;
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
@RequestMapping("/api/itemsMenu")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemMenuController {

    @Autowired
    private ItemMenuService itemMenuService;

    @GetMapping
    public List<ItemMenu> getAllItemMenus() {
        return itemMenuService.getAllItemMenus();
    }
    
    @GetMapping("/{id}")
    public Optional<ItemMenu> getItemMenuById(@PathVariable Long id) {
        return itemMenuService.getItemMenuById(id);
    }
    
    // Crear un nuevo ItemMenu
    @PostMapping
    public ItemMenu createItemMenu(@RequestBody ItemMenu itemMenu) {
        return itemMenuService.createItemMenu(itemMenu);
    }
    
     @PutMapping("/{id}")
    public ItemMenu updateItemMenu(@PathVariable Long id, @RequestBody ItemMenu itemMenuDetails) {
        return itemMenuService.updateItemMenu(id, itemMenuDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteItemMenu(@PathVariable Long id) {
        itemMenuService.deleteItemMenu(id);
    }

}

