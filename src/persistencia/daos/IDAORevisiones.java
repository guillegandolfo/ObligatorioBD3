package persistencia.daos;

import persistencia.poolConexiones.IConexion;
import logica.excepciones.Exc_Persistencia;


public interface IDAORevisiones {

	public void InsBack(String CodFolio, String Desc ,IConexion ic) throws Exc_Persistencia;
	
}
