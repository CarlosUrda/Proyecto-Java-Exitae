/**
 * Paquete para gestionar un sistema de menús de cualquier programa
 */
package menus;
import java.util.ArrayList;
import java.util.Iterator;

import gestionBD.*;

/** 
 * Clase para gestionar el sistema de menús de un programa
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class SistemaMenus 
{
	private SistemaMenus.Opcion opcionCabeza;    // Opcion que representa la cabecera del sistema de menús 
	private SistemaMenus.Opcion opcionActual;    // Opcion en la que nos encontramos actualmente dentro del sistema de menús.
	
	/**
	 * Constructor
	 * @param nombre Nombre identificativo del menú principal.
	 * @param cabecera Cabecera del sistema de menús
	 */
	public SistemaMenus( String nombre, String cabecera)
	{
		this.opcionCabeza = new SistemaMenus.Opcion( nombre, cabecera, "", null);
		this.opcionActual = this.opcionCabeza;
	}
	
	
	/**	
	 * Agregar los datos del menú dentro de la opción actual donde nos encontremos.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opción del submenú.
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
	 * @throws IndexOutOfBoundsException Si no existe una opción, dentro del sistema de menús,
	 * con los índices pasados.
	 */
	public void agregarMenu( String datosMenu[][], int indiceLocal)
	{
		this.opcionActual.obtenerOpcionHija( indiceLocal).agregarOpcionesHijas( datosMenu);
	}


	/**
	 * Agregar los datos del submenú dentro de una de las opciones del menú de la opción 
	 * actual donde nos encontremos.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opción del submenú.
	 * @param nombre Nombre de la opción local donde agregar el menú.
	 * @throws NoExisteMenuExcepcion Si no existe con ese nombre la opción local en el 
	 * submenú actual.
	 */
	public void agregarMenu( String datosMenu[][], String nombre) throws NoExisteMenuExcepcion
	{
		Opcion opcionHija = this.opcionActual.obtenerOpcionHija( nombre);
		if (opcionHija == null)
			throw new NoExisteMenuExcepcion( "No existe la opción local con el nombre" + nombre);
		
		opcionHija.agregarOpcionesHijas( datosMenu);
	}

	
	/**
	 * Agregar los datos del submenú dentro de una de las opciones de todo el sistema de menús.
	 * @param datosmenuHijo Array de datos de las opciones. Cada elemento del array
	 * es un array con los elementos nombre, cabecera y texto de cada opción del submenú.
	 * @param indiceGlobal Array de indices que indican la posición, dentro del sistema de menús, 
	 * de la opción donde se agregará el menú. Si null, se agrega el submenú a la cabecera
	 * @throws IndexOutOfBoundsException Si no existe una opción, dentro del sistema de menús,
	 * con los índices pasados.
	 */
	public void agregarMenu( String datosMenu[][], int indiceGlobal[])
	{
		this.obtenerOpcion( indiceGlobal).agregarOpcionesHijas( datosMenu);
	}

	
	/**
	 * Asigna a la opcion activa el menú asignado a otra opción indiceGlobal en el 
	 * sistema de menús 
	 * @param indiceGlobal Índice en el sistema de menús de la opción que contiene 
	 * el menú que se asociará a la opción actual.
	 * @throws IndexOutOfBoundsException Si no existe una opción, dentro del sistema de menús,
	 * con los índices pasados.
	 */
	public void asociarMenu( int indiceGlobal[])
	{		
		this.opcionActual.cambiarOpcionesHijas( this.obtenerOpcion( indiceGlobal).obtenerOpcionesHijas());
	}
	
	
	/**
	 * Asigna a la opcion indiceLocal dentro de la opción actual el menú asignado a otra opción 
	 * indiceGlobal en el sistema de menús 
	 * @param indiceLocal Índice de la opción del menú de la opción actual donde se
	 * asociará el menú de la opción cuyo índice global es indiceGlobal 
	 * @param indiceGlobal Índice en el sistema de menús de la opción que contiene 
	 * el menú que se asociará a la opción con indiceLocal dentro del menú de la opción actual.
	 * @throws IndexOutOfBoundsException Si no existe una opción, dentro del sistema de menús,
	 * con los índices pasados.
	 */
	public void asociarMenu( int indiceLocal, int indiceGlobal[])
	{		
		this.opcionActual.obtenerOpcionHija( indiceLocal).cambiarOpcionesHijas( this.obtenerOpcion( indiceGlobal).obtenerOpcionesHijas());
	}

	
	/**
	 * Asigna a la opcion activa actualmente el menú asignado a otra opción indiceGlobal 
	 * en el sistema de menús 
	 * @param nombre Nombre de la opción del menú de la opción actual donde se
	 * asociará el menú de la opción cuyo índice global es indiceGlobal 
	 * @param indiceGlobal Índice en el sistema de menús de la opción que contiene 
	 * el menú que se asociará a la opción con indiceLocal dentro del menú de la opción actual.
	 * @throws IndexOutOfBoundsException Si no existe una opción, dentro del sistema de menús,
	 * con el índice global pasados.
	 * @throws NoExisteMenuExcepcion Si no existe con ese nombre la opción local en el 
	 * submenú actual.
	 */
	public void asociarMenu( String nombre, int indiceGlobal[]) throws NoExisteMenuExcepcion
	{		
		Opcion opcionHija = this.opcionActual.obtenerOpcionHija( nombre);
		if (opcionHija == null)
			throw new NoExisteMenuExcepcion( "No existe la opción local con el nombre" + nombre);
		
		opcionHija.cambiarOpcionesHijas( this.obtenerOpcion( indiceGlobal).obtenerOpcionesHijas());
	}

	
	/** 
	 * Método para agregar una opción en una posición índice del menú de la opción actual
	 * @param nombre Nombre identificativo de la subopción a agregar.
	 * @param cabecera Cabecera del submenú que se accede a través de la subopción a agregar.
	 * @param texto Texto que representa la subopción a agregar. Este texto aparece en el menú
	 * de la opción actual
	 * @param indice Posición o índice dentro del submenú de la opción actual donde situar la nueva subopción.
	 * @throws IndexOutOfBoundsException Si el índice donde agregar la subopción esta fuera del rango 
	 * de opciones de ese submenú.
	 */
	public void agregarOpcion( String nombre, String cabecera, String texto, int indice)
	{
		this.opcionActual.agregarOpcionHija( nombre, cabecera, texto, indice);
	}


	/** 
	 * Método para agregar una opción al final de las opciones del menú de la opción actual
	 * @param nombre Nombre identificativo de la subopción a agregar.
	 * @param cabecera Cabecera del submenú que se accede a través de la subopción a agregar.
	 * @param texto Texto que representa la subopción a agregar. Este texto aparece en el menú
	 * de la opción actual
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
	 * @throws IndexOutOfBoundsException Si no existe la opción con índice local en 
	 * el menú actual. 
	 */
	public void avanzar( int indice)
	{
		this.opcionActual = this.opcionActual.obtenerOpcionHija( indice);
	}
	
	
	/**
	 * Avanzar por el sistema de menús entrando en una opción dentro de las opciones 
	 * posibles del submenú de la opción actual.
	 * MEJORA: Se podría hacer que avanzase a la opción nombre dentro del sistema de
	 * menús sin importar si esa opción es local o no.
	 * @param nombre Nombre de la opción a elegir para avanzar.
	 * @throws NoExisteMenuExcepcion Si no existe la opción en el submenú con ese nombre.
	 */
	public void avanzar( String nombre) throws NoExisteMenuExcepcion
	{
		Opcion opcionHija = this.opcionActual.obtenerOpcionHija( nombre);
		if (opcionHija == null)
			throw new NoExisteMenuExcepcion( "No existe la opción local con el nombre " + nombre);

		this.opcionActual = opcionHija;
	}
	
	
	/**
	 * Retrocede por el sistema del menús a la opción padre de la opción en la que estamos actualmente. 
	 * posibles del submenú de la opción actual.
	 * @throws NoExisteMenuExcepcion Si no existe una opción padre porque ya se encuentra en la
	 * cabecera del sistema de menús.
	 */
	public void retroceder() throws NoExisteMenuExcepcion
	{
		Opcion opcionPadre = this.opcionActual.obtenerOpcionPadre();
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
		return opcionActual.getNombre();
	}
	
	
	/**
	 * Devuelve el nombre de la opción con indiceLocal dentro del menú de la opción actual.
	 * @return Nombre de la opción. Null si no existe la opción con indiceLocal.
	 * @throws IndexOutOfBoundsException Si no existe opcion con el índice local. 
	 */
	public String obtenerNombre( int indiceLocal) throws NoExisteMenuExcepcion
	{
		return this.opcionActual.obtenerOpcionHija( indiceLocal).getNombre();
	}
	
	
	/**
	 * Obtener la opción dentro del sistema de menús a partir de su posición global
	 * @param indiceGlobal Array con los índices que indican la posición global de la opción.
	 * @return Opcion con el índice global.
	 * @throws IndexOutOfBoundsException Si no existe el índice dentro del sistema de menús. 
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
	 * Obtener la opción dentro del sistema de menús a partir de su nombre
	 * @param nombre Nombre de la opción a buscar.
	 * @return Opcion con el nombre pasado.
	 * @throws NoExisteMenuExcepcion Si no existe una opción con el nombre.
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
	 * Clase que representa cada opción de un menú
	 * @author Carlos A. Gómez Urda
	 * @version 1.0
	 */
	private class Opcion implements ObjetoBD
	{
		private String nombre;     				  		// Nombre identificativo de la opción de menú
		private String cabecera;   				  		// Cabecera del submenú que representa esta opción
		private String texto;    				  		// Texto descriptivo de la opción
		private ArrayList<SistemaMenus.Opcion> opcionesHijas;   // Array de opciones que forman el menú de esta opción
		private SistemaMenus.Opcion opcionPadre;          		// Opcion en cuyo menú está la opción en la que nos encontramos actualmente
		
		/**
		 * Constructor
		 * @param nombre Nombre descriptivo de la opción que aparecerá en el menú de la 
		 * Opcion padre de esta opción.
		 * @param cabecera Texto de la cabecera que aparecerá dentro del submenú a
		 * donde lleva esta opción
		 * @param texto Texto descriptivo de la opción que aparecerá en el menú padre.
		 * @param opcionPadre Opcion en cuyo menú está la Opcion que seestá creando. 
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
		 * Devuelve el nombre de la opción.
		 * @return Nombre de la opción.
		 */
		public String getNombre()
		{
			return this.nombre;
		}
	
		
		/**
		 * Modificar la cabecera del menú de esta opción
		 * @param cabecera
		 */
		public void setCabecera( String cabecera)
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
		 * @throws IndexOutOfBoundsException Si el índice donde agregar la subopción esta fuera del rango 
		 * de opciones de ese submenú.
		 */
		public void agregarOpcionHija( String nombre, String cabecera, String texto, int indice) 
		{
			this.opcionesHijas.add( indice, SistemaMenus.this.new Opcion( nombre, cabecera, texto, this));
		}

	
		/** 
		 * Método para agregar una opción al final de las opciones del menú de la opción actual
		 * @param nombre Nombre identificativo de la subopción a agregar.
		 * @param cabecera Cabecera del submenú que se accede a través de la subopción a agregar.
		 * @param texto Texto que representa la subopción a agregar. Este texto aparece en el menú
		 * de la opción actual
		 */
		public void agregarOpcionHija( String nombre, String cabecera, String texto)
		{
			this.opcionesHijas.add( SistemaMenus.this.new Opcion( nombre, cabecera, texto, this));
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
		 */
		public Opcion obtenerOpcionHija( int indice)
		{
			return this.opcionesHijas.get( indice);
		}

		
		/**
		 * Obtener la Opcion con un nombre determinado en el menú de la Opcion actual.
		 * @param nombre Nombre de la opción a obtener dentro del submenú de la opción actual.
		 * @return La Opcion encontrada dentro del submenú de la opción actual.
		 * Null si no existe ninguna Opcion con ese nombre
		 */
		public Opcion obtenerOpcionHija( String nombre)
		{
			return BD.buscarObjeto( this.opcionesHijas, nombre);
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

		
		/**
		 * Devolver el id del objeto Opcion (su nombre). 
		 * @return nombre identificativo de la opción.
		 */
		public String getId()
		{
			return this.nombre;
		}		
	}
}
