package accesodatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dtos.Producto;

public class ProductoCrud {
	private static final String JDBC_URL = "jdbc:sqlite:productosconsola.db";
	
	private static final String SQL_SELECT = "SELECT * FROM productos";
	private static final String SQL_SELECT_ID = "SELECT * FROM productos WHERE id=?";
	
	private static final String SQL_INSERT = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
	private static final String SQL_UPDATE_ID = "UPDATE productos SET nombre=?, precio=? WHERE id=?";
	private static final String SQL_DELETE_ID = "DELETE FROM productos WHERE id=?";
	
	public static ArrayList<Producto> obtenerTodos() {
		try (Connection con = DriverManager.getConnection(JDBC_URL); PreparedStatement pst = con.prepareStatement(SQL_SELECT); ResultSet rs = pst.executeQuery()) {
			ArrayList<Producto> productos = new ArrayList<Producto>();
			
			while(rs.next()) {
				Producto producto = new Producto(rs.getLong("id"), rs.getString("nombre"), rs.getBigDecimal("precio"));
				productos.add(producto);
			}
			
			return productos;
		} catch (SQLException e) {
			throw new RuntimeException("Error al obtener todos los productos: " + e.getMessage(), e); 
		}
	}

	public static Producto obtenerPorId(Long id) {
		// conexion de base de Datos
		try (Connection con = DriverManager.getConnection(JDBC_URL); PreparedStatement pst = con.prepareStatement(SQL_SELECT_ID)) {
			
			pst.setLong(1, id);
			
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) { 
					Producto producto = new Producto(rs.getLong("id"), rs.getString("nombre"), rs.getBigDecimal("precio"));
					return producto;
				} else {
					//System.out.println("No se ha encontrado el id " + id);
					return null;
				}
			} 
		} catch (SQLException e) {
			throw new RuntimeException("Error al obtener el producto " + id + ":" + e.getMessage(), e); 
		}
	}

	public static void insertar(Producto producto) {
		// conexion de base de Datos
		try (Connection con = DriverManager.getConnection(JDBC_URL); PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
			
			pst.setString(1, producto.nombre());
			pst.setBigDecimal(2, producto.precio());
			
			//Ejecutar un cambio dentro de lo que ahi dentro de la base
			int numeroRegistrosModificados = pst.executeUpdate();
			
			if (numeroRegistrosModificados != 1) {
				throw new RuntimeException("Se han modificado " + numeroRegistrosModificados);
			} 
		} catch (SQLException e) {
			throw new RuntimeException("Error al insertar el producto" + e.getMessage(), e); 
		}
	}
	
	public static void modificar(Producto producto) {
		// conexion de base de Datos
		try (Connection con = DriverManager.getConnection(JDBC_URL);  PreparedStatement pst = con.prepareStatement(SQL_UPDATE_ID)) {
			pst.setString(1, producto.nombre());
			pst.setBigDecimal(2, producto.precio());
			pst.setLong(3, producto.id());
			
			//Ejecutar un cambio dentro de lo que ahi dentro de la base
			int numeroRegistrosModificados = pst.executeUpdate();
			
			if (numeroRegistrosModificados != 1) {
				throw new RuntimeException("Se ha modificado " + numeroRegistrosModificados);
			} 
		} catch (SQLException e) {
			throw new RuntimeException("Error al modificar el producto" + e.getMessage(), e); 
		}
	}

	public static void borrar(Long id) {
		// conexion de base de Datos
		try (Connection con = DriverManager.getConnection(JDBC_URL); PreparedStatement pst = con.prepareStatement(SQL_DELETE_ID)) {

			pst.setLong(1, id);
			
			//Ejecutar un cambio dentro de lo que ahi dentro de la base
			int numeroRegistrosModificados = pst.executeUpdate();
			
			if (numeroRegistrosModificados != 1) {
				throw new RuntimeException("Se ha borrado " + numeroRegistrosModificados);
			} 
		} catch (SQLException e) {
			throw new RuntimeException("Error al borrar el producto" + e.getMessage(), e); 
		}
	}
}
