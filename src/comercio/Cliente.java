/**
 * 
 */
package comercio;

import excepcionesGenericas.ObjetoExisteExcepcion;
import gestionBD.BD;
import gestionBD.ObjetoBD;

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
 * Clase Cliente que representa un cliente registrado en la cadena de tiendas.
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class Cliente implements Serializable, ObjetoBD 
{
	private static final long serialVersionUID = 1L;
	private static int idGeneral = 1;
	private static ArrayList<Cliente> lista;	// Lista de objetos Cliente cargados en memoria desde la base de datos.
	
	private int id;			  // Id del cliente en la base de datos.
	private String nombre;	  // Nombre del cliente
	private String dni; 	  // DNI
	
	
	/**
	 * Constructor
	 * @param nombre Nombre del Cliente
	 * @param dni Dni del Cliente.
	 */
	private Cliente( String nombre, String dni)
	{
		this.nombre = nombre;
		this.dni = dni;
		this.id = Cliente.idGeneral++;
	}


	/**
	 * Función encargada de leer los objetos Cliente grabados en un archivo
	 * y cargarlos en una lista en memoria.
	 * @param nombreArchivo Nombre del archivo de donde leer los objetos Musica
	 * @return Ninguno.
	 * @throws ClassNotFoundException Si no se encuentra ningún objeto Musica
	 * @throws IOException Cualquier otro error en la lectura del archivo
	 * @throws FileNotFoundException Si no se encuentra el archivo con nombreArchivo
	 */
	public static void cargarBD( String nombreArchivo) 
			throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Cliente.lista = BD.leerObjetos( nombreArchivo);
	}
	
	
	/**
	 * Función encargada de escribir los objetos Cliente de memoria a un archivo.
	 * @param nombreArchivo Nombre del archivo de donde escribir los objetos Cliente
	 * @return Ninguno.
	 * @throws IOException cualquier otro error en la lectura del archivo
	 */
	public static void guardarBD( String nombreArchivo) throws IOException
	{
		BD.escribirObjetos( nombreArchivo, Cliente.lista);
	}

	
	/**
	 * Obtener la lista de objetos Cliente cargadas en memoria a partir de los datos grabados
	 * en un archivo.
	 * @return Lista de objetos Cliente cargadas en memoria actualmente
	 */
	public static ArrayList<Cliente> obtenerClientes()
	{
		return Cliente.lista;
	}
	
	
	/**
	 * Buscar un objeto Cliente con un id determinado dentro de la lista de objetos 
	 * cargadas actualmente en memoria a partir del archivo. 
	 * @param id Id del objeto Cliente a buscar
	 * @return Objeto Cliente encontrado. Si no se encuentra devuelve null.
	 */
	public static Cliente buscar( Integer id)
	{
		return BD.buscarObjeto( Cliente.lista, id);			
	}

	
	/**
	 * Registrar un cliente en la base de datos de clientes en memoria
	 * @param nombre Nombre del Cliente.
	 * @param dni Dni del Cliente.
	 * @throws ObjetoExisteExcepcion Si ya existe un Cliente con estos datos
	 */
	public static void registrar( String nombre, String dni) throws ObjetoExisteExcepcion
	{
		// Se comprueba si el cliente ya existe
		Cliente cliente = new Cliente( nombre, dni);
		if (Cliente.lista.contains( cliente))
			throw new ObjetoExisteExcepcion( "Ya existe un cliente con estos datos."); 
		
		Cliente.lista.add( cliente);
	}
	
	
	/**
	 * Sobrecarga de método equals para comparar objetos Cliente
	 * @param cliente Objeto a comparar
	 * @return Si el objeto es de tipo Cliente devuelve true si coincide el dni,
	 * sino devuelve false; Si el objeto no es de tipo Cliente, devuelve directamente
	 * el resultado de la comparación de las referencias a los objetos
	 */
	@Override
	public boolean equals( Object cliente)
	{
		if (cliente instanceof Cliente)
			return this.dni.equalsIgnoreCase( ((Cliente)cliente).obtenerDni());
		
		return this == cliente;
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
	 * Obtener el nombre de un CLiente.
	 * @return Precio base
	 */
	public String obtenerNombre()
	{
		return this.nombre;
	}


	/**
	 * Obtener el DNI de un CLiente.
	 * @return Precio base
	 */
	public String obtenerDni()
	{
		return this.dni;
	}
}
