package logica.objetos;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;

import persistencia.daos.IDAORevisiones;
import persistencia.poolConexiones.IConexion;
import logica.excepciones.Exc_Persistencia;
import logica.vo.VORevision;

public class Folio implements Serializable{
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String caratula;
	private int paginas;
	private IDAORevisiones Revisiones;
	
	public Folio(){
		super();
		this.setCodigo("");
		this.setCaratula("");
		this.setPaginas(1);
	}
	
	public Folio(String Codigo, String Caratula, int Paginas) {
		super();
		this.setCodigo(Codigo);
		this.setCaratula(Caratula);
		this.setPaginas(Paginas);
		this.Revisiones.setCodigoFolio(Codigo);
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
	
	public boolean tieneRevision(int numR, IConexion con) throws Exc_Persistencia{
		
		boolean tiene = false;
		int largo = this.Revisiones.Largo(con);
		if (largo != 0){
			tiene = true;
		}
		
		return tiene;
	}
	
	public int cantidadRevisiones(IConexion con) throws Exc_Persistencia{
		return this.Revisiones.Largo(con);
	}
	
	public void addRevision(Revision rev, IConexion con) throws Exc_Persistencia{
		this.Revisiones.InsBack(rev.getDescripcion(), con);
	}
	
	public Revision obtenerRevision(int numR, IConexion con) throws Exc_Persistencia{
		VORevision rev = this.Revisiones.kEsimo(numR, con);
		Revision revision = new Revision(rev.getNumero(), rev.getCodigoFolio(), rev.getDescripcion());
		return revision;
	}
	
	public LinkedList <VORevision> listarRevisiones(IConexion con) throws SQLException, Exc_Persistencia{
		
		return this.Revisiones.listarRevisiones(con);
	}
	
	public void borrarRevisiones(IConexion con) throws Exc_Persistencia{
		this.Revisiones.borrarRevisiones(con);
	}
}
