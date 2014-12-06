/**
 * 
 */
package comercio;
import menus.*;

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
	private static SistemaMenus crearMenus() throws NoExisteMenuExcepcion
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
								 {"EliminarTienda", "Eliminaci�n de tienda", "Elimina una tienda registrada."},
					   			 {"EditarTienda", "Edici�n de la tienda.", "Edici�n de una de las tiendas registradas."},
					   			 {"ListarTiendas", "Listado de tiendas.", "Listado de todas las tiendas registradas."},
					   			 {"Volver", "", "Volver al men� anterior."}};
		menu.agregarMenu( subMenu, "GestionarTiendas");
		
		subMenu = new String[][]{{"RegistrarCliente", "Registro de un nuevo cliente.", "Registra un nuevo cliente en la cadena de tiendas."},
								 {"EliminarCliente", "Eliminaci�n de un cliente.", "Elimina un cliente ya registrado."},
					   			 {"EditarCliente", "Edici�n del cliente.", "Edici�n de los datos de un cliente registrado."},
					   			 {"ListarClientes", "Listado de clientes.", "Listado de todos los clientes registrados."},
					   			 {"Volver", "", "Volver al men� anterior."}};
		menu.agregarMenu( subMenu, "GestionarClientes");
		
		subMenu = new String[][]{{"RegistrarProducto", "Registro de un nuevo producto.", "Registra un nuevo producto en la cadena de tiendas."},
								 {"EliminarProducto", "Eliminaci�n de un producto.", "Elimina un producto registrado."},
					   			 {"EditarCliente", "Edici�n del cliente.", "Edici�n de los datos de un cliente registrado."},
					   			 {"ListarClientes", "Listado de clientes.", "Listado de todos los clientes registrados."},
					   			 {"Volver", "", "Volver al men� anterior."}};
		menu.agregarMenu( subMenu, "GestionarClientes");

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
			GestionTiendas.crearMenus();
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

