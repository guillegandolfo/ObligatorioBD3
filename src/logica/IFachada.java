package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

import logica.excepciones.NoExisteFolioException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.YaExisteFolioException;
import logica.vo.VOFolioMaxRev;
import logica.vo.VORevision;
import logica.vo.VoFolio;

public interface IFachada extends Remote {

    public void agregarFolio(VoFolio VoF) throws YaExisteFolioException, PersistenciaException, RemoteException;
    
    public LinkedList <VoFolio> listarFolios() throws RemoteException, PersistenciaException;
    
    public VOFolioMaxRev folioMasRevisado() throws PersistenciaException, RemoteException;
    
    public int agregarRevision(String codFolio, String desc) throws RemoteException, PersistenciaException;
    
   // public int cantidadRevisiones(String codFolio) throws RemoteException, PersistenciaException, NoExisteFolioException;
    
    public LinkedList <VORevision> listarRevisiones(String codFolio) throws RemoteException, PersistenciaException;
    
    public String darDescripcion(String codFolio, int Numero) throws RemoteException, PersistenciaException;
    
    public void borrarFolioRevisiones(String codFolio) throws RemoteException, PersistenciaException;
    
}

