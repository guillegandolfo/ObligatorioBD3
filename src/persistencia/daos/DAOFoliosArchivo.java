package persistencia.daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import logica.excepciones.PersistenciaException;
import logica.objetos.Folio;
import logica.vo.VOFolio;
import logica.vo.VOFolioMaxRev;
import persistencia.poolConexiones.ConexionArchivo;
import persistencia.poolConexiones.IConexion;


public class DAOFoliosArchivo implements IDAOFolios, Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File folder;
    
    public DAOFoliosArchivo(){

        this.folder = new File(System.getProperty("user.dir")+"/Archivos");

    }
    
    private Folio leerFolioDeArchivo(String archivo) throws PersistenciaException{
    	
        Folio fol = new Folio();
        FileReader fr = null;
        BufferedReader br = null;
        File file = new File(archivo);
		try {
			
			//Abro el flujo
	         fr = new FileReader (file);
	         br = new BufferedReader(fr);
	         String linea;
	         if((linea=br.readLine())!=null){

                fol.setCodigo(linea);
                linea = br.readLine();
                fol.setCaratula(linea);
                linea = br.readLine();
                fol.setPaginas(Integer.parseInt(linea));

	         }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	} finally {
	        try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		return fol;
    }

    public boolean member(String cod, IConexion ic) throws PersistenciaException {
    		boolean existe = false;
 
            for(File archFolio : this.folder.listFiles()){

                String nombreArch = archFolio.getName();
                if(nombreArch.equals("Folios-" + cod.trim() + ".txt")){

                    existe = true; 
                }
            }
        
        return existe;
    }

    public void insert(Folio fol, IConexion ic) throws PersistenciaException {

    	String archivo = this.folder.getPath()+"/Folios-" + fol.getCodigo() + ".txt";
    	BufferedWriter bufferedWriter = null;
    	
        try {
         
        	bufferedWriter = new BufferedWriter(new FileWriter(archivo));

        	bufferedWriter.write(fol.getCodigo());
        	bufferedWriter.newLine();
        	bufferedWriter.write(fol.getCaratula());
        	bufferedWriter.newLine();
        	bufferedWriter.write(String.valueOf(fol.getPaginas()));
	    	
        } catch (IOException e) {
			// TODO Auto-generated catch block
			
	    } finally {
	    
		    if (bufferedWriter != null) {
		    	try {
					bufferedWriter.flush();
					bufferedWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new PersistenciaException("Error al cerrar el archivo");
				}
		    	
		    }
	    }

    }

    
    public Folio find(String cod, IConexion ic) throws PersistenciaException {
    	ConexionArchivo con = (ConexionArchivo) ic;
    	Folio fol = new Folio();
        for(File archFolio : this.folder.listFiles()){
            String nombreArch = archFolio.getName();
            if(nombreArch.contains("Folios-" + cod + ".txt")){
            	fol.setCodigo(cod);
                fol = this.leerFolioDeArchivo(archFolio.getPath());
            }
        }
        return fol;
    }

    public void delete(String cod, IConexion ic) throws PersistenciaException {
    	ConexionArchivo con = (ConexionArchivo) ic;
        for(File archFolio : this.folder.listFiles()){
            String nombreArch = archFolio.getName();
            if(nombreArch.contains("Folios-" + cod + ".txt")){
            	archFolio.delete();
            }
        }
    }

    public LinkedList<VOFolio> listarFolios(IConexion ic) throws PersistenciaException {
    	ConexionArchivo con = (ConexionArchivo) ic;
    	LinkedList<VOFolio> list = new LinkedList<VOFolio>();
    	Folio fol = new Folio();
        for(File archFolio : this.folder.listFiles()){
            fol = this.leerFolioDeArchivo(archFolio.getPath());
            VOFolio VoF = new VOFolio(fol.getCodigo(), fol.getCaratula(), fol.getPaginas());
            list.add(VoF);
        }

        return list;
    }

    public VOFolioMaxRev folioMasRevisado(IConexion ic) throws PersistenciaException {
        return null;
    }
    
}
