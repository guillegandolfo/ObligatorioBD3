package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import logica.Fachada;
import logica.excepciones.ConexionBDException;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ServidorException;
import persistencia.config.Propiedades;

public class Servidor {

    public static void main(String[] args) {
        // instancio mi Objeto Remoto y lo publico
        // en el rmiregistry

        try {
            // obtengo ip y puerto de un archivo de configuracion
        	Propiedades p = new Propiedades();
			String ip = p.getIpServidor();
			String puerto = p.getPuertoServidor();
			int port = Integer.parseInt(puerto);

            // pongo a correr el rmiregistry
            Registry reg = LocateRegistry.createRegistry(port);
            
            // publico el objeto remoto en dicha ip y puerto
            String ruta = "//" + ip + ":" + puerto + "/logicaPersistencia/accesoBD";
             
            Fachada fachada = Fachada.getInstancia();
            System.out.println("Antes de publicar");
            
            Naming.rebind(ruta, fachada);
            
            System.out.println("Luego de publicar");
            
        } catch (ConfiguracionException e) // si ocurre cualquier problema de red
        {
            e.printStackTrace();
        } catch (MalformedURLException e) // si la ruta no esta bien formada
        {
            e.printStackTrace();
        } catch (IOException e) // si ocurre cualquier otro error de E/S
        {
            e.printStackTrace();
        } catch (PersistenciaException e) // si ocurre cualquier error con bd
        {
            e.printStackTrace();
        }
    }

	
}
