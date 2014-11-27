/**
 * Paquete para gestionar un sistema de menús de cualquier programa
 */
package menus;
import java.util.ArrayList;
import java.util.Iterator;

/** 
 * Clase para gestionar el sistema de menús de un programa
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class SistemaMenus 
{
	private SistemaMenus.Opcion opcionCabeza;    // Opcion que representa la cabecera del sistema de menús 
	private SistemaMenus.Opcion opcionActual;    // Opcion en la que nos encontramos actualmente dentro del sistema de menús.
	
	public SistemaMenus( String nombre, String cabecera)
	{
		this.opcionCabeza = new SistemaMenus.Opcion( nombre, cabecera, "", null);
		this.opcionActual = this.opcionCabeza;
	}
	
	
	/**	
	 * Agregar los datos del menú dentro de la opción actual donde nos encontremos.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opción del submenú.
	 * @return Ninguno.
	 */
	public void agregarMenu( String datosMenu[][])
	{
		this.opcionActual.agregarOpcionesHijas( datosMenu);
	}
	
	
	/**
	 * Agregar los datos del submenú dentro de una de las opciones del menú de la opción 
	 * actual donde nos encontremos.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opción del submenú.
	 * @param indiceLocal Posición de la opción donde se agregará el submenú.
	 * @return Ninguno.
	 * @throws NoExistemenuHijoExcepcion Si el índice de la opción donde agregar el nuevo
	 * submenú no existe dentro de las opciones del submenú de la opción actual.
	 */
	public void agregarMenu( String datosMenu[][], int indiceLocal) throws NoExisteMenuExcepcion
	{
		this.opcionActual.obtenerOpcionHija( indiceLocal).agregarOpcionesHijas( datosMenu);
	}


	/**
	 * Agregar los datos del submenú dentro de una de las opciones de todo el sistema de menús.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opción del submenú.
	 * @param indiceGlobal Array de indices que indican la posición, dentro del sistema de menús, 
	 * de la opción donde se agregará el menú. Si null, se agrega el submenú a la cabecera
	 * @throws NoExisteMenuExcepcion Si algún índice del array indiceGlobal no existe dentro
	 * de las opciones del submenú de la opción correspondiente.
	 */
	public void agregarMenu( String datosMenu[][], int indiceGlobal[]) throws NoExisteMenuExcepcion
	{
		SistemaMenus.Opcion opcion = this.opcionCabeza;
		
		// Se obtiene la opción de indiceGlobal
		if (indiceGlobal != null)
			for (int i = 0; i < indiceGlobal.length; i++)
				opcion = opcion.obtenerOpcionHija( indiceGlobal[i]);
		
		opcion.agregarOpcionesHijas( datosMenu);
	}

	
	/**
	 * Asigna a la opcion activa el menú asignado a otra opción indiceGlobal en el 
	 * sistema de menús 
	 * @param indiceGlobal Índice en el sistema de menús de la opción que contiene 
	 * el menú que se asociará a la opción actual.
	 * @throws NoExisteMenuExcepcion Si algún índice del array indiceGlobal no existe dentro
	 * de las opciones del submenú de la opción correspondiente.
	 */
	public void asociarMenu( int indiceGlobal[]) throws NoExisteMenuExcepcion
	{		
		SistemaMenus.Opcion opcion = this.opcionCabeza;
		
		// Se obtiene la opción de indiceGlobal
		if (indiceGlobal != null)
			for (int i = 0; i < indiceGlobal.length; i++)
				opcion = opcion.obtenerOpcionHija( indiceGlobal[i]);
		
		this.opcionActual.cambiarOpcionesHijas( opcion.obtenerOpcionesHijas());
	}
	
	
	/**
	 * Asigna a la opcion activa actualmente el menú asignado a otra opción indiceGlobal 
	 * en el sistema de menús 
	 * @param indiceLocal Índice de la opción del menú de la opción actual donde se
	 * asociará el menú de la opción cuyo índice global es indiceGlobal 
	 * @param indiceGlobal Índice en el sistema de menús de la opción que contiene 
	 * el menú que se asociará a la opción con indiceLocal dentro del menú de la opción actual.
	 * @throws NoExisteMenuExcepcion Si algún índice del array indiceGlobal no existe dentro
	 * de las opciones del submenú de la opción correspondiente.
	 */
	public void asociarMenu( int indiceLocal, int indiceGlobal[]) throws NoExisteMenuExcepcion
	{		
		SistemaMenus.Opcion opcion = this.opcionCabeza;
		
		// Se obtiene la opción de indiceGlobal
		if (indiceGlobal != null)
			for (int i = 0; i < indiceGlobal.length; i++)
				opcion = opcion.obtenerOpcionHija( indiceGlobal[i]);
		
		this.opcionActual.obtenerOpcionHija( indiceLocal).cambiarOpcionesHijas( opcion.obtenerOpcionesHijas());
	}

	
	/** 
	 * Método para agregar una opción en un índice del menú de la opción actual
	 * @param nombre Nombre identificativo de la subopción a agregar.
	 * @param cabecera Cabecera del submenú que se accede a través de la subopción a agregar.
	 * @param texto Texto que representa la subopción a agregar. Este texto aparece en el menú
	 * de la opción actual
	 * @param indice Posición o índice dentro del submenú de la opción actual donde situar la nueva subopción.
	 * @return Ninguno
	 * @throws IndexOutOfBoundsException Si el índice donde agregar la subopción esta fuera del rango 
	 * de opciones de ese submenú.
	 */
	public void agregarOpcion( String nombre, String cabecera, String texto, int indice)
	{
		this.opcionActual.agregarOpcionHija(nombre, cabecera, texto, indice);
	}


	/** 
	 * Método para agregar una opción al final de las opciones del menú de la opción actual
	 * @param nombre Nombre identificativo de la subopción a agregar.
	 * @param cabecera Cabecera del submenú que se accede a través de la subopción a agregar.
	 * @param texto Texto que representa la subopción a agregar. Este texto aparece en el menú
	 * de la opción actual
	 * @return Ninguno
	 */
	public void agregarOpcion( String nombre, String cabecera, String texto)
	{
		this.opcionActual.agregarOpcionHija(nombre, cabecera, texto);
	}
	
	
	/**
	 * Mostrar por pantalla el menú de la opción donde nos encontremos actualmente
	 */
	public void mostrarMenu()
	{
		this.opcionActual.mostrarOpcionesHijas();
	}
	
	
	/**
	 * Avanzar por el sistema de menús entrando en una opción dentro de las opciones 
	 * posibles del submenú de la opción actual.
	 * @param indice Número de opción a elegir para avanzar.
	 * @throws NoExisteMenuExcepcion Si el índice de la opción a avanzar no existe dentro
	 * de las opciones del submenú de la opción actual.
	 */
	public void avanzar( int indice) throws NoExisteMenuExcepcion
	{
		this.opcionActual = this.opcionActual.obtenerOpcionHija( indice);
	}
	
	
	/**
	 * Retrocede por el sistema del menús a la opción padre de la opción en la que estamos actualmente. 
	 * posibles del submenú de la opción actual.
	 * @throws NoExisteMenuExcepcion Si no existe una opción padre porque ya se encuentra en la
	 * cabecera del sistema de menús.
	 */
	public void retroceder() throws NoExisteMenuExcepcion
	{
		SistemaMenus.Opcion opcionPadre = this.opcionActual.obtenerOpcionPadre();
		if (opcionPadre == null)
			throw new NoExisteMenuExcepcion( "Ya te encuentras en la cabecera del sistema de menús");
		
		this.opcionActual = opcionPadre;
	}

		
	/**
	 * Devuelve el nombre de la opción actual en la que nos encontramos
	 * @return Nombre de la opción actual en la que nos encontramos del sistema de menús
	 */
	public String obtenerNombre()
	{
		return opcionActual.obtenerNombre();
	}
	
	
	/**
	 * Clase que representa cada opción de un menú
	 * @author Carlos A. Gómez Urda
	 * @version 1.0
	 */
	private class Opcion 
	{
		private String nombre;     				  		// Nombre identificativo de la opción de menú
		private String cabecera;   				  		// Cabecera del submenú que representa esta opción
		private String texto;    				  		// Texto explicativo de la opción
		private ArrayList<SistemaMenus.Opcion> opcionesHijas;   // Array de opciones que forman el menú de esta opción
		private SistemaMenus.Opcion opcionPadre;          		// Opcion en cuyo menú está la opción en la que nos encontramos actualmente
		
		public Opcion( String nombre, String cabecera, String texto, SistemaMenus.Opcion opcionPadre)
		{
			this.nombre = nombre;
			this.cabecera = cabecera;
			this.texto = texto;
			this.opcionPadre = opcionPadre;
			this.opcionesHijas = null;
		}
		
		/**
		 * Devuelve el nombre de la opción.
		 * @return Nombre de la opción.
		 */
		public String obtenerNombre()
		{
			return this.nombre;
		}
	
		
		/**
		 * Modificar la cabecera del menú de esta opción
		 * @param cabecera
		 * @return Ninguno
		 */
		public void cambiarCabecera( String cabecera)
		{
			this.cabecera = cabecera;
		}
		
		
		/** 
		 * Método para agregar una opción en un índice del menú de la opción actual
		 * @param nombre Nombre identificativo de la subopción a agregar.
		 * @param cabecera Cabecera del submenú que se accede a través de la subopción a agregar.
		 * @param texto Texto que representa la subopción a agregar. Este texto aparece en el menú
		 * de la opción actual
		 * @param indice Posición o índice dentro del submenú de la opción actual donde situar la nueva subopción.
		 * @return Ninguno
		 * @throws IndexOutOfBoundsException Si el índice donde agregar la subopción esta fuera del rango 
		 * de opciones de ese submenú.
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
		 * Método para agregar una opción al final de las opciones del menú de la opción actual
		 * @param nombre Nombre identificativo de la subopción a agregar.
		 * @param cabecera Cabecera del submenú que se accede a través de la subopción a agregar.
		 * @param texto Texto que representa la subopción a agregar. Este texto aparece en el menú
		 * de la opción actual
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
		 * Agregar los datos de todas las opciones del menú que surge a partir de la opción actual.
		 * Las nuevas opciones se agregan a las ya existentes en el menú.
		 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
		 * es un array con los elementos nombre, cabecera y texto de cada opción del submenú.
		 */
		public void agregarOpcionesHijas( String datosMenu[][])
		{
			for (int i = 0; i < datosMenu.length; i++)
				this.agregarOpcionHija( datosMenu[i][0], datosMenu[i][1], datosMenu[i][2]);
		}
		
		
		/**
		 * Cambiar la lista de opciones hijas de la opción actual
		 * @param opcionesHijas Nueva lista de opciones a asignar como opciones hijas.
		 */
		public void cambiarOpcionesHijas( ArrayList<SistemaMenus.Opcion> opcionesHijas)
		{
			this.opcionesHijas = opcionesHijas;
		}
		
		
		/**
		 * Obtener la opción de posición índice en el menú de la opción actual.
		 * @param indice Posición, dentro del submenú de la opción actual, de la opción a obtener.
		 * @return La Opcion encontrada en la posición índice dentro del submenú de la opción actual.
		 * @throws IndexOutOfBoundsException Si existe un submenú pero el índice no existe dentro de 
		 * las opciones de ese submenú
		 * @throws NoExisteMenuExcepcion Si esta opción no tiene ningún menú hijo
		 */
		public SistemaMenus.Opcion obtenerOpcionHija( int indice) throws NoExisteMenuExcepcion
		{
			if (this.opcionesHijas == null)
				throw new NoExisteMenuExcepcion( "Esta opción no tiene ningún submenú configurado");
			return this.opcionesHijas.get( indice);
		}

		
		/**
		 * Obtener la lista de opciones hijas de esta opción
		 * @return Lista ArrayList de Opcion hijas.
		 */
		public ArrayList<SistemaMenus.Opcion> obtenerOpcionesHijas()
		{
			return this.opcionesHijas;
		}
	
		
		/**
		 * Obtener la opción padre cuyo menú contiene la opción actual.
		 * @return La Opcion padre de esta opción.
		 */
		public SistemaMenus.Opcion obtenerOpcionPadre()
		{
			return this.opcionPadre;
		}

		
		/**
		 * Mostrar por pantalla las opciones del menú de esta opción.
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
