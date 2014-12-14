/**
 * 
 */
package comercio;

import excepcionesGenericas.*;

/**
 * Clase que representa un objeto Vinilo
 * @author Carlos A. Gómez Urda
 * @version 1.0
 *
 */
public class Vinilo extends Musica 
{

	private static final long serialVersionUID = 1L;

	/**
	 * Registrar un producto Vinilo en la base de datos de músicas en memoria
	 * @param nombre Nombre del Vinilo.
	 * @param codigo Código del Vinilo.|
	 * @param precio Precio base del Vinilo.
	 * @throws ObjetoExisteExcepcion Si ya existe un Vinilo con estos datos
	 */
	public static Vinilo registrar( String nombre, String codigo, float precio) throws ObjetoExisteExcepcion
	{
		// Se comprueba si el cliente ya existe
		Vinilo vinilo = new Vinilo( nombre, codigo, precio);
		if (Musica.lista.contains( vinilo))
			throw new ObjetoExisteExcepcion( "Ya existe un Vinilo con estos datos."); 
		
		Musica.lista.add( vinilo);
		return vinilo;
	}
	
	
	/**
	 * Constructor
	 * @param nombre
	 * @param codigo
	 * @param precio
	 */
	public Vinilo( String nombre, String codigo, float precio) 
	{
		super( nombre, codigo, precio);
	}


	/**
	 * Obtener el nombre del tipo de producto de música
	 * @return El nombre del tipo de música: "Vinilo"
	 */
	@Override
	public String getNombreTipo() 
	{
		return "Vinilo";
	}

	
	/**
	 * Mostrar por pantalla los datos del Vinilo
	 */
	@Override
	public final void mostrarDatos()
	{
		System.out.println( "Id: " + this.id + "; Tipo: Vinilo; Nombre: " + this.nombre + "; Código: " + 
							this.codigo + "; Precio base: " + this.precio);
	}

	
}
