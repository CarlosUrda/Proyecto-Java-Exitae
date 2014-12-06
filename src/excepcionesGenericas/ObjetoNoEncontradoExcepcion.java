package excepcionesGenericas;
import java.lang.Exception;

/**
 * Excepci�n que informa si un objeto no est� registrado en la base de datos.
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public class ObjetoNoEncontradoExcepcion extends Exception 
{
	private static final long serialVersionUID = 1L;

	public ObjetoNoEncontradoExcepcion( String mensaje)
	{
		super( mensaje);
	}
}
