package logica.excepciones;

public class PersistenciaRevisionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PersistenciaRevisionException (String mensaje) {
		
		super(mensaje);
	}

}
