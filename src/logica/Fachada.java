package logica;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

import logica.excepciones.ConfiguracionException;
import logica.excepciones.Exc_Persistencia;
import logica.excepciones.PersistenciaException;
import logica.excepciones.YaExisteFolioException;
import logica.objetos.Folio;
import logica.objetos.Revision;
import logica.vo.VOFolioMaxRev;
import logica.vo.VORevision;
import logica.vo.VoFolio;
import persistencia.daos.DAOFolios;
import persistencia.daos.IDAOFolios;
import persistencia.poolConexiones.IConexion;
import persistencia.poolConexiones.IPoolConexiones;
import persistencia.poolConexiones.PoolConexiones;


public class Fachada extends UnicastRemoteObject implements IFachada {

    private static final long serialVersionUID = 1L;

    private static Fachada f = null;
    private IDAOFolios daoFolios;
    private IPoolConexiones ipc = null;

    //singleton
    public static Fachada getInstancia() throws ConfiguracionException, RemoteException {
        if (f == null) {
            Fachada.f = new Fachada();
        }
        return Fachada.f;
    }

    private Fachada() throws RemoteException, ConfiguracionException  {
        try {
            this.daoFolios = new DAOFolios();
            this.ipc = new PoolConexiones();
        } catch (ConfiguracionException e) {
            throw new ConfiguracionException(e.getMessage());
        }
    }

	///////////////////////////
	//////////FOLIOS///////////
	///////////////////////////
    
    public void agregarFolio(VoFolio VoF) throws YaExisteFolioException, PersistenciaException, RemoteException {
        
    	 IConexion con = null;
    	 try {
    		 con = this.ipc.obtenerConexion(true);
       
        	//Si no existe el folio a insertar
            if(! this.daoFolios.member(VoF.getCodigo(), con)) {
                Folio Fol = new Folio(VoF.getCodigo(), VoF.getCaratula(), VoF.getPaginas());
                this.daoFolios.insert(Fol, con);
            }
            else{
                throw new YaExisteFolioException("El folio indicado ya existe");
            }
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error en el alta de Folio");
        }
    }

    
    public LinkedList <VoFolio> listarFolios() throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        LinkedList <VoFolio> Lista = new LinkedList <VoFolio>();
        try {
        	Lista = this.daoFolios.listarFolios(con);
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
        return Lista;
    }
    
    
    public VOFolioMaxRev folioMasRevisado() throws PersistenciaException, RemoteException {
        IConexion con = this.ipc.obtenerConexion(true);
        VOFolioMaxRev VoF = new VOFolioMaxRev();
        try {
            VoF = this.daoFolios.folioMasRevisado(con); 
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error en el alta de Folio");
        }
        return VoF;
    }
    
	///////////////////////////
	//////////REVISION/////////
	///////////////////////////
    
    public void agregarRevision(String codFolio, String desc) throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        try {
        	Folio Fol = this.daoFolios.find(codFolio, con);
        	
        	//Si existe folio
        	if (Fol != null){
        		int Numero = Fol.cantidadRevisiones(con) + 1;
            	Revision rev = new Revision(Numero, codFolio, desc);
            	
            	Fol.addRevision(rev, con);
        	}else{
        		throw new Exc_Persistencia("No existe Folio");
        	}
        	
		    this.ipc.liberarConexion(con, true);
		} catch (Exception e) {
		    this.ipc.liberarConexion(con, false);
		    throw new PersistenciaException("Error de conexion");
		}
    }
    
    public int cantidadRevisiones(String codFolio) throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        int Cantidad =0;
        try {
        	Folio Fol = this.daoFolios.find(codFolio, con);
        	if (Fol != null){
            	Cantidad = Fol.cantidadRevisiones(con);
        	}else{
        		throw new Exc_Persistencia("No existe Folio");
        	}
        	
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
        return Cantidad;
    }
    
    public LinkedList <VORevision> listarRevisiones(String codFolio, int Numero) throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        LinkedList <VORevision> Lista = new LinkedList <VORevision>();
        try {
        	Folio Fol = this.daoFolios.find(codFolio, con);
        	if (Fol != null){
        		Lista = Fol.listarRevisiones(con);
        	}else{
        		throw new Exc_Persistencia("No existe Folio");
        	}
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
        return Lista;
    }
    
    public void borrarFolioRevisiones(String codFolio) throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        try {
        	Folio folio = this.daoFolios.find(codFolio, con);
        	if (folio != null){
        		
        		this.daoFolios.delete(codFolio, con);
        	}else{
        		throw new PersistenciaException("No existe Folio");
        	}
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
    }

	public void agregarRevision(String codFolio, String desc, int cedN)
			throws RemoteException, PersistenciaException {
		// TODO Auto-generated method stub
		
	}

}