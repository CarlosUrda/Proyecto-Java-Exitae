/**
 * 
 */
package comercio;
import menus.*;

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
	private static SistemaMenus crearMenus() throws NoExisteMenuExcepcion
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
								 {"EliminarTienda", "Eliminación de tienda", "Elimina una tienda registrada."},
					   			 {"EditarTienda", "Edición de la tienda.", "Edición de una de las tiendas registradas."},
					   			 {"ListarTiendas", "Listado de tiendas.", "Listado de todas las tiendas registradas."},
					   			 {"Volver", "", "Volver al menú anterior."}};
		menu.agregarMenu( subMenu, "GestionarTiendas");
		
		subMenu = new String[][]{{"RegistrarCliente", "Registro de un nuevo cliente.", "Registra un nuevo cliente en la cadena de tiendas."},
								 {"EliminarCliente", "Eliminación de un cliente.", "Elimina un cliente ya registrado."},
					   			 {"EditarCliente", "Edición del cliente.", "Edición de los datos de un cliente registrado."},
					   			 {"ListarClientes", "Listado de clientes.", "Listado de todos los clientes registrados."},
					   			 {"Volver", "", "Volver al menú anterior."}};
		menu.agregarMenu( subMenu, "GestionarClientes");
		
		subMenu = new String[][]{{"RegistrarProducto", "Registro de un nuevo producto.", "Registra un nuevo producto en la cadena de tiendas."},
								 {"EliminarProducto", "Eliminación de un producto.", "Elimina un producto registrado."},
					   			 {"EditarCliente", "Edición del cliente.", "Edición de los datos de un cliente registrado."},
					   			 {"ListarClientes", "Listado de clientes.", "Listado de todos los clientes registrados."},
					   			 {"Volver", "", "Volver al menú anterior."}};
		menu.agregarMenu( subMenu, "GestionarClientes");

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

