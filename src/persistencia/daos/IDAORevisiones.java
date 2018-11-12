package persistencia.daos;

import java.util.LinkedList;

import logica.excepciones.ConsultaRevisionException;
import logica.excepciones.LecturaArchivoException;
import logica.excepciones.PersistenciaException;
import logica.objetos.Revision;
import persistencia.poolConexiones.IConexion;
import logica.vo.VORevision;


public interface IDAORevisiones {

	public String getCodigoFolio();

	public void setCodigoFolio(String codigoFolio);
	
	public void insBack(Revision rev, IConexion ic) throws PersistenciaException, LecturaArchivoException;
	
	public int largo(IConexion ic) throws ConsultaRevisionException;
	
	public VORevision kEsimo(int numero, IConexion ic) throws ConsultaRevisionException;
	
	public LinkedList <VORevision> listarRevisiones(IConexion ic) throws ConsultaRevisionException, PersistenciaException;
	
	public void borrarRevisiones(IConexion ic) throws PersistenciaException;
	
}
