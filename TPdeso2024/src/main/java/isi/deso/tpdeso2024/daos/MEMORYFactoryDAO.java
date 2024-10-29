/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

/**
 *
 * @author augus
 */
public class MEMORYFactoryDAO extends FactoryDAO{

    @Override
    public VendedorDAO getVendedorDAO() {return new VendedorMEMORYDAO();}
    @Override
    public ClienteDAO getClienteDAO() {return new ClienteMEMORYDAO();}
    @Override
    public ItemMenuDAO getItemMenuDAO() {return new ItemMenuMEMORYDAO();}
    @Override
    public PedidoDAO getPedidoDAO() {return new PedidoMEMORYDAO();}
    
}
