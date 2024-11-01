/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package isi.deso.tpdeso2024;

import isi.deso.tpdeso2024.controllers.*;
import isi.deso.tpdeso2024.daos.*;
import isi.deso.tpdeso2024.dtos.*;
import isi.deso.tpdeso2024.utils.ModeloTabla;


import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JFrame;
/**
 *
 * @author gabic
 */
public class mainPrueba {

    public static void main(String[] args) throws IOException { 
      
       VendedorDAO vendedorMemory = FactoryDAO.getFactory(FactoryDAO.MEMORY).getVendedorDAO();
       VendedorController vendedorController = VendedorController.getInstance();
       
       vendedorController.crear( new VendedorDTO("Agustin","Lavaise 800", new CoordenadaDTO(1,1)));
       vendedorController.crear( new VendedorDTO("Augusto","Lavaise 900", new CoordenadaDTO(2,1)));
       vendedorController.crear( new VendedorDTO("Gabriel","Lavaise 200", new CoordenadaDTO(5,1)));
 
       System.out.println(vendedorController.listar());

        VendedorInterface miVentana = new VendedorInterface();
        miVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        miVentana.setVisible(true);
       
        
     
    }
    
    
    
}

