package comercio;
import java.lang.Exception;

/**
 * Excepción que informa de la inexistencia de un cliente en el registro.
 * un pago.
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class ClienteNoEncontradoExcepcion extends Exception 
{
	private static final long serialVersionUID = 1L;

	public ClienteNoEncontradoExcepcion( String mensaje)
	{
		super( mensaje);
	}
}
