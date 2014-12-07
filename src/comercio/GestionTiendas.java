/**
 * 
 */
package comercio;
import java.util.Scanner;
import menus.*;
import excepcionesGenericas.*;

/**
 * Gesti�n de un sistema de Tiendas
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public class GestionTiendas 
{
	// Nombre de archivo donde se guardan los datos de las tiendas.
	private static String nombreArchivoTiendas = "tiendas.txt";
	private static String nombreArchivoMusicas = "musicas.txt";
	private static String nombreArchivoClientes = "clientes.txt";

	
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
					   			 {"AdministrarTienda", "Administrar Tienda", "Administrar y gestionar una Tienda."},
					   			 {"Volver", "", "Volver al men� anterior."}};
		menu.agregarMenu( subMenu, "GestionarTiendas");
		
		subMenu = new String[][]{{"RegistrarCliente", "Registro de un nuevo cliente.", "Registra un nuevo cliente en la cadena de tiendas."},
								 {"EliminarCliente", "Eliminaci�n de un cliente.", "Elimina un cliente ya registrado."},
					   			 {"EditarCliente", "Edici�n del cliente.", "Edici�n de los datos de un cliente registrado."},
					   			 {"ListarClientes", "Listado de clientes.", "Listado de todos los clientes registrados."},
					   			 {"Volver", "", "Volver al men� anterior."}};
		menu.agregarMenu( subMenu, "GestionarClientes");
		
		subMenu = new String[][]{{"RegistrarCD", "Registro de un nuevo CD.", "Registra un nuevo CD en la cadena de tiendas."},
								 {"EliminarProducto", "Eliminaci�n del producto.", "Elimina un producto registrado."},
					   			 {"EditarProducto", "Edici�n del producto.", "Edici�n de los datos de un producto registrado."},
					   			 {"ListarProductos", "Listado de productos.", "Listado de todos los productos registrados."},
					   			 {"Volver", "", "Volver al men� anterior."}};
		menu.agregarMenu( subMenu, "GestionarProductos");

		subMenu = new String[][]{{"GestionarStock", "Modificar stock", "Aumentar o disminuir el stock en la tienda de un producto registrado."},
								 {"GestionarPrecio", "Modificar precio", "Cambiar el prcio de un producto en esta tienda."},
								 {"GestionarSaldo", "Modificar saldo.", "Modificar el saldo de la cuenta de un cliente."},
					   			 {"BloquearCliente", "Cliente bloqueado.", "Bloquear la cuenta de un cliente."},
					   			 {"RealizarVenta", "Venta de un producto.", "Realizar la venta de un producto a un cliente."},
					   			 {"Volver", "", "Volver al men� anterior."}};
		menu.agregarMenu( subMenu, new int[]{0, 4});

		return menu;
	}
	
	
	
	/**
	 * Funci�n principal
	 * @param args Argumentos de entrada en la l�nea de comandos
	 */
	public static void main( String[] args) 
	{
		SistemaMenus menu = null;
		
		if (args.length > 0)
			GestionTiendas.nombreArchivoTiendas = args[0];
		
		// Se obtienen las tiendas guardadas en la base de datos.
		try
		{
			Tienda.cargarBD( GestionTiendas.nombreArchivoTiendas);
			Musica.cargarBD( GestionTiendas.nombreArchivoMusicas);
			Cliente.cargarBD( GestionTiendas.nombreArchivoClientes);
			menu = GestionTiendas.crearMenus();
		}
		catch (Exception e)
		{
			System.err.println( e.getMessage());
			return;
		}
		
		boolean salir = false;
		
		// Variables temporales para leer datos del usuario
		int opcion;
		String nombre;
		String codigo;
		float valor;
		char respuesta;
		Scanner scanner = new Scanner( System.in);

		do
		{
			menu.mostrarMenu();
			System.out.print( "Introduce la opci�n: ");
			opcion = scanner.nextInt();
			
			switch (menu.obtenerNombre( opcion))
			{
				case "RegistrarTienda":					
					while (true)
					{
						System.out.print( "Introduce el nombre de la tienda: ");
						nombre = scanner.nextLine();
						System.out.print( "Introduce el cif de la tienda: ");
						codigo = scanner.nextLine();
						System.out.print( "Introduce la deuda l�mite por cliente admitida en la tienda: ");
						valor = scanner.nextFloat(); 
						try
						{						
							Tienda.registrar( nombre, codigo, valor);
						}
						catch (ObjetoExisteExcepcion e)
						{
							System.out.println( e.getMessage());
							continue;
						}
						break;
					};
					break;
			
				case "RegistrarCliente":					
					while (true)
					{
						System.out.print( "Introduce el nombre del cliente: ");
						nombre = scanner.nextLine();
						System.out.print( "Introduce el dni del cliente: ");
						codigo = scanner.nextLine();
						try
						{						
							Cliente.registrar(nombre, codigo);
						}
						catch (ObjetoExisteExcepcion e)
						{
							System.out.println( e.getMessage());
							continue;
						}
						break;
					};
					break;

				case "RegistrarCD":					
					while (true)
					{
						System.out.print( "Introduce el nombre del CD: ");
						nombre = scanner.nextLine();
						System.out.print( "Introduce el c�digo del CD: ");
						codigo = scanner.nextLine();
						System.out.print( "Introduce el precio base oficial del CD: ");
						valor = scanner.nextFloat(); 
						try
						{						
							CD.registrar(nombre, codigo, valor);
						}
						catch (ObjetoExisteExcepcion e)
						{
							System.out.println( e.getMessage());
							System.out.println( "�Deseas intentarlo de nuevo? (s/n): ");
							if (((Byte)scanner.next).toString().to == 's'
							
							continue;
						}
						break;
					};
					break;

				case "Volver":
					try
					{
						menu.retroceder();
						break;
					}
					catch (Exception e)
					{
						System.err.println( e.getMessage());
						return;
					}
					
				case "Salir":
					salir = true;
					break;
					
				default:
					menu.avanzar( opcion);
					break;
			}
			
		} while (!salir);
		
		scanner.close();
		
		
		// Se guardan los datos en la base de datos.
		try
		{
			Tienda.guardarBD( GestionTiendas.nombreArchivoTiendas);
			Musica.guardarBD( GestionTiendas.nombreArchivoMusicas);
			Cliente.guardarBD( GestionTiendas.nombreArchivoClientes);
		}
		catch (Exception e)
		{
			System.err.println( e.getMessage());
		}
		
	}

}

// Modo comandos

