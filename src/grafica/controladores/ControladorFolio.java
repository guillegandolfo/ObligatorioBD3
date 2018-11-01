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
import logica.excepciones.YaExisteFolioException;
import logica.vo.VoFolio;
import persistencia.config.Propiedades;


public class ControladorFolio {

	private FAbFolio ventana = null;
	private IFachada fachada;
	private String resultado = "";
	
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
	/*public VOAlumno buscarAlumno (Integer ced)  {
		VOAlumno voAlumno = new VOAlumno();
		try {
			if (ced.equals(null)) {
				throw new Exception("La cedula no debe estar vacia");
			}
			else
			{
				voAlumno = fachada.listarPorCedula(ced);
			}
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana("Error en el servidor. " + e.getMessage());
		} catch (NoExisteAlumnoException e) {
			ventana.imprimirVentana("El alumno no existe desea ingresarlo?","Alumno no existe");			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana(e.getMessage());
		}
		return voAlumno;
	}*/
	
	public Boolean agregarFolio (VoFolio folio) {
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
			ventana.imprimirVentana(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana(e.getMessage());
		}
		return ok;
	}
	
	public LinkedList<VoFolio> listarFolios() {
		LinkedList<VoFolio> lista = new LinkedList<VoFolio>();
		try {
			lista = fachada.listarFolios();
			//ventana.limpiarFrame();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana("Error en el servidor. " + e.getMessage());
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana(e.getMessage());
		}
		return lista;
	}
	
	/*public void modificarAlumno (VOAlumno alu) {

		try {
			fachada.modificarAlumno(alu);
			ventana.imprimirVentana("Alumno modificado correctamente");
			ventana.limpiarFrame();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana("Error en el servidor. " + e.getMessage());
		} catch (NoExisteAlumnoException e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ventana.imprimirVentana(e.getMessage());
		}
	}*/

}
