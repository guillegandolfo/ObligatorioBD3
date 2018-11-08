package grafica.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Properties;

import grafica.ventanas.FAbFolio;
import grafica.ventanas.FAbFolio;
import logica.IFachada;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ServidorException;
import logica.excepciones.YaExisteFolioException;
import logica.vo.VOFolio;
import persistencia.config.Propiedades;


public class ControladorFolio {

	private FAbFolio ventana = null;
	private IFachada fachada;
	
	public ControladorFolio(FAbFolio jinternalFrame) {
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
	
	public Boolean agregarFolio (VOFolio folio) {
		Boolean ok = false;
		try {
			fachada.agregarFolio(folio);
			ventana.imprimirVentana("Folio ingresado correctamente");
			ok = true;
			//ventana.limpiarFrame();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana("Error en el servidor. " + e.getMessage());
		} catch (YaExisteFolioException e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana("Ya existe folio");
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana("Ocurrio error al agregar el folio");
		}
		return ok;
	}
	
	public Boolean borrarFolio (String codFolio) {
		Boolean ok = false;
		try {
			fachada.borrarFolioRevisiones(codFolio);
			ventana.imprimirVentana("Folio borrado correctamente");
			ok = true;
			//ventana.limpiarFrame();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana("Error en el servidor. " + e.getMessage());
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana("Ocurrio error al borrar el folio");
		}
		return ok;
	}
	
	public LinkedList<VOFolio> listarFolios() {
		LinkedList<VOFolio> lista = new LinkedList<VOFolio>();
		try {
			if (fachada != null) {
				lista = fachada.listarFolios();
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			//throw new ServidorException("Error en el servidor. " + e.getMessage());
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			// new PersistenciaException(e.getMessage());
		}
		return lista;
	}

}
