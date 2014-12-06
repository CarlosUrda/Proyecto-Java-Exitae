/**
 * 
 */
package comercio;

import excepcionesGenericas.*;

/**
 * Clase que representa un objeto CD
 * @author Carlos A. Gómez Urda
 * @version 1.0
 *
 */
public class CD extends Musica 
{

	private static final long serialVersionUID = 1L;

	/**
	 * Registrar un producto CD en la base de datos de músicas en memoria
	 * @param nombre Nombre del CD.
	 * @param codigo Código del CD.|
	 * @param precio Precio base del CD.
	 * @throws ObjetoExisteExcepcion Si ya existe un CD con estos datos
	 */
	public static void registrar( String nombre, String codigo, float precio) throws ObjetoExisteExcepcion
	{
		// Se comprueba si el cliente ya existe
		CD cd = new CD( nombre, codigo, precio);
		if (Musica.lista.contains( cd))
			throw new ObjetoExisteExcepcion( "Ya existe un CD con estos datos."); 
		
		Musica.lista.add( cd);
	}
	
	
	/**
	 * Constructor
	 * @param nombre
	 * @param codigo
	 * @param precio
	 */
	public CD( String nombre, String codigo, float precio) 
	{
		super( nombre, codigo, precio);
	}

}
