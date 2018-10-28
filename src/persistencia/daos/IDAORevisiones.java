package persistencia.daos;

import java.sql.SQLException;
import java.util.LinkedList;

import persistencia.poolConexiones.IConexion;
import logica.excepciones.Exc_Persistencia;
import logica.objetos.Revision;
import logica.vo.VORevision;


public interface IDAORevisiones {

	public void InsBack(String CodFolio, String Desc ,IConexion ic) throws Exc_Persistencia;
	
	public int Largo(String CodFolio, IConexion ic) throws Exc_Persistencia;
	
	public Revision kEsimo(int numero, IConexion ic) throws Exc_Persistencia;
	
	public LinkedList <VORevision> listarRevisiones(IConexion ic) throws SQLException, Exc_Persistencia;
	
	public void borrarRevision(int numero, IConexion ic) throws Exc_Persistencia;
	
}
