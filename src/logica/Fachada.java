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
import logica.vo.VOFolio;
import persistencia.Fabrica.FabricaAbstracta;
import persistencia.config.Propiedades;
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
	private FabricaAbstracta fabrica;

    //singleton
    public static Fachada getInstancia() throws ConfiguracionException, RemoteException {
        if (f == null) {
            Fachada.f = new Fachada();
        }
        return Fachada.f;
    }

    private Fachada() throws RemoteException, ConfiguracionException  {
        try {
        	Propiedades p = new Propiedades();
			this.fabrica = (FabricaAbstracta) Class.forName(p.getFabrica()).newInstance();
			
            this.daoFolios = this.fabrica.crearIDAOFolio();
			this.ipc = this.fabrica.crearIPoolConexiones();
        } catch (InstantiationException e) {
			// TODO Auto-generated catch block
        	throw new ConfiguracionException("Error en la configuracion");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new ConfiguracionException("Error en la configuracion");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new ConfiguracionException("No se encontro configuracion necesaria");
		} catch (ConfiguracionException e) {
            throw new ConfiguracionException(e.getMessage());
        }
    }

	///////////////////////////
	//////////FOLIOS///////////
	///////////////////////////
    
    public void agregarFolio(VOFolio VoF) throws YaExisteFolioException, PersistenciaException, RemoteException {
        
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

    
    public LinkedList <VOFolio> listarFolios() throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        LinkedList <VOFolio> Lista = new LinkedList <VOFolio>();
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
            throw new PersistenciaException("Error al listar folio mas revisado");
        }
        return VoF;
    }
    
	///////////////////////////
	//////////REVISION/////////
	///////////////////////////
    
    public int agregarRevision(String codFolio, String desc) throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        int numero = -1;
        try {
        	Folio folio = this.daoFolios.find(codFolio, con);
        	
        	//Si existe folio
        	if (folio != null){
        		numero = folio.cantidadRevisiones(con) + 1;
            	Revision rev = new Revision(numero, codFolio, desc);
            		
            	folio.addRevision(rev, con);
        	}else{
        		throw new PersistenciaException("No existe Folio");
        	}
		    this.ipc.liberarConexion(con, true);
		} catch (PersistenciaException e) {
			
		    this.ipc.liberarConexion(con, false);
		    throw new PersistenciaException("No existe Folio");
		} catch (Exception e) {
		    this.ipc.liberarConexion(con, false);
		    throw new PersistenciaException("Error al agregar la revision");
		}
        return numero;
    }
    
    public int cantidadRevisiones(String codFolio) throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        int cantidad = 0;
        try {
        	Folio Fol = this.daoFolios.find(codFolio, con);
        	if (Fol != null){
        		cantidad = Fol.cantidadRevisiones(con);
        	}else{
        		throw new Exc_Persistencia("No existe Folio");
        	}
        	
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
        return cantidad;
    }
    
    public LinkedList <VORevision> listarRevisiones(String codFolio) throws RemoteException, PersistenciaException {
    	
        IConexion con = this.ipc.obtenerConexion(true);
        LinkedList <VORevision> lista = new LinkedList <VORevision>();
        try {
        	Folio fol = this.daoFolios.find(codFolio, con);
        	if (fol != null){
        		lista = fol.listarRevisiones(con);
        	}else{
        		throw new PersistenciaException("No existe Folio");
        	}
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
        return lista;
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

}