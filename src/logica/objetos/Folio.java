package logica.objetos;

import logica.excepciones.ConfiguracionException;
import logica.excepciones.ConsultaRevisionException;
import logica.excepciones.PersistenciaException;
import logica.vo.VORevision;
import persistencia.config.Propiedades;
import persistencia.daos.DAORevisiones;
import persistencia.daos.IDAORevisiones;
import persistencia.Fabrica.FabricaAbstracta;
import persistencia.poolConexiones.IConexion;

import java.io.Serializable;
import java.util.LinkedList;

public class Folio implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codigo;
	private String caratula;
	private int paginas;
	private IDAORevisiones revisiones;
    private FabricaAbstracta fabrica;
	
	public Folio() throws ConfiguracionException {
		super();
//		this.setCodigo("");
//		this.setCaratula("");
//		this.setPaginas(0);

        try {
            Propiedades p = new Propiedades();
            this.fabrica = (FabricaAbstracta) Class.forName(p.getFabrica()).newInstance();
            this.revisiones =  this.fabrica.crearIDAORevisiones(codigo);

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
	
	public Folio(String codigo, String caratula, int paginas) throws ConfiguracionException {
		super();
		this.setCodigo(codigo);
		this.setCaratula(caratula);
		this.setPaginas(paginas);


        try {
            Propiedades p = new Propiedades();
            this.fabrica = (FabricaAbstracta) Class.forName(p.getFabrica()).newInstance();
            this.revisiones =  this.fabrica.crearIDAORevisiones(codigo);

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

    public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCaratula() {
		return caratula;
	}

	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

    public void setRevisiones(IDAORevisiones revisiones) {
        this.revisiones = revisiones;
    }

    public boolean tieneRevision(int numR, IConexion con) throws ConsultaRevisionException {
		
		boolean tiene = false;
		int largo = this.revisiones.largo(con);
		if (largo != 0){
			tiene = true;
		}
		
		return tiene;
	}
	
	public int cantidadRevisiones(IConexion con) throws ConsultaRevisionException {
		return this.revisiones.largo(con);
	}
	
	public void addRevision(Revision rev, IConexion con) throws PersistenciaException {
		this.revisiones.insBack(rev, con);
	}
	
	public Revision obtenerRevision(int numR, IConexion con) throws ConsultaRevisionException {
		VORevision rev = this.revisiones.kEsimo(numR, con);
		Revision revision = new Revision(rev.getNumero(), rev.getCodigoFolio(), rev.getDescripcion());
		return revision;
	}
	
	public LinkedList <VORevision> listarRevisiones(IConexion con) throws ConsultaRevisionException {
		
		return this.revisiones.listarRevisiones(con);
	}
	
	public void borrarRevisiones(IConexion con) throws PersistenciaException {
		this.revisiones.borrarRevisiones(con);
	}
	
	public void borrarFolio(IConexion con) throws PersistenciaException {
		this.revisiones.borrarRevisiones(con);
	}
}
