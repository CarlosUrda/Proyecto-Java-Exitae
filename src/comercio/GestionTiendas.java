/**
 * 
 */
package comercio;
import java.util.Iterator;
import java.util.Scanner;

import menus.*;
import excepcionesGenericas.*;

/**
 * Gestión de un sistema de Tiendas
 * @author Carlos A. Gómez Urda
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
	 * Creación de todo el sistema de menús de la gestión de tiendas.
	 */
	private static SistemaMenus crearMenus() throws NoExisteMenuExcepcion
	{
		// Se crean el menú principal.
		SistemaMenus menu = new SistemaMenus( "MenuPrincipal", "Menú principal del Sistema");
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
					   			 {"AdministrarTienda", "Administrar Tienda", "Administrar y gestionar una Tienda."},
					   			 {"Volver", "", "Volver al menú anterior."}};
		menu.agregarMenu( subMenu, "GestionarTiendas");
		
		subMenu = new String[][]{{"RegistrarCliente", "Registro de un nuevo cliente.", "Registra un nuevo cliente en la cadena de tiendas."},
								 {"EliminarCliente", "Eliminación de un cliente.", "Elimina un cliente ya registrado."},
					   			 {"EditarCliente", "Edición del cliente.", "Edición de los datos de un cliente registrado."},
					   			 {"ListarClientes", "Listado de clientes.", "Listado de todos los clientes registrados."},
					   			 {"Volver", "", "Volver al menú anterior."}};
		menu.agregarMenu( subMenu, "GestionarClientes");
		
		subMenu = new String[][]{{"RegistrarCD", "Registro de un nuevo CD.", "Registra un nuevo CD en la cadena de tiendas."},
								 {"RegistrarCasete", "Registro de un nuevo Casete.", "Registra un nuevo Casete en la cadena de tiendas."},
								 {"RegistrarVinilo", "Registro de un nuevo Vinilo.", "Registra un nuevo Vinilo en la cadena de tiendas."},
								 {"EliminarMusica", "Eliminación del producto.", "Elimina un producto registrado."},
					   			 {"EditarMusica", "Edición del producto música.", "Edición de los datos de un producto registrado."},
					   			 {"ListarMusicas", "Listado de productos música.", "Listado de todos los productos registrados."},
					   			 {"Volver", "", "Volver al menú anterior."}};
		menu.agregarMenu( subMenu, "GestionarProductos");

		subMenu = new String[][]{{"GestionarStock", "Modificar stock", "Adquirir o variar el stock en la tienda de un producto registrado."},
								 {"GestionarPrecio", "Modificar precio", "Cambiar el precio de un producto en esta tienda."},
								 {"GestionarSaldo", "Modificar saldo.", "Modificar el saldo de la cuenta de un cliente."},
					   			 {"BloquearCliente", "Cliente bloqueado.", "Bloquear la cuenta de un cliente."},
					   			 {"RealizarVenta", "Venta de un producto.", "Realizar la venta de un producto a un cliente."},
					   			 {"ListarVentas", "Ventas de la Tienda.", "Mostrar las ventas realizadas en la Tienda."},
					   			 {"Volver", "", "Volver al menú anterior."}};
		menu.agregarMenu( subMenu, new int[]{0, 4});

		return menu;
	}
	
	
	/**
	 * Pregunta para reintentar una acción.
	 * @param pregunta Frase a preguntar.
	 * @return true si se desea reintentar, false si no.
	 */
	private static boolean preguntaReintentar( String pregunta)
	{
		char caracter = 0;
		
		do {
			try
			{
				System.out.print( pregunta + " (s/n): ");
				caracter = Character.toLowerCase(scanner.nextLine().charAt(0));
			}
			catch (Exception e)	{}
		} while ((caracter != 's') && (caracter != 'n'));

		return caracter == 's';
	}
	

	private static void listarTiendas()
	{		
		System.out.println( "- Listado de Tiendas -");
		Iterator<Tienda> tiendasIterator = Tienda.obtenerTiendas().iterator(); 
		while (tiendasIterator.hasNext())
		{
			tiendasIterator.next().mostrarDatos();
		}
	}
	
	private static void listarClientes()
	{		
		System.out.println( "- Listado de Clientes -");
		Iterator<Cliente> clientesIterator = Cliente.obtenerClientes().iterator(); 
		while (clientesIterator.hasNext())
		{
			clientesIterator.next().mostrarDatos();
		}
	}

	private static void listarMusicas()
	{		
		System.out.println( "- Listado de Productos Música -");
		Iterator<Musica> musicasIterator = Musica.obtenerMusicas().iterator(); 
		while (musicasIterator.hasNext())
		{
			musicasIterator.next().mostrarDatos();
		}
	}

	
	private static void registrarTienda()
	{		
		boolean salir;
		String nombre, cif;
		float deuda;
		Tienda tienda;

		do
		{
			try
			{						
				System.out.print( "Introduce el nombre de la tienda: ");
				nombre = scanner.nextLine();
				System.out.print( "Introduce el cif de la tienda: ");
				cif = scanner.nextLine();
				System.out.print( "Introduce la deuda límite por cliente admitida en la tienda: ");
				deuda = (float)Double.parseDouble( scanner.nextLine()); 
				tienda = Tienda.registrar( nombre, cif, deuda);
				salir = true;
				System.out.println( "- Tienda registrada con id " + tienda.getId() + " -");
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}

	
	private static void registrarCliente()
	{		
		boolean salir;
		String nombre, dni;
		Cliente cliente;

		do
		{
			try
			{						
				System.out.print( "Introduce el nombre del cliente: ");
				nombre = scanner.nextLine();
				System.out.print( "Introduce el dni del cliente: ");
				dni = scanner.nextLine();
				cliente = Cliente.registrar(nombre, dni);
				System.out.println( "- Cliente registrado con id " + cliente.getId() + " -");
				salir = true;
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}
	

	private static void registrarCD()
	{		
		boolean salir;
		String nombre, codigo;
		float precio;
		CD cd;

		do
		{
			try
			{						
				System.out.print( "Introduce el nombre del CD: ");
				nombre = scanner.nextLine();
				System.out.print( "Introduce el código del CD: ");
				codigo = scanner.nextLine();
				System.out.print( "Introduce el precio base oficial del CD: ");
				precio = (float)Double.parseDouble( scanner.nextLine()); 
				cd = CD.registrar(nombre, codigo, precio);
				System.out.println( "- CD registrado con id " + cd.getId() + " -");
				salir = true;
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}

	
	private static void registrarCasete()
	{		
		boolean salir;
		String nombre, codigo;
		float precio;
		Casete casete;

		do
		{
			try
			{						
				System.out.print( "Introduce el nombre del Casete: ");
				nombre = scanner.nextLine();
				System.out.print( "Introduce el código del Casete: ");
				codigo = scanner.nextLine();
				System.out.print( "Introduce el precio base oficial del Casete: ");
				precio = (float)Double.parseDouble( scanner.nextLine()); 
				casete = Casete.registrar(nombre, codigo, precio);
				System.out.println( "- Casete registrado con id " + casete.getId() + " -");
				salir = true;
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}
	

	private static void registrarVinilo()
	{		
		boolean salir;
		String nombre, codigo;
		float precio;
		Vinilo vinilo;

		do
		{
			try
			{						
				System.out.print( "Introduce el nombre del Vinilo: ");
				nombre = scanner.nextLine();
				System.out.print( "Introduce el código del Vinilo: ");
				codigo = scanner.nextLine();
				System.out.print( "Introduce el precio base oficial del Vinilo: ");
				precio = (float)Double.parseDouble( scanner.nextLine()); 
				vinilo = Vinilo.registrar( nombre, codigo, precio);
				System.out.println( "- Vinilo registrado con id " + vinilo.getId() + " -");
				salir = true;
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}

	
	private static void eliminarTienda()
	{		
		boolean salir;
		Integer idTienda;

		do
		{
			try
			{	
				System.out.print( "Introduce el Id de la tienda: ");
				idTienda = Integer.parseInt( scanner.nextLine());
				Tienda.eliminar( idTienda);
				System.out.println( "- Tienda id " + idTienda + " eliminada del registro -");
				salir = true;
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}

	
	private static void eliminarCliente()
	{		
		boolean salir;
		Integer idCliente;
		char caracter;

		do
		{						
			try
			{		
				System.out.print( "Introduce el Id del cliente: ");
				idCliente = Integer.parseInt( scanner.nextLine()); 
				System.out.print( "¿Deseas eliminar todas las cuentas en tiendas del cliente que tengan saldo positivo? (s/n): ");
				caracter = Character.toLowerCase(scanner.nextLine().charAt(0));
				Cliente.eliminar( idCliente, caracter == 's');
				System.out.println( (caracter == 's') ? "- Cliente id " + idCliente + " eliminado completamente del registro -"
						  							  : "- Cuentas vacías del cliente en tiendas eliminadas. -");
				salir = true;
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}

	
	private static void eliminarMusica()
	{		
		boolean salir;
		Integer idMusica;
		char caracter;

		do
		{						
			try
			{		
				System.out.print( "Introduce el Id del producto Musica: ");
				idMusica = Integer.parseInt( scanner.nextLine()); 
				System.out.print( "Deseas eliminar toda la información del producto en las tiendas aunque exista stock? (s/n): ");
				caracter = Character.toLowerCase(scanner.nextLine().charAt(0));
				Musica.eliminar( idMusica, caracter == 's');
				System.out.println( (caracter == 's') ? "- Producto id " + idMusica + " eliminado completamente del registro -"
													  : "- Datos del producto eliminados en las tiendas sin stock -");
				salir = true;
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}

	
	private static void editarTienda()
	{		
		boolean salir;
		String nombre, cif, codigo;
		Integer idTienda;
		Tienda tienda;

		do
		{
			try
			{
				System.out.print( "Introduce el Id de la tienda: ");						
				idTienda = Integer.parseInt( scanner.nextLine());
				if ((tienda = Tienda.buscar( idTienda)) == null)
				{
					System.out.println( "La tienda no existe");
					salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
				}
				else
				{
					System.out.println( "Nombre actual de la tienda: " + tienda.getNombre());
					System.out.print( "Introduce el nuevo nombre (vacío no modifica): ");
					nombre = scanner.nextLine();
					if (!nombre.isEmpty())
						tienda.setNombre( nombre);
					System.out.println( "Cif actual de la tienda: " + tienda.getCif());
					System.out.print( "Introduce el nuevo cif (vacío no modifica): ");
					cif = scanner.nextLine();
					if (!cif.isEmpty())
						tienda.setCif( cif);
					System.out.println( "Deuda límite por cliente actual admitida la tienda: " + tienda.getDeudaLimite());
					System.out.print( "Nueva deuda límite (vacío no modifica): ");
					codigo = scanner.nextLine();
					if (!codigo.isEmpty())		
						tienda.setDeudaLimite( (float)Double.parseDouble( codigo));
					System.out.println( "- Tienda editada -");
					salir = true;								
				}
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}


	private static void editarCliente()
	{		
		boolean salir;
		String nombre, dni;
		Integer idCliente;
		Cliente cliente;

		do
		{
			try
			{
				System.out.print( "Introduce el Id del Cliente: ");
				idCliente = Integer.parseInt( scanner.nextLine()); 
				
				if ((cliente = Cliente.buscar( idCliente)) == null)
				{
					System.out.println( "El cliente con no existe");
					salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
				}
				else
				{
					System.out.println( "Nombre actual del cliente: " + cliente.getNombre());
					System.out.print( "Introduce el nuevo nombre (vacío no modifica): ");
					nombre = scanner.nextLine();
					if (!nombre.isEmpty())
						cliente.setNombre( nombre);
					System.out.println( "DNI actual del cliente: " + cliente.getDni());
					System.out.print( "Introduce el nuevo DNI (vacío no modifica): ");
					dni = scanner.nextLine();
					if (!dni.isEmpty())
						cliente.setDni( dni);
					System.out.println( "- Cliente editado -");
					salir = true;
				}
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}

	
	private static void editarMusica()
	{		
		boolean salir;
		String nombre, codigo;
		Integer idMusica;
		Musica musica;

		do
		{
			try
			{
				System.out.print( "Introduce el Id del producto música: ");
				idMusica = Integer.parseInt( scanner.nextLine()); 
				
				if ((musica = Musica.buscar( idMusica)) == null)
				{
					System.out.println( "El producto no existe");
					salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
				}
				else
				{
					System.out.println( "Tipo de producto: " + musica.getNombreTipo());
					System.out.println( "Nombre actual del producto: " + musica.getNombre());
					System.out.print( "Introduce el nuevo nombre (vacío no modifica): ");
					nombre = scanner.nextLine();
					if (!nombre.isEmpty())
						musica.setNombre( nombre);
					System.out.println( "Código actual del producto: " + musica.getCodigo());
					System.out.print( "Introduce el nuevo código (vacío no modifica): ");
					codigo = scanner.nextLine();
					if (!codigo.isEmpty())
						musica.setCodigo( codigo);
					System.out.println( "Precio base del producto actual " + musica.getPrecio());
					System.out.print( "Nuevo precio base (vacío no modifica): ");
					codigo = scanner.nextLine();
					if (!codigo.isEmpty())		
						musica.setPrecio( (float)Double.parseDouble( codigo));
					System.out.println( "- Producto editado -");
					salir = true;
				}
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}	

	
	private static void gestionarStock( Tienda tienda)
	{		
		boolean salir;
		Integer idMusica, stock;

		do
		{
			System.out.print( "Introduce el Id del producto música: ");
			idMusica = Integer.parseInt( scanner.nextLine());
			try
			{
				System.out.println( "El stock actual de este producto es: " + tienda.getStockMusica( idMusica));
				System.out.print( "Introduce la cantidad de stock a añadir o a eliminar para este producto: ");
			}
			catch (StockInsuficienteExcepcion e)
			{
				System.out.println( e.getMessage());
				System.out.print( "Introduce la cantidad de stock a adquirir para el producto: ");
			}
			
			try
			{
				stock = Integer.parseInt( scanner.nextLine());
				tienda.setStockMusica( idMusica, stock);
				System.out.println( "- Stock modificado -");
				salir = true;
			}
			catch (Exception e)
			{							
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);										
	}
	
	
	private static void gestionarPrecio( Tienda tienda)
	{		
		boolean salir;
		Integer idMusica;
		float precio;

		do
		{
			try
			{
				System.out.print( "Introduce el Id del producto música: ");
				idMusica = Integer.parseInt( scanner.nextLine());
				System.out.println( "El precio actual de este producto es: " + tienda.getPrecioMusica( idMusica));
				System.out.print( "Introduce el nuevo precio para este producto: ");
				precio = (float)Double.parseDouble( scanner.nextLine());
				tienda.setPrecioMusica( idMusica, precio);
				System.out.println( "- Precio del producto en tienda modificado -");
				salir = true;
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}
		

	private static void gestionarSaldo( Tienda tienda)
	{		
		boolean salir;
		Integer idCliente;
		float precio;

		do
		{
			System.out.print( "Introduce el Id del cliente: ");
			idCliente = Integer.parseInt( scanner.nextLine());
			try
			{
				System.out.println( "El saldo actual de este cliente es: " + tienda.getCuentaCliente( idCliente));
				System.out.print( "Introduce el saldo a añadir o restar en la cuenta de este cliente: ");
			}
			catch (SaldoInsuficienteExcepcion e)
			{
				System.out.println( e.getMessage());
				System.out.print( "Introduce el saldo inicial en la cuenta de este cliente: ");
			}
			
			try
			{
				precio = (float)Double.parseDouble( scanner.nextLine());
				tienda.setCuentaCliente( idCliente, precio);
				System.out.println( "- Saldo del cliente modificado -");
				salir = true;
			}
			catch (Exception e)
			{							
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}

	
	private static void bloquearCliente( Tienda tienda)
	{		
		boolean salir;
		Integer idCliente;
		boolean bloqueo;
		char caracter;

		do
		{
			try
			{
				System.out.print( "Introduce el Id del cliente: ");
				idCliente = Integer.parseInt( scanner.nextLine());
				bloqueo = tienda.getBloqueoCliente( idCliente);
				System.out.println( "La cuenta del cliente se encuentra " + 
									(bloqueo ? "bloqueada. ¿Deseas desbloquearla?" 
											 : "desbloqueada. ¿Deseas bloquearla?") + " (s/n)");
				caracter = Character.toLowerCase(scanner.nextLine().charAt(0));
				tienda.setBloqueoCliente( idCliente, bloqueo ^ (caracter == 's'));
				System.out.println( "- Bloqueo de la cuenta del cliente modificado -");
				salir = true;
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
			}
		} while (!salir);
	}
	

	private static void realizarVenta( Tienda tienda)
	{		
		boolean salir;
		Integer idCliente, idMusica, stock;

		do
		{
			try
			{							
				System.out.print( "Introduce el Id del cliente comprador: ");
				idCliente = Integer.parseInt( scanner.nextLine());
				System.out.print( "Introduce el Id del producto a vender: ");
				idMusica = Integer.parseInt( scanner.nextLine());
				System.out.print( "Introduce el número de productos a vender: ");
				stock = Integer.parseInt( scanner.nextLine());
				tienda.realizarVenta(idMusica, idCliente, stock);
				System.out.println( "- Venta realizada -");
				salir = true;
			}
			catch (Exception e)
			{
				System.out.println( e.getMessage());
				salir = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");							
			}
		} while (!salir);
	}										

	
	private static void modoComandos()
	{		
		boolean salir = false;

		do
		{
			System.out.println();
			System.out.println( "Posibles instrucciones:");
			System.out.println( "- listar tiendas|clientes|musicas");
			System.out.println( "- registrar tienda|cliente|cd|casete|vinilo");
			System.out.println( "- editar tienda|cliente|musica");
			System.out.println( "- eliminar tienda|cliente|musica");
			System.out.println( "- gestionar venta|stock|precio|saldo idTienda");
			System.out.println( "- salir");
			System.out.println();
			System.out.println( "Esperando instrucción: ");
			String[] comandos = scanner.nextLine().split(" ");
			switch (comandos[0].toLowerCase())
			{
				case "listar":
					switch (comandos[1])
					{
						case "tiendas":
							GestionTiendas.listarTiendas();
							break;
	
						case "clientes":
							GestionTiendas.listarClientes();
							break;
							
						case "musicas":
							GestionTiendas.listarMusicas();
							break;
	
						default:
							System.out.println( "No existe la instrucción");
							break;
					}
					break;
				
				case "registrar":
					switch (comandos[1])
					{
						case "tienda":
							GestionTiendas.registrarTienda();
							break;
	
						case "cliente":
							GestionTiendas.registrarCliente();
							break;
							
						case "cd":
							GestionTiendas.registrarCD();
							break;
	
						case "casete":
							GestionTiendas.registrarCasete();
							break;
	
						case "Vinilo":
							GestionTiendas.registrarVinilo();
							break;
	
						default:
							System.out.println( "No existe la instrucción");
							break;
					}
					break;
				
				case "eliminar":
					switch (comandos[1])
					{
						case "tienda":
							GestionTiendas.eliminarTienda();
							break;
	
						case "cliente":
							GestionTiendas.eliminarCliente();
							break;
							
						case "musica":
							GestionTiendas.eliminarMusica();
							break;
	
						default:
							System.out.println( "No existe la instrucción");
							break;
					}
					break;
				
				case "editar":
					switch (comandos[1])
					{
						case "tienda":
							GestionTiendas.editarTienda();
							break;
	
						case "cliente":
							GestionTiendas.editarCliente();
							break;
							
						case "musica":
							GestionTiendas.editarMusica();
							break;
	
						default:
							System.out.println( "No existe la instrucción");
							break;
					}
					break;
				
				case "gestionar":
					Tienda tienda = Tienda.buscar( Integer.parseInt( comandos[2]));
					if (tienda == null)
					{
						System.out.println( "La tienda no existe");
						break;
					}
					switch (comandos[1])
					{
						case "venta":
							GestionTiendas.realizarVenta( tienda);
							break;
	
						case "stock":
							GestionTiendas.gestionarStock( tienda);
							break;
							
						case "saldo":
							GestionTiendas.gestionarSaldo( tienda);
							break;
	
						case "precio":
							GestionTiendas.gestionarPrecio( tienda);
							break;
	
						default:
							System.out.println( "No existe la instrucción");
							break;
					}
					break;
				
				case "salir":
					salir = true;
					break;
					
				default:
					System.out.println( "No existe la instrucción");
					break;
			}
		} while (!salir);
	}
	

	
	
	/**
	 * Función principal
	 * @param args Argumentos de entrada en la línea de comandos
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
		
		boolean salirPrograma = false;		// Flag para saber si salir del programa.
		int opcion = 0;				// Opción actual elegida del menú.
		Tienda tiendaAdmin = null;	// Tienda que estamos administrando actualmente.
		String nombreMenu = null;
		boolean salirPregunta = true;

		do	// Bucle principal del programa.
		{
			do // Bucle para asegurar la elección de una opción del menú actual.
			{
				try
				{
					System.out.println();
					menu.mostrarMenu();
					System.out.print( "Introduce la opción: ");
					opcion = Integer.parseInt( scanner.nextLine());
					nombreMenu = menu.obtenerNombre( opcion - 1);				
					salirPregunta = true;
				}
				catch (Exception e)
				{					
					System.out.println( "No existe la opción. Elige otra");
					salirPregunta = false;
				}				
			} while (!salirPregunta);
			
			switch (nombreMenu)
			{
				case "RegistrarTienda":	
					GestionTiendas.registrarTienda();
					break;
			
				case "RegistrarCliente":
					GestionTiendas.registrarCliente();
					break;

				case "RegistrarCD":	
					GestionTiendas.registrarCD();
					break;

				case "RegistrarCasete":
					GestionTiendas.registrarCasete();
					break;

				case "RegistrarVinilo":	
					GestionTiendas.registrarVinilo();
					break;

				case "EliminarTienda":
					GestionTiendas.eliminarTienda();
					break;

				case "EliminarCliente":
					GestionTiendas.eliminarCliente();
					break;

				case "EliminarMusica":	
					GestionTiendas.eliminarMusica();
					break;

				case "EditarTienda":
					GestionTiendas.editarTienda();
					break;
			
				case "EditarCliente":
					GestionTiendas.editarCliente();
					break;
			
				case "EditarMusica":
					GestionTiendas.editarMusica();
					break;
			
				case "ListarTiendas":		
					GestionTiendas.listarTiendas();
					break;
			
				case "ListarClientes":					
					GestionTiendas.listarClientes();
					break;
			
				case "ListarMusicas":					
					GestionTiendas.listarMusicas();
					break;
					
				case "AdministrarTienda":
					do {						
						System.out.print( "Introduce el Id de la tienda: ");
						if ((tiendaAdmin = Tienda.buscar( Integer.parseInt( scanner.nextLine()))) == null)
						{
							System.out.println( "La tienda no existe");
							salirPregunta = !GestionTiendas.preguntaReintentar( "¿Deseas intentarlo de nuevo?");
						}
						else
						{
							System.out.println( "** Tienda a administrar: " + tiendaAdmin.getNombre() + " - " + tiendaAdmin.getCif() + " **");
							salirPregunta = true;
							menu.avanzar( opcion - 1);
						}
					} while (!salirPregunta);

					break;
					
				case "GestionarStock":
					GestionTiendas.gestionarStock( tiendaAdmin);
					break;
					
				case "GestionarPrecio":
					GestionTiendas.gestionarPrecio( tiendaAdmin);
					break;
								
				case "GestionarSaldo":
					GestionTiendas.gestionarSaldo( tiendaAdmin);
					break;
					
				case "BloquearCliente":
					GestionTiendas.bloquearCliente( tiendaAdmin);
					break;
					
				case "RealizarVenta":
					GestionTiendas.realizarVenta( tiendaAdmin);
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
					
				case "Comandos":
					GestionTiendas.modoComandos();
					break;

				case "Salir":
					salirPrograma = true;
					break;
					
				default:
					menu.avanzar( opcion - 1);
					break;
			}
			
		} while (!salirPrograma);
		
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

