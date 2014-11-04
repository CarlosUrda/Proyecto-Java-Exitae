/**
 * 
 */
package menus;
import java.lang.Exception;

/**
 * Excepci�n que indica si una opci�n del sistema de men�s no tiene men� a desplegar.
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public class NoExisteMenuExcepcion extends Exception
{
	public NoExisteMenuExcepcion( String mensaje)
	{
		super( mensaje);
	}

}
