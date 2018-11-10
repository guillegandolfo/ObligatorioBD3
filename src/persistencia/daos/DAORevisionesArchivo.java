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
			// TODO Auto-generated catch block
			throw new PersistenciaException("Error al leer revision");
    	} finally {
	        try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new PersistenciaException("Error al leer revision");
			}
    	}
		return Rev;
    }


    public void insBack(Revision rev, IConexion ic)throws PersistenciaException {

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
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
       	
        } catch (IOException e) {
			// TODO Auto-generated catch block
			
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
		return null;
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

    public LinkedList<VORevision> listarRevisiones(IConexion ic) {

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
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
            VORevision vor = new VORevision(rev.getNumero(), rev.getCodigoFolio(), rev.getDescripcion());
            listRevisiones.add(vor);
        }

        return listRevisiones;
    }

    
	/*public int Largo(IConexion ic) throws Exc_Persistencia{
	
	Revision rev = new Revision();
	int Cantidad = 0;
	try 
	{
		for(File archRevision : this.folder.listFiles()){
			Cantidad ++;
		}
	}
	catch()
	{
		
	}

		return Cantidad;
	}*/

/*public VORevision kEsimo(int numero, IConexion ic) throws Exc_Persistencia{
	
	VORevision rev = new VORevision();
	try{
	Conexion c = (Conexion) ic;
    Connection con = c.getConexion();
    Consultas consulta = new Consultas();
	String query = consulta.existeRevision();
	PreparedStatement pstmt = con.prepareStatement(query);
	pstmt.setString(1, this.codigoFolio);
	pstmt.setInt(2, numero);
	ResultSet rs = pstmt.executeQuery();
    if (rs.next()){
    	rev.setNumero(rs.getInt(1));
    	rev.setCodigoFolio(rs.getString(2));
    	rev.setDescripcion(rs.getString(3));
    }
    
    rs.close();
    pstmt.close();
  
    } catch (SQLException e) {
        throw new Exc_Persistencia("Error en la conexion");
    }
	
	return rev;
}*/

    
   /* public Revision find(String cod, IConexion ic) throws PersistenciaException {
    	ConexionArchivo con = (ConexionArchivo) ic;
    	Revision rev = new Revision();
        for(File archFolio : this.folder.listFiles()){
            String nombreArch = archFolio.getName();
            if(nombreArch.contains("Revisiones-" + cod + ".txt")){
            	rev.setCodigoFolio(cod);
            	rev = this.leerRevisionDeArchivo(archFolio.getPath());
            }
        }
        return rev;
    }*/
}
