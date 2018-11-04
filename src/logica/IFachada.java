package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

import logica.excepciones.PersistenciaException;
import logica.excepciones.YaExisteFolioException;
import logica.vo.VOFolioMaxRev;
import logica.vo.VORevision;
import logica.vo.VoFolio;

public interface IFachada extends Remote {

    public void agregarFolio(VoFolio VoF) throws YaExisteFolioException, PersistenciaException, RemoteException;
    
    public LinkedList <VoFolio> listarFolios() throws RemoteException, PersistenciaException;
    
    public VOFolioMaxRev folioMasRevisado() throws PersistenciaException, RemoteException;
    
    public void agregarRevision(String codFolio, String desc, int cedN) throws RemoteException, PersistenciaException;
    
    public int cantidadRevisiones(String codFolio) throws RemoteException, PersistenciaException;
    
    public LinkedList <VORevision> listarRevisiones(String codFolio, int Numero) throws RemoteException, PersistenciaException;
    
    public void borrarFolioRevisiones(String codFolio) throws RemoteException, PersistenciaException;
    
}

