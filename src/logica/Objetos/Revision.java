package logica.Objetos;

import java.io.Serializable;

public class Revision implements Serializable{
	private static final long serialVersionUID = 1L;
	private int numero;
	private String codigoFolio;
	private String descripcion;
	
	public Revision(){
		super();
		this.setNumero(0);
		this.setCodigoFolio("");
		this.setDescripcion("");
	}
	
	public Revision(int Numero, String CodigoFolio, String Descripcion) {
		super();
		this.setNumero(Numero);;
		this.setCodigoFolio(CodigoFolio);
		this.setDescripcion(Descripcion);
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
