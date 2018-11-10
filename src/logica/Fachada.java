package logica;

import logica.excepciones.ConfiguracionException;
import logica.excepciones.NoExisteFolioException;
import logica.excepciones.LecturaArchivoException;
import logica.excepciones.ConsultaRevisionException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.YaExisteFolioException;
import logica.objetos.Folio;
import logica.objetos.Revision;
import logica.vo.VOFolioMaxRev;
import logica.vo.VORevision;
import logica.vo.VoFolio;
import persistencia.config.Propiedades;
import persistencia.daos.IDAOFolios;
import persistencia.fabrica.FabricaAbstracta;
import persistencia.poolConexiones.IConexion;
import persistencia.poolConexiones.IPoolConexiones;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;


public class Fachada extends UnicastRemoteObject implements IFachada {

    private static final long serialVersionUID = 1L;

    private static Fachada f = null;
    private IDAOFolios daoFolios;
    private IPoolConexiones ipc = null;
    private FabricaAbstracta fabrica;

    private Fachada() throws RemoteException, ConfiguracionException {
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

    //singleton
    public static Fachada getInstancia() throws ConfiguracionException, RemoteException {
        if (f == null) {
            Fachada.f = new Fachada();
        }
        return Fachada.f;
    }

    ///////////////////////////
    //////////FOLIOS///////////
    ///////////////////////////

    public void agregarFolio(VoFolio voFolio) throws YaExisteFolioException, PersistenciaException, RemoteException {

        IConexion con = null;
        try {
            con = this.ipc.obtenerConexion(true);

            //Si no existe el folio a insertar
            if (!this.daoFolios.member(voFolio.getCodigo(), con)) {
                Folio folio = new Folio(voFolio.getCodigo(), voFolio.getCaratula(), voFolio.getPaginas());
                this.daoFolios.insert(folio, con);
            } else {
                throw new YaExisteFolioException("El folio indicado ya existe");
            }
            this.ipc.liberarConexion(con, true);
        } catch (YaExisteFolioException e) {
            this.ipc.liberarConexion(con, false);
            throw new YaExisteFolioException(e.getMessage());
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error en el alta de Folio");
        }
    }


    public LinkedList<VoFolio> listarFolios() throws RemoteException, PersistenciaException {
        IConexion con = this.ipc.obtenerConexion(true);
        LinkedList<VoFolio> lista = new LinkedList<VoFolio>();
        try {
            lista = this.daoFolios.listarFolios(con);
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion al listar folio");
        }
        return lista;
    }


    public VOFolioMaxRev folioMasRevisado() throws PersistenciaException, RemoteException {
        IConexion con = this.ipc.obtenerConexion(true);
        VOFolioMaxRev voFolio = new VOFolioMaxRev();
        try {
            voFolio = this.daoFolios.folioMasRevisado(con);
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error al listar folio mas revisado");
        }
        return voFolio;
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
            //if (folio != null) {
                numero = folio.cantidadRevisiones(con) + 1;
                Revision rev = new Revision(numero, codFolio, desc);

                folio.addRevision(rev, con);
            /*} else {
                throw new PersistenciaException("No existe Folio");
            }*/
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

    /*public int cantidadRevisiones(String codFolio) throws RemoteException, PersistenciaException, NoExisteFolioException {
        IConexion con = this.ipc.obtenerConexion(true);
        int cantidad = 0;
        try {
            Folio folio = this.daoFolios.find(codFolio, con);
            if (folio != null) {
                cantidad = folio.cantidadRevisiones(con);
            } else {
                throw new NoExisteFolioException("No existe Folio");
            }

            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
        return cantidad;
    }*/

    public LinkedList<VORevision> listarRevisiones(String codFolio) throws RemoteException, PersistenciaException {

        IConexion con = this.ipc.obtenerConexion(true);
        LinkedList<VORevision> lista = new LinkedList<VORevision>();
        try {
            Folio folio = this.daoFolios.find(codFolio, con);
            if (folio != null) {
                lista = folio.listarRevisiones(con);
            } else {
                throw new PersistenciaException("No existe Folio");
            }
            this.ipc.liberarConexion(con, true);
        } catch (PersistenciaException e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException(e.getMessage());
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
        return lista;
    }
	    public String darDescripcion(String codFolio, int Numero) throws RemoteException, PersistenciaException {
    	IConexion con = this.ipc.obtenerConexion(true);

    		Folio folio;
    		Revision rev = new Revision();
			try {
				folio = this.daoFolios.find(codFolio, con);
				boolean sePuede = folio.tieneRevision(Numero, con);
				if (sePuede){
					rev = folio.obtenerRevision(Numero, con);
				}else{
					throw new PersistenciaException("No existe Revision");
				}
					
			} catch (LecturaArchivoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConfiguracionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConsultaRevisionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return rev.getDescripcion();
    	
    }
    public void borrarFolioRevisiones(String codFolio) throws RemoteException, PersistenciaException {

        IConexion con = this.ipc.obtenerConexion(true);
        try {
            Folio folio = this.daoFolios.find(codFolio, con);
            if (folio != null) {

                folio.borrarRevisiones(con);
                this.daoFolios.delete(codFolio, con);
            } else {
                throw new PersistenciaException("No existe Folio");
            }
            this.ipc.liberarConexion(con, true);
        } catch (Exception e) {
            this.ipc.liberarConexion(con, false);
            throw new PersistenciaException("Error de conexion");
        }
    }

}