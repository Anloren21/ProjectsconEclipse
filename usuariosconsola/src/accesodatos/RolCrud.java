package accesodatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.*;

public record RolCrud() {

	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/amazonia";
	public static final String JDBC_USER = "amazonia_app";
	public static final String JDBC_PASS = "admin";
	
	private static final String SQL_SELECT = "SELECT * FROM roles r";	
	
	
	private static PreparedStatement crearSentencia(String sql) {
		try {
			Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
			PreparedStatement pst = con.prepareStatement(sql);
			
			return pst;
		} catch (SQLException e) {
			throw new RuntimeException("No se ha podido conectar a la base de datos", e);
		}
	}


	public static ArrayList<Rol> obtenerTodos() {
		try {
			PreparedStatement pst = crearSentencia(SQL_SELECT);
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Rol> roles = new ArrayList<>();
			
			while (rs.next()) {
				Rol rol = new Rol(rs.getLong("id"), rs.getString("nombre"), rs.getString("descripción"));
				
				roles.add(rol);
			}
			
			return roles;
		} catch (SQLException e) {
			throw new RuntimeException("No se ha podido leer los roles", e);
		}
	}

}
