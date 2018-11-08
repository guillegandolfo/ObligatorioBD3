package logica.vo;

import java.io.Serializable;


public class VOFolio implements Serializable{
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String caratula;
	private int paginas;
	
	public VOFolio(){
		super();
		this.setCodigo("");
		this.setCaratula("");
		this.setPaginas(1);
	}
	
	public VOFolio(String Codigo, String Caratula, int Paginas) {
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
}