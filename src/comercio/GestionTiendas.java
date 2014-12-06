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
 * Gestión de un sistema de Tiendas
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class GestionTiendas 
{
	// Nombre de archivo donde se guardan los datos de las tiendas.
	private static String nombreArchivoTiendas = "tiendas.txt";

	
	/**
	 * Creación de todo el sistema de menús de la gestión de tiendas.
	 */
	private static SistemaMenus crearMenus()
	{
		// Se crean el menú principal.
		SistemaMenus menu = new SistemaMenus( "MenuPrincipal", "Sistema de gestión de Tiendas - Menú principal");
		String subMenu[][] = {{"GestionarTiendas", "Menú de gestión de Tiendas", "Gestionar Tiendas."},
							  {"GestionarClientes", "Menú de gestión de Clientes", "Gestionar Clientes."},
							  {"GestionarProductos", "Menú de gestión de Productos musicales", "Gestionar Música."},
							  {"Comandos", "Modo comandos", "Entrar en modo comandos."},
							  {"Salir", "", "Salir del programa"}};
		menu.agregarMenu( subMenu);		

		subMenu = new String[][]{{"RegistrarTienda", "Registro de nueva tienda", "Registra una nueva tienda."},
								 {"EliminarTienda", "Eliminación de tienda", "Elimina una tienda existente"},
					   			 {"ListarTiendas", "Listado de tiendas.", "Listado de todas las tiendas existentes."},
					   			 {"Volver", "", "Volver al menú anterior."}};
		menu.agregarMenu(datosMenu, indiceLocal);
		
		return menu;
	}
	
	
	
	/**
	 * Función principal
	 * @param args Argumentos de entrada en la línea de comandos
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

