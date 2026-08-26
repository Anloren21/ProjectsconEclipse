package presentacion;

//import java.util.Scanner;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

import accesodatos.ProductoCrud;
import dtos.Producto;

//import accesodatos.ProductoCrud;

import static bibliotecas.Consola.*;
//import static accesodatos.ProductoCrud.*;

public class ProductosConsolaAplicacion {
	//	Refactorizaciones
	private static final int OPCION_SALIR = 0;

	private static final String FORMATO_CABECERAS = "%2s %-20s %12s\n";
	private static final String FORMATO_REGISTRO = "%6s: %s\n";
	private static final String FORMATO_LINEA = "%2d %-20s %10.2f €\n";
	
//	Programa principal
	public static void main(String[] args) {
		try  {			
			int opcion;

			do {
				mostrarMenu();
				opcion = pedirOpcion();
				System.out.println();
				try {
					procesarOpcion(opcion);					
				}catch (Exception e) {
					System.out.println("Error en la operación de base de datos");
					System.out.println(e.getMessage());
				}
				System.out.println();
			} while (opcion != OPCION_SALIR);

		} catch (Exception e) {
			System.out.println("Error no controlado en la app");
			System.out.println(e.getMessage());
		} 
	}

	private static void mostrarMenu() {
		System.out.println("""
				====
				MENÚ
				====
	
				1. Listado de productos
				2. Buscar por id
	
				3. Añadir producto
				4. Modificar producto
				5. Borrar producto
	
				0. Salir
				""");
	}

	private static int pedirOpcion() {
		return pedirInt("Selecciona una opcion: ");
	}

	private static void procesarOpcion(int opcion) throws SQLException {
		switch (opcion) {

		case 1:
			listado();
			break;
		case 2:
			buscarID();
			break;
		case 3:
			insertar();
			break;
		case 4: 
			modificar();
			break;
		case 5:  
			borrar();
			break;
		case 0:
			System.out.println("Gracias por usar el programa");
			break;
		default:
			System.out.println("Opción no reconocida");
		}
	}

	private static void listado() {
		System.out.print("""
				
				LISTADO
				
				""");
		
		ArrayList<Producto> productos = ProductoCrud.obtenerTodos();
		
		mostrarListado(productos);
		
	}

	private static void buscarID() {
		System.out.print("""
				
				BUSCAR POR ID
				
				""");
		
		Long id = pedirLong("Dime el ID: ");
		System.out.println();

		Producto producto = ProductoCrud.obtenerPorId(id);
		
		if(producto != null) {
			mostrarRegistro(producto);
		} else {
			System.out.println("No se ha encontrado el id " + id);
		}
	}

	public static void insertar() {
		System.out.print("""
				
				NUEVO PRODUCTO
				
				""");
		
		String nombre = pedirString("Nombre");
		BigDecimal precio = pedirBigDecimal("Precio");
		
		Producto producto = new Producto(null, nombre, precio);
		
		ProductoCrud.insertar(producto);
		
		System.out.println("Inserción correcta");
	}

	public static void modificar() {
		System.out.print("""
				
				MODIFICAR PRODUCTO
				
				""");
		
		Long id = pedirLong("Id: ");
		String nombre = pedirString("Nombre: ");
		BigDecimal precio = pedirBigDecimal("Precio: ");
		
		Producto producto = new Producto(id, nombre, precio);
		
		ProductoCrud.modificar(producto);
		
		System.out.println("Modificación correcta.");
	}

	public static void borrar() {
		System.out.print("""
				
				ELIMINAR PRODUCTO
				
				""");
		
		Long id = pedirLong("Id: ");
		
		ProductoCrud.borrar(id);
		
		System.out.println("Borrado correcto.");
	}

	public static void mostrarListado(ArrayList<Producto> productos) {
		mostrarCabeceras();
		
		for(Producto producto: productos) { //De uno en uno mientras haya carga que procesar
			mostrasLinea(producto);
		}
	}

	private static void mostrarCabeceras() {
		System.out.printf(FORMATO_CABECERAS, "ID", "Producto","Precio");
		System.out.printf(FORMATO_CABECERAS, "--", "--------","------");
	}

	private static void mostrasLinea(Producto producto) {
		System.out.printf(FORMATO_LINEA, producto.id(), producto.nombre(), producto.precio());
	}

	private static void mostrarRegistro(Producto producto) {
		System.out.printf(FORMATO_REGISTRO, "Id", producto.id());
		System.out.printf(FORMATO_REGISTRO, "Nombre", producto.nombre());
		System.out.printf(FORMATO_REGISTRO, "Precio", producto.precio());
	}
}
