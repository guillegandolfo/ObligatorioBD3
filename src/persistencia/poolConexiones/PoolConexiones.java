package logicaPersistencia.accesoBD;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import logicaPersistencia.excepciones.PersistenciaException;

public class PoolConexiones implements IPoolConexiones {

	private String driver;
	private String url;
	private String user;
	private String password;
	private int nivelTransaccionalidad; 
	private Conexion[] conexiones;
	private int tamanio;
	private int creadas;
	private int tope;
	
	public PoolConexiones () throws PersistenciaException {
		
		try {
			Properties p = new Properties();
			String archivo = "config/config.properties";
			p.load(new FileInputStream(archivo));
			
			driver = p.getProperty("driver");
			url = p.getProperty("url");
			user = p.getProperty("username");
			password = p.getProperty("password");
			
			//Definir pool
			tamanio = 5;//leer de properties
			tope = 0;
			conexiones = new Conexion[tamanio];

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new PersistenciaException("Ocurrio un error");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new PersistenciaException("Ocurrio un error");
		}	
	}
	
	@Override
	public IConexion obtenerConexion(boolean modifica) throws PersistenciaException {
		
		IConexion conexion = null;
		
		synchronized (this) {
			//1- Verificar si tengo alguna con para prestar en el arreglo
			
		}

		
		//2- Si hay alguna, la saco del arreglo y la retorno
		//3- Si no hay ning, puedo crear otra ? (tamanio vs creadas)
		//4- Si puedo la creo y la retorno (creadas++)
		//5- Si no puedo crear a dormir (wait indefinido )
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
			conexion = new Conexion(con);
			
		} catch (ClassNotFoundException e) {
			
			throw new PersistenciaException("Ocurrio un error");
		} catch (SQLException e) {

			throw new PersistenciaException("Ocurrio un error");
		}
		return conexion;
	}
	
	@Override
	public void liberarConexion(IConexion con, boolean ok) throws PersistenciaException{
		// TODO Auto-generated method stub
		
		//1- Recibe con y la agrega en el arreglo
		//2- Hace notify
		synchronized (this) {
			
		}
		
	}
	
	
	
}
