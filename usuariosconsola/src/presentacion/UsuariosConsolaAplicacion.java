package presentacion;

import static bibliotecas.Consola.*;

import java.util.ArrayList;

import accesodatos.RolCrud;
import accesodatos.UsuariosCrud;
import dto.Rol;
import dto.Usuario;

public class UsuariosConsolaAplicacion {
	private static final int SALIR = 0;

	public static void main(String[] args) {
		int opcion;
		
		do {
			mostrarMenu();
			opcion = pedirOpcion();
			procesarOpcion(opcion);
		} while (opcion != SALIR);
	}

	private static void mostrarMenu() {
		pl("""
				MENU
				====
				
				1. Listado usuarios
				2. Buscar por email
				
				3. Alta
				4. Modificación
				5. Baja
				
				0. SALIR
				""");
	}

	private static int pedirOpcion() {
		return pedirInt("Dime la opción");
	}

	private static void procesarOpcion(int opcion) {
		switch(opcion) {
		case 1-> listado();
		case 2-> buscarPorEmail();
		case 3-> alta();
		case 4-> modificarUsuario();
		case 5-> bajaUsuario();
		case SALIR-> pl("Gracias por usar esta aplicación");
		default -> pl("Opción incorrecta!");
		}
	}

	private static void listado() {
		ArrayList<Usuario> usuarios = UsuariosCrud.obtenerTodos();
		
		for(Usuario usuario: usuarios) {
			pl(usuario);
		}
	}

	private static void buscarPorEmail() {
		String email = pedirString("Email");
		
		Usuario usuario = UsuariosCrud.obtenerPorEmail(email);
		
		if(usuario != null) {
			pl(usuario);
		}else {
			System.out.println("No se ha encontrado el usuario");
		}
	}

	private static void alta() {
		String nombre = pedirString("Nombre");
		String email = pedirString("email");
		String password = pedirString("password");
		
		ArrayList<Rol> roles = RolCrud.obtenerTodos();
		
		for (Rol rol:roles) {
			pf("%2s: %s\n", rol.id(), rol.nombre());
		}
		
		Long rolId = pedirLong(" Elige un id de rol: ");
		
		Usuario usuario = new Usuario(null, nombre,  email, password, rolId, null);
		
		UsuariosCrud.insertar(usuario);
	}

	private static void modificarUsuario() {
		
	}

	private static void bajaUsuario() {
	
	}
}
