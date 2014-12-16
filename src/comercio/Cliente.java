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
 * Clase Cliente que representa un cliente registrado en la cadena de tiendas.
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class Cliente implements Serializable, ObjetoBD 
{
	private static final long serialVersionUID = 1L;
	private static Integer idGeneral = 1;
	private static ArrayList<Cliente> lista;	// Lista de objetos Cliente cargados en memoria desde la base de datos.
	
	private Integer id;			  // Id del cliente en la base de datos.
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
		int sizeLista = Cliente.lista.size();
		Cliente.idGeneral = (sizeLista == 0) ? 1 : (Cliente.lista.get( sizeLista-1).id + 1);
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
	 * @return Cliente registrado.
	 * @throws ObjetoExisteExcepcion Si ya existe un Cliente con estos datos
	 */
	public static Cliente registrar( String nombre, String dni) throws ObjetoExisteExcepcion
	{
		// Se comprueba si el cliente ya existe
		Cliente cliente = new Cliente( nombre, dni);
		if (Cliente.lista.contains( cliente))
			throw new ObjetoExisteExcepcion( "Ya existe un cliente con estos datos."); 
		
		Cliente.lista.add( cliente);
		return cliente;
	}
	
	
	/**
	 * Eliminar un registro de Cliente de la base de datos. 
	 * @param id Id del Cliente a eliminar
	 * @param destruir True si se desea eliminar las cuentas del cliente en las
	 * tiendas sin importar su saldo y si está bloqueado. False para eliminar la 
	 * cuenta de Cliente en las tiendas sólo en el caso de tener saldo cero y no 
	 * estar bloqueado.
	 * @throws ObjetoNoEncontradoExcepcion Si no hay ninguna Musica con el id.
	 * @return True si se ha eliminado el registro. False si no se ha podido eliminar
	 * debido a la existencia de datos de este cliente en las tiendas que no se han
	 * podido eliminar.
	 */
	public static boolean eliminar( Integer id, boolean destruir) throws ObjetoNoEncontradoExcepcion
	{
		Cliente cliente = Cliente.buscar( id);
		if (cliente == null)
			throw new ObjetoNoEncontradoExcepcion( "El cliente con id " + id + " no existe");

		// Se elimina la información del producto en las tiendas.
		ArrayList<Tienda> tiendas = Tienda.obtenerTiendas();
		Iterator<Tienda> iterator = tiendas.iterator();
		
		boolean eliminado = true;  // Flag para saber si se ha eliminado toda la información del Cliente en las tiendas. 
		while (iterator.hasNext())
			 if ((iterator.next().eliminarCuentaCliente( id, destruir) == false) && eliminado)
				 eliminado = false;
		
		if (eliminado)
			Cliente.lista.remove( cliente);
		
		return eliminado;
	}
	
	
	/**
	 * Mostrar por pantalla los datos del Cliente
	 */
	public final void mostrarDatos()
	{
		System.out.println( "Id: " + this.id + "; Nombre: " + this.nombre + "; DNI: " + this.dni);
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
			return this.dni.equalsIgnoreCase( ((Cliente)cliente).getDni());
		
		return this == cliente;
	}


	/**
	 * @return the nombre
	 */
	public final String getNombre() {
		return nombre;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * @return the dni
	 */
	public final String getDni() {
		return dni;
	}


	/**
	 * @param dni the dni to set
	 */
	public final void setDni(String dni) {
		this.dni = dni;
	}


	/**
	 * @return the id
	 */
	public final Integer getId() {
		return id;
	}
	
	
}
