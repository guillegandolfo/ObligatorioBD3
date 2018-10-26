package logica.excepciones;

public class ConexionBDException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConexionBDException (String mensaje) {
		
		super(mensaje);
	}

}
