/**
 * 
 */
package comercio;
import java.lang.Exception;

/**
 * Excepci�n que informa de la carencia de un determinado producto en la Tienda.
 * un pago.
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public class StockInsuficienteExcepcion extends Exception 
{
	private static final long serialVersionUID = 1L;

	public StockInsuficienteExcepcion( String mensaje)
	{
		super( mensaje);
	}
}
