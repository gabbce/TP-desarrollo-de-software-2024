/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.Bebida;
import isi.deso.tpdeso2024.Categoria;
import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.Plato;
import isi.deso.tpdeso2024.Vendedor;
import isi.deso.tpdeso2024.excepciones.ItemNoEncontradoExcepcion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author gabic
 */
public class ItemMenuSQLDAO implements ItemMenuDAO {

    private DataBaseConnection conector;

    public ItemMenuSQLDAO() {
        conector = new DataBaseConnection();
    }


	@Override
	public boolean crear(ItemMenu v){
		//if(! FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO().buscarPorID(v.getVendedor().getId()))throw new VendedorNoEncontradoExcepcion("No existe el vendedor asociado al item");
		if(v.esComida()){
			return crear((Plato)v);
		}
		return crear((Bebida)v);
    }
	private boolean crear(Plato v){
		try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
                                                                            INSERT INTO Item_menu (es_comida,nombre,descripcion,id_vendedor,id_categoria,precio,peso,calorias,apto_celiaco,apto_vegano) 
                                                                            VALUES (?,?,?,?,?,?,?,?,?,?);""");

            //setear valores
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, v.getNombre());
            preparedStatement.setString(3, v.getDescripcion());
            preparedStatement.setInt(4, v.getVendedor().getId());
			preparedStatement.setInt(5, v.getCategoria().getId());
			preparedStatement.setFloat(6, v.getPrecio());
			preparedStatement.setFloat(7, v.peso());
			preparedStatement.setFloat(8, v.getCalorias());
			preparedStatement.setBoolean(9, v.aptoCeliaco());
			preparedStatement.setBoolean(10, v.aptoVegano());
			
			

            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.conector.cerrar();
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".create() " + e.getMessage());
            return false;
        }
        return true;
	}
    private boolean crear(Bebida v){
		try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
                                                                            INSERT INTO item_menu (es_comida,nombre,descripcion,id_vendedor,id_categoria,precio,graduacion_alcoholica,tam) 
                                                                            VALUES (?,?,?,?,?,?,?,?);""");

            //setear valores
            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, v.getNombre());
            preparedStatement.setString(3, v.getDescripcion());
            preparedStatement.setInt(4, v.getVendedor().getId());
			preparedStatement.setInt(5, v.getCategoria().getId());
			preparedStatement.setFloat(6, v.getPrecio());
			preparedStatement.setFloat(7, v.getGraduacionAlcoholica());
			preparedStatement.setFloat(8, v.getTam());
			
			

            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.conector.cerrar();
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".create() " + e.getMessage());
            return false;
        }
        return true;
	}

    @Override
    public boolean eliminar(int id) {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		DELETE FROM item_menu WHERE id = ?;
		""");

            //setear valores
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.conector.cerrar();
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".eliminar() " + e.getMessage());
            return false;
        }
        return true;
    }


	@Override
    public List<ItemMenu> listar() {
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM item_menu;								
		""");

            ArrayList<ItemMenu> ret = new ArrayList<>();
            ResultSet resultados = preparedStatement.executeQuery();
            
           
            
            while (resultados.next()) {
                 Vendedor v = FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO().buscarPorID(resultados.getInt(5));
                Categoria c = FactoryDAO.getFactory(FactoryDAO.SQL).getCategoriaDAO().buscarPorID(resultados.getInt(6));
				if(resultados.getBoolean(2)){ //es comida
					Plato p = new Plato(
					//general a ItemMenu
						resultados.getInt(1),
						resultados.getString(3),
						resultados.getString(4),
						//fks
                                                v,
                                                c,
						
						resultados.getFloat(7),
					//especifico
						resultados.getFloat(10),
						resultados.getFloat(11),
						resultados.getBoolean(12),
						resultados.getBoolean(13)
					);
					ret.add((ItemMenu)p);		//upcastea para el ret
				}
				else{
					Bebida b = new Bebida(
					//general a ItemMenu
						resultados.getInt(1),
						resultados.getString(3),
						resultados.getString(4),
						//fks
						v,
                                                c,
						
						resultados.getFloat(7),
					//especifico
						resultados.getFloat(8),
						resultados.getFloat(9)
					);
					
					
					ret.add((ItemMenu)b);
					
				}
            }

            preparedStatement.close();
            this.conector.cerrar();

            return ret;
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".listar() " + e.getMessage());
            return null;
        }

    }



    @Override
	public boolean actualizar(ItemMenu v){
		//if(!FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO().buscarPorID(v.getVendedor().getId()))throw new VendedorNoEncontradoExcepcion("No existe el vendedor asociado al item");
		if(v.esComida()){
			return actualizar((Plato)v);
		}
		return actualizar((Bebida)v);
    }
	private boolean actualizar(Plato v){
		try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
			UPDATE item_menu
			SET es_comida = ?, nombre = ?, descripcion = ?, id_vendedor= ?, id_categoria = ?, precio = ?,
			
			peso = ?, calorias = ?, aptoCeliaco = ?, aptoVegano = ?
			WHERE id = ?
			""");

            //setear valores
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, v.getNombre());
            preparedStatement.setString(3, v.getDescripcion());
            preparedStatement.setInt(4, v.getVendedor().getId());
			preparedStatement.setInt(5, v.getCategoria().getId());
			preparedStatement.setFloat(6, v.getPrecio());
			preparedStatement.setFloat(7, v.peso());
			preparedStatement.setFloat(8, v.getCalorias());
			preparedStatement.setBoolean(9, v.aptoCeliaco());
			preparedStatement.setBoolean(10, v.aptoVegano());
			preparedStatement.setInt(11,v.getId());
			
			

            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.conector.cerrar();
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".actualizar() " + e.getMessage());
            return false;
        }
        return true;
	}
    private boolean actualizar(Bebida v){
		try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
			UPDATE item_menu
			SET es_comida = ?, nombre = ?, descripcion = ?, id_vendedor= ?, id_categoria = ?, precio = ?,
			
			graduacion_alcoholica = ?, tam = ?
			WHERE id = ?
			""");

            //setear valores
            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, v.getNombre());
            preparedStatement.setString(3, v.getDescripcion());
            preparedStatement.setInt(4, v.getVendedor().getId());
			preparedStatement.setInt(5, v.getCategoria().getId());
			preparedStatement.setFloat(6, v.getPrecio());
			
			preparedStatement.setFloat(7, v.getGraduacionAlcoholica());
			preparedStatement.setFloat(8, v.getTam());
			preparedStatement.setInt(9,v.getId());
			

            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.conector.cerrar();
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".actualizar() " + e.getMessage());
            return false;
        }
        return true;
	}


     @Override
    public ItemMenu buscarPorID(int id) throws ItemNoEncontradoExcepcion{
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM item_menu WHERE id = ?;								
		""");

            //setear valores
            preparedStatement.setInt(1, id);
            
            ItemMenu ret = null;
            ResultSet resultados = preparedStatement.executeQuery();
            int contador = 0;
            
            
            while (resultados.next()) {
                
                Vendedor v = FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO().buscarPorID(resultados.getInt(5));
                Categoria c = FactoryDAO.getFactory(FactoryDAO.SQL).getCategoriaDAO().buscarPorID(resultados.getInt(6));
            
                if(resultados.getBoolean(2)){ //es comida
					Plato p = new Plato(
					//general a ItemMenu
						resultados.getInt(1),
						resultados.getString(3),
						resultados.getString(4),
						//fks
						v,
						c,
						
						resultados.getFloat(7),
					//especifico
						resultados.getFloat(10),
						resultados.getFloat(11),
						resultados.getBoolean(12),
						resultados.getBoolean(13)
					);
					
					ret = (ItemMenu)p;		//upcastea para el ret
				}
				else{
					Bebida b = new Bebida(
					//general a ItemMenu
						resultados.getInt(1),
						resultados.getString(3),
						resultados.getString(4),
						//fks
						v,
						c,
						
						resultados.getFloat(7),
					//especifico
						resultados.getFloat(8),
						resultados.getFloat(9)
					);
					
					
					ret = (ItemMenu)b;
				}
				contador++;
            }
            if (contador == 0) {
                throw new ItemNoEncontradoExcepcion("No existe vendedor con id " + id);
            }

            preparedStatement.close();
            this.conector.cerrar();
            
            return ret;
            
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".buscarPorID() " + e.getMessage());
            return null;
        }
        
    }

    @Override
    public List<ItemMenu> buscar(String nombre) {
		throw new UnsupportedOperationException("Hay que acomodar la interfaz dao");
	}
    
	
    @Override
	public List<ItemMenu> buscar(Boolean esComida, String nombre, String idVendedor, String tipoCategoria){
		if(esComida == null && nombre.equals("") && idVendedor.equals("") && tipoCategoria.equals("Todas")) return listar();
        try {
			//segun nulls armar el string de la query
			String queryBase;
			if(!tipoCategoria.equals("Todas"))queryBase = "SELECT * FROM item_menu join Categoria c on id_categoria = c.id WHERE c.tipo LIKE '" + tipoCategoria +"' ";
			else queryBase = "SELECT * FROM item_menu";
			
                        if(tipoCategoria.equals("Todas") && (esComida != null || !nombre.equals("") || !idVendedor.equals(""))) queryBase += " WHERE ";
                        
			if(esComida != null){
                            if(!tipoCategoria.equals("Todas")) queryBase+="AND ";
			if(esComida)queryBase += "es_comida = true ";
			else queryBase += "es_comida = false ";
			}
			
			if(!nombre.equals("")){
				if(!tipoCategoria.equals("Todas") || esComida!=null) queryBase+="AND ";
				queryBase += "nombre LIKE '%"+ nombre +"%' ";
			}
			
			if(!idVendedor.equals("")){
				if(!tipoCategoria.equals("Todas") || esComida!=null || !nombre.equals("")) queryBase+="AND ";
				queryBase += "id_vendedor = "+ idVendedor + " ";
			}
                        
                        queryBase += ";";
                        System.out.println(queryBase);
			
			//consulta
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement(queryBase);
			
            ArrayList<ItemMenu> ret = new ArrayList<>();
            ResultSet resultados = preparedStatement.executeQuery();
            
            
            while (resultados.next()) {
                    Vendedor v = FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO().buscarPorID(resultados.getInt(5));
                    Categoria c = FactoryDAO.getFactory(FactoryDAO.SQL).getCategoriaDAO().buscarPorID(resultados.getInt(6));
            
				if(resultados.getBoolean(2)){ //es comida
					Plato p = new Plato(
					//general a ItemMenu
						resultados.getInt(1),
						resultados.getString(3),
						resultados.getString(4),
						//fks
						v,
						c,
						
						resultados.getFloat(7),
					//especifico
						resultados.getFloat(10),
						resultados.getFloat(11),
						resultados.getBoolean(12),
						resultados.getBoolean(13)
					);
					ret.add((ItemMenu)p);		//upcastea para el ret
				}
				else{
					Bebida b = new Bebida(
					//general a ItemMenu
						resultados.getInt(1),
						resultados.getString(3),
						resultados.getString(4),
						//fks
						v, 
                                                c,
						
						resultados.getFloat(7),
					//especifico
						resultados.getFloat(8),
						resultados.getFloat(9)
					);
					
					
					ret.add((ItemMenu)b);
				
					}
			}
            preparedStatement.close();
            this.conector.cerrar();

            return ret;
        } catch (Exception e) {
            System.out.println("excepcion en " + this.getClass().getName() + ".buscar() " + e.getMessage());
            return null;
        }
    }
	
	
	//estos no se usan
    @Override
    public ArrayList<ItemMenu> Filtrado(String criterio, String valor) throws ItemNoEncontradoExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ItemMenu> OrdenarPorCriterios(Comparator<ItemMenu> criterio) throws ItemNoEncontradoExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ItemMenu> BuscarPorRangoDePrecios(float precioMin, float precioMax) throws ItemNoEncontradoExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ItemMenu> BuscarPorRestaurante(int idItemMenu) throws ItemNoEncontradoExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }    
}
