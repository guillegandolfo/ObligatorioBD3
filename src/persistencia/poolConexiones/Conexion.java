package persistencia.poolConexiones;

import java.sql.Connection;

public class Conexion implements IConexion {

	private Connection con;

	public Conexion (Connection con) {
		this.con = con;
	}
	
	public Connection getConexion() {
		return con;
	}

	public void setConexion(Connection con) {
		this.con = con;
	}
	
	
}
