/**
 * Paquete para gestionar un sistema de men�s de cualquier programa
 */
package menus;
import java.util.ArrayList;
import java.util.Iterator;

/** 
 * Clase para gestionar el sistema de men�s de un programa
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public class SistemaMenus 
{
	private SistemaMenus.Opcion opcionCabeza;    // Opcion que representa la cabecera del sistema de men�s 
	private SistemaMenus.Opcion opcionActual;    // Opcion en la que nos encontramos actualmente dentro del sistema de men�s.
	
	public SistemaMenus( String nombre, String cabecera)
	{
		this.opcionCabeza = new SistemaMenus.Opcion( nombre, cabecera, "", null);
		this.opcionActual = this.opcionCabeza;
	}
	
	
	/**
	 * Agregar los datos del men� dentro de la opci�n actual donde nos encontremos.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opci�n del submen�.
	 * @return Ninguno.
	 */
	public void agregarMenu( String datosMenu[][])
	{
		this.opcionActual.agregarOpcionesHijas( datosMenu);
	}
	
	
	/**
	 * Agregar los datos del submen� dentro de una de las opciones del men� de la opci�n 
	 * actual donde nos encontremos.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opci�n del submen�.
	 * @param indiceLocal Posici�n de la opci�n donde se agregar� el submen�.
	 * @return Ninguno.
	 * @throws NoExistemenuHijoExcepcion Si el �ndice de la opci�n donde agregar el nuevo
	 * submen� no existe dentro de las opciones del submen� de la opci�n actual.
	 */
	public void agregarMenu( String datosMenu[][], int indiceLocal) throws NoExisteMenuExcepcion
	{
		this.opcionActual.obtenerOpcionHija( indiceLocal).agregarOpcionesHijas( datosMenu);
	}


	/**
	 * Agregar los datos del submen� dentro de una de las opciones de todo el sistema de men�s.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opci�n del submen�.
	 * @param indiceGlobal Array de indices que indican la posici�n, dentro del sistema de men�s, 
	 * de la opci�n donde se agregar� el men�. Si null, se agrega el submen� a la cabecera
	 * @throws NoExisteMenuExcepcion Si alg�n �ndice del array indiceGlobal no existe dentro
	 * de las opciones del submen� de la opci�n correspondiente.
	 */
	public void agregarMenu( String datosMenu[][], int indiceGlobal[]) throws NoExisteMenuExcepcion
	{
		SistemaMenus.Opcion opcion = this.opcionCabeza;
		
		// Se obtiene la opci�n de indiceGlobal
		if (indiceGlobal != null)
			for (int i = 0; i < indiceGlobal.length; i++)
				opcion = opcion.obtenerOpcionHija( indiceGlobal[i]);
		
		opcion.agregarOpcionesHijas( datosMenu);
	}

	
	/**
	 * Asigna a la opcion activa el men� asignado a otra opci�n indiceGlobal en el 
	 * sistema de men�s 
	 * @param indiceGlobal �ndice en el sistema de men�s de la opci�n que contiene 
	 * el men� que se asociar� a la opci�n actual.
	 * @throws NoExisteMenuExcepcion Si alg�n �ndice del array indiceGlobal no existe dentro
	 * de las opciones del submen� de la opci�n correspondiente.
	 */
	public void asociarMenu( int indiceGlobal[]) throws NoExisteMenuExcepcion
	{		
		SistemaMenus.Opcion opcion = this.opcionCabeza;
		
		// Se obtiene la opci�n de indiceGlobal
		if (indiceGlobal != null)
			for (int i = 0; i < indiceGlobal.length; i++)
				opcion = opcion.obtenerOpcionHija( indiceGlobal[i]);
		
		this.opcionActual.cambiarOpcionesHijas( opcion.obtenerOpcionesHijas());
	}
	
	
	/**
	 * Asigna a la opcion activa actualmente el men� asignado a otra opci�n indiceGlobal 
	 * en el sistema de men�s 
	 * @param indiceLocal �ndice de la opci�n del men� de la opci�n actual donde se
	 * asociar� el men� de la opci�n cuyo �ndice global es indiceGlobal 
	 * @param indiceGlobal �ndice en el sistema de men�s de la opci�n que contiene 
	 * el men� que se asociar� a la opci�n con indiceLocal dentro del men� de la opci�n actual.
	 * @throws NoExisteMenuExcepcion Si alg�n �ndice del array indiceGlobal no existe dentro
	 * de las opciones del submen� de la opci�n correspondiente.
	 */
	public void asociarMenu( int indiceLocal, int indiceGlobal[]) throws NoExisteMenuExcepcion
	{		
		SistemaMenus.Opcion opcion = this.opcionCabeza;
		
		// Se obtiene la opci�n de indiceGlobal
		if (indiceGlobal != null)
			for (int i = 0; i < indiceGlobal.length; i++)
				opcion = opcion.obtenerOpcionHija( indiceGlobal[i]);
		
		this.opcionActual.obtenerOpcionHija( indiceLocal).cambiarOpcionesHijas( opcion.obtenerOpcionesHijas());
	}

	
	/** 
	 * M�todo para agregar una opci�n en un �ndice del men� de la opci�n actual
	 * @param nombre Nombre identificativo de la subopci�n a agregar.
	 * @param cabecera Cabecera del submen� que se accede a trav�s de la subopci�n a agregar.
	 * @param texto Texto que representa la subopci�n a agregar. Este texto aparece en el men�
	 * de la opci�n actual
	 * @param indice Posici�n o �ndice dentro del submen� de la opci�n actual donde situar la nueva subopci�n.
	 * @return Ninguno
	 * @throws IndexOutOfBoundsException Si el �ndice donde agregar la subopci�n esta fuera del rango 
	 * de opciones de ese submen�.
	 */
	public void agregarOpcion( String nombre, String cabecera, String texto, int indice)
	{
		this.opcionActual.agregarOpcionHija(nombre, cabecera, texto, indice);
	}


	/** 
	 * M�todo para agregar una opci�n al final de las opciones del men� de la opci�n actual
	 * @param nombre Nombre identificativo de la subopci�n a agregar.
	 * @param cabecera Cabecera del submen� que se accede a trav�s de la subopci�n a agregar.
	 * @param texto Texto que representa la subopci�n a agregar. Este texto aparece en el men�
	 * de la opci�n actual
	 * @return Ninguno
	 */
	public void agregarOpcion( String nombre, String cabecera, String texto)
	{
		this.opcionActual.agregarOpcionHija(nombre, cabecera, texto);
	}
	
	
	/**
	 * Mostrar por pantalla el men� de la opci�n donde nos encontremos actualmente
	 */
	public void mostrarMenu()
	{
		this.opcionActual.mostrarOpcionesHijas();
	}
	
	
	/**
	 * Avanzar por el sistema de men�s entrando en una opci�n dentro de las opciones 
	 * posibles del submen� de la opci�n actual.
	 * @param indice N�mero de opci�n a elegir para avanzar.
	 * @throws NoExisteMenuExcepcion Si el �ndice de la opci�n a avanzar no existe dentro
	 * de las opciones del submen� de la opci�n actual.
	 */
	public void avanzar( int indice) throws NoExisteMenuExcepcion
	{
		this.opcionActual = this.opcionActual.obtenerOpcionHija( indice);
	}
	
	
	/**
	 * Retrocede por el sistema del men�s a la opci�n padre de la opci�n en la que estamos actualmente. 
	 * posibles del submen� de la opci�n actual.
	 * @throws NoExisteMenuExcepcion Si no existe una opci�n padre porque ya se encuentra en la
	 * cabecera del sistema de men�s.
	 */
	public void retroceder() throws NoExisteMenuExcepcion
	{
		SistemaMenus.Opcion opcionPadre = this.opcionActual.obtenerOpcionPadre();
		if (opcionPadre == null)
			throw new NoExisteMenuExcepcion( "Ya te encuentras en la cabecera del sistema de men�s");
		
		this.opcionActual = opcionPadre;
	}

		
	/**
	 * Devuelve el nombre de la opci�n actual en la que nos encontramos
	 * @return Nombre de la opci�n actual en la que nos encontramos del sistema de men�s
	 */
	public String obtenerNombre()
	{
		return opcionActual.obtenerNombre();
	}
	
	
	/**
	 * Clase que representa cada opci�n de un men�
	 * @author Carlos A. G�mez Urda
	 * @version 1.0
	 */
	private class Opcion 
	{
		private String nombre;     				  		// Nombre identificativo de la opci�n de men�
		private String cabecera;   				  		// Cabecera del submen� que representa esta opci�n
		private String texto;    				  		// Texto explicativo de la opci�n
		private ArrayList<SistemaMenus.Opcion> opcionesHijas;   // Array de opciones que forman el men� de esta opci�n
		private SistemaMenus.Opcion opcionPadre;          		// Opcion en cuyo men� est� la opci�n en la que nos encontramos actualmente
		
		public Opcion( String nombre, String cabecera, String texto, SistemaMenus.Opcion opcionPadre)
		{
			this.nombre = nombre;
			this.cabecera = cabecera;
			this.texto = texto;
			this.opcionPadre = opcionPadre;
			this.opcionesHijas = null;
		}
		
		/**
		 * Devuelve el nombre de la opci�n.
		 * @return Nombre de la opci�n.
		 */
		public String obtenerNombre()
		{
			return this.nombre;
		}
	
		
		/**
		 * Modificar la cabecera del men� de esta opci�n
		 * @param cabecera
		 * @return Ninguno
		 */
		public void cambiarCabecera( String cabecera)
		{
			this.cabecera = cabecera;
		}
		
		
		/** 
		 * M�todo para agregar una opci�n en un �ndice del men� de la opci�n actual
		 * @param nombre Nombre identificativo de la subopci�n a agregar.
		 * @param cabecera Cabecera del submen� que se accede a trav�s de la subopci�n a agregar.
		 * @param texto Texto que representa la subopci�n a agregar. Este texto aparece en el men�
		 * de la opci�n actual
		 * @param indice Posici�n o �ndice dentro del submen� de la opci�n actual donde situar la nueva subopci�n.
		 * @return Ninguno
		 * @throws IndexOutOfBoundsException Si el �ndice donde agregar la subopci�n esta fuera del rango 
		 * de opciones de ese submen�.
		 */
		public void agregarOpcionHija( String nombre, String cabecera, String texto, int indice) 
			   throws IndexOutOfBoundsException
		{
			SistemaMenus.Opcion opcionHija = SistemaMenus.this.new Opcion( nombre, cabecera, texto, this);
			if (this.opcionesHijas == null)
				this.opcionesHijas = new ArrayList<SistemaMenus.Opcion>();
			
			try
			{
				this.opcionesHijas.add( indice, opcionHija);
			}
			catch (IndexOutOfBoundsException excepcionIndice)
			{
				if (this.opcionesHijas.isEmpty())
					this.opcionesHijas = null;
				throw excepcionIndice;
			}
		}

	
		/** 
		 * M�todo para agregar una opci�n al final de las opciones del men� de la opci�n actual
		 * @param nombre Nombre identificativo de la subopci�n a agregar.
		 * @param cabecera Cabecera del submen� que se accede a trav�s de la subopci�n a agregar.
		 * @param texto Texto que representa la subopci�n a agregar. Este texto aparece en el men�
		 * de la opci�n actual
		 * @return Ninguno
		 */
		public void agregarOpcionHija( String nombre, String cabecera, String texto)
		{
			SistemaMenus.Opcion opcionHija = SistemaMenus.this.new Opcion( nombre, cabecera, texto, this);
			if (this.opcionesHijas == null)
				this.opcionesHijas = new ArrayList<SistemaMenus.Opcion>();
			this.opcionesHijas.add( opcionHija);
		}
		
		
		/**
		 * Agregar los datos de todas las opciones del men� que surge a partir de la opci�n actual.
		 * Las nuevas opciones se agregan a las ya existentes en el men�.
		 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
		 * es un array con los elementos nombre, cabecera y texto de cada opci�n del submen�.
		 */
		public void agregarOpcionesHijas( String datosMenu[][])
		{
			for (int i = 0; i < datosMenu.length; i++)
				this.agregarOpcionHija( datosMenu[i][0], datosMenu[i][1], datosMenu[i][2]);
		}
		
		
		/**
		 * Cambiar la lista de opciones hijas de la opci�n actual
		 * @param opcionesHijas Nueva lista de opciones a asignar como opciones hijas.
		 */
		public void cambiarOpcionesHijas( ArrayList<SistemaMenus.Opcion> opcionesHijas)
		{
			this.opcionesHijas = opcionesHijas;
		}
		
		
		/**
		 * Obtener la opci�n de posici�n �ndice en el men� de la opci�n actual.
		 * @param indice Posici�n, dentro del submen� de la opci�n actual, de la opci�n a obtener.
		 * @return La Opcion encontrada en la posici�n �ndice dentro del submen� de la opci�n actual.
		 * @throws IndexOutOfBoundsException Si existe un submen� pero el �ndice no existe dentro de 
		 * las opciones de ese submen�
		 * @throws NoExisteMenuExcepcion Si esta opci�n no tiene ning�n men� hijo
		 */
		public SistemaMenus.Opcion obtenerOpcionHija( int indice) throws NoExisteMenuExcepcion
		{
			if (this.opcionesHijas == null)
				throw new NoExisteMenuExcepcion( "Esta opci�n no tiene ning�n submen� configurado");
			return this.opcionesHijas.get( indice);
		}

		
		/**
		 * Obtener la lista de opciones hijas de esta opci�n
		 * @return Lista ArrayList de Opcion hijas.
		 */
		public ArrayList<SistemaMenus.Opcion> obtenerOpcionesHijas()
		{
			return this.opcionesHijas;
		}
	
		
		/**
		 * Obtener la opci�n padre cuyo men� contiene la opci�n actual.
		 * @return La Opcion padre de esta opci�n.
		 */
		public SistemaMenus.Opcion obtenerOpcionPadre()
		{
			return this.opcionPadre;
		}

		
		/**
		 * Mostrar por pantalla las opciones del men� de esta opci�n.
		 */
		public void mostrarOpcionesHijas()
		{
			System.out.println( this.cabecera);
			
			Iterator<SistemaMenus.Opcion> opcionesIterator = this.opcionesHijas.iterator(); 
			for (int i = 1; opcionesIterator.hasNext(); i++)
			{
				System.out.println( i + ".-" + opcionesIterator.next().texto);
			}
		}		
	}
}
