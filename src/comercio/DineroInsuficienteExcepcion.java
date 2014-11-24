/**
 * 
 */
package comercio;
import java.lang.Exception;

/**
 * Excepci�n que informa de la carencia de dinero en la cuenta de cliente para realizar
 * un pago.
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public class DineroInsuficienteExcepcion extends Exception 
{
	public DineroInsuficienteExcepcion( String mensaje)
	{
		super( mensaje);
	}
}
