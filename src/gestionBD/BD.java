/**
 * 
 */
package gestionBD;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Clase para el acceso genérico a los datos de archivos.
 * @author Carlos A. Gómez Urda
 * @version 1.0
 */
public class BD 
{
	/**
	 * Función encargada de leer los objetos genéricos <T> grabados en un archivo.
	 * @param nombreArchivo Nombre del archivo origen de donde leer los objetos T
	 * @return Ninguno.
	 * @throws ClassNotFoundException Si no se encuentra ningún objeto Tienda
	 * @throws InvalidClassException Error del objeto Tienda leído.
	 * @throws OptionalDataException Error al leer un tipo de dato primitivo en
	 * lugar de un objeto Tienda
	 * @throws IOException cualquier otro error en la lectura del archivo
	 * @throws FileNotFoundException Si no se encuentra el archivo con nombreArchivo
	 * @return ArrayList<T> Lista de objetos T leídos del archivo.
	 */
	public static <T> ArrayList<T> leerObjetos( String nombreArchivo) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream archivo = new ObjectInputStream( new FileInputStream( nombreArchivo));
		ArrayList<T> lista = new ArrayList<T>();
		
		try
		{
			while (true)
				lista.add( (T)archivo.readObject());
		}
		catch (EOFException e)
		{
			archivo.close();
			return lista;
		}		
	}
	
	
	/**
	 * Función encargada de escribir los objetos Tienda de memoria en un archivo.
	 * @param nombreArchivo Nombre del archivo de donde escribir los objetos T.
	 * @param lista Lista de objetos T a escribir en el archivo.
	 * @return Ninguno.
s	 * @throws IOException cualquier otro error en la lectura del archivo
	 */
	public static <T> void escribirObjetos( String nombreArchivo, ArrayList<T> lista) throws IOException
	{
		ObjectOutputStream archivo = new ObjectOutputStream( new FileOutputStream( nombreArchivo));
		Iterator<T> iterator = lista.iterator();
		
		while (iterator.hasNext())
			archivo.writeObject( iterator.next());
		
		archivo.close();
	}
	
	
	/**
	 * Buscar un objeto T con un id determinado dentro de una lista de objetos 
	 * @param lista Lista de objetos donde buscar. 
	 * @param id Id del objeto a buscar
	 * @return Objeto encontrado. Si no se encuentra devuelve null.
	 */
	public static <T> T buscarObjeto( ArrayList<T> lista, int id)
	{
		Iterator<T> iterator = lista.iterator();
		T objeto;
		
		while (iterator.hasNext())
		{
			objeto = iterator.next();
			if (((ObjetoBD) objeto).obtenerId() == id)
				return objeto;
		}
		
		return null;
		
	}
}
