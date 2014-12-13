package excepcionesGenericas;
import java.lang.Exception;

/**
 * Excepci�n que informa si un objeto ya est� registrado en la base de datos.
 * @author Carlos A. G�mez Urda
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
