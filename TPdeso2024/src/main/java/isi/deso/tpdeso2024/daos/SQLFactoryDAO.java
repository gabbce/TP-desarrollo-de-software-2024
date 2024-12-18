/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

/**
 *
 * @author augus
 */
public class SQLFactoryDAO extends FactoryDAO{

    @Override
    public VendedorDAO getVendedorDAO() {return new VendedorSQLDAO();}
    @Override
    public ClienteDAO getClienteDAO() {return new ClienteSQLDAO();}
    @Override
    public ItemMenuDAO getItemMenuDAO() {return new ItemMenuSQLDAO();}
    @Override
    public PedidoDAO getPedidoDAO() {return new PedidoSQLDAO();}

    @Override
    public CategoriaDAO getCategoriaDAO() {return new CategoriaSQLDAO();}

    @Override
    public PagoDAO getPagoDAO() {
        return new PagoSQLDAO();
    }
    
    
    
}
