/**
 * 
 */
package comercio;

import excepcionesGenericas.*;

/**
 * Clase que representa un objeto Casete
 * @author Carlos A. G�mez Urda
 * @version 1.0
 *
 */
public class Casete extends Musica 
{

	private static final long serialVersionUID = 1L;

	/**
	 * Registrar un producto Casete en la base de datos de m�sicas en memoria
	 * @param nombre Nombre del Casete.
	 * @param codigo C�digo del Casete.|
	 * @param precio Precio base del Casete.
	 * @throws ObjetoExisteExcepcion Si ya existe un Casete con estos datos
	 */
	public static Casete registrar( String nombre, String codigo, float precio) throws ObjetoExisteExcepcion
	{
		// Se comprueba si el cliente ya existe
		Casete casete = new Casete( nombre, codigo, precio);
		if (Musica.lista.contains( casete))
			throw new ObjetoExisteExcepcion( "Ya existe un Casete con estos datos."); 
		
		Musica.lista.add( casete);
		return casete;
	}
	
	
	/**
	 * Constructor
	 * @param nombre
	 * @param codigo
	 * @param precio
	 */
	public Casete( String nombre, String codigo, float precio) 
	{
		super( nombre, codigo, precio);
	}


	/**
	 * Obtener el nombre del tipo de producto de m�sica
	 * @return El nombre del tipo de m�sica: "Casete"
	 */
	@Override
	public String getNombreTipo() 
	{
		return "Casete";
	}
	
	
	/**
	 * Mostrar por pantalla los datos del Casete
	 */
	@Override
	public final void mostrarDatos()
	{
		System.out.println( "Id: " + this.id + "; Tipo: Casete; Nombre: " + this.nombre + 
							"; C�digo: " + this.codigo + "; Precio base: " + this.precio);
	}


}
