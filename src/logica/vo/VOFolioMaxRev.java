package logica.vo;

import java.io.Serializable;

public class VOFolioMaxRev extends VOFolio implements Serializable{
	private static final long serialVersionUID = 1L;
	private int cantRevisiones;
	
	public VOFolioMaxRev(){
		super();
		this.setCantRevisiones(0);
		
	}
	
	public VOFolioMaxRev(int CantRevisiones, String Codigo, String Caratula, int Paginas) {
		super(Codigo, Caratula, Paginas);
		this.setCantRevisiones(CantRevisiones);
	}

	public int getCantRevisiones() {
		return cantRevisiones;
	}

	public void setCantRevisiones(int cantRevisiones) {
		this.cantRevisiones = cantRevisiones;
	}
}