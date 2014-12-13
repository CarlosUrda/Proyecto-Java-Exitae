/**
 * 
 */
package comercio;

import excepcionesGenericas.*;

/**
 * Clase que representa un objeto CD
 * @author Carlos A. G�mez Urda
 * @version 1.0
 *
 */
public class CD extends Musica 
{

	private static final long serialVersionUID = 1L;

	/**
	 * Registrar un producto CD en la base de datos de m�sicas en memoria
	 * @param nombre Nombre del CD.
	 * @param codigo C�digo del CD.|
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


	/**
	 * Obtener el nombre del tipo de producto de m�sica
	 * @return El nombre del tipo de m�sica: "CD"
	 */
	@Override
	public String getNombreTipo() {
		return "CD";
	}

	
	/**
	 * Mostrar por pantalla los datos del CD
	 */
	@Override
	public final void mostrarDatos()
	{
		System.out.println( "Id: " + this.id + "; Tipo: CD; Nombre: " + this.nombre + "; C�digo: " + 
							this.codigo + "; Precio base: " + this.precio);
	}

	
}
