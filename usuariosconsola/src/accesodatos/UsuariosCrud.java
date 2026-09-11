package accesodatos;

import java.sql.*;
import java.util.ArrayList;

import dto.Usuario;

public class UsuariosCrud {
	
	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/amazonia";
	public static final String JDBC_USER = "amazonia_app";
	public static final String JDBC_PASS = "admin";
	
	private static final String SQL_SELECT = """
			SELECT 
				u.id AS u_id, u.email AS u_email, u.password AS u_password, u.nombre AS u_nombre, 
				r.id AS r_id, r.nombre AS r_nombre 
			FROM usuarios u
			JOIN roles r ON u.roles_id = r.id	
			""";
	
	
	private static final String SQL_SELECT_EMAIL = SQL_SELECT + "WHERE email=?";
	private static final String SQL_INSERT = "INSERT INTO usuarios (nombre, email, password, roles_id) VALUES (?,?,?,?)";
	
	public static ArrayList<Usuario> obtenerTodos() {
		try {
			PreparedStatement pst = crearSentencia(SQL_SELECT);
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Usuario> usuarios = new ArrayList<>();
			
			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getLong("u_id"), rs.getString("u_nombre"), 
						rs.getString("u_email"), rs.getString("u_password"), rs.getLong("r_id"), rs.getString("r_nombre"));
				
				usuarios.add(usuario);
			}
			
			return usuarios;
		} catch (SQLException e) {
			throw new RuntimeException("No se ha podido leer el listado", e);
		}
	}
	
	private static PreparedStatement crearSentencia(String sql) {
		try {
			Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
			PreparedStatement pst = con.prepareStatement(sql);
			
			return pst;
		} catch (SQLException e) {
			throw new RuntimeException("No se ha podido conectar a la base de datos", e);
		}
	}

	public static Usuario obtenerPorEmail(String email) {
		try (PreparedStatement pst = crearSentencia(SQL_SELECT_EMAIL)) {
			pst.setString(1, email);
			
			ResultSet rs = pst.executeQuery();
			
			Usuario usuario = null;
			
			if (rs.next()) {
				usuario = new Usuario(rs.getLong("u_id"), rs.getString("u_nombre"), 
						rs.getString("u_email"), rs.getString("u_password"), rs.getLong("r_id"), rs.getString("r_nombre"));
			}
			
			return usuario;
		} catch (SQLException e) {
			throw new RuntimeException("No se ha podido encontrar el usuario ", e);
		}
	}

	public static void insertar(Usuario usuario) {
		try (PreparedStatement pst = crearSentencia(SQL_INSERT)) {
			pst.setString(1, usuario.nombre());
			pst.setString(2, usuario.email());
			pst.setString(3, usuario.password());
			pst.setLong(4, usuario.rolId());
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("No se ha podido encontrar el usuario ", e);
		}
	}

}
