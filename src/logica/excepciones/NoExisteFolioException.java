package logica.excepciones;

public class NoExisteFolioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoExisteFolioException (String mensaje) {
		
		super(mensaje);
	}

}
