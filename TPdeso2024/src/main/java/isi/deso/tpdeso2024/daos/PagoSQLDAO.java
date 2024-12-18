/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Pago;
import isi.deso.tpdeso2024.PagoMercadoPago;
import isi.deso.tpdeso2024.PagoTransferencia;
import isi.deso.tpdeso2024.PagoType;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabic
 */
class PagoSQLDAO implements PagoDAO{
    private DataBaseConnection conector;
    public PagoSQLDAO() {
		conector = new DataBaseConnection();
    }
    
    

    @Override
    public boolean crear(Pago p) throws SQLException {
        try {
            this.conector.conectar();

            // Se verifica el tipo de pago mediante el método getStrategyType
            String pagoType = String.valueOf(p.getStrategyType());  // MERCADOPAGO o TRANSFERENCIA
            PreparedStatement preparedStatement;

            if (pagoType.equals("MERCADO_PAGO")) {
                // Si es MercadoPago, se obvia el cuit
                preparedStatement = conector.con.prepareStatement("""
                INSERT INTO pago (id_pedido, pagoType, alias_cbu, cuit, fecha_pago)
                VALUES (?, ?, ?, NULL, ?);
            """);
                
                PagoMercadoPago paux = (PagoMercadoPago) p;
                // Setear valores para MercadoPago
                preparedStatement.setInt(1, paux.getId_pedido());
                preparedStatement.setString(2, pagoType);
                preparedStatement.setString(3, ((PagoMercadoPago) p).getAlias());  // Obtener alias de MercadoPago
                preparedStatement.setDate(4, Date.valueOf(paux.getFechaPago().toLocalDate()));
            } else {
                // Si es Transferencia, se usa cbu y cuit
                preparedStatement = conector.con.prepareStatement("""
                INSERT INTO pago (id_pedido, pagoType, alias_cbu, cuit, fecha_pago)
                VALUES (?, ?, ?, ?, ?);
            """);

                // Setear valores para Transferencia
                PagoTransferencia paux = (PagoTransferencia) p;
                preparedStatement.setInt(1, paux.getId_pedido());
                preparedStatement.setString(2, pagoType);
                preparedStatement.setString(3, ((PagoTransferencia) p).getCbu());  
                preparedStatement.setString(4, ((PagoTransferencia) p).getCuit());  
                preparedStatement.setObject(5, paux.getFechaPago());
            }

            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.conector.cerrar();
        } catch (SQLException e) {
            System.out.println("Excepción en " + this.getClass().getName() + ".crear() " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    
    @Override
public boolean eliminar(int id_pedido) throws SQLException{
    try {
        this.conector.conectar();

        PreparedStatement preparedStatement = conector.con.prepareStatement("""
            DELETE FROM pago WHERE id_pedido = ?;
        """);

        // Setear valores
        preparedStatement.setInt(1, id_pedido);

        preparedStatement.executeUpdate();
        preparedStatement.close();
        this.conector.cerrar();
    } catch (SQLException e) {
        System.out.println("Excepción en " + this.getClass().getName() + ".eliminar() " + e.getMessage());
        throw new SQLException(e.getMessage());
    }
    return true;
}

    @Override
    public boolean actualizar(Pago p) throws SQLException {
        try {
            this.conector.conectar();

            // Se verifica el tipo de pago mediante el método getStrategyType
            String pagoType = String.valueOf(p.getStrategyType());  // MERCADOPAGO o TRANSFERENCIA
            PreparedStatement preparedStatement;

            if (pagoType.equals("MERCADO_PAGO")) {
                // Si es MercadoPago, se obvia el cuit
                preparedStatement = conector.con.prepareStatement("""
                UPDATE pago
                SET  pagoType = ?, alias_cbu = ?, cuit = NULL
                WHERE id_pedido = ?;
            """);

                PagoMercadoPago paux = (PagoMercadoPago) p;
                // Setear valores para MercadoPago
                
                preparedStatement.setString(1, pagoType);
                preparedStatement.setString(2, ((PagoMercadoPago) p).getAlias());  // Obtener alias de MercadoPago
                preparedStatement.setInt(3, paux.getId_pedido());
            } else {
                // Si es Transferencia, se usa cbu y cuit
                preparedStatement = conector.con.prepareStatement("""
                UPDATE pago
                SET pagoType = ?, alias_cbu = ?, cuit = ?
                WHERE id_pedido = ?;
            """);

                // Setear valores para Transferencia
                PagoTransferencia paux = (PagoTransferencia) p;
                preparedStatement.setString(1, pagoType);
                preparedStatement.setString(2, ((PagoTransferencia) p).getCbu());  
                preparedStatement.setString(3, ((PagoTransferencia) p).getCuit());  
                preparedStatement.setInt(4, paux.getId_pedido());
            }

            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.conector.cerrar();
        } catch (SQLException e) {
            System.out.println("Excepción en " + this.getClass().getName() + ".actualizar() " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return true;
    }

    @Override
    public Pago buscarPorIdPedido(int id) {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
            SELECT * FROM pago WHERE id_pedido = ?;
        """);

            // Setear valores
            preparedStatement.setInt(1, id);

            Pago pago = null;
            ResultSet resultados = preparedStatement.executeQuery();
            int contador = 0;
            while (resultados.next()) {
                String pagoType = resultados.getString("pagoType");  // Obtener tipo de pago

                if (pagoType.equals("MERCADO_PAGO")) {
                    pago = new PagoMercadoPago(
                            resultados.getInt("id"),
                            resultados.getInt("id_pedido"),
                            resultados.getString("alias_cbu"),
                            resultados.getObject("fecha_pago", LocalDateTime.class)
                    );
                } else if (pagoType.equals("TRANSFERENCIA")) {
                    pago = new PagoTransferencia(
                            resultados.getInt("id"),
                            resultados.getInt("id_pedido"),
                            resultados.getString("alias_cbu"),
                            resultados.getString("cuit"),
                            resultados.getObject("fecha_pago", LocalDateTime.class)
                    );
                }

                contador++;
            }

            if (contador == 0) {
                System.out.println("No existe un pago con id_pedido " + id);
            }

            preparedStatement.close();
            this.conector.cerrar();
            return pago;
        } catch (Exception e) {
            System.out.println("Excepción en " + this.getClass().getName() + ".buscarPorIdPedido() " + e.getMessage());
            return null;
        }
    }

    
    @Override
    public List<PagoType> getPagoTypes(){
        List<PagoType> ret = new ArrayList<>();
        
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM pago_type;								
		""");
            
            ResultSet resultados = preparedStatement.executeQuery();
            
            while (resultados.next()) {
                ret.add(PagoType.valueOf(resultados.getString(1)));
            }

            preparedStatement.close();
            this.conector.cerrar();
        } catch(SQLException e){
             System.out.println("excepcion en " + this.getClass().getName() + ".getPagoTypes() " + e.getMessage());
        }
        
        return ret;
    }
}
