package logica.Objetos;

import java.io.Serializable;
import java.util.LinkedList;

import logica.VO.VORevision;

public class Folio implements Serializable{
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String caratula;
	private int paginas;
	
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
	
	public boolean tieneRevision(int numR){
		return false;
	}
	
	public int cantidadRevisiones(){
		return 0;
	}
	
	public void addRevision(Revision rev){
		
	}
	
	public Revision obtenerRevision(int numR){
		Revision rev = new Revision();
		
		return rev;
	}
	
	public LinkedList <VORevision> listarRevisiones(){
		LinkedList <VORevision> Lista = new LinkedList <VORevision>();
		
		return Lista;
	}
	
	public void borrarRevisiones(){
		
	}
}
