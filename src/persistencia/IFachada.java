package persistencia;

import java.rmi.Remote;
import java.rmi.RemoteException;

import logica.excepciones.PersistenciaException;
import logica.vo.VoFolio;

public interface IFachada extends Remote {

    public void altaFolio(VoFolio VoF) throws PersistenciaException, RemoteException;
    
    public void insertarRevision(String codFolio, String desc, int cedN) throws RemoteException, PersistenciaException;
    
}

