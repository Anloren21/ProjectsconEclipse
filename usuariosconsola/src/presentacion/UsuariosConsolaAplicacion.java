package presentacion;

import static bibliotecas.Consola.*;

import java.util.ArrayList;

import accesodatos.UsuariosCrud;
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
		case 3-> altaUsuario();
		case 4-> modificarUsuario();
		case 5-> bajaUsuario();
		case SALIR-> pl("Gracias por usar esta aplicación");
		}
	}

	private static void listado() {
		ArrayList<Usuario> usuarios = UsuariosCrud.obtenerTodos();
		
		for(Usuario usuario: usuarios) {
			pl(usuario);
		}
	}

	private static Object buscarPorEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object altaUsuario() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object modificarUsuario() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object bajaUsuario() {
		// TODO Auto-generated method stub
		return null;
	}
}
