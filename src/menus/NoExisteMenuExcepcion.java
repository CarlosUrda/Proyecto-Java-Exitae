/**
 * 
 */
package menus;
import java.lang.Exception;

/**
 * Excepción que indica si una opción del sistema de menús no tiene menú a desplegar.
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class NoExisteMenuExcepcion extends Exception
{
	public NoExisteMenuExcepcion( String mensaje)
	{
		super( mensaje);
	}

}
