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


/**
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class Tienda implements Serializable 
{
	static private ArrayList<Tienda> tiendas;   // Lista de tiendas totales cargadas de la base de datos.	
	static private int idGeneral = 1;
	
	private int id;				// Nº identificación de la tienda
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
		private int idMusica;       // Id del objeto Musica
		private int numProductos;   // Número de productos idMusica disponibles en stock.
		private float precio;       // Precio del producto en la Tienda.
		
		/**
		 * Constructor
		 * @param idMusica Id del objeto Musica
		 * @param numProductos Número de productos en stock en la Tienda
		 * @param precio Precio del producto en la Tienda
		 */
		public InfoMusica( int idMusica, int numProductos, float precio)
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
		private int idCliente;    // Id del objeto Cliente.
		private float dinero;     // Dinero disponible por el cliente en la tienda.
		
		public InfoCliente( int idCliente, float dinero)
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
		public void realizarPago( int pago) throws DineroInsuficienteExcepcion
		{
			if (this.dinero < pago)
				throw new DineroInsuficienteExcepcion( "El cliente no tiene suficiente dinero en la cuenta.")

			this.dinero -= pago;
		}
		
		
		/**
		 * Ingresar dinero en la cuenta del cliente
		 * @param dinero Dinero a ingresar en la cuenta del cliente
		 */
		public void ingresarDinero( int ingreso)
		{
			this.dinero += ingreso;
		}
	}
}
