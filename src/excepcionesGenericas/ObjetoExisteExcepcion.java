package excepcionesGenericas;
import java.lang.Exception;

/**
 * Excepción que informa si un objeto ya está registrado en la base de datos.
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class ObjetoExisteExcepcion extends Exception 
{
	private static final long serialVersionUID = 1L;

	public ObjetoExisteExcepcion( String mensaje)
	{
		super( mensaje);
	}
}
