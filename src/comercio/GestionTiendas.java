/**
 * 
 */
package comercio;
import java.util.Iterator;
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
	private static Scanner scanner = new Scanner( System.in);

	
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
								 {"RegistrarCasete", "Registro de un nuevo Casete.", "Registra un nuevo Casete en la cadena de tiendas."},
								 {"RegistrarVinilo", "Registro de un nuevo Vinilo.", "Registra un nuevo Vinilo en la cadena de tiendas."},
								 {"EliminarProducto", "Eliminaci�n del producto.", "Elimina un producto registrado."},
					   			 {"EditarProducto", "Edici�n del producto.", "Edici�n de los datos de un producto registrado."},
					   			 {"ListarProductos", "Listado de productos.", "Listado de todos los productos registrados."},
					   			 {"Volver", "", "Volver al men� anterior."}};
		menu.agregarMenu( subMenu, "GestionarProductos");

		subMenu = new String[][]{{"GestionarStock", "Modificar stock", "Adquirir o variar el stock en la tienda de un producto registrado."},
								 {"GestionarPrecio", "Modificar precio", "Cambiar el precio de un producto en esta tienda."},
								 {"GestionarSaldo", "Modificar saldo.", "Modificar el saldo de la cuenta de un cliente."},
					   			 {"BloquearCliente", "Cliente bloqueado.", "Bloquear la cuenta de un cliente."},
					   			 {"RealizarVenta", "Venta de un producto.", "Realizar la venta de un producto a un cliente."},
					   			 {"ListarVentas", "Ventas de la Tienda.", "Mostrar las ventas realizadas en la Tienda."},
					   			 {"Volver", "", "Volver al men� anterior."}};
		menu.agregarMenu( subMenu, new int[]{0, 4});

		return menu;
	}
	
	
	/**
	 * Pregunta para reintentar una acci�n.
	 * @param pregunta Frase a preguntar.
	 * @return true si se desea reintentar, false si no.
	 */
	private static boolean preguntaReintentar( String pregunta)
	{
		char caracter;
		
		do {
			System.out.println( pregunta + " (s/n): ");
			caracter = Character.toLowerCase((char)scanner.nextByte());
		} while ((caracter != 's') && (caracter != 'n'));

		return caracter == 's';
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
		
		boolean salir = false;		// Flag para saber si salir del programa.
		int opcion;					// Opci�n actual elegida del men�.
		Tienda tiendaAdmin = null;	// Tienda que estamos administrando actualmente.

		// Variables para guardar datos temporales.
		String nombre, cif, dni, codigo;
		float deuda, precio;
		int stock, idCliente, idMusica;
		char caracter;
		Tienda tienda;
		Cliente cliente;
		Musica musica;
		boolean salirPregunta, bloqueo;

		do
		{
			menu.mostrarMenu();
			System.out.print( "Introduce la opci�n: ");
			salirPregunta = true;
			opcion = scanner.nextInt();
			
			switch (menu.obtenerNombre( opcion + 1))
			{
				case "RegistrarTienda":					
					do
					{
						System.out.print( "Introduce el nombre de la tienda: ");
						nombre = scanner.nextLine();
						System.out.print( "Introduce el cif de la tienda: ");
						cif = scanner.nextLine();
						System.out.print( "Introduce la deuda l�mite por cliente admitida en la tienda: ");
						deuda = scanner.nextFloat(); 
						try
						{						
							Tienda.registrar( nombre, cif, deuda);
						}
						catch (ObjetoExisteExcepcion e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);
					break;
			
				case "RegistrarCliente":					
					do
					{
						System.out.print( "Introduce el nombre del cliente: ");
						nombre = scanner.nextLine();
						System.out.print( "Introduce el dni del cliente: ");
						dni = scanner.nextLine();
						try
						{						
							Cliente.registrar(nombre, dni);
						}
						catch (ObjetoExisteExcepcion e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);
					break;

				case "RegistrarCD":					
					do
					{
						System.out.print( "Introduce el nombre del CD: ");
						nombre = scanner.nextLine();
						System.out.print( "Introduce el c�digo del CD: ");
						codigo = scanner.nextLine();
						System.out.print( "Introduce el precio base oficial del CD: ");
						precio = scanner.nextFloat(); 
						try
						{						
							CD.registrar(nombre, codigo, precio);
						}
						catch (ObjetoExisteExcepcion e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);
					break;

				case "RegistrarCasete":					
					do
					{
						System.out.print( "Introduce el nombre del Casete: ");
						nombre = scanner.nextLine();
						System.out.print( "Introduce el c�digo del Casete: ");
						codigo = scanner.nextLine();
						System.out.print( "Introduce el precio base oficial del Casete: ");
						precio = scanner.nextFloat(); 
						try
						{						
							Casete.registrar(nombre, codigo, precio);
						}
						catch (ObjetoExisteExcepcion e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);
					break;

				case "RegistrarVinilo":					
					do
					{
						System.out.print( "Introduce el nombre del Vinilo: ");
						nombre = scanner.nextLine();
						System.out.print( "Introduce el c�digo del Vinilo: ");
						codigo = scanner.nextLine();
						System.out.print( "Introduce el precio base oficial del Vinilo: ");
						precio = scanner.nextFloat(); 
						try
						{						
							Vinilo.registrar( nombre, codigo, precio);
						}
						catch (ObjetoExisteExcepcion e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);
					break;

				case "EliminarTienda":					
					do
					{
						System.out.print( "Introduce el Id de la tienda: ");
						try
						{		
							Tienda.eliminar( scanner.nextInt());
						}
						catch (ObjetoNoEncontradoExcepcion e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);
					break;

				case "EliminarCliente":					
					do
					{
						System.out.print( "Introduce el Id de la tienda: ");
						try
						{		
							Tienda.eliminar( scanner.nextInt());
						}
						catch (ObjetoNoEncontradoExcepcion e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);
					break;

				case "EliminarMusica":					
					do
					{
						System.out.print( "Introduce el Id del producto Musica: ");
						idMusica = scanner.nextInt(); 
						System.out.print( "Deseas eliminar toda la informaci�n del producto en las tiendas aunque exista stock?: (s/n)");
						caracter = Character.toLowerCase((char)scanner.nextByte());
						
						try
						{		
							Musica.eliminar( idMusica, caracter == 's');
						}
						catch (ObjetoNoEncontradoExcepcion e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);
					break;

				case "EditarTienda":					
					do
					{
						System.out.print( "Introduce el Id de la tienda: ");						
						if ((tienda = Tienda.buscar( scanner.nextInt())) == null)
						{
							System.out.println( "La tienda no existe");
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);

					System.out.println( "Nombre actual de la tienda: " + tienda.getNombre());
					System.out.print( "Introduce el nuevo nombre (vac�o no modifica): ");
					nombre = scanner.nextLine();
					if (!nombre.isEmpty())
						tienda.setNombre( nombre);
					System.out.println( "Cif actual de la tienda: " + tienda.getNombre());
					System.out.print( "Introduce el nuevo cif (vac�o no modifica): ");
					cif = scanner.nextLine();
					if (!cif.isEmpty())
						tienda.setCif( cif);
					System.out.println( "Deuda l�mite por cliente actual admitida la tienda: " + tienda.getDeudaLimite());
					System.out.print( "Nueva deuda l�mite (vac�o no modifica): ");
					codigo = scanner.nextLine();
					if (!codigo.isEmpty())		
						tienda.setDeudaLimite( (float)Double.parseDouble( codigo));

					break;
			
				case "EditarCliente":					
					do
					{
						System.out.print( "Introduce el Id del Cliente: ");
						
						if ((cliente = Cliente.buscar( scanner.nextInt())) == null)
						{
							System.out.println( "El cliente con no existe");
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);

					System.out.println( "Nombre actual del cliente: " + cliente.getNombre());
					System.out.print( "Introduce el nuevo nombre (vac�o no modifica): ");
					nombre = scanner.nextLine();
					if (!nombre.isEmpty())
						cliente.setNombre( nombre);
					System.out.println( "DNI actual del cliente: " + cliente.getDni());
					System.out.print( "Introduce el nuevo DNI (vac�o no modifica): ");
					dni = scanner.nextLine();
					if (!dni.isEmpty())
						cliente.setDni( dni);

					break;
			
				case "EditarMusica":					
					do
					{
						System.out.print( "Introduce el Id del producto m�sica: ");
						
						if ((musica = Musica.buscar( scanner.nextInt())) == null)
						{
							System.out.println( "El producto no existe");
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);
						
					System.out.println( "Tipo de producto: " + musica.getNombreTipo());
					System.out.println( "Nombre actual del producto: " + musica.getNombre());
					System.out.print( "Introduce el nuevo nombre (vac�o no modifica): ");
					nombre = scanner.nextLine();
					if (!nombre.isEmpty())
						musica.setNombre( nombre);
					System.out.println( "C�digo actual del producto: " + musica.getCodigo());
					System.out.print( "Introduce el nuevo c�digo (vac�o no modifica): ");
					codigo = scanner.nextLine();
					if (!codigo.isEmpty())
						musica.setCodigo( codigo);
					System.out.println( "Precio base del producto actual " + musica.getPrecio());
					System.out.print( "Nuevo precio base (vac�o no modifica): ");
					codigo = scanner.nextLine();
					if (!codigo.isEmpty())		
						musica.setPrecio( (float)Double.parseDouble( codigo));

					break;
			
				case "ListarTiendas":					
					Iterator<Tienda> tiendasIterator = Tienda.obtenerTiendas().iterator(); 
					while (tiendasIterator.hasNext())
					{
						tiendasIterator.next().mostrarDatos();
					}

					break;
			
				case "ListarClientes":					
					Iterator<Cliente> clientesIterator = Cliente.obtenerClientes().iterator(); 
					while (clientesIterator.hasNext())
					{
						clientesIterator.next().mostrarDatos();
					}

					break;
			
				case "ListarMusicas":					
					Iterator<Musica> musicasIterator = Musica.obtenerMusicas().iterator(); 
					while (musicasIterator.hasNext())
					{
						musicasIterator.next().mostrarDatos();
					}

					break;
					
				case "AdministrarTienda":
					do {						
						System.out.print( "Introduce el Id de la tienda: ");
						if ((tiendaAdmin = Tienda.buscar( scanner.nextInt())) == null)
						{
							System.out.println( "La tienda no existe");
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);

					menu.avanzar( opcion + 1);
					break;
					
				case "GestionarStock":
					do
					{
						System.out.print( "Introduce el Id del producto m�sica: ");
						idMusica = scanner.nextInt();
						try
						{
							System.out.println( "El stock actual de este producto es: " + tiendaAdmin.getStockMusica( idMusica));
							System.out.print( "Introduce la cantidad de stock a adquirir o a eliminar para este producto: ");
						}
						catch (StockInsuficienteExcepcion e)
						{
							System.out.println( e.getMessage());
							System.out.print( "Introduce la cantidad de stock a adquirir para el producto: ");
						}
						
						stock = scanner.nextInt();
						try
						{
							tiendaAdmin.setStockMusica( idMusica, stock);
						}
						catch (Exception e)
						{							
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);										
					break;
					
				case "GestionarPrecio":
					do
					{
						System.out.print( "Introduce el Id del producto m�sica: ");
						idMusica = scanner.nextInt();
						try
						{
							System.out.println( "El precio actual de este producto es: " + tiendaAdmin.getPrecioMusica( idMusica));
							System.out.print( "Introduce el nuevo precio para este producto: ");
							precio = scanner.nextFloat();
							tiendaAdmin.setPrecioMusica( idMusica, precio);
						}
						catch (Exception e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);										
						
					break;
								
				case "GestionarSaldo":
					do
					{
						System.out.print( "Introduce el Id del cliente: ");
						idCliente = scanner.nextInt();
						try
						{
							System.out.println( "El saldo actual de este cliente es: " + tiendaAdmin.getCuentaCliente( idCliente));
							System.out.print( "Introduce el saldo a modificar en la cuenta de este cliente: ");
						}
						catch (SaldoInsuficienteExcepcion e)
						{
							System.out.println( e.getMessage());
							System.out.print( "Introduce el saldo inicial en la cuenta de este cliente: ");
						}
						
						precio = scanner.nextFloat();
						try
						{
							tiendaAdmin.setCuentaCliente( idCliente, precio);
						}
						catch (Exception e)
						{							
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);										
					break;
					
				case "BloquearCliente":
					do
					{
						System.out.print( "Introduce el Id del cliente: ");
						idCliente = scanner.nextInt();
						try
						{
							bloqueo = tiendaAdmin.getBloqueoCliente( idCliente);
							System.out.println( "La cuenta del cliente se encuentra " + 
												(bloqueo ? "bloqueada. �Deseas desbloquearla?" 
														 : "desbloqueada. �Deseas bloquearla?") + " (s/n)");
							caracter = Character.toLowerCase((char)scanner.nextByte());
							tiendaAdmin.setBloqueoCliente( idCliente, bloqueo ^ (caracter == 's'));
						}
						catch (Exception e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");
						}
					} while (!salirPregunta);										
					
					break;
					
				case "RealizarVenta":
					do
					{
						try
						{							
							System.out.print( "Introduce el Id del cliente comprador: ");
							idCliente = scanner.nextInt();
							System.out.print( "Introduce el Id del producto a vender: ");
							idMusica = scanner.nextInt();
							System.out.print( "Introduce el n�mero de productos a vender: ");
							stock = scanner.nextInt();
							tiendaAdmin.realizarVenta(idMusica, idCliente, stock);
						}
						catch (Exception e)
						{
							System.out.println( e.getMessage());
							salirPregunta = !GestionTiendas.preguntaReintentar( "�Deseas intentarlo de nuevo?");							
						}
					} while (!salirPregunta);										
					break;
					
				case "ListarVentas":
					tiendaAdmin.mostrarVentas();
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
					menu.avanzar( opcion + 1);
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

