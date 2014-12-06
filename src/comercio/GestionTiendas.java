/**
 * 
 */
package comercio;
import menus.SistemaMenus;
import menus.NoExisteMenuExcepcion;
import gestionBD.BD;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Gesti�n de un sistema de Tiendas
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public class GestionTiendas 
{
	// Nombre de archivo donde se guardan los datos de las tiendas.
	private static String nombreArchivoTiendas = "tiendas.txt";

	
	/**
	 * Creaci�n de todo el sistema de men�s de la gesti�n de tiendas.
	 */
	private static SistemaMenus crearMenus()
	{
		// Se crean el men� principal.
		SistemaMenus menu = new SistemaMenus( "MenuPrincipal", "Sistema de gesti�n de Tiendas - Men� principal");
		String subMenu[][] = {{"GestionarTiendas", "Men� de gesti�n de Tiendas", "Gestionar Tiendas."},
							  {"GestionarClientes", "Men� de gesti�n de Clientes", "Gestionar Clientes."},
							  {"GestionarProductos", "Men� de gesti�n de Productos musicales", "Gestionar M�sica."},
							  {"Comandos", "Modo comandos", "Entrar en modo comandos."},
							  {"Salir", "", "Salir del programa"}};
		menu.agregarMenu( subMenu);		

		subMenu = new String[][]{{"RegistrarTienda", "Registro de nueva tienda", "Registra una nueva tienda."},
								 {"EliminarTienda", "Eliminaci�n de tienda", "Elimina una tienda existente"},
					   			 {"ListarTiendas", "Listado de tiendas.", "Listado de todas las tiendas existentes."},
					   			 {"Volver", "", "Volver al men� anterior."}};
		menu.agregarMenu(datosMenu, indiceLocal);
		
		return menu;
	}
	
	
	
	/**
	 * Funci�n principal
	 * @param args Argumentos de entrada en la l�nea de comandos
	 */
	public static void main( String[] args) 
	{
		if (args.length > 0)
			GestionTiendas.nombreArchivoTiendas = args[0];
		
		// Se obtienen las tiendas guardadas en la base de datos.
		try
		{
			Tienda.cargarBD( GestionTiendas.nombreArchivoTiendas);
		}
		catch (Exception e)
		{
			System.err.println( e.getMessage());
			return;
		}
		
		
		
		
		
		
		// Se guardan las tiendas.
		try
		{
			Tienda.guardarBD( GestionTiendas.nombreArchivoTiendas);
		}
		catch (Exception e)
		{
			System.err.println( e.getMessage());
		}
		
	}

}

