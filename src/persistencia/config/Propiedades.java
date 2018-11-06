package persistencia.config;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

<<<<<<< HEAD
import logica.excepciones.Exc_Persistencia;;
=======
import logica.excepciones.ConfiguracionException;
import logica.excepciones.Exc_Persistencia;
>>>>>>> parent of d30371c... asa

public class Propiedades {
	
	private Properties prop;
	
	
	public Propiedades() throws Exc_Persistencia {	
		prop = new Properties();
		String pathProperties =  ".settings/datos.properties";
		try {
			prop.load(new FileInputStream(pathProperties));
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			throw new Exc_Persistencia("No se ha podido cargar la configuracion"); 
		} 				
	}
	
	public String getDriver() 
	{
		String driver;
		driver = prop.getProperty("driver");
		return driver;
	}
	
	public String getUrl() 
	{
		String url;
		url = prop.getProperty("url");
		return url;
	}
	
	public String getUser() 
	{
		String usr;
		usr = prop.getProperty("usuario");
		return usr;
	}
	
	public String getPass() 
	{
		String pass;
		pass = prop.getProperty("password");
		return pass;
	}
	
	public String getIpServidor() 
	{
		String ip;
		ip = prop.getProperty("ipServidor");
		return ip;
	}
	
	public String getPuertoServidor() 
	{
		String puerto;
		puerto = prop.getProperty("puertoServidor");
		return puerto;
	}
	
	public String getFabrica() 
	{
		String fabrica;
		fabrica = prop.getProperty("fabrica");
		return fabrica;
	}
	
	public String buscar(String nomProp) throws Exc_Persistencia { 

		try{
			Properties p = new Properties();
			String nombreProperties = ".settings/datos.properties";
			p.load(new FileInputStream(nombreProperties));
			if(nomProp == "driver"){
				String archivo = p.getProperty("driver");
				return archivo;
			}else if(nomProp == "url"){
				String archivo = p.getProperty("url");
				return archivo;
			}else if((nomProp == "usuario")){
				String archivo = p.getProperty("usuario");
				return archivo;
			}else if((nomProp == "fabrica")){
				String archivo = p.getProperty("fabrica");
				return archivo;
			}else if((nomProp == "password")){
				String archivo = p.getProperty("password");
				return archivo;
			}else{
				throw new Exc_Persistencia("No se encuentra los datos en la Properties");
			}
		} catch (IOException e){ 
			throw new Exc_Persistencia("Hubo un error al buscar la properties");
		}	
	}
}
