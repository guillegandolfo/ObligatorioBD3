package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

import logica.excepciones.PersistenciaException;
import logica.excepciones.YaExisteFolioException;
import logica.vo.VOFolioMaxRev;
import logica.vo.VORevision;
import logica.vo.VOFolio;

public interface IFachada extends Remote {

    public void agregarFolio(VOFolio VoF) throws YaExisteFolioException, PersistenciaException, RemoteException;
    
    public LinkedList <VOFolio> listarFolios() throws RemoteException, PersistenciaException;
    
    public VOFolioMaxRev folioMasRevisado() throws PersistenciaException, RemoteException;
    
    public int agregarRevision(String codFolio, String desc) throws RemoteException, PersistenciaException;
    
    public int cantidadRevisiones(String codFolio) throws RemoteException, PersistenciaException;
    
    public LinkedList <VORevision> listarRevisiones(String codFolio) throws RemoteException, PersistenciaException;
    
    public void borrarFolioRevisiones(String codFolio) throws RemoteException, PersistenciaException;
    
}

