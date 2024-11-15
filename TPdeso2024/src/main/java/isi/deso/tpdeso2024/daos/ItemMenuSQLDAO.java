/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024.daos;

import isi.deso.tpdeso2024.ItemMenu;
import isi.deso.tpdeso2024.excepciones.ItemNoEncontradoExcepcion;
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
		if(v.esComida()){
			return crear((Plato)v);
		}
		return crear((Bebida)v);
    }
	private boolean crear(Plato v){
		try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
                                                                            INSERT INTO ItemMenu (es_comida,nombre,descripcion,id_vendedor,id_categoria,precio,peso,calorias,apto_celiaco,apto_vegano) 
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
                                                                            INSERT INTO ItemMenu (es_comida,nombre,descripcion,id_vendedor,id_categoria,precio,graduacion_alcoholica,tam) 
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
		DELETE FROM ItemMenu WHERE id = ?;
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
		SELECT * FROM ItemMenu;								
		""");

            ArrayList<ItemMenu> ret = new ArrayList<>();
            ResultSet resultados = preparedStatement.executeQuery();
            while (resultados.next()) {
				if(resultados.getBoolean(1)){ //es comida
					Plato p = new Plato(
					//general a ItemMenu
						resultados.getInt(1),
						resultados.getString(3),
						resultados.getString(4),
						//fks
						resultados.getInt(5),
						resultados.getInt(6),
						
						resultados.getFloat(7),
					//especifico
						resultados.getFloat(10);
						resultados.getFloat(11);
						resultados.getBoolean(12);
						resultados.getBoolean(13);
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
						resultados.getInt(5),
						resultados.getInt(6),
						
						resultados.getFloat(7),
					//especifico
						resultados.getFloat(8);
						resultados.getFloat(9);
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
		if(v.esComida()){
			return actualizar((Plato)v);
		}
		return actualizar((Bebida)v);
    }
	private boolean actualizar(Plato v){
		try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
			UPDATE ItemMenu
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
			UPDATE ItemMenu
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



	//falta de aca para abajo

	//BUSQUEDAS: por criterios independientes. Controller coordina para combinar criterios
	//y la excepcion la tira el controller
	/*buscar por nombre,Categoria.tipo, id_vendedor, id de item
		Se me ocurrio hacer busqueda por un criterio y luego pasar al proximo 
		llevando un arreglo con los resultados, y lo vas recortando 
	*/
    public ItemMenu buscarPorID(int id){
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM ItemMenu WHERE id = ?;								
		""");

            //setear valores
            preparedStatement.setInt(1, id);

            ItemMenu ret = null;
            ResultSet resultados = preparedStatement.executeQuery();
            int contador = 0;
            while (resultados.next()) {
                ret = new ItemMenu(
                        resultados.getInt(1),
                        resultados.getString(2),
                        resultados.getString(3),
                        new Coordenada(
                                resultados.getFloat(4),
                                resultados.getFloat(5)
                        )
                );
                contador++;
            }
            if (contador == 0) {
                throw new ItemMenuNoEncontradoException("No existe vendedor con id " + id);
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
    
	
	
	public List<ItemMenu> buscar(String nombre) throws ItemMenuNoEncontradoException{
		
        try {
            this.conector.conectar();

            PreparedStatement preparedStatement = conector.con.prepareStatement("""
		SELECT * FROM ItemMenu WHERE nombre LIKE ?;								
		""");

            //setear valores
            preparedStatement.setString(1, "%" + nombre + "%");

            ArrayList<ItemMenu> ret = new ArrayList<>();
            ResultSet resultados = preparedStatement.executeQuery();
            while (resultados.next()) {
                ItemMenu tmp = new ItemMenu(
                        resultados.getInt(1),
                        resultados.getString(2),
                        resultados.getString(3),
                        new Coordenada(
                                resultados.getFloat(4),
                                resultados.getFloat(5)
                        )
                );
                ret.add(tmp);
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
