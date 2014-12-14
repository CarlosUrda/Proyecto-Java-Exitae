/**
 * 
 */
package comercio;
import gestionBD.*;
import excepcionesGenericas.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;


/**
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class Tienda implements Serializable, ObjetoBD
{
	private static final long serialVersionUID = 1L;
	private static Integer idGeneral = 1;
	private static ArrayList<Tienda> lista;		// Lista de tiendas totales cargadas de la base de datos.	
	
	private Integer id;					// Nº identificación de la tienda
	private String nombre;			// Nombre de la tienda
	private String cif;				// CIF de la tienda
	private ArrayList<Tienda.InfoMusica> listaInfoMusica;
	private ArrayList<Venta> listaVentas;
	private ArrayList<Tienda.InfoCliente> listaInfoCliente;
	private float deudaLimite;		// Deuda límite en el saldo negativo que puede tener un cliente.  
	
	/**
	 * @return the id
	 */
	public final Integer getId() {
		return id;
	}


	/**
	 * @return the nombre
	 */
	public final String getNombre() {
		return nombre;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * @return the cif
	 */
	public final String getCif() {
		return cif;
	}


	/**
	 * @param cif the cif to set
	 */
	public final void setCif(String cif) {
		this.cif = cif;
	}


	/**
	 * @return the deudaLimite
	 */
	public final float getDeudaLimite() {
		return deudaLimite;
	}


	/**
	 * @param deudaLimite the deudaLimite to set
	 */
	public final void setDeudaLimite(float deudaLimite) {
		this.deudaLimite = deudaLimite;
	}

	
	/**
	 * Mostrar por pantalla los datos de la Tienda
	 */
	public final void mostrarDatos()
	{
		System.out.println( "Id: " + this.id + "; Nombre: " + this.nombre + "; Cif: " + this.cif +
							"; Deuda límite: " + this.deudaLimite);
	}

	
	/**
	 * Función encargada de leer los objetos Tienda grabados en un archivo y guardarlos
	 * como una lista interna en memoria.
	 * @param nombreArchivo Nombre del archivo de donde leer los objetos Tienda
	 * @return Ninguno.
	 * @throws ClassNotFoundException Si no se encuentra ningún objeto Tienda
	 * @throws InvalidClassException Error del objeto Tienda leído.
	 * @throws OptionalDataException Error al leer un tipo de dato primitivo en
	 * lugar de un objeto Tienda
	 * @throws IOException cualquier otro error en la lectura del archivo
	 * @throws FileNotFoundException Si no se encuentra el archivo con nombreArchivo
	 */
	public static void cargarBD( String nombreArchivo) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		// Se obtiene el id más alto a empezar asignar para tiendas
		Tienda.lista = BD.leerObjetos( nombreArchivo);
		int sizeLista = Tienda.lista.size();
		Tienda.idGeneral = (sizeLista == 0) ? 1 : (Tienda.lista.get( sizeLista-1).id + 1);

		// Se obtiene el id más alto a empezar asignar para ventas
		Tienda tienda;
		Iterator<Tienda> iteratorTienda = Tienda.lista.iterator();
		Integer idGeneralVentas = 0;
		Integer idVenta, sizeVentas;
		while (iteratorTienda.hasNext())
		{
			tienda = iteratorTienda.next();
			sizeVentas = tienda.listaVentas.size();
			if (sizeVentas > 0)
			{
				idVenta = tienda.listaVentas.get( sizeVentas - 1).getId();
				if (idVenta > idGeneralVentas)
					idGeneralVentas = idVenta;
			}
		}
		Venta.setIdGeneral( idGeneralVentas + 1);
	}
	
	
	/**
	 * Función encargada de escribir los objetos Tienda de memoria en un archivo.
	 * @param nombreArchivo Nombre del archivo de donde escribir los objetos Tienda
	 * @return Ninguno.
	 * @throws IOException cualquier otro error en la lectura del archivo
	 */
	public static void guardarBD( String nombreArchivo) throws IOException
	{
		BD.escribirObjetos( nombreArchivo, Tienda.lista);
	}


	/**
	 * Obtener la lista de tiendas cargadas en memoria a partir de los datos grabados
	 * en un archivo.
	 * @return Lista de tiendas cargadas en memoria actualmente
	 */
	public static ArrayList<Tienda> obtenerTiendas()
	{
		return Tienda.lista;
	}
	
	
	/**
	 * Constructor
	 * @param nombre Nombre de la Tienda
	 * @param cif Cif de la Tienda
	 * @param deudaLimite Deuda límite para cada cliente.
	 */
	private Tienda( String nombre, String cif, float deudaLimite)
	{
		this.id = Tienda.idGeneral++;
		this.nombre = nombre;
		this.cif = cif;
		this.deudaLimite = deudaLimite;
		this.listaInfoCliente = new ArrayList<InfoCliente>();
		this.listaInfoMusica = new ArrayList<InfoMusica>();
		this.listaVentas = new ArrayList<Venta>();
	}


	/**
	 * Registrar una Tienda en la base de datos de tiendas en memoria
	 * @param nombre Nombre de la Tienda.
	 * @param cif Cif de la Tienda.
	 * @param deudaLimite Límite base de crédito a cada cliente
	 * @return Tienda registrada.
	 * @throws ObjetoExisteExcepcion Si ya existe una Tienda con estos datos
	 */
	public static Tienda registrar( String nombre, String cif, float deudaLimite) throws ObjetoExisteExcepcion
	{
		// Se comprueba si la Tienda ya existe
		Tienda tienda = new Tienda( nombre, cif, deudaLimite);
		if (Tienda.lista.contains( tienda))
			throw new ObjetoExisteExcepcion( "Ya existe una tienda con estos datos."); 
		
		Tienda.lista.add( tienda);
		return tienda;
	}
	
	
	/**
	 * Eliminar una Tienda de la base de datos de tiendas en memoria
	 * @param id Id de la tienda a eliminar
	 * @throws ObjetoNoEncontradoExcepcion Si no hay ninguna tienda con el id.
	 */
	public static void eliminar( Integer id) throws ObjetoNoEncontradoExcepcion
	{
		Tienda tienda = Tienda.buscar( id);
		if (tienda == null)
			throw new ObjetoNoEncontradoExcepcion( "La tienda con id " + id + " no existe");

		Tienda.lista.remove( tienda);
	}
	
	
	/**
	 * Buscar un objeto Tienda con un id determinado dentro de la lista de objetos 
	 * cargadas actualmente en memoria a partir del archivo. 
	 * @param id
	 * @return Objeto Tienda encontrado. Si no se encuentra devuelve null.
	 */
	public static Tienda buscar( Integer id)
	{
		return BD.buscarObjeto( Tienda.lista, id);
	}

	
	/**
	 * Sobrecarga de método equals para comparar objetos Tienda
	 * @param tienda Tienda a comparar
	 * @return Si el objeto es de tipo Tienda devuelve true si coincide el codigo,
	 * sino devuelve false; Si el objeto no es de tipo Tienda, devuelve directamente
	 * el resultado de la comparación de las referencias a los objetos
	 */
	@Override
	public boolean equals( Object tienda)
	{
		if (tienda instanceof Tienda)
			return this.cif.equalsIgnoreCase( ((Tienda)tienda).cif);
		
		return this == tienda;
	}

	
	/**
	 * Método encargadoo de gestionar la realización de una venta.
	 * @param idMusica Producto a vender
	 * @param idCliente Cliente que hace la compra
	 * @param numProductos Número de productos vendidos
	 * @param StockInsuficienteExcepcion Si no hay stock del producto para poder hacer la venta.
	 * @param SaldoInsuficienteExcepcion Si el cliente no puede realizar el pago con su cuenta.
	 * @param ArgumentoInvalidoExcepcion El número de productos debe ser positivo.
	 */
	public void realizarVenta( Integer idMusica, Integer idCliente, Integer numProductos) 
			throws StockInsuficienteExcepcion, SaldoInsuficienteExcepcion, ArgumentoInvalidoExcepcion
	{
		if (numProductos < 0)
			throw new ArgumentoInvalidoExcepcion( "El número de productos debe ser positivo");
		
		// Se comprueba si existe stock la Musica en la Tienda.
		InfoMusica infoMusica = this.buscarInfoMusica( idMusica);
		if ((infoMusica == null) || (infoMusica.comprobarStock( numProductos) == false))
			throw new StockInsuficienteExcepcion( "No hay stock en la tienda.");
		
		// Se comprueba si el cliente puede realizar el pago.
		InfoCliente infoCliente = this.buscarInfoCliente( idCliente);
		if (infoCliente == null) 
			throw new SaldoInsuficienteExcepcion( "El cliente no tiene activada su cuenta en la Tienda.");

		String estadoPago = infoCliente.comprobarPago( numProductos * infoMusica.getPrecio());
		if (estadoPago != null)
			throw new SaldoInsuficienteExcepcion( estadoPago);

		// Se actualiza el stock y la cuenta del cliente
		infoMusica.variarStock( numProductos);
		infoCliente.realizarPago( infoMusica.getPrecio() * numProductos);
		
		// Se realiza la venta.
		Venta venta = new Venta( idMusica, idCliente, numProductos, infoMusica.getPrecio());
		this.listaVentas.add( venta);
	}
	
	
	/**
	 * Método encargado de modificar la cuenta de un Cliente registrado. Si el cliente
	 * no tenía cuenta en esta Tienda, se crea con el saldo inicial.
	 * @param idCliente Id del Cliente registrado
	 * @param dinero Dinero a modificar del saldo en la cuenta del cliente.
	 * @throws ObjetoNoEncontradoExcepcion El Cliente no está registrado.
	 * @throws SaldoInsuficienteExcepcion Si la variación de la cuenta sobrepasa la deuda permitida
	 */
	public void setCuentaCliente( Integer idCliente, float dinero) throws ObjetoNoEncontradoExcepcion, SaldoInsuficienteExcepcion
	{
		// Se comprueba si el cliente ya dispone de una cuenta en esta Tienda.
		InfoCliente infoCliente = this.buscarInfoCliente( idCliente);
		if (infoCliente == null)
		{
			// Se comprueba la existencia del objeto Cliente
			if (Cliente.buscar( idCliente) == null)
				throw new ObjetoNoEncontradoExcepcion( "El cliente con id " + idCliente + " no está registrado");

			this.listaInfoCliente.add( this.new InfoCliente( idCliente, dinero));
		}
		else
			infoCliente.variarSaldo( dinero);
	}
	
	
	/**
	 * Obtener el saldo de la cuenta de un Cliente en la Tienda. 
	 * @param idCliente Id del Cliente
	 * @return Saldo de la cuenta.
	 * @throws SaldoInsuficienteExcepcion Si el Cliente no tenía cuenta en esta Tienda.
	 */
	public float getCuentaCliente( Integer idCliente) throws SaldoInsuficienteExcepcion
	{
		// Se comprueba si el cliente ya dispone de una cuenta en esta Tienda.
		InfoCliente infoCliente = this.buscarInfoCliente( idCliente);
		if (infoCliente == null)
			throw new SaldoInsuficienteExcepcion( "El cliente con id " + idCliente + " no dispone de cuenta en esta tienda.");

		return infoCliente.getSaldo();
	}
	
	
	/**
	 * Método encargado de variar el stock de un producto en la Tienda. Si el producto no
	 * se encuentra en la Tienda, se adquiere con el stock dado.
	 * @param idMusica Id del producto Musica a incorporar.
	 * @param stock Número de productos adquiridos.
	 * @throws StockInsuficienteExcepcion Si no hay stock suficiente para extraer (esté o no el producto en Tienda)
	 * @throws ObjetoNoEncontradoExcepcion Si no existe ningún objeto Musica registrado 
	 *  con el idMusica.
	 */
	public void setStockMusica( Integer idMusica, Integer stock) 
			throws StockInsuficienteExcepcion, ObjetoNoEncontradoExcepcion
	{
		// Se comprueba si el objeto Musica ha sido anteriormente adquirido por la Tienda.
		InfoMusica infoMusica = this.buscarInfoMusica( idMusica);
		if (infoMusica == null)
		{
			if (stock < 0)
				throw new StockInsuficienteExcepcion( "No puedes bajar stock porque el producto con id " + idMusica + "  no se encuentra en esta Tienda.");
			
			// Se comprueba la existencia del objeto Musica
			Musica musica = Musica.buscar( idMusica);
			if (musica == null)
				throw new ObjetoNoEncontradoExcepcion( "El objeto música con id " + idMusica + " no existe en el registro.");

			this.listaInfoMusica.add( this.new InfoMusica( idMusica, stock, musica.getPrecio()));
		}
		else
			infoMusica.variarStock( stock);
	}
	
	
	/**
	 * Obtener el stock de un producto Musica en la Tienda.
	 * @param idMusica Id del producto Musica.
	 * @return Stock del producto Musica existente en la tienda.
	 * @throws StockInsuficienteExcepcion Si el producto Musica no 
	 * ha sido adquirido aún por la Tienda
	 */
	public Integer getStockMusica( Integer idMusica) throws StockInsuficienteExcepcion
	{
		// Se comprueba si el objeto Musica ha sido anteriormente adquirido por la Tienda.
		InfoMusica infoMusica = this.buscarInfoMusica(idMusica);
		if (infoMusica == null)
			throw new StockInsuficienteExcepcion( "El objeto música con id " + idMusica + " no se encuentra en esta Tienda.");

		return infoMusica.getStock();
	}
	
	
	/**
	 * Cambier el precio de un producto en la Tienda.
	 * @param idMusica Id del producto Musica
	 * @param precio Nuevo precio del producto en esta Tienda.
	 */
	public void setPrecioMusica( Integer idMusica, float precio) throws StockInsuficienteExcepcion, ArgumentoInvalidoExcepcion
	{
		// Se comprueba si el objeto Musica ha sido anteriormente adquirido por la Tienda.
		InfoMusica infoMusica = this.buscarInfoMusica(idMusica);
		if (infoMusica == null)
			throw new StockInsuficienteExcepcion( "El objeto música con id " + idMusica + " no se encuentra en esta Tienda.");
		
		infoMusica.setPrecio( precio);
		
	}
	
	
	/**
	 * Obtener el precio de un producto Musica en la Tienda.
	 * @param idMusica Id del producto Musica.
	 * @return Precio del producto en la Tienda.
	 */
	public float getPrecioMusica( Integer idMusica) throws StockInsuficienteExcepcion
	{
		// Se comprueba si el objeto Musica ha sido anteriormente adquirido por la Tienda.
		InfoMusica infoMusica = this.buscarInfoMusica(idMusica);
		if (infoMusica == null)
			throw new StockInsuficienteExcepcion( "El objeto música con id " + idMusica + " no se encuentra en esta Tienda.");
		
		return infoMusica.getPrecio();
		
	}

	
	/**
	 * Eliminar los datos del producto Musica dentro de esta Tienda en el caso de
	 * no tener stock.
	 * @param idMusica Id del producto Musica a limpiar de la Tienda.
	 * @param destruir True si se desea eliminar la información del producto en la
	 * Tienda sin importar su stock. False para eliminar sólo en el caso de tener 
	 * stock cero en la Tienda.
	 * @return False si el producto tenía stock. True si se ha limpiado los datos del
	 * producto en esta Tienda.
	 */
	public boolean eliminarMusica( Integer idMusica, boolean destruir)
	{
		InfoMusica infoMusica = this.buscarInfoMusica( idMusica);
		if (infoMusica != null)
		{
			if (destruir || !infoMusica.hayStock())
				this.listaInfoMusica.remove( infoMusica);
			else
				return false;
		}
		
		return true;
	}

	
	/**
	 * Eliminar los datos de la cuenta de un Cliente dentro de esta Tienda.
	 * @param idCliente Id del Cliente a eliminar su cuenta.
	 * @param destruir True si se desea eliminar la cuenta sin importar su saldo 
	 * o si está bloqueada. False para eliminar sólo en el caso de no estar 
	 * boqueada y su saldo está a cero.
	 * @return False si la cuenta no se ha borrado. True si se ha borrado.
	 */
	public boolean eliminarCuentaCliente( Integer idCliente, boolean destruir)
	{
		InfoCliente infoCliente = this.buscarInfoCliente( idCliente);
		if (infoCliente != null)
		{
			if (destruir || (!infoCliente.getBloqueado() && !(infoCliente.getSaldo() > 0)))
				this.listaInfoCliente.remove( infoCliente);
			else
				return false;
		}
		
		return true;
	}

	
	/**
	 * Bloquear o desbloquear la cuenta de un cliente.
	 * @param idCliente Id del cliente a bloquear
	 * @param bloqueo true => Bloquear; false => No bloquear.
	 * @throws SaldoInsuficienteExcepcion Si el Cliente no tenía cuenta en esta Tienda.
	 */
	public void setBloqueoCliente( Integer idCliente, boolean bloqueo) throws SaldoInsuficienteExcepcion
	{
		// Se comprueba si el cliente ya dispone de una cuenta en esta Tienda.
		InfoCliente infoCliente = this.buscarInfoCliente( idCliente);
		if (infoCliente == null)
			throw new SaldoInsuficienteExcepcion( "El cliente con id " + idCliente + " no dispone de cuenta en esta tienda.");
		
		infoCliente.setBloqueado( bloqueo);
	}
	
	
	/**
	 * Bloquear o desbloquear la cuenta de un cliente.
	 * @param idCliente Id del cliente a bloquear
	 * @return true o false si está bloqueada la cuenta o no.
	 * @throws SaldoInsuficienteExcepcion Si el Cliente no tenía cuenta en esta Tienda.
	 */
	public boolean getBloqueoCliente( Integer idCliente) throws SaldoInsuficienteExcepcion
	{
		// Se comprueba si el cliente ya dispone de una cuenta en esta Tienda.
		InfoCliente infoCliente = this.buscarInfoCliente( idCliente);
		if (infoCliente == null)
			throw new SaldoInsuficienteExcepcion( "El cliente con id " + idCliente + " no dispone de cuenta en esta tienda.");
		
		return infoCliente.getBloqueado();
	}
	
	
	/**
	 * Mostrar por pantalla el listado de ventas realizado en esta Tienda.
	 */
	public void mostrarVentas()
	{
		Iterator<Venta> iteratorVentas = this.listaVentas.iterator();
		while (iteratorVentas.hasNext())
		{
			iteratorVentas.next().mostrarDatos();
		}
	}
	
	
	/**
	 * Método encargado de buscar, en la lista de musicas adquiridos a disposición
	 * en la Tienda, alguno con un id de Musica concreto.
	 * @param idMusica Id del objeto Musica a buscar.
	 * @return Objeto InfoMusica encontrado o null si la tienda no lo ha adquirido
	 */
	private InfoMusica buscarInfoMusica( Integer idMusica)
	{
		return BD.buscarObjeto( this.listaInfoMusica, idMusica);
	}
	
	
	/**
	 * Método encargado de buscar, en la lista de clientes con cuenta
	 * en la Tienda, alguno con un id de Cliente concreto.
	 * @param idMusica Id del objeto Musica a buscar.
	 * @return Objeto InfoMusica encontrado o null si la tienda no lo ha adquirido
	 */
	private InfoCliente buscarInfoCliente( Integer idCliente)
	{
		return BD.buscarObjeto( this.listaInfoCliente, idCliente);
	}

	
	/**
	 * Clase con la información de cada producto a disposición en la tienda.
	 * @author Carlos A. Gómez Urda
	 * @version 1.0
	 */
	private class InfoMusica implements Serializable, ObjetoBD 
	{
		private static final long serialVersionUID = 1L;
		private Integer idMusica;   // Id del objeto Musica
		private Integer stock;   	// Número de productos idMusica disponibles en stock.
		private float precio;       // Precio del producto en la Tienda.
		
		/**
		 * Constructor
		 * @param idMusica Id del objeto Musica
		 * @param stock Número de productos en stock en la Tienda
		 * @param precio Precio del producto en la Tienda
		 */
		private InfoMusica( Integer idMusica, Integer stock, float precio)
		{
			this.idMusica = idMusica;
			this.stock = stock;
			this.precio = precio;
		}
		
		
		/**
		 * Comprobar si hay suficiente stock para una cantidad de productos solicitada
		 * @param stock Cantidad a comprobar si existe en stock.
		 * @return true si hay stock, false si no hay stock para esa cantidad solicitada.
		 * @throws ArgumentoInvalidoExcepcion Si el numProductos es negativo.
		 */
		private boolean comprobarStock( Integer numProductos) throws ArgumentoInvalidoExcepcion
		{
			if (numProductos < 0)
				throw new ArgumentoInvalidoExcepcion( "El número de productos no puede ser negativo");

			return (this.stock - numProductos) >= 0;
		}
		
		
		/**
		 * Comprobar si hay stock
		 * @return true si hay stock, false si no hay stock.
		 */
		private boolean hayStock()
		{
			return this.stock > 0;
		}
		
		
		/**
		 * Cambiar el número de productos en stock del objeto Musica en la Tienda.
		 * @param stock Número de productos a modificar en el stock de la tienda.
		 * @throws StockInsuficienteExcepcion Si no hay stock suficiente
		 */
		private void variarStock( Integer stock) throws StockInsuficienteExcepcion
		{
			try {				
				if ((stock < 0) && (this.comprobarStock( -stock) == false))
					throw new StockInsuficienteExcepcion( "No hay suficiente stock en la tienda.");
			} catch (ArgumentoInvalidoExcepcion e) {}
			
			this.stock += stock;
		}
		
		
		/**
		 * Cambiar el precio de venta de un producto Musica en la Tienda.
		 * @param precio Nuevo precio de venta del producto.
		 * @throws ArgumentoInvalido Si el precio es negativo.
		 */
		private void setPrecio( float precio) throws ArgumentoInvalidoExcepcion
		{
			if (precio < 0)
				throw new ArgumentoInvalidoExcepcion( "El precio no puede ser negativo");
			
			this.precio = precio;
		}
		
		
		/**
		 * Devolver el id del objeto Musica al cual se referencia 
		 * @return idMusica
		 */
		public Integer getId()
		{
			return this.idMusica;
		}
		

		/**
		 * Obtener el precio al que se vendió el producto.
		 * @return Precio al que se vendió el producto.
		 */
		private float getPrecio()
		{
			return this.precio;
		}


		/**
		 * @return the stock
		 */
		private final Integer getStock() {
			return stock;
		}


		/**
		 * Sobrecarga de método equals para comparar objetos infoMusica
		 * @param infoMusica infoMusica a comparar
		 * @return Si el objeto es de tipo InfoMusica devuelve true si coincide el idMusica,
		 * sino devuelve false; Si el objeto no es de tipo InfoMusica, devuelve directamente
		 * el resultado de la comparación de las referencias a los objetos
		 */
		@Override
		public boolean equals( Object infoMusica)
		{
			if (infoMusica instanceof InfoMusica)
				return this.idMusica.equals( ((InfoMusica) infoMusica).idMusica);
			
			return this == infoMusica;
		}

	}


	/**
	 * Clase con la información de cada cliente inscrito en la tienda.
	 * @author Carlos A. Gómez Urda
	 * @version 1.0
	 */
	private class InfoCliente implements Serializable, ObjetoBD
	{
		private static final long serialVersionUID = 1L;
		private Integer idCliente;      // Id del objeto Cliente.
		private float saldo;        // Dinero disponible por el cliente en la tienda.
		private boolean bloqueado;  // Flag para saber si la cuenta está bloqueada.
		
		/**
		 * Constructor
		 * @param idCliente Id del Cliente
		 * @param saldo Dinero a disponer en la Tienda.
		 */
		private InfoCliente( Integer idCliente, float saldo)
		{
			this.idCliente = idCliente;
			this.saldo = saldo;
			this.bloqueado = false;
		}
		
		
		/**
		 * Comprobar si el cliente puede realizar un pago.
		 * @param pago Dinero a comprobar si se puede afrontar en la cuenta del cliente
		 * @return null si puede realizar el pago; Mensaje de error si no puede realizarlo.
		 */
		private String comprobarPago( float pago)
		{
			if (this.bloqueado)
				return new String( "La cuenta está bloqueada.");

			// Se comprueba si se puede realizar el pago
			if (-Tienda.this.deudaLimite > (this.saldo - pago))
				return new String( "El pago sobrepasa el límite de crédito.");
			
			return null;
		}
		
		
		/**
		 * Aumentar o disminuir el dinero
		 * @param pago Pago a descontar de la cuenta del cliente
		 * @throws SaldoInsuficienteExcepcion Si no hay dinero en la cuenta para
		 * poder realizar el pago.
		 * @throws ArgumentoInvalidoExcepcion Si el valor del pago es negativo
		 */
		private void realizarPago( float pago) throws SaldoInsuficienteExcepcion, ArgumentoInvalidoExcepcion
		{
			if (pago < 0)
				throw new ArgumentoInvalidoExcepcion( "El valor del pago no puede ser negativo");

			variarSaldo( -pago);
		}
		
		
		/**
		 * Ingresar o extraer dinero en la cuenta del cliente
		 * @param dinero Dinero a ingresar o extraer en la cuenta del cliente
		 * @throws SaldoInsuficienteExcepcion Si no hay dinero en la cuenta para
		 * afrontar el límite de la deuda.
		 */
		private void variarSaldo( float dinero)  throws SaldoInsuficienteExcepcion
		{
			if (dinero < 0)
			{				
				String estado = this.comprobarPago( -dinero);
				if (estado != null)
					throw new SaldoInsuficienteExcepcion( estado);
			}

			this.saldo += dinero;
		}		


		/**
		 * Devolver el id del objeto Cliente al cual se referencia 
		 * @return idMusica
		 */
		public Integer getId()
		{
			return this.idCliente;
		}


		/**
		 * Devolver el saldo del objeto Cliente. 
		 * @return idMusica
		 */
		private float getSaldo()
		{
			return this.saldo;
		}

		
		/**
		 * Bloquear o desboquear la cuenta
		 * @param bloquear true o false si se desea bloquear la cuenta o no
		 */
		private void setBloqueado( boolean bloquear)
		{
			this.bloqueado = bloquear;
		}
		
		
		/**
		 * Informa si la cuenta de un cliente está bloqueada. 
		 * @return idMusica
		 */
		private boolean getBloqueado()
		{
			return this.bloqueado;
		}

		
		/**
		 * Sobrecarga de método equals para comparar objetos infoCliente
		 * @param infoCliente InfoCliente a comparar
		 * @return Si el objeto es de tipo InfoCliente devuelve true si coincide el idCliente,
		 * sino devuelve false; Si el objeto no es de tipo InfoCliente, devuelve directamente
		 * el resultado de la comparación de las referencias a los objetos
		 */
		@Override
		public boolean equals( Object infoCliente)
		{
			if (infoCliente instanceof InfoCliente)
				return this.idCliente.equals( ((InfoCliente) infoCliente).idCliente);
			
			return this == infoCliente;
		}
		
	}
	
}



/**
 * Clase que representa cada venta realizada en la Tienda
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
class Venta implements Serializable, ObjetoBD
{
	private static final long serialVersionUID = 1L;

	private static Integer idGeneral = 1;
	
	private Integer id;
	private Integer idMusica;
	private Integer idCliente;
	private Integer numProductos; 			 // Número de productos vendidos en la venta.
	private float precio;                // Precio de cada unidad vendida.
	private GregorianCalendar fecha;	 // Fecha de la venta.
	
	
	/**
	 * @param idGeneral the idGeneral to set
	 */
	static final void setIdGeneral( Integer idGeneral) {
		Venta.idGeneral = idGeneral;
	}


	/**
	 * Constructor
	 * @param idMusica Id de Musica a vender
	 * @param idCliente Id del cliente que realiza la compra
	 * @param numProductos Número de productos vendidos en la venta.
	 * @param precio Precio de cada unidad vendida del producto Musica.
	 */
	Venta( Integer idMusica, Integer idCliente, Integer numProductos, float precio)
	{
		this.idMusica = idMusica;
		this.idCliente = idCliente;
		this.numProductos = numProductos;
		this.id = Venta.idGeneral++;
		this.precio = precio;				// Precio de cada unidad del producto de la venta
		this.fecha = new GregorianCalendar();
	}
	
	
	/**
	 * Obtener el valor del Id de la venta
	 * @return Id de la venta
	 */
	public Integer getId()
	{
		return this.id;
	}
	
	
	/**
	 * Obtener el gasto de la venta.
	 * @return Precio al que se vendió el producto.
	 */
	float getGasto()
	{
		return this.precio * this.numProductos;
	}
	
	
	/**
	 * Mostrar por pantalla los datos de la Venta
	 */
	public final void mostrarDatos()
	{
		System.out.println( "Id: " + this.id + "; Id Cliente: " + this.idCliente + "; Id Musica: " + 
							this.idMusica +	"; Núm Productos: " + this.numProductos + "; Precio total: " +
							this.getGasto() + "; Fecha: " + this.fecha.getTime().toString());
	}


}
