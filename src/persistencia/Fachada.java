package persistencia;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

import logica.excepciones.Exc_Persistencia;
import logica.excepciones.PersistenciaException;
import logica.objetos.Folio;
import logica.vo.VORevision;
import logica.vo.VoFolio;
import persistencia.daos.DAOFolios;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;
import persistencia.poolConexiones.IConexion;
import persistencia.poolConexiones.IPoolConexiones;
import persistencia.poolConexiones.PoolConexiones;




public class Fachada extends UnicastRemoteObject implements IFachada {

    private static final long serialVersionUID = 1L;

    private static Fachada f = null;
    private IDAOFolios Folios;
    private IDAORevisiones Revisiones;
    private IPoolConexiones ipc = null;

    public static Fachada getInstancia() throws PersistenciaException, RemoteException, Exc_Persistencia {
        if (f == null) {
            Fachada.f = new Fachada();
        }
        return Fachada.f;
    }

    private Fachada() throws RemoteException, PersistenciaException, Exc_Persistencia  {
        try {
            this.Folios = new DAOFolios();
            this.ipc = new PoolConexiones();
        } catch (PersistenciaException e) {
            throw new Exc_Persistencia("Error en la conexion");
        }
    }

    public void altaFolio(VoFolio VoF) throws PersistenciaException, RemoteException {
        IConexion con = this.ipc.obtenerConexion(true);
        try {
            if(! this.Folios.member(VoF.getCodigo(), con)){
                Folio Fol = new Folio(VoF.getCodigo(), VoF.getCaratula(), VoF.getPaginas());
                this.Folios.insert(Fol, con);
            }
            else{
                throw new PersistenciaException("Error en el alta de Folio");
            }
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error en el alta de Folio");
        }
    }
    
	///////////////////////////
	//////////REVISION/////////
	///////////////////////////
    
    public void altaRevision(String codFolio, String desc, int cedN) throws RemoteException, PersistenciaException {
        IConexion ic = this.ipc.obtenerConexion(true);
        try {
        	//Verifico si existe Folio
            if(this.Folios.member(codFolio, ic)){
            	this.Revisiones.InsBack(codFolio, desc, ic);
            }
            else {
                throw new Exc_Persistencia("No existe Folio");
            }
            this.ipc.liberarConexion(ic, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(ic, false);
            throw new PersistenciaException("Error de conexion");
        }
        
    }
    
    public int cantidadRevisiones(String codFolio) throws RemoteException, PersistenciaException {
        IConexion ic = this.ipc.obtenerConexion(true);
        int Cantidad =0;
        try {
        	//Verifico si existe Folio
            if(this.Folios.member(codFolio, ic)){
            	Cantidad = this.Revisiones.Largo(codFolio, ic);
            }
            else {
                throw new Exc_Persistencia("No existe Folio");
            }
            this.ipc.liberarConexion(ic, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(ic, false);
            throw new PersistenciaException("Error de conexion");
        }
        return Cantidad;
    }
    
    public VORevision kEsimo(String codFolio, int Numero) throws RemoteException, PersistenciaException {
        IConexion ic = this.ipc.obtenerConexion(true);
        VORevision VoR = new VORevision();
        try {
        	//Verifico si existe Folio
            if(this.Folios.member(codFolio, ic)){
            	this.Revisiones.setCodigoFolio(codFolio);
            	VoR = this.Revisiones.kEsimo(Numero, ic);
            }
            else {
                throw new Exc_Persistencia("No existe Folio");
            }
            this.ipc.liberarConexion(ic, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(ic, false);
            throw new PersistenciaException("Error de conexion");
        }
        return VoR;
    }
    
    public LinkedList <VORevision> listarRevisiones(String codFolio, int Numero) throws RemoteException, PersistenciaException {
        IConexion ic = this.ipc.obtenerConexion(true);
        LinkedList <VORevision> Lista = new LinkedList <VORevision>();
        try {
        	//Verifico si existe Folio
            if(this.Folios.member(codFolio, ic)){
            	this.Revisiones.setCodigoFolio(codFolio);
            	Lista = this.Revisiones.listarRevisiones(ic);
            }
            else {
                throw new Exc_Persistencia("No existe Folio");
            }
            this.ipc.liberarConexion(ic, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(ic, false);
            throw new PersistenciaException("Error de conexion");
        }
        return Lista;
    }
    
    public void borrarRevisiones(String codFolio) throws RemoteException, PersistenciaException {
        IConexion ic = this.ipc.obtenerConexion(true);
        try {
        	//Verifico si existe Folio
            if(this.Folios.member(codFolio, ic)){
            	this.Revisiones.setCodigoFolio(codFolio);
            	this.Revisiones.borrarRevisiones(ic);
            }
            else {
                throw new Exc_Persistencia("No existe Folio");
            }
            this.ipc.liberarConexion(ic, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(ic, false);
            throw new PersistenciaException("Error de conexion");
        }
    }

}