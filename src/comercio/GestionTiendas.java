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
		SistemaMenus menu = new SistemaMenus( "Principal", "Selección de la tienda a gestionar");
		String subMenu[][] = {{"EligeTienda", "Selecciona una de las tiendas ya creadas", "Elige tienda creada"},
							  {"CreaTienda", "Crear una nueva tienda", "Introduce los datos de la nueva tienda"},
							  {"Salir", "Salir del programa", ""}};
		menu.agregarSubMenu( subMenu);		

		subMenu = new String[][]{{"EligeTienda", "Selecciona una de las tiendas ya creadas", "Elige tienda creada"},
					   			 {"CreaTienda", "Crear una nueva tienda", "Introduce los datos de la nueva tienda"},
					   			 {"Salir", "Salir del programa", ""}};
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
			Tienda.cargarTiendas( GestionTiendas.nombreArchivoTiendas);
		}
		catch (FileNotFoundException e)
		{
			System.err.println( "El archivo " + GestionTiendas.nombreArchivoTiendas + " no existe.");
			System.err.println( e.getMessage());
		}
		catch (IOException e)
		{
			System.err.println( "Error al acceder al archivo " + GestionTiendas.nombreArchivoTiendas);
			System.err.println( e.getMessage());			
		}
		
		
		
		
		// Se guardan las tiendas.
		try
		{
			GestionTiendas.guardarTiendas( GestionTiendas.nombreArchivoTiendas);
		}
		catch (FileNotFoundException e)
		{
			System.err.println( "El archivo " + GestionTiendas.nombreArchivoTiendas + " no existe.");
			System.err.println( e.getMessage());
		}
		catch (IOException e)
		{
			System.err.println( "Error al acceder al archivo " + GestionTiendas.nombreArchivoTiendas);
			System.err.println( e.getMessage());			
		}
		
	}

}

