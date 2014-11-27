/**
 * 
 */
package comercio;
import java.lang.Exception;

/**
 * Excepci�n que informa de la carencia de liquidez en la cuenta de cliente para realizar
 * un pago.
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public class SaldoInsuficienteExcepcion extends Exception 
{
	private static final long serialVersionUID = 1L;

	public SaldoInsuficienteExcepcion( String mensaje)
	{
		super( mensaje);
	}
}
