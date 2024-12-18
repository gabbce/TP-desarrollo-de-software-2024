/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;
/**
 *
 * @author augus
 */
public abstract class FactoryDAO {
    public static final int SQL = 1;

    public abstract VendedorDAO getVendedorDAO();
    public abstract ClienteDAO getClienteDAO();
    public abstract ItemMenuDAO getItemMenuDAO();
    public abstract PedidoDAO getPedidoDAO();
    public abstract CategoriaDAO getCategoriaDAO();
    public abstract PagoDAO getPagoDAO();
    
    public static FactoryDAO getFactory(int claveFactory){
        switch (claveFactory) {
            case SQL -> {
                return new SQLFactoryDAO();
            }
            
        }
        return null; //Netbeans exige que retorne
    }

}
