/**
 * 
 */
package comercio;

import excepcionesGenericas.*;
import gestionBD.*;

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
public abstract class Musica implements Serializable, ObjetoBD
{
	private static final long serialVersionUID = 1L;
	private static Integer idGeneral = 1;
	protected static ArrayList<Musica> lista;	// Lista de objetos Musica cargados en memoria desde la base de datos
	
	private Integer id;			  // Id del producto en la base de datos.
	private String nombre;	  // Nombre del producto
	private String codigo;    // Código único del producto
	private float precio;     // Precio base del producto.
	
	
	/**
	 * Constructor
	 * @param nombre Nombre del objeto Musica
	 * @param codigo Codigo único (estilo código de barras) del producto.
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
	 * Función encargada de leer los objetos Musica grabados en un archivo y guardarlos
	 * como una lista interna.
	 * @param nombreArchivo Nombre del archivo de donde leer los objetos Musica
	 * @return Ninguno.
	 * @throws ClassNotFoundException Si no se encuentra ningún objeto Musica
	 * @throws IOException Cualquier otro error en la lectura del archivo
	 * @throws FileNotFoundException Si no se encuentra el archivo con nombreArchivo
	 */
	public static void cargarBD( String nombreArchivo) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Musica.lista = BD.leerObjetos( nombreArchivo);
		Musica.idGeneral = Musica.lista.get( Musica.lista.size()-1).id + 1;
	}
	
	
	/**
	 * Función encargada de escribir los objetos Musica de memoria a un archivo.
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
	public static Musica buscar( Integer id)
	{
		return BD.buscarObjeto( Musica.lista, id);
	}


	/**
	 * Eliminar un producto Musica de la base de datos de músicas registradas. 
	 * @param id Id de la Musica a eliminar
	 * @param destruir True si se desea eliminar la información del producto en las
	 * tiendas sin importar su stock. False para eliminar la información en las tiendas
	 * sólo en el caso de tener stock cero.
	 * @throws ObjetoNoEncontradoExcepcion Si no hay ninguna Musica con el id.
	 * @return True si se ha eliminado el registro. False si no se ha podido eliminar debido
	 * a la existencia de datos de este producto en las tiendas que no se han podido eliminar.
	 */
	public static boolean eliminar( Integer id, boolean destruir) throws ObjetoNoEncontradoExcepcion
	{
		Musica musica = Musica.buscar( id);
		if (musica == null)
			throw new ObjetoNoEncontradoExcepcion( "La musica con id " + id + " no existe");

		// Se elimina la información del producto en las tiendas.
		ArrayList<Tienda> tiendas = Tienda.obtenerTiendas();
		Iterator<Tienda> iterator = tiendas.iterator();
		
		boolean eliminado = true;  // Flag para saber si se ha eliminado toda la información del Cliente en las tiendas. 
		while (iterator.hasNext())
			 if ((iterator.next().eliminarMusica( id, destruir) == false) && eliminado)
				 eliminado = false;
		
		if (eliminado)
			Musica.lista.remove( musica);
		
		return eliminado;
	}
	
	
	/**
	 * Sobrecarga de método equals para comparar objetos Musica
	 * @param musica Musica a comparar
	 * @return Si el objeto es de tipo Musica devuelve true si coincide el codigo,
	 * sino devuelve false; Si el objeto no es de tipo Musica, devuelve directamente
	 * el resultado de la comparación de las referencias a los objetos
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
	public Integer obtenerId()
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
