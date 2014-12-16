/**
 * 
 */
package excepcionesGenericas;

import java.lang.Exception;

/**
 * Excepci�n que informa sobre la invalidez de un par�metro pasado a un m�todo.
 * un pago.
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public class ArgumentoInvalidoExcepcion extends Exception 
{
	private static final long serialVersionUID = 1L;

	public ArgumentoInvalidoExcepcion( String mensaje)
	{
		super( mensaje);
	}
}
