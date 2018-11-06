package persistencia.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import logica.excepciones.PersistenciaException;
import logica.objetos.Folio;
import logica.vo.VOFolioMaxRev;
import logica.vo.VoFolio;
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
    	
        Folio fol = null;
        FileInputStream Arch = null;
        ObjectInputStream flujo = null;
        try {
            Arch = new FileInputStream(archivo);
            flujo = new ObjectInputStream(Arch);
            fol = (Folio) flujo.readObject();
        } catch (FileNotFoundException ex) {
        	throw new PersistenciaException("Error abrir el archivo");
        } catch (IOException ex) {
        	throw new PersistenciaException("Error abrir el flujo");
        } catch (ClassNotFoundException ex) {
        	throw new PersistenciaException("Error obtener datos del archivo");
        } finally {
            if(flujo != null){
                try {
                    flujo.close();
                } catch (IOException ex) {
                	throw new PersistenciaException("Error cerrando el flujo");
                }
            }
            if(Arch != null){
                try {
                    Arch.close();
                } catch (IOException ex) {
                	throw new PersistenciaException("Error cerrando el archivo");
                }
            }
        }
        return fol;
    }

    public boolean member(String cod, IConexion ic) throws PersistenciaException {
    		System.out.println("1");
            boolean existe = true;
            System.out.println("2");
            for(File archFolio : this.folder.listFiles()){
            	System.out.println("2.Iterador");
                String nombreArch = archFolio.getName();
                System.out.println("3");
                if(nombreArch.contains("Folios - " + cod)){
                	System.out.println("4");
                    existe = true;
                }
            }     
            System.out.println("5");
        return existe;
    }

    public void insert(Folio fol, IConexion ic) throws PersistenciaException {
    	System.out.println("1");
    	System.out.println("2");
    	FileOutputStream Arch = null;
    	System.out.println("3");
    	ObjectOutputStream flujo = null;
    	System.out.println("4");
        try {
        	System.out.println("5");
            String archivo = this.folder.getPath()+"/Folios-" + fol.getCodigo() + ".txt";
        	System.out.println("6");
            Arch = new FileOutputStream(archivo);
        	System.out.println("7");
            flujo = new ObjectOutputStream(Arch);
        	System.out.println("8");
            flujo.writeObject(fol);
        	System.out.println("9");
        	
        } catch (FileNotFoundException ex) {
            throw new PersistenciaException("Error en la Persistencia");
        } catch (IOException e) {
        	throw new PersistenciaException("Error abrir el flujo");
		} finally {
            try {
                flujo.close();
            } catch (IOException ex){
                throw new PersistenciaException("Error en la Persistencia");
            }
            try {
                Arch.close();
            } catch (IOException ex) {
               throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }

    
    public Folio find(String cod, IConexion ic) throws PersistenciaException {
    	ConexionArchivo con = (ConexionArchivo) ic;
    	Folio fol = new Folio();
        for(File archFolio : this.folder.listFiles()){
            String nombreArch = archFolio.getName();
            if(nombreArch.contains("Folios - " + cod)){
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
            if(nombreArch.contains("Folios - " + cod)){
            	archFolio.delete();
            }
        }
    }

    public LinkedList<VoFolio> listarFolios(IConexion ic) throws PersistenciaException {
    	ConexionArchivo con = (ConexionArchivo) ic;
    	LinkedList<VoFolio> list = new LinkedList<VoFolio>();
    	Folio fol = new Folio();
        for(File archFolio : this.folder.listFiles()){
           
            fol = this.leerFolioDeArchivo(archFolio.getPath());
            VoFolio VoF = new VoFolio(fol.getCodigo(), fol.getCaratula(), fol.getPaginas());
            list.add(VoF);
            
        }
        return list;
    }

    //Sin terminar
    public VOFolioMaxRev folioMasRevisado(IConexion ic) throws PersistenciaException {
        return null;
    }
    
}
