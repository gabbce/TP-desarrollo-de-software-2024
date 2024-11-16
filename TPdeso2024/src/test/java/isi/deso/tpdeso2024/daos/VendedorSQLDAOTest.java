/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Vendedor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author augus
 */
public class VendedorSQLDAOTest {
    

    
    public VendedorSQLDAOTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    @Test
    public void testPKUnica() {
        System.out.println("pk unica");
        VendedorSQLDAO instance = (VendedorSQLDAO) FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO();
        boolean expResult = true;
        List<Vendedor> lista = instance.listar();
        
        HashSet<Integer> ids;
        ids = new HashSet<>();
        boolean pkUnica = true;
        for(Vendedor v:lista)if(false == ids.add(v.getId())){
        pkUnica = false;
        break;
        }
        
        assertEquals(expResult, pkUnica);
        // TODO review the generated test code and remove the default call to fail.
        fail("Las pks de vendedor no son unicas(falla consistencia en la BD)");
    }

    /**
     * Test of listar method, of class VendedorSQLDAO.
     
    @Test
    public void testCrear() {
        System.out.println("crear");
        Vendedor v = null;
        VendedorSQLDAO instance = new VendedorSQLDAO();
        boolean expResult = false;
        boolean result = instance.crear(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminar method, of class VendedorSQLDAO.
    
    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        int id = 0;
        VendedorSQLDAO instance = new VendedorSQLDAO();
        boolean expResult = false;
        boolean result = instance.eliminar(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscar method, of class VendedorSQLDAO.
    
    @Test
    public void testBuscar() {
        System.out.println("buscar");
        String nombre = "";
        VendedorSQLDAO instance = new VendedorSQLDAO();
        List<Vendedor> expResult = null;
        List<Vendedor> result = instance.buscar(nombre);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listar method, of class VendedorSQLDAO.
    
    @Test
    public void testListar() {
        System.out.println("listar");
        VendedorSQLDAO instance = new VendedorSQLDAO();
        List<Vendedor> expResult = null;
        List<Vendedor> result = instance.listar();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actualizar method, of class VendedorSQLDAO.
    
    @Test
    public void testActualizar() {
        System.out.println("actualizar");
        Vendedor v = null;
        VendedorSQLDAO instance = new VendedorSQLDAO();
        boolean expResult = false;
        boolean result = instance.actualizar(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarPorID method, of class VendedorSQLDAO.
     
    @Test
    public void testBuscarPorID() throws Exception {
        System.out.println("buscarPorID");
        int id = 0;
        VendedorSQLDAO instance = new VendedorSQLDAO();
        Vendedor expResult = null;
        Vendedor result = instance.buscarPorID(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
  */
}
