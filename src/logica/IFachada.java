package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

import logica.excepciones.PersistenciaException;
import logica.vo.VOFolioMaxRev;
import logica.vo.VORevision;
import logica.vo.VoFolio;

public interface IFachada extends Remote {

    public void altaFolio(VoFolio VoF) throws PersistenciaException, RemoteException;
    
    public LinkedList <VoFolio> listarFolios() throws RemoteException, PersistenciaException;
    
    public VOFolioMaxRev folioMasRevisado() throws PersistenciaException, RemoteException;
    
    public void altaRevision(String codFolio, String desc, int cedN) throws RemoteException, PersistenciaException;
    
    public int cantidadRevisiones(String codFolio) throws RemoteException, PersistenciaException;
    
    public LinkedList <VORevision> listarRevisiones(String codFolio, int Numero) throws RemoteException, PersistenciaException;
    
    public void borrarRevisiones(String codFolio) throws RemoteException, PersistenciaException;
    
    public VORevision kEsimo(String codFolio, int Numero) throws RemoteException, PersistenciaException;
    
    public boolean member(String Codigo) throws PersistenciaException, RemoteException;
    
    public VoFolio find(String Codigo) throws PersistenciaException, RemoteException;
    
    public void delete(String Codigo) throws PersistenciaException, RemoteException;
    
}

