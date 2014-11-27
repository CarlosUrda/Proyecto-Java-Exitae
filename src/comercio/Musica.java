/**
 * 
 */
package comercio;

import gestionBD.BD;
import gestionBD.DatoObjeto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase Musica que representa un producto de música genñerico (CD, Vinilo, Casette, etc)
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public abstract class Musica implements Serializable, DatoObjeto
{
	private static int idGeneral = 1;
	private static ArrayList<Musica> lista;	// Lista de objetos Musica cargados en memoria desde la base de datos
	
	private int id;			  // Id del producto en la base de datos.
	private String nombre;	  // Nombre del producto
	private String codigo;    // Código único del producto
	private float precio;     // Precio base del producto.
	
	
	/**
	 * Constructor
	 * @param nombre Nombre del objeto Musica
	 * @param codigo Codigo único (estilo código de barras) del producto.
	 * @param precio Precio base del producto.
	 */
	public Musica( String nombre, String codigo, float precio)
	{
		this.nombre = nombre;
		this.codigo = codigo;
		this.precio = precio;
		this.id = Musica.idGeneral++;
	}


	/**
	 * Función encargada de leer los objetos Musica grabados en un archivo y guardarlos
	 * como una lista interna.
	 * @param nombreArchivo Nombre del archivo de donde leer los objetos Musica
	 * @return Ninguno.
	 * @throws ClassNotFoundException Si no se encuentra ningún objeto Musica
	 * @throws IOException Cualquier otro error en la lectura del archivo
	 * @throws FileNotFoundException Si no se encuentra el archivo con nombreArchivo
	 */
	public static void cargar( String nombreArchivo) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Musica.lista = BD.leerObjetos( nombreArchivo);
	}
	
	
	/**
	 * Función encargada de escribir los objetos Musica de memoria a un archivo.
	 * @param nombreArchivo Nombre del archivo de donde escribir los objetos Musica
	 * @return Ninguno.
	 * @throws IOException cualquier otro error en la lectura del archivo
	 */
	public static void guardar( String nombreArchivo) throws IOException
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
		return BD.buscar( Musica.lista, id);
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
	 * Obtener el precio base del objeto Musica
	 * @return Precio base
	 */
	public float obtenerPrecio()
	{
		return this.precio;
	}
}
