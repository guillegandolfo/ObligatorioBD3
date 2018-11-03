package logica;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import logica.Fachada;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.Exc_Persistencia;
import logica.excepciones.ServidorException;
import persistencia.config.Propiedades;

public class Servidor {

    public static void main(String[] args) throws ServidorException, Exc_Persistencia {
        // instancio mi Objeto Remoto y lo publico
        // en el rmiregistry

        try {
            // obtengo ip y puerto de un archivo de configuracion
        	Propiedades p = new Propiedades();
			String ip = p.getIpServidor();
			String puerto = p.getPuertoServidor();
			int port = Integer.parseInt(puerto);
			
            // pongo a correr el rmiregistry
            LocateRegistry.getRegistry(port);// createRegistry(port);
            
            // publico el objeto remoto en dicha ip y puerto
            String ruta = "//" + ip + ":" + puerto + "/logica";
             
            Fachada fachada = Fachada.getInstancia();
            System.out.println("Antes de publicar");
            
            Naming.rebind(ruta, fachada);
            
            System.out.println("Luego de publicar");
            
        } catch (ConfiguracionException e) // si ocurre cualquier problema de red
        {
        	throw new ServidorException(e.getMessage());
        } catch (MalformedURLException e) // si la ruta no esta bien formada
        {
            throw new ServidorException(e.getMessage());
        } catch (IOException e) // si ocurre cualquier otro error de E/S
        {
        	throw new ServidorException(e.getMessage());
        }
    }

	
}
