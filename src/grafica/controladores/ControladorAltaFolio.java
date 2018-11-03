package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import persistencia.config.Propiedades;
import grafica.ventanas.FAltaFolio;
import logica.IFachada;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.Exc_Persistencia;
import logica.excepciones.PersistenciaException;
import logica.excepciones.YaExisteFolioException;
import logica.vo.VoFolio;

public class ControladorAltaFolio {
	private IFachada f;
	private FAltaFolio ven;
	
	public ControladorAltaFolio(FAltaFolio ventana)throws MalformedURLException, RemoteException, NotBoundException, ConfiguracionException, Exc_Persistencia {
		try{
			ven = ventana;

			Propiedades p = new Propiedades();
			int puerto = Integer.parseInt(p.getPuertoServidor());
			String ip = p.getIpServidor();
			this.f = (IFachada) Naming.lookup("//" + ip + ":" + puerto + "/logica");
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			ven.mostrarError("Error en la controladora", 0);
		}
	}
	
	public void altaFolio(String codFolio, String caraptula, int paginas) throws YaExisteFolioException{
		
		if(!codFolio.isEmpty()){	
			if(!caraptula.isEmpty()){
				
				try {	
					System.out.println("Entra a agregar");
					VoFolio VoF = new VoFolio(codFolio, caraptula, paginas);
					this.f.agregarFolio(VoF); ;
					System.out.println("Sale del agregar");
				} catch (RemoteException | PersistenciaException e) {
					ven.mostrarError(e.toString(), 0);
				}

			}else{
				ven.mostrarError("La caraptula del Folio esta vacia", 0);
			}
		}else{
			ven.mostrarError("El Codigo del Folio esta vacio", 0);
		}
	}
	
}


