package grafica.ventanas;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;


public class Ventana {
	
	/*Ventana que se encuentra abierta*/
	private static Ventana ven = null;
	
	public Ventana() {
		super();
		ven = null;
	}


	public void mostrarError(String mensaje, int tipoError){
		//En el caso que sea una excepsion por defecto, borra desde los dos puntos hasta el principio
		if(mensaje.contains(":")){
			mensaje = mensaje.substring(mensaje.lastIndexOf(':')+2);
		}
		switch (tipoError) {
		case 0:
			JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
			break;

		case 1:
			JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
			break;
			
		case 2:
			JOptionPane.showMessageDialog(null, mensaje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
			break;
		}
	}
	
	public void mostrarCorrecto(String mensaje){
		JOptionPane.showMessageDialog(null, mensaje);
	}
	
	
	
	public void setVentanaAbierta(Ventana ventana){
		Ventana.ven = ventana;
	}
	
	public Ventana getVentanaAbierta(){
		return Ventana.ven;
	}
	
	public boolean esNumerico(char c){
		boolean valida=false;
		switch (c) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':	
		case '.':
			valida=true;
			break;
		}
		
		return valida;
	}
	
	public boolean esEntero(char c){
		boolean valida=false;
		switch (c) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':	
			valida=true;
			break;
		}
		
		return valida;
	}
	
	public void escribirNumericoConPunto(String texto, KeyEvent e){
		if(texto.contains(".")){
			if(e.getKeyChar() == '.'){
				e.consume();
			}
		}
		else{
			if(! esNumerico(e.getKeyChar())){
				e.consume();
			}
		}
	}
	
	public void toFront(){
		
	}
	
}
