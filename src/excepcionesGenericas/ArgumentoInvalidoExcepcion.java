/**
 * 
 */
package excepcionesGenericas;

import java.lang.Exception;

/**
 * Excepción que informa sobre la invalidez de un parámetro pasado a un método.
 * un pago.
 * @author Carlos A. Gómez Urda
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
