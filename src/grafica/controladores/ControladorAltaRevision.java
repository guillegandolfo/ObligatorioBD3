package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import persistencia.config.Propiedades;
import grafica.ventanas.FAltaRevision;
import logica.IFachada;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.PersistenciaException;

public class ControladorAltaRevision {
	private IFachada f;
	private FAltaRevision ven;
	
	public ControladorAltaRevision(FAltaRevision ventana)throws MalformedURLException, RemoteException, NotBoundException, ConfiguracionException {
		try{
			ven = ventana;
			System.out.println("2.1");
			Propiedades p = new Propiedades();
			System.out.println("2.2");
			String puerto = p.buscar("Puerto");
			System.out.println("2.3");
			String ip = p.buscar("Ip");
			System.out.println("2.4");
			f = (IFachada) Naming.lookup("//"+ip+":"+puerto+"/logica/IFachada");
			System.out.println("2.5");
		} catch (MalformedURLException | RemoteException | NotBoundException | ConfiguracionException e) {
			ven.mostrarError("Error en la controladora", 0);
		}
	}
	
	public void altaRevision(String codFolio, String descripcion){
		
		if(!codFolio.isEmpty()){	
			if(!descripcion.isEmpty()){
				
				try {	
					f.altaRevision(codFolio, descripcion);
				} catch (RemoteException | PersistenciaException e) {
					ven.mostrarError(e.toString(), 0);
				}

			}else{
				ven.mostrarError("La descripcion de la Revision esta vacia", 0);
			}
		}else{
			ven.mostrarError("El Codigo del Folio esta vacio", 0);
		}
	}
	
}


