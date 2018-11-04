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
    private IDAOFolios folios;
    private IPoolConexiones ipc = null;

    //singleton
    public static Fachada getInstancia() throws ConfiguracionException, RemoteException, Exc_Persistencia {
        if (f == null) {
            Fachada.f = new Fachada();
        }
        return Fachada.f;
    }

    private Fachada() throws RemoteException, ConfiguracionException, Exc_Persistencia  {
        try {
            this.folios = new DAOFolios();
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
            if(! this.folios.member(VoF.getCodigo(), con)) {
                Folio Fol = new Folio(VoF.getCodigo(), VoF.getCaratula(), VoF.getPaginas());
                this.folios.insert(Fol, con);
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
    
    /*public boolean member(String Codigo) throws PersistenciaException, RemoteException {
        IConexion con = this.ipc.obtenerConexion(true);
        boolean existe = false;
        try {
        		//Uso directamente el metodo en el DAOFolios
        		existe = this.folios.member(Codigo, con);
        		this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("No se encontro Folio");
        }
        return existe;
    }*/
    
    public VoFolio find(String Codigo) throws PersistenciaException, RemoteException {
        IConexion con = this.ipc.obtenerConexion(true);
        VoFolio VoF = null;
        try {
        	Folio Fo = this.folios.find(Codigo, con);
        	
        	//Si existe el folio
            if(Fo != null){
                VoF = new VoFolio(Fo.getCodigo(), Fo.getCaratula(), Fo.getPaginas());
            }
            else{
                throw new PersistenciaException("No se encontro el Folio");
            }
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error en el alta de Folio");
        }
        return VoF;
    }
    
    public void delete(String Codigo) throws PersistenciaException, RemoteException {
        IConexion con = this.ipc.obtenerConexion(true);
        try {
        	//Si existe el folio a eliminar
            if(this.folios.member(Codigo, con)){
                this.folios.delete(Codigo, con); 
            }
            else{
                throw new PersistenciaException("No se encontro Folio a eliminar");
            }
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error al eliminar Folio");
        }
    }
    
    public LinkedList <VoFolio> listarFolios() throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        LinkedList <VoFolio> Lista = new LinkedList <VoFolio>();
        try {
        	Lista = this.folios.listarFolios(con);
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
            VoF = this.folios.folioMasRevisado(con); 
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
    	System.out.println("Entro a la fachada");
    	IConexion con = this.ipc.obtenerConexion(true);
    	System.out.println("Tengo Conexion");
        try {
        	System.out.println("1");
        	Folio Fol = this.folios.find(codFolio, con);
        	System.out.println("2");
        	//Si existe folio
        	if (Fol != null){
        		System.out.println("3");
        		int Numero = Fol.cantidadRevisiones(con) + 1;
        		System.out.println("4");
            	Revision rev = new Revision(Numero, codFolio, desc);
            	System.out.println("5");
            	Fol.addRevision(rev, con);
            	System.out.println("6");
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
        	Folio Fol = this.folios.find(codFolio, con);
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
    
    public VORevision kEsimo(String codFolio, int Numero) throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        VORevision VoR = new VORevision();
        try {
        	Folio Fol = this.folios.find(codFolio, con);
        	if (Fol != null){
            	Revision rev = Fol.obtenerRevision(Numero, con);
            	VoR.setNumero(Numero);
            	VoR.setCodigoFolio(codFolio);
            	VoR.setDescripcion(rev.getDescripcion());
        	}else{
        		throw new Exc_Persistencia("No existe Folio");
        	}
        	
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
        return VoR;
    }
    
    public LinkedList <VORevision> listarRevisiones(String codFolio, int Numero) throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        LinkedList <VORevision> Lista = new LinkedList <VORevision>();
        try {
        	Folio Fol = this.folios.find(codFolio, con);
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
        	Folio Fol = this.folios.find(codFolio, con);
        	if (Fol != null){
        		Fol.borrarRevisiones(con);
        	}else{
        		throw new Exc_Persistencia("No existe Folio");
        	}
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
    }


}