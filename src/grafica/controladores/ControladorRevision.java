package grafica.controladores;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import persistencia.config.Propiedades;
import grafica.ventanas.FAltaRevision;
import logica.IFachada;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.PersistenciaException;
import logica.vo.VORevision;

public class ControladorRevision {
	private IFachada fachada;
	private FAltaRevision ventana;
	
	public ControladorRevision(FAltaRevision jinternalFrame) {
	
		super();
		// TODO Auto-generated constructor stub
		this.ventana = jinternalFrame;
		
		try {
			Propiedades p = new Propiedades();
			String ip = p.getIpServidor();
			String puerto = p.getPuertoServidor();
			int port = Integer.parseInt(puerto);

			fachada = (IFachada)Naming.lookup("//" + ip + ":" + port + "/logica");
		} catch (ConfiguracionException e){
			jinternalFrame.imprimirVentana(e.getMessage());
		} catch (IOException e) {
		// TODO Auto-generated catch block
	    	jinternalFrame.imprimirVentana("Ha ocurrido un error al establecer conexion con el servidor");
	    }
		catch (NotBoundException e) {
			// TODO Auto-generated catch block
			jinternalFrame.imprimirVentana(e.getMessage());
		}

	}
	
	public int altaRevision(String codFolio, String descripcion){
		
		int numero = -1;
		if(!codFolio.isEmpty()) {	
			if(!descripcion.isEmpty()){
				
				try {
					numero = fachada.agregarRevision(codFolio, descripcion);
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					ventana.imprimirVentana(e.getMessage());
				} catch (PersistenciaException e) {
					// TODO Auto-generated catch block
					ventana.imprimirVentana(e.getMessage());
				}

			}else{
				ventana.imprimirVentana("La descripcion de la Revision esta vacia");
			}
		}else{
			ventana.imprimirVentana("El Codigo del Folio esta vacio");
		}
		return numero;
	}
	
	public LinkedList<VORevision> listarRevisiones(String codFolio) {
		
		LinkedList<VORevision> lista = new LinkedList<VORevision>();
		try {
			lista = fachada.listarRevisiones(codFolio);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana("Error en el servidor. " + e.getMessage());
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana(e.getMessage());
		}
		return lista;
	}
	
}


