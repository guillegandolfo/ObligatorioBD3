package persistencia.poolConexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import persistencia.config.Propiedades;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.PersistenciaException;

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
	
	public PoolConexiones () throws ConfiguracionException {
		
		try {
			
			Propiedades p = new Propiedades();
			driver = p.getDriver();
			url = p.getUrl();
			user = p.getUser();
			password = p.getPass();
			
			//Definir pool
			tamanio = 5;//leer de properties
			tope = 0;
			conexiones = new Conexion[tamanio];

		} catch (ConfiguracionException e) {
			// TODO Auto-generated catch block
			throw new ConfiguracionException("Ocurrio un error en el pool de Conexiones");
		}	
	}
	
	public IConexion obtenerConexion(boolean modifica) throws PersistenciaException {
		
		IConexion conexion = null;

		synchronized (this) {
			while (conexion == null) {
				//1- Verificar si tengo alguna con para prestar en el arreglo
				if (creadas > 0 && tope > 0) {
					//2- Si hay alguna, la saco del arreglo y la retorno
					conexion = this.conexiones[tope];
					
					try {
						((Conexion) conexion).getConexion().setAutoCommit(false);
						((Conexion) conexion).getConexion().setTransactionIsolation(nivelTransaccionalidad);
					} catch(SQLException ex) {
						throw new PersistenciaException("Ocurrio un error");
					}
					tope--;
				}
				else if (creadas < tamanio) {
					//3- Si no hay ning, puedo crear otra ? (tamanio vs creadas)
					//4- Si puedo la creo y la retorno (creadas++)
					
					try {
						Class.forName(driver);
						Connection con = DriverManager.getConnection(url, user, password);
						conexion = new Conexion(con);
						creadas++;
						
						try {
							((Conexion) conexion).getConexion().setAutoCommit(false);
							((Conexion) conexion).getConexion().setTransactionIsolation(nivelTransaccionalidad);
						} catch(SQLException ex) {
							throw new PersistenciaException("Ocurrio un error");
						}
						
					} catch (ClassNotFoundException e) {
						
						throw new PersistenciaException("Ocurrio un error");
					} catch (SQLException e) {
						throw new PersistenciaException("Ocurrio un error");
					}
				}
				else {
					//5- Si no puedo crear a dormir (wait indefinido )
					try {
						wait();
					} catch (InterruptedException e) {
						throw new PersistenciaException("Ocurrio un error");
					}
				}
			}
		}
		
		return conexion;
	}
	
	public void liberarConexion(IConexion con, boolean ok) throws PersistenciaException{
		// TODO Auto-generated method stub
		
		//1- Recibe con y la agrega en el arreglo
		//2- Hace notify
		synchronized (this) {
			if (ok) {
				try {
					((Conexion) con).getConexion().commit();
				} catch(SQLException ex) {
					throw new PersistenciaException("Ocurrio un error");
				}
			}
			else {
				try {
					((Conexion) con).getConexion().rollback();
				} catch(SQLException ex) {
					throw new PersistenciaException("Ocurrio un error");
				}
			}
			notify();
		}
		
	}
	
	
	
}
