package persistencia.daos;

import java.util.LinkedList;

import persistencia.poolConexiones.IConexion;
import logica.excepciones.PersistenciaException;
import logica.objetos.Folio;
import logica.vo.VOFolioMaxRev;
import logica.vo.VOFolio;

public interface IDAOFolios {

    public boolean member(String cod, IConexion ic) throws PersistenciaException;

    public void insert(Folio fol, IConexion ic) throws PersistenciaException;

    public Folio find(String cod, IConexion ic) throws PersistenciaException;
    
    public void delete(String cod, IConexion ic) throws PersistenciaException;

    public LinkedList<VOFolio> listarFolios(IConexion ic) throws PersistenciaException;
    
    public VOFolioMaxRev folioMasRevisado(IConexion ic) throws PersistenciaException;
    
}
