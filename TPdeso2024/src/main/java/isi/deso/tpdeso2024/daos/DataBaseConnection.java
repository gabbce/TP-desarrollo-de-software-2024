/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 *
 * @author gabic
 */
public class DataBaseConnection {
        Connection con = null;
        String url="jdbc:postgresql://localhost:5432/tp_deso";
        String usuario="postgres";
        String clave="postgres";
        
        public Connection conectar(){
            try{
                Class.forName("org.postgresql.Driver");
                con=DriverManager.getConnection(url, usuario, clave);
                //JOptionPane.showMessageDialog(null, "conexion exitosa", "conexion", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "conexion fallida "+e, "conexion", JOptionPane.ERROR_MESSAGE);
				
			}
            return con;
        }
        public Connection cerrar(){
            try {
                con.close();
                //JOptionPane.showMessageDialog(null, "desconexion exitosa", "desconexion", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "desconexion fallida "+e, "desconexion", JOptionPane.ERROR_MESSAGE);
            }
            return con;
        }
}
