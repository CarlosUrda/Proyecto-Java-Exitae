/**
 * 
 */
package comercio;
import gestionBD.BD;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;


/**
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class Tienda implements Serializable 
{
	static private ArrayList<Tienda> tiendas;   // Lista de tiendas totales cargadas de la base de datos.	
	static private Integer idGeneral = 1;
	
	private Integer id;				// Nº identificación de la tienda
	private String nombre;		// Nombre de la tienda
	private String cif;			// CIF de la tienda
	private ArrayList<Tienda.InfoMusica> listaInfoMusica;
	private ArrayList<Integer> listaIdVentas;
	private ArrayList<Tienda.InfoCliente> listaInfoClientes;
	

	/**
	 * Constructor
	 * @param nombre Nombre de la Tienda
	 * @param cif Cif de la Tienda
	 */
	public Tienda( String nombre, String cif)
	{
		this.id = Tienda.idGeneral++;
		this.nombre = nombre;
		this.cif = cif;
	}

	
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
	public static void cargarTiendas( String nombreArchivo) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Tienda.tiendas = BD.leerObjetos( nombreArchivo);
	}
	
	
	/**
	 * Función encargada de escribir los objetos Tienda de memoria en un archivo.
	 * @param nombreArchivo Nombre del archivo de donde escribir los objetos Tienda
	 * @return Ninguno.
s	 * @throws IOException cualquier otro error en la lectura del archivo
	 */
	public static void guardarTiendas( String nombreArchivo) throws IOException
	{
		BD.escribirObjetos( nombreArchivo, Tienda.tiendas);
	}


	/**
	 * Método encargadoo de gestionar la realización de una venta.
	 * @param idMusica Producto a vender
	 * @param idCliente Cliente que hace la compra
	 * @param numProductos Número de productos vendidos
	 */
	public void realizarVenta( Integer idMusica, Integer idCliente, int numProductos)
	{
		// Se comprueba si existe la Musica y el Cliente
		
		// Se comprueba si queda stock
		
		// Se comprueba si el cliente tiene dinero en su cuenta.
		
		Venta venta = new Venta( idMusica, idCliente, numProductos);
	}
	
	public void asociarCliente( Integer idCliente)
	{
		
	}
	
	public void adquirirMusica( Integer idMusica)
	{
		
	}
	
	/**
	 * Obtener la lista de tiendas cargadas en memoria a partir de los datos grabados
	 * en un archivo.
	 * @return Lista de tiendas cargadas en memoria actualmente
	 */
	public static ArrayList<Tienda> obtenerTiendas()
	{
		return Tienda.tiendas;
	}
	
	
	/**
	 * Clase con la información de cada producto a vender en la tienda.
	 * @author Carlos A. Gómez Urda
	 * @version 1.0
	 */
	private class InfoMusica
	{
		private Integer idMusica;       // Id del objeto Musica
		private Integer numProductos;   // Número de productos idMusica disponibles en stock.
		private float precio;       // Precio del producto en la Tienda.
		
		/**
		 * Constructor
		 * @param idMusica Id del objeto Musica
		 * @param numProductos Número de productos en stock en la Tienda
		 * @param precio Precio del producto en la Tienda
		 */
		public InfoMusica( Integer idMusica, int numProductos, float precio)
		{
			this.idMusica = idMusica;
			this.numProductos = numProductos;
			this.precio = precio;
		}
		
		
		/**
		 * Cambiar el núnero de productos en stock del objeto Musica en la Tienda.
		 * @param numProductos Número de productos a modificar en el stock.
		 * @throws StockInsuficienteExcepcion Si no hay stock suficiente
		 */
		public void cambiarStock( int numProductos) throws StockInsuficienteExcepcion
		{
			if ((this.numProductos + numProductos) < 0)
				throw new StockInsuficienteExcepcion( "No hay suficiente stock en la tienda.");
			
			this.numProductos += numProductos;
		}
		
	}


	/**
	 * Clase con la información de cada cliente inscrito en la tienda.
	 * @author Carlos A. Gómez Urda
	 * @version 1.0
	 */
	private class InfoCliente
	{
		private Integer idCliente;    // Id del objeto Cliente.
		private float dinero;     // Dinero disponible por el cliente en la tienda.
		
		public InfoCliente( Integer idCliente, float dinero)
		{
			this.idCliente = idCliente;
			this.dinero = dinero;
		}
		
		
		/**
		 * Aumentar o disminuir el dinero
		 * @param pago Pago a descontar de la cuenta del cliente
		 * @throws DineroInsuficienteExcepcion Si no hay dinero en la cuenta para
		 * poder realizar el pago.
		 */
		public void realizarPago( float pago) throws DineroInsuficienteExcepcion
		{
			if (this.dinero < pago)
				throw new DineroInsuficienteExcepcion( "El cliente no tiene suficiente dinero en la cuenta.")

			this.dinero -= pago;
		}
		
		
		/**
		 * Ingresar dinero en la cuenta del cliente
		 * @param dinero Dinero a ingresar en la cuenta del cliente
		 */
		public void ingresarDinero( float ingreso)
		{
			this.dinero += ingreso;
		}		
	}
	
}



/**
 * Clase que representa cada venta realizada en la Tienda
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
class Venta
{
	private static Integer idGeneral = 1;
	
	private Integer id;
	private Integer idMusica;
	private Integer idCliente;
	private int numProductos; 			 // Número de productos vendidos en la venta.
	private GregorianCalendar fecha;	 // Fecha de la venta.
	
	/**
	 * Constructor
	 * @param idMusica Id de Musica a vender
	 * @param idCliente Id del cliente que realiza la compra
	 * @param numProductos Número de productos vendidos en la venta.
	 */
	Venta( int idMusica, int idCliente, int numProductos)
	{
		this.idMusica = idMusica;
		this.idCliente = idCliente;
		this.numProductos = numProductos;
		this.id = Venta.idGeneral++;
		this.fecha = new GregorianCalendar();
	}
	
	
	/**
	 * Obtener el valor del Id de la venta
	 * @return Id de la venta
	 */
	int obtenerId()
	{
		return( this.id);
	}
}
