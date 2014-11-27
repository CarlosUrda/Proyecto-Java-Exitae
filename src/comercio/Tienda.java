/**
 * 
 */
package comercio;
import gestionBD.BD;
import gestionBD.ObjetoBD;
import excepcionesGenericas.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;


/**
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class Tienda implements Serializable, ObjetoBD
{
	private static final long serialVersionUID = 1L;
	private static int idGeneral = 1;
	private static ArrayList<Tienda> lista;   // Lista de tiendas totales cargadas de la base de datos.	
	
	private int id;					// Nº identificación de la tienda
	private String nombre;			// Nombre de la tienda
	private String cif;				// CIF de la tienda
	private ArrayList<Tienda.InfoMusica> listaInfoMusica;
	private ArrayList<Venta> listaVentas;
	private ArrayList<Tienda.InfoCliente> listaInfoCliente;
	private float deudaLimite;		// Deuda límite en el saldo negativo que puede tener un cliente.  
	
	/**
	 * Función encargada de leer los objetos Tienda grabados en un archivo y guardarlos
	 * como una lista interna en memoria.
	 * @param nombreArchivo Nombre del archivo de donde leer los objetos Tienda
	 * @return Ninguno.
	 * @throws ClassNotFoundException Si no se encuentra ningún objeto Tienda
	 * @throws InvalidClassException Error del objeto Tienda leído.
	 * @throws OptionalDataException Error al leer un tipo de dato primitivo en
	 * lugar de un objeto Tienda
	 * @throws IOException cualquier otro error en la lectura del archivo
	 * @throws FileNotFoundException Si no se encuentra el archivo con nombreArchivo
	 */
	public static void cargarBD( String nombreArchivo) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Tienda.lista = BD.leerObjetos( nombreArchivo);
	}
	
	
	/**
	 * Función encargada de escribir los objetos Tienda de memoria en un archivo.
	 * @param nombreArchivo Nombre del archivo de donde escribir los objetos Tienda
	 * @return Ninguno.
s	 * @throws IOException cualquier otro error en la lectura del archivo
	 */
	public static void guardarBD( String nombreArchivo) throws IOException
	{
		BD.escribirObjetos( nombreArchivo, Tienda.lista);
	}


	/**
	 * Obtener la lista de tiendas cargadas en memoria a partir de los datos grabados
	 * en un archivo.
	 * @return Lista de tiendas cargadas en memoria actualmente
	 */
	public static ArrayList<Tienda> obtenerTiendas()
	{
		return Tienda.lista;
	}
	
	
	/**
	 * Registrar un producto Tieda en la base de datos de tiendas en memoria
	 * @param nombre Nombre de la Tienda.
	 * @param cif Cif de la Tienda.
	 * @paraç deudaLimite Límite base de crédito a cada cliente
	 * @throws ObjetoExisteExcepcion Si ya existe una Tienda con estos datos
	 */
	public static void registrar( String nombre, String cif, float deudaLimite) throws ObjetoExisteExcepcion
	{
		// Se comprueba si la Tienda ya existe
		Tienda tienda = new Tienda( nombre, cif, deudaLimite);
		if (Tienda.lista.contains( tienda))
			throw new ObjetoExisteExcepcion( "Ya existe una tienda con estos datos."); 
		
		Tienda.lista.add( tienda);
	}
	
	
	/**
	 * Constructor
	 * @param nombre Nombre de la Tienda
	 * @param cif Cif de la Tienda
	 * @param deudaLimite Deuda límite para cada cliente.
	 */
	private Tienda( String nombre, String cif, float deudaLimite)
	{
		this.id = Tienda.idGeneral++;
		this.nombre = nombre;
		this.cif = cif;
		this.deudaLimite = deudaLimite;
	}

	
	/**
	 * Sobrecarga de método equals para comparar objetos Tienda
	 * @param tienda Tienda a comparar
	 * @return Si el objeto es de tipo Tienda devuelve true si coincide el codigo,
	 * sino devuelve false; Si el objeto no es de tipo Tienda, devuelve directamente
	 * el resultado de la comparación de las referencias a los objetos
	 */
	@Override
	public boolean equals( Object tienda)
	{
		if (tienda instanceof Tienda)
			return this.cif.equalsIgnoreCase( ((Tienda)tienda).obtenerCif());
		
		return this == tienda;
	}

	
	/**
	 * Obtener el valor del Cif de la Tienda
	 * @return Id de la venta
	 */
	public String obtenerCif()
	{
		return this.cif;
	}
	
	
	/**
	 * Obtener el valor del Id de la Tienda
	 * @return Id de la venta
	 */
	public int obtenerId()
	{
		return this.id;
	}
	
	
	/**
	 * Método encargadoo de gestionar la realización de una venta.
	 * @param idMusica Producto a vender
	 * @param idCliente Cliente que hace la compra
	 * @param numProductos Número de productos vendidos
	 * @param StockInsuficienteExcepcion Si no hay stock del producto para poder hacer la venta.
	 * @param SaldoInsuficienteExcepcion Si el cliente no puede realizar el pago con su cuenta.
	 */
	public void realizarVenta( int idMusica, int idCliente, int numProductos) 
			throws StockInsuficienteExcepcion, SaldoInsuficienteExcepcion
	{
		// Se comprueba si existe stock la Musica en la Tienda.
		InfoMusica infoMusica = this.buscarInfoMusica( idMusica);
		if ((infoMusica == null) || (infoMusica.comprobarStock( numProductos) == false))
			throw new StockInsuficienteExcepcion( "No hay stock en la tienda.");
		
		// Se comprueba si el cliente puede realizar el pago.
		InfoCliente infoCliente = this.buscarInfoCliente( idCliente);
		if (infoCliente == null) 
			throw new SaldoInsuficienteExcepcion( "El cliente no tiene activada su cuenta en la Tienda.");

		String estadoPago = infoCliente.comprobarPago( numProductos * infoMusica.obtenerPrecio());
		if (estadoPago != null)
			throw new SaldoInsuficienteExcepcion( estadoPago);

		// Se actualiza el stock y la cuenta del cliente
		infoMusica.variarStock( numProductos);
		infoCliente.realizarPago( infoMusica.obtenerPrecio() * numProductos);
		
		// Se realiza la venta.
		Venta venta = new Venta( idMusica, idCliente, numProductos, infoMusica.obtenerPrecio());
		this.listaVentas.add( venta);
	}
	
	
	/**
	 * Método encargado de modificar la cuenta de un Cliente registrado. Si el cliente
	 * no tenía cuenta en esta Tienda, se crea con el saldo inicial.
	 * @param idCliente Id del Cliente registrado
	 * @param dinero Dinero a modificar del saldo en la cuenta del cliente.
	 * @throws ObjetoNoEncontradoExcepcion El Cliente no está registrado.
	 */
	public void cuentaCliente( int idCliente, float dinero) throws ObjetoNoEncontradoExcepcion
	{
		// Se comprueba la existencia del objeto Cliente
		Cliente cliente = Cliente.buscar( idCliente);
		if (cliente == null)
			throw new ObjetoNoEncontradoExcepcion( "El cliente con id " + idCliente + " no está registrado");

		// Se comprueba si el cliente ya dispone de una cuenta en esta Tienda.
		InfoCliente infoCliente = this.buscarInfoCliente( idCliente);
		if (infoCliente == null)
			this.listaInfoCliente.add( this.new InfoCliente( idCliente, dinero));
		else
			infoCliente.variarSaldo( dinero);
	}
	
	
	/**
	 * Método encargado de incorporar a la Tienda un nuevo producto con un determinado
	 * stock. Si el producto ya se encuentra en la Tienda, se modifica el stock.
	 * @param idMusica Id del producto Musica a incorporar
	 * @param stock Número de productos adquiridos.
	 * @throws StockInsuficienteExcepcion Si no hay stock suficiente para extraer.
	 * @throws ObjetoNoEncontradaExcepcion Si no existe ningún objeto Musica registrado 
	 *  con el idMusica.
	 */
	public void stockMusica( int idMusica, int stock) 
			throws StockInsuficienteExcepcion, ObjetoNoEncontradoExcepcion
	{
		// Se comprueba la existencia del objeto Musica
		Musica musica = Musica.buscar( idMusica);
		if (musica == null)
			throw new ObjetoNoEncontradoExcepcion( "El objeto música con id " + idMusica + " no existe");

		// Se comprueba si el objeto Musica ha sido anteriormente adquirido por la Tienda.
		InfoMusica infoMusica = this.buscarInfoMusica(idMusica);
		if (infoMusica == null)
		{
			if (stock < 0)
				throw new StockInsuficienteExcepcion( "No se puede incorporar un producto con stock negativo");
			
			this.listaInfoMusica.add( this.new InfoMusica( idMusica, stock, musica.obtenerPrecio()));
		}
		else
			infoMusica.variarStock( stock);		
	}
	

	/**
	 * Método encargado de buscar, en la lista de musicas adquiridos a disposición
	 * en la Tienda, alguno con un id de Musica concreto.
	 * @param idMusica Id del objeto Musica a buscar.
	 * @return Objeto InfoMusica encontrado o null si la tienda no lo ha adquirido
	 */
	private InfoMusica buscarInfoMusica( int idMusica)
	{
		return BD.buscarObjeto( this.listaInfoMusica, idMusica);
	}
	
	
	/**
	 * Método encargado de buscar, en la lista de clientes registrados
	 * en la Tienda, alguno con un id de Cliente concreto.
	 * @param idMusica Id del objeto Musica a buscar.
	 * @return Objeto InfoMusica encontrado o null si la tienda no lo ha adquirido
	 */
	private InfoCliente buscarInfoCliente( int idCliente)
	{
		return BD.buscarObjeto( this.listaInfoCliente, idCliente);
	}

	
	/**
	 * Clase con la información de cada producto a disposición en la tienda.
	 * @author Carlos A. Gómez Urda
	 * @version 1.0
	 */
	private class InfoMusica implements ObjetoBD
	{
		private Integer idMusica;   // Id del objeto Musica
		private Integer stock;   	// Número de productos idMusica disponibles en stock.
		private float precio;       // Precio del producto en la Tienda.
		
		/**
		 * Constructor
		 * @param idMusica Id del objeto Musica
		 * @param stock Número de productos en stock en la Tienda
		 * @param precio Precio del producto en la Tienda
		 */
		public InfoMusica( Integer idMusica, Integer stock, float precio)
		{
			this.idMusica = idMusica;
			this.stock = stock;
			this.precio = precio;
		}
		
		
		/**
		 * Comprobar si hay suficiente stock para una cantidad de productos solicitada
		 * @param stock Cantidad a comprobar si existe en stock.
		 * @return true si hay stock, false si no hay stock para esa cantidad solicitada.
		 */
		public boolean comprobarStock( int numProductos)
		{
			return (this.stock - numProductos) >= 0;
		}
		
		
		/**
		 * Cambiar el número de productos en stock del objeto Musica en la Tienda.
		 * @param stock Número de productos a modificar en el stock de la tienda.
		 * @throws StockInsuficienteExcepcion Si no hay stock suficiente
		 */
		public void variarStock( int stock) throws StockInsuficienteExcepcion
		{
			if ((stock < 0) && (this.comprobarStock( -stock) == false))
				throw new StockInsuficienteExcepcion( "No hay suficiente stock en la tienda.");
			
			this.stock += stock;
		}
		
		
		/**
		 * Cambiar el precio de venta de un producto Musica en la Tienda.
		 * @param precio Nuevo precio de venta del producto.
		 * @throws ArgumentoInvalido Si el precio es negativo.
		 */
		public void cambiarPrecio( float precio) throws ArgumentoInvalidoExcepcion
		{
			if (precio < 0)
				throw new ArgumentoInvalidoExcepcion( "El precio no puede ser negativo");
			
			this.precio = precio;
		}
		
		
		/**
		 * Devolver el id del objeto Musica al cual se referencia 
		 * @return idMusica
		 */
		public int obtenerId()
		{
			return this.idMusica;
		}
		

		/**
		 * Obtener el precio al que se vendió el producto.
		 * @return Precio al que se vendió el producto.
		 */
		public float obtenerPrecio()
		{
			return this.precio;
		}
	}


	/**
	 * Clase con la información de cada cliente inscrito en la tienda.
	 * @author Carlos A. Gómez Urda
	 * @version 1.0
	 */
	private class InfoCliente implements ObjetoBD
	{
		private int idCliente;      // Id del objeto Cliente.
		private float saldo;        // Dinero disponible por el cliente en la tienda.
		private boolean bloqueada;  // Flag para saber si la cuenta está bloqueada.
		
		/**
		 * Constructor
		 * @param idCliente Id del Cliente
		 * @param saldo Dinero a disponer en la Tienda.
		 */
		public InfoCliente( int idCliente, float saldo)
		{
			this.idCliente = idCliente;
			this.saldo = saldo;
			this.bloqueada = false;
		}
		
		
		/**
		 * Comprobar si el cliente puede realizar un pago.
		 * @param pago
		 * @return null si puede realizar el pago; Mensaje de error si no puede realizarlo.
		 */
		public String comprobarPago( float pago)
		{
			if (this.bloqueada)
				return new String( "La cuenta está bloqueada.");

			// Se comprueba si se puede realizar el pago
			if (-Tienda.this.deudaLimite > (this.saldo - pago))
				return new String( "El pago sobrepasa el límite de crédito.");
			
			return null;
		}
		
		
		/**
		 * Aumentar o disminuir el dinero
		 * @param pago Pago a descontar de la cuenta del cliente
		 * @throws SaldoInsuficienteExcepcion Si no hay dinero en la cuenta para
		 * poder realizar el pago.
		 */
		public void realizarPago( float pago) throws SaldoInsuficienteExcepcion
		{
			String estado = this.comprobarPago( pago);
			if (estado != null)
				throw new SaldoInsuficienteExcepcion( estado);

			this.saldo -= pago;
		}
		
		
		/**
		 * Ingresar o extraer dinero en la cuenta del cliente
		 * @param dinero Dinero a ingresar o extraer en la cuenta del cliente
		 */
		public void variarSaldo( float dinero)
		{
			this.saldo += dinero;
		}		


		/**
		 * Devolver el id del objeto Cliente al cual se referencia 
		 * @return idMusica
		 */
		public int obtenerId()
		{
			return this.idCliente;
		}
	}
	
}



/**
 * Clase que representa cada venta realizada en la Tienda
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
class Venta implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static int idGeneral = 1;
	
	private int id;
	private int idMusica;
	private int idCliente;
	private int numProductos; 			 // Número de productos vendidos en la venta.
	private float precio;                // Precio de cada unidad vendida.
	private GregorianCalendar fecha;	 // Fecha de la venta.
	
	
	/**
	 * Constructor
	 * @param idMusica Id de Musica a vender
	 * @param idCliente Id del cliente que realiza la compra
	 * @param numProductos Número de productos vendidos en la venta.
	 * @param precio Precio de cada unidad vendida del producto Musica.
	 */
	public Venta( int idMusica, int idCliente, int numProductos, float precio)
	{
		this.idMusica = idMusica;
		this.idCliente = idCliente;
		this.numProductos = numProductos;
		this.id = Venta.idGeneral++;
		this.precio = precio;
		this.fecha = new GregorianCalendar();
	}
	
	
	/**
	 * Obtener el valor del Id de la venta
	 * @return Id de la venta
	 */
	public int obtenerId()
	{
		return this.id;
	}
	
	
	/**
	 * Obtener el precio al que se vendió el producto.
	 * @return Precio al que se vendió el producto.
	 */
	public float obtenerPrecio()
	{
		return this.precio;
	}
}
