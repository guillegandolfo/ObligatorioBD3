package persistencia.daos;

import java.util.LinkedList;

import logica.excepciones.ConfiguracionException;
import logica.excepciones.ConsultaRevisionException;
import logica.excepciones.LecturaArchivoException;
import persistencia.poolConexiones.IConexion;
import logica.excepciones.PersistenciaException;
import logica.objetos.Folio;
import logica.vo.VOFolioMaxRev;
import logica.vo.VoFolio;

public interface IDAOFolios {

    public boolean member(String cod, IConexion ic) throws PersistenciaException;

    public void insert(Folio fol, IConexion ic) throws PersistenciaException, LecturaArchivoException;

    public Folio find(String cod, IConexion ic) throws PersistenciaException, LecturaArchivoException, ConfiguracionException;
    
    public void delete(String cod, IConexion ic) throws PersistenciaException;

    public LinkedList<VoFolio> listarFolios(IConexion ic) throws PersistenciaException, LecturaArchivoException, ConfiguracionException;
    
    public VOFolioMaxRev folioMasRevisado(IConexion ic) throws PersistenciaException, ConsultaRevisionException;
    
}
