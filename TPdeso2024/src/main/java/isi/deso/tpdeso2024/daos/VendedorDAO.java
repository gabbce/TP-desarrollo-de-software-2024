/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Vendedor;
import java.util.List;

/**
 *
 * @author augus
 */
public interface VendedorDAO {
    public boolean create(Vendedor v);
    public boolean delete(Vendedor v);
    public boolean update(Vendedor v);
    public boolean read (Vendedor v);

    public List<Vendedor> listar();
}
