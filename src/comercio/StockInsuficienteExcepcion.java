/**
 * 
 */
package comercio;
import java.lang.Exception;

/**
 * Excepción que informa de la carencia de un determinado producto en la Tienda.
 * un pago.
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class StockInsuficienteExcepcion extends Exception 
{
	public StockInsuficienteExcepcion( String mensaje)
	{
		super( mensaje);
	}
}
