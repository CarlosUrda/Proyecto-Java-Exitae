/**
 * 
 */
package comercio;
import java.lang.Exception;

/**
 * Excepción que informa de la inexistencia de un objeto Musica.
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class MusicaNoEncontradaExcepcion extends Exception 
{
	private static final long serialVersionUID = 1L;

	public MusicaNoEncontradaExcepcion( String mensaje)
	{
		super( mensaje);
	}
}
