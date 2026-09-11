package bibliotecas;

import java.math.BigDecimal;
import java.util.Scanner;


public class Consola {
	private static final Scanner sc = new Scanner(System.in);
	
	public static void pl(Object mensaje) {
		System.out.println(mensaje);
	}

	public static void p(Object mensaje) {
		System.out.print(mensaje);
	}
	
	public static void pf(String formato, Object...objetos) {
		System.out.printf(formato, objetos);
	}
	
	public static String pedirString(String mensaje) {
		System.out.print(mensaje + ": ");
		return sc.next();
	}
	
	public static Long pedirLong(String mensaje) {
		return Long.parseLong(pedirString(mensaje));
	}
	
	public static BigDecimal pedirBigDecimal(String mensaje) {
		return new BigDecimal(pedirString(mensaje));
	}
	
	public static int pedirInt(String mensaje) {
		int valor = 0;
		
		boolean estaBien = false;
		
		do {
			try {
				valor = Integer.parseInt(pedirString(mensaje));
				
				estaBien = true;
			} catch (NumberFormatException e) {
				pl("No es un nº válido");
			} 
		} while (!estaBien);
		
		return valor;
	}
}
