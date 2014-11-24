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
import java.util.Iterator;

/**
 * Clase Musica que representa un producto de música genñerico (CD, Vinilo, Casette, etc)
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public abstract class Musica implements Serializable
{
	private static int idGeneral = 1;
	private static ArrayList<Musica> musicas;
	
	private int id;			  // Id del producto en la base de datos.
	private String nombre;	  // Nombre del producto
	private String codigo;    // Código único del producto
	
	
	/**
	 * Constructor
	 * @param nombre Nombre del objeto Musica
	 * @param codigo Codigo único (estilo código de barras) del producto.
	 */
	public Musica( String nombre, String codigo)
	{
		this.nombre = nombre;
		this.codigo = codigo;
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
	public static void cargarMusicas( String nombreArchivo) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Musica.musicas = BD.leerObjetos( nombreArchivo);
	}
	
	
	/**
	 * Función encargada de escribir los objetos Musica de memoria a un archivo.
	 * @param nombreArchivo Nombre del archivo de donde escribir los objetos Musica
	 * @return Ninguno.
	 * @throws IOException cualquier otro error en la lectura del archivo
	 */
	public static void guardarMusicas( String nombreArchivo) throws IOException
	{
		BD.escribirObjetos( nombreArchivo, Musica.musicas);
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
	
	
}
