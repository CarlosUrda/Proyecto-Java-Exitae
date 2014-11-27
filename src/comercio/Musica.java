/**
 * 
 */
package comercio;

import gestionBD.BD;
import gestionBD.ObjetoBD;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase Musica que representa un producto de m�sica gen�erico (CD, Vinilo, Casette, etc)
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public abstract class Musica implements Serializable, ObjetoBD
{
	private static final long serialVersionUID = 1L;
	private static int idGeneral = 1;
	protected static ArrayList<Musica> lista;	// Lista de objetos Musica cargados en memoria desde la base de datos
	
	private int id;			  // Id del producto en la base de datos.
	private String nombre;	  // Nombre del producto
	private String codigo;    // C�digo �nico del producto
	private float precio;     // Precio base del producto.
	
	
	/**
	 * Constructor
	 * @param nombre Nombre del objeto Musica
	 * @param codigo Codigo �nico (estilo c�digo de barras) del producto.
	 * @param precio Precio base del producto.
	 */
	protected Musica( String nombre, String codigo, float precio)
	{
		this.nombre = nombre;
		this.codigo = codigo;
		this.precio = precio;
		this.id = Musica.idGeneral++;
	}


	/**
	 * Funci�n encargada de leer los objetos Musica grabados en un archivo y guardarlos
	 * como una lista interna.
	 * @param nombreArchivo Nombre del archivo de donde leer los objetos Musica
	 * @return Ninguno.
	 * @throws ClassNotFoundException Si no se encuentra ning�n objeto Musica
	 * @throws IOException Cualquier otro error en la lectura del archivo
	 * @throws FileNotFoundException Si no se encuentra el archivo con nombreArchivo
	 */
	public static void cargarBD( String nombreArchivo) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Musica.lista = BD.leerObjetos( nombreArchivo);
	}
	
	
	/**
	 * Funci�n encargada de escribir los objetos Musica de memoria a un archivo.
	 * @param nombreArchivo Nombre del archivo de donde escribir los objetos Musica
	 * @return Ninguno.
	 * @throws IOException cualquier otro error en la lectura del archivo
	 */
	public static void guardarBD( String nombreArchivo) throws IOException
	{
		BD.escribirObjetos( nombreArchivo, Musica.lista);
	}

	
	/**
	 * Obtener la lista de objeos Musica cargadas en memoria a partir de los datos grabados
	 * en un archivo.
	 * @return Lista de objetos Musica cargadas en memoria actualmente
	 */
	public static ArrayList<Musica> obtenerMusicas()
	{
		return Musica.lista;
	}
	
	
	/**
	 * Buscar un objeto Musica con un id determinado dentro de la lista de objetos 
	 * cargadas actualmente en memoria a partir del archivo. 
	 * @param id
	 * @return Objeto Musica encontrado. Si no se encuentra devuelve null.
	 */
	public static Musica buscar( int id)
	{
		return BD.buscarObjeto( Musica.lista, id);
	}


	/**
	 * Sobrecarga de m�todo equals para comparar objetos Musica
	 * @param musica Musica a comparar
	 * @return Si el objeto es de tipo Musica devuelve true si coincide el codigo,
	 * sino devuelve false; Si el objeto no es de tipo Musica, devuelve directamente
	 * el resultado de la comparaci�n de las referencias a los objetos
	 */
	@Override
	public boolean equals( Object musica)
	{
		if (musica instanceof Musica)
			return this.codigo.equalsIgnoreCase( ((Musica)musica).obtenerCodigo());
		
		return this == musica;
	}

	
	/**
	 * Obtener el Id del objeto Musica
	 * @return Id
	 */
	public int obtenerId()
	{
		return this.id;
	}


	/**
	 * Obtener el codigo del objeto Musica
	 * @return Precio base
	 */
	public String obtenerCodigo()
	{
		return this.codigo;
	}

	
	/**
	 * Obtener el precio base del objeto Musica
	 * @return Precio base
	 */
	public float obtenerPrecio()
	{
		return this.precio;
	}
}
