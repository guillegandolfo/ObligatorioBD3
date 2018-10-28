package logica.excepciones;

public class PersistenciaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PersistenciaException (String mensaje) {
		
		super(mensaje);
	}

}
