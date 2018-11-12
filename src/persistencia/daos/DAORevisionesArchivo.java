package persistencia.daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import logica.excepciones.ConsultaRevisionException;
import logica.excepciones.LecturaArchivoException;
import logica.excepciones.PersistenciaException;
import logica.objetos.Revision;
import logica.vo.VORevision;
import persistencia.poolConexiones.ConexionArchivo;
import persistencia.poolConexiones.IConexion;

public class DAORevisionesArchivo implements IDAORevisiones ,Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codigoFolio;
	private File folder;
	
	
    public DAORevisionesArchivo(String codF){

        this.folder = new File(System.getProperty("user.dir")+"/Archivos/Revisiones");
        this.codigoFolio = codF;

    }
		
	public String getCodigoFolio() {
		return codigoFolio;
	}

	public void setCodigoFolio(String codigoFolio) {
		this.codigoFolio = codigoFolio;
	}
    
    private Revision leerRevisionDeArchivo(String archivo) throws PersistenciaException {
    	
        Revision Rev = new Revision();
        FileReader fr = null;
        BufferedReader br = null;
        File file = new File(archivo);
		try {		
			//Abro el flujo
	         fr = new FileReader (file);
	         br = new BufferedReader(fr);
	         String linea;
	         if((linea=br.readLine())!=null){
                Rev.setNumero(Integer.parseInt(linea));
                linea = br.readLine();
                Rev.setCodigoFolio(linea);
                linea = br.readLine();
                Rev.setDescripcion(linea);
	         }
			
		} catch (IOException e) {
			throw new PersistenciaException("Error al leer revision");
    	} finally {
	        try {
				br.close();
				fr.close();
			} catch (IOException e) {
				throw new PersistenciaException("Error al leer revision");
			}
    	}
		return Rev;
    }


    public void insBack(Revision rev, IConexion ic)throws PersistenciaException, LecturaArchivoException {

        //int num = this.largo(ic);
    	String archivo = this.folder.getPath()+"/ " + rev.getNumero() + "-" + this.getCodigoFolio() + ".txt";
    	BufferedWriter bufferedWriter = null;   	
    	DAOFoliosArchivo daofa = new DAOFoliosArchivo();

        try {
         //pregunto si existe folioarchivo
        	try {
				if(daofa.member(this.getCodigoFolio(), ic)) 
				{
				 	bufferedWriter = new BufferedWriter(new FileWriter(archivo));
					bufferedWriter.write(String.valueOf(rev.getNumero()));
					bufferedWriter.newLine();
					bufferedWriter.write(this.codigoFolio);
					bufferedWriter.newLine();
					bufferedWriter.write(rev.getDescripcion());
				}
			} catch (PersistenciaException e) {
				throw new PersistenciaException("Error al intentar una revision como archivo");
			}
       	
        } catch (IOException e) {
        	throw new LecturaArchivoException("Error al intentar leer archivo de revision");
			
	    } finally {
	    
		    if (bufferedWriter != null) {
		    	try {
					bufferedWriter.flush();
					bufferedWriter.close();
				} catch (IOException e) {
					throw new PersistenciaException("Error al cerrar el archivo");
				}		    	
		    }
	    }
    }

	
	public int largo(IConexion ic) {

        int largo = 0;
        
        //ConexionArchivo con = (ConexionArchivo) ic;
        //Revision rev = new Revision();

        for(File archRevision : this.folder.listFiles()) {

            String nombreArch = archRevision.getName();

            if(nombreArch.contains("-" + this.codigoFolio + ".txt")){
                largo++;

            }
        }

        return largo;
	}

	
    public VORevision kEsimo(int numero, IConexion ic) throws ConsultaRevisionException {
    	VORevision vorev = new VORevision();
    	try 
		{
    		Revision rev = new Revision();
    		
    		FileReader fr = null;
            BufferedReader br = null;
    		for(File archRevision : this.folder.listFiles())
    		{
    			 try {
    					rev = this.leerRevisionDeArchivo(archRevision.getPath());
    				} catch (PersistenciaException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			 if(rev.getNumero() == numero) 
    			 {
    				 vorev = new VORevision(rev.getNumero(), rev.getCodigoFolio(), rev.getDescripcion()); 
    				 break;
    			 }
    		}
		}
    	catch(Exception e) 
    	{
    		throw new ConsultaRevisionException(e.getMessage());
    	}
			
		return vorev;
	}


	public void borrarRevisiones( IConexion ic) {

    	ConexionArchivo con = (ConexionArchivo) ic;
        for(File archRevision : this.folder.listFiles()){
            String nombreArch = archRevision.getName();
            if(nombreArch.contains("-" + this.codigoFolio + ".txt")){
            	archRevision.delete();
            }
        }
    }

    public LinkedList<VORevision> listarRevisiones(IConexion ic) throws PersistenciaException {

    	ConexionArchivo con = (ConexionArchivo) ic;
    	LinkedList<VORevision> listRevisiones = new LinkedList<VORevision>();
    	Revision rev = new Revision();
        for(File archRevision : this.folder.listFiles()){
            try {
                String nombreArch = archRevision.getName();
                if(nombreArch.contains("-" + this.codigoFolio + ".txt")) {
                    rev = this.leerRevisionDeArchivo(archRevision.getPath());
                }
			} catch (PersistenciaException e) {
				throw new PersistenciaException("Error al itentar listar las revisiones en archivos");
			}
            VORevision vor = new VORevision(rev.getNumero(), rev.getCodigoFolio(), rev.getDescripcion());
            listRevisiones.add(vor);
        }

        return listRevisiones;
    }

    

}
