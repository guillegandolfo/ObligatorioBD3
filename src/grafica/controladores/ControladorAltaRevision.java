package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import persistencia.config.Propiedades;
import grafica.ventanas.FAltaRevision;
import logica.Fachada;
import logica.IFachada;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.Exc_Persistencia;
import logica.excepciones.PersistenciaException;

public class ControladorAltaRevision {
	private IFachada f;
	private FAltaRevision ven;
	
	public ControladorAltaRevision(FAltaRevision ventana)throws MalformedURLException, RemoteException, NotBoundException, ConfiguracionException, Exc_Persistencia {
		try{
			ven = ventana;
<<<<<<< HEAD

			Propiedades p = new Propiedades();
			String puerto = p.getPuertoServidor();
			String ip = p.getIpServidor();
			this.f = (IFachada) Naming.lookup("//" + ip + ":" + puerto + "/logica");
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
=======
			Propiedades p = new Propiedades();
			String puerto = p.buscar("Puerto");
			String ip = p.buscar("Ip");
			f = (IFachada) Naming.lookup("//"+ip+":"+puerto+"/logica/IFachada");
		} catch (MalformedURLException | RemoteException | NotBoundException | ConfiguracionException e) {
>>>>>>> origin/master
			ven.mostrarError("Error en la controladora", 0);
		}
	}
	
	public void altaRevision(String codFolio, String descripcion){
		
		if(!codFolio.isEmpty()){	
			if(!descripcion.isEmpty()){
				
				try {	
					System.out.println("Entra a agregar");
					this.f.agregarRevision(codFolio, descripcion);
					System.out.println("Sale del agregar");
					//f.altaRevision(codFolio, descripcion);
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


