/**
 * Paquete para gestionar un sistema de men�s de cualquier programa
 */
package menus;
import java.util.ArrayList;
import java.util.Iterator;

import gestionBD.*;

/** 
 * Clase para gestionar el sistema de men�s de un programa
 * @author Carlos A. G�mez Urda
 * @version 1.0
 */
public class SistemaMenus 
{
	private SistemaMenus.Opcion opcionCabeza;    // Opcion que representa la cabecera del sistema de men�s 
	private SistemaMenus.Opcion opcionActual;    // Opcion en la que nos encontramos actualmente dentro del sistema de men�s.
	
	/**
	 * Constructor
	 * @param nombre Nombre identificativo del men� principal.
	 * @param cabecera Cabecera del sistema de men�s
	 */
	public SistemaMenus( String nombre, String cabecera)
	{
		this.opcionCabeza = new SistemaMenus.Opcion( nombre, cabecera, "", null);
		this.opcionActual = this.opcionCabeza;
	}
	
	
	/**	
	 * Agregar los datos del men� dentro de la opci�n actual donde nos encontremos.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opci�n del submen�.
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
	 * @throws IndexOutOfBoundsException Si no existe una opci�n, dentro del sistema de men�s,
	 * con los �ndices pasados.
	 */
	public void agregarMenu( String datosMenu[][], int indiceLocal)
	{
		this.opcionActual.obtenerOpcionHija( indiceLocal).agregarOpcionesHijas( datosMenu);
	}


	/**
	 * Agregar los datos del submen� dentro de una de las opciones del men� de la opci�n 
	 * actual donde nos encontremos.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opci�n del submen�.
	 * @param nombre Nombre de la opci�n local donde agregar el men�.
	 * @throws NoExisteMenuExcepcion Si no existe con ese nombre la opci�n local en el 
	 * submen� actual.
	 */
	public void agregarMenu( String datosMenu[][], String nombre) throws NoExisteMenuExcepcion
	{
		Opcion opcionHija = this.opcionActual.obtenerOpcionHija( nombre);
		if (opcionHija == null)
			throw new NoExisteMenuExcepcion( "No existe la opci�n local con el nombre" + nombre);
		
		opcionHija.agregarOpcionesHijas( datosMenu);
	}

	
	/**
	 * Agregar los datos del submen� dentro de una de las opciones de todo el sistema de men�s.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opci�n del submen�.
	 * @param indiceGlobal Array de indices que indican la posici�n, dentro del sistema de men�s, 
	 * de la opci�n donde se agregar� el men�. Si null, se agrega el submen� a la cabecera
	 * @throws IndexOutOfBoundsException Si no existe una opci�n, dentro del sistema de men�s,
	 * con los �ndices pasados.
	 */
	public void agregarMenu( String datosMenu[][], int indiceGlobal[])
	{
		this.obtenerOpcion( indiceGlobal).agregarOpcionesHijas( datosMenu);
	}

	
	/**
	 * Asigna a la opcion activa el men� asignado a otra opci�n indiceGlobal en el 
	 * sistema de men�s 
	 * @param indiceGlobal �ndice en el sistema de men�s de la opci�n que contiene 
	 * el men� que se asociar� a la opci�n actual.
	 * @throws IndexOutOfBoundsException Si no existe una opci�n, dentro del sistema de men�s,
	 * con los �ndices pasados.
	 */
	public void asociarMenu( int indiceGlobal[])
	{		
		this.opcionActual.cambiarOpcionesHijas( this.obtenerOpcion( indiceGlobal).obtenerOpcionesHijas());
	}
	
	
	/**
	 * Asigna a la opcion indiceLocal dentro de la opci�n actual el men� asignado a otra opci�n 
	 * indiceGlobal en el sistema de men�s 
	 * @param indiceLocal �ndice de la opci�n del men� de la opci�n actual donde se
	 * asociar� el men� de la opci�n cuyo �ndice global es indiceGlobal 
	 * @param indiceGlobal �ndice en el sistema de men�s de la opci�n que contiene 
	 * el men� que se asociar� a la opci�n con indiceLocal dentro del men� de la opci�n actual.
	 * @throws IndexOutOfBoundsException Si no existe una opci�n, dentro del sistema de men�s,
	 * con los �ndices pasados.
	 */
	public void asociarMenu( int indiceLocal, int indiceGlobal[])
	{		
		this.opcionActual.obtenerOpcionHija( indiceLocal).cambiarOpcionesHijas( this.obtenerOpcion( indiceGlobal).obtenerOpcionesHijas());
	}

	
	/**
	 * Asigna a la opcion activa actualmente el men� asignado a otra opci�n indiceGlobal 
	 * en el sistema de men�s 
	 * @param nombre Nombre de la opci�n del men� de la opci�n actual donde se
	 * asociar� el men� de la opci�n cuyo �ndice global es indiceGlobal 
	 * @param indiceGlobal �ndice en el sistema de men�s de la opci�n que contiene 
	 * el men� que se asociar� a la opci�n con indiceLocal dentro del men� de la opci�n actual.
	 * @throws IndexOutOfBoundsException Si no existe una opci�n, dentro del sistema de men�s,
	 * con el �ndice global pasados.
	 * @throws NoExisteMenuExcepcion Si no existe con ese nombre la opci�n local en el 
	 * submen� actual.
	 */
	public void asociarMenu( String nombre, int indiceGlobal[]) throws NoExisteMenuExcepcion
	{		
		Opcion opcionHija = this.opcionActual.obtenerOpcionHija( nombre);
		if (opcionHija == null)
			throw new NoExisteMenuExcepcion( "No existe la opci�n local con el nombre" + nombre);
		
		opcionHija.cambiarOpcionesHijas( this.obtenerOpcion( indiceGlobal).obtenerOpcionesHijas());
	}

	
	/** 
	 * M�todo para agregar una opci�n en una posici�n �ndice del men� de la opci�n actual
	 * @param nombre Nombre identificativo de la subopci�n a agregar.
	 * @param cabecera Cabecera del submen� que se accede a trav�s de la subopci�n a agregar.
	 * @param texto Texto que representa la subopci�n a agregar. Este texto aparece en el men�
	 * de la opci�n actual
	 * @param indice Posici�n o �ndice dentro del submen� de la opci�n actual donde situar la nueva subopci�n.
	 * @throws IndexOutOfBoundsException Si el �ndice donde agregar la subopci�n esta fuera del rango 
	 * de opciones de ese submen�.
	 */
	public void agregarOpcion( String nombre, String cabecera, String texto, int indice)
	{
		this.opcionActual.agregarOpcionHija( nombre, cabecera, texto, indice);
	}


	/** 
	 * M�todo para agregar una opci�n al final de las opciones del men� de la opci�n actual
	 * @param nombre Nombre identificativo de la subopci�n a agregar.
	 * @param cabecera Cabecera del submen� que se accede a trav�s de la subopci�n a agregar.
	 * @param texto Texto que representa la subopci�n a agregar. Este texto aparece en el men�
	 * de la opci�n actual
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
	 * @throws IndexOutOfBoundsException Si no existe la opci�n con �ndice local en 
	 * el men� actual. 
	 */
	public void avanzar( int indice)
	{
		this.opcionActual = this.opcionActual.obtenerOpcionHija( indice);
	}
	
	
	/**
	 * Avanzar por el sistema de men�s entrando en una opci�n dentro de las opciones 
	 * posibles del submen� de la opci�n actual.
	 * MEJORA: Se podr�a hacer que avanzase a la opci�n nombre dentro del sistema de
	 * men�s sin importar si esa opci�n es local o no.
	 * @param nombre Nombre de la opci�n a elegir para avanzar.
	 * @throws NoExisteMenuExcepcion Si no existe la opci�n en el submen� con ese nombre.
	 */
	public void avanzar( String nombre) throws NoExisteMenuExcepcion
	{
		Opcion opcionHija = this.opcionActual.obtenerOpcionHija( nombre);
		if (opcionHija == null)
			throw new NoExisteMenuExcepcion( "No existe la opci�n local con el nombre " + nombre);

		this.opcionActual = opcionHija;
	}
	
	
	/**
	 * Retrocede por el sistema del men�s a la opci�n padre de la opci�n en la que estamos actualmente. 
	 * posibles del submen� de la opci�n actual.
	 * @throws NoExisteMenuExcepcion Si no existe una opci�n padre porque ya se encuentra en la
	 * cabecera del sistema de men�s.
	 */
	public void retroceder() throws NoExisteMenuExcepcion
	{
		Opcion opcionPadre = this.opcionActual.obtenerOpcionPadre();
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
	 * Devuelve el nombre de la opci�n con indiceLocal dentro del men� de la opci�n actual.
	 * @return Nombre de la opci�n.
	 */
	public String obtenerNombre( int indiceLocal)
	{
		return opcionActual.obtenerOpcionHija( indiceLocal).obtenerNombre();
	}
	
	
	/**
	 * Obtener la opci�n dentro del sistema de men�s a partir de su posici�n global
	 * @param indiceGlobal Array con los �ndices que indican la posici�n global de la opci�n.
	 * @return Opcion con el �ndice global.
	 * @throws IndexOutOfBoundsException Si no existe el �ndice dentro del sistema de men�s. 
	 */
	private Opcion obtenerOpcion( int indiceGlobal[])
	{		
		SistemaMenus.Opcion opcion = this.opcionCabeza;
		
		if (indiceGlobal != null)
			for (int i = 0; i < indiceGlobal.length; i++)
				opcion = opcion.obtenerOpcionHija( indiceGlobal[i]);
		
		return opcion;
	}
	
	
	/**
	 * Obtener la opci�n dentro del sistema de men�s a partir de su nombre
	 * @param nombre Nombre de la opci�n a buscar.
	 * @return Opcion con el nombre pasado.
	 * @throws NoExisteMenuExcepcion Si no existe una opci�n con el nombre.
	 */
//	private Opcion obtenerOpcion( String nombre) throws NoExisteMenuExcepcion
//	{		
//		Opcion opcionBusqueda = this.opcionCabeza;
//		Opcion opcionEncontrada = null; 
//		
//		if (opcionBusqueda.nombre.equals( nombre))
//			return opcionBusqueda;
//		
//		while (opcionBusqueda != null)
//		{
//			opcionEncontrada = opcionBusqueda.obtenerOpcionHija(nombre);
//			if (opcionEncontrada != null)
//				break;
//
//			Iterator<Opcion> iterator = opcionBusqueda.obtenerOpcionesHijas().iterator();
//
//			if (iterator.hasNext())
//				opcionBusqueda = iterator.next();
//			else
//				opcionBusqueda = opcionBusqueda.obtenerOpcionPadre();
//		}
//		
//		return opcionEncontrada;
//	}
	
	
	/**
	 * Clase que representa cada opci�n de un men�
	 * @author Carlos A. G�mez Urda
	 * @version 1.0
	 */
	private class Opcion implements ObjetoBD
	{
		private String nombre;     				  		// Nombre identificativo de la opci�n de men�
		private String cabecera;   				  		// Cabecera del submen� que representa esta opci�n
		private String texto;    				  		// Texto descriptivo de la opci�n
		private ArrayList<SistemaMenus.Opcion> opcionesHijas;   // Array de opciones que forman el men� de esta opci�n
		private SistemaMenus.Opcion opcionPadre;          		// Opcion en cuyo men� est� la opci�n en la que nos encontramos actualmente
		
		/**
		 * Constructor
		 * @param nombre Nombre descriptivo de la opci�n que aparecer� en el men� de la 
		 * Opcion padre de esta opci�n.
		 * @param cabecera Texto de la cabecera que aparecer� dentro del submen� a
		 * donde lleva esta opci�n
		 * @param texto Texto descriptivo de la opci�n que aparecer� en el men� padre.
		 * @param opcionPadre Opcion en cuyo men� est� la Opcion que seest� creando. 
		 */
		public Opcion( String nombre, String cabecera, String texto, SistemaMenus.Opcion opcionPadre)
		{
			this.nombre = nombre;
			this.cabecera = cabecera;
			this.texto = texto;
			this.opcionPadre = opcionPadre;
			this.opcionesHijas = new ArrayList<SistemaMenus.Opcion>();;
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
		 * @throws IndexOutOfBoundsException Si el �ndice donde agregar la subopci�n esta fuera del rango 
		 * de opciones de ese submen�.
		 */
		public void agregarOpcionHija( String nombre, String cabecera, String texto, int indice) 
		{
			this.opcionesHijas.add( indice, SistemaMenus.this.new Opcion( nombre, cabecera, texto, this));
		}

	
		/** 
		 * M�todo para agregar una opci�n al final de las opciones del men� de la opci�n actual
		 * @param nombre Nombre identificativo de la subopci�n a agregar.
		 * @param cabecera Cabecera del submen� que se accede a trav�s de la subopci�n a agregar.
		 * @param texto Texto que representa la subopci�n a agregar. Este texto aparece en el men�
		 * de la opci�n actual
		 */
		public void agregarOpcionHija( String nombre, String cabecera, String texto)
		{
			this.opcionesHijas.add( SistemaMenus.this.new Opcion( nombre, cabecera, texto, this));
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
		 */
		public Opcion obtenerOpcionHija( int indice)
		{
			return this.opcionesHijas.get( indice);
		}

		
		/**
		 * Obtener la Opcion con un nombre determinado en el men� de la Opcion actual.
		 * @param nombre Nombre de la opci�n a obtener dentro del submen� de la opci�n actual.
		 * @return La Opcion encontrada dentro del submen� de la opci�n actual.
		 * Null si no existe ninguna Opcion con ese nombre
		 */
		public Opcion obtenerOpcionHija( String nombre)
		{
			return BD.buscarObjeto( this.opcionesHijas, nombre);
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

		
		/**
		 * Devolver el id del objeto Opcion (su nombre). 
		 * @return nombre identificativo de la opci�n.
		 */
		public String obtenerId()
		{
			return this.nombre;
		}		
	}
}
