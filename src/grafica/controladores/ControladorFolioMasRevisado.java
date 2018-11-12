package grafica.controladores;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import grafica.ventanas.FolioMasRevisado;
import logica.IFachada;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.ConsultaRevisionException;
import logica.excepciones.LecturaArchivoException;
import logica.excepciones.PersistenciaException;
import logica.vo.VOFolioMaxRev;
import persistencia.config.Propiedades;

public class ControladorFolioMasRevisado {
	
	private FolioMasRevisado ventana ;
	private IFachada fachada;

	public ControladorFolioMasRevisado(FolioMasRevisado vent) {
		
		this.ventana = vent;
		try {
			Propiedades p = new Propiedades();
			String ip = p.getIpServidor();
			String puerto = p.getPuertoServidor();
			int port = Integer.parseInt(puerto);
			fachada = (IFachada)Naming.lookup("//" + ip + ":" + port + "/logica");
		} catch (IOException e) {
		// TODO Auto-generated catch block
			vent.imprimirVentana("Ha ocurrido un error al establecer conexion con el servidor");
	    }
		catch (NotBoundException e) {
			// TODO Auto-generated catch block
			vent.imprimirVentana(e.getMessage());
		} catch (ConfiguracionException e) {
			// TODO Auto-generated catch block
			vent.imprimirVentana(e.getMessage());
		}
	}
	
	
	public VOFolioMaxRev getFolioMasRevisado() {
		
		VOFolioMaxRev VOFMR = new VOFolioMaxRev();
		try {
			VOFMR = fachada.folioMasRevisado();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return VOFMR;
	}
	
	public String getDescripcion(String codigo, int numRevision) throws LecturaArchivoException, ConfiguracionException, ConsultaRevisionException {
		
		String Descripcion = "Error";
		try {
			Descripcion = fachada.darDescripcion(codigo, numRevision);
		} catch (PersistenciaException e) {
			this.ventana.imprimirVentana("No existe Revision");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return Descripcion;
	}
	
}
