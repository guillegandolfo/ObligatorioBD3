package logica.vo;

import java.io.Serializable;

public class VORevision implements Serializable{
	private static final long serialVersionUID = 1L;
	private int numero;
	private String codigoFolio;
	private String descripcion;
	
	public VORevision(){
		super();
		this.setNumero(0);
		this.setCodigoFolio("");
		this.setDescripcion("");
	}
	
	public VORevision(String codigoFolio, String descripcion) {
		super();
		this.setCodigoFolio(codigoFolio);
		this.setDescripcion(descripcion);
	}
	
	public VORevision(int numero, String codigoFolio, String descripcion) {
		super();
		this.setCodigoFolio(codigoFolio);
		this.setDescripcion(descripcion);
		this.setNumero(numero);
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCodigoFolio() {
		return codigoFolio;
	}

	public void setCodigoFolio(String codigoFolio) {
		this.codigoFolio = codigoFolio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}