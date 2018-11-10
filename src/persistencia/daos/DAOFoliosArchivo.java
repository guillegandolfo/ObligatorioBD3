package persistencia.daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import logica.excepciones.ConfiguracionException;
import logica.excepciones.ConsultaRevisionException;
import logica.excepciones.LecturaArchivoException;
import logica.excepciones.PersistenciaException;
import logica.objetos.Folio;
import logica.vo.VoFolio;
import logica.vo.VOFolioMaxRev;
import persistencia.poolConexiones.ConexionArchivo;
import persistencia.poolConexiones.IConexion;


public class DAOFoliosArchivo implements IDAOFolios, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File folder;
    
    public DAOFoliosArchivo(){

        this.folder = new File(System.getProperty("user.dir")+"/Archivos/Folios");

    }

    private Folio leerFolioDeArchivo(String archivo) throws ConfiguracionException, LecturaArchivoException {

        Folio fol = null;
        try {
            fol = new Folio();
        } catch (ConfiguracionException e) {
            throw new ConfiguracionException(e.getMessage());
        }
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
                //fol = new Folio(new DAORevisionesArchivo(fol.getCodigo()));
                linea = br.readLine();
                fol.setCaratula(linea);
                linea = br.readLine();
                fol.setPaginas(Integer.parseInt(linea));

	         }
			
		} catch (IOException e) {
            throw new LecturaArchivoException("No se ha podido completar la lectura");
    	} finally {
	        try {
				br.close();
				fr.close();
			} catch (IOException e) {
				throw new LecturaArchivoException("No se ha podido completar la lectura");
			}
    	}
		return fol;
    }

    public boolean member(String cod, IConexion ic) throws PersistenciaException {

        boolean existe = false;
        int i = 0;
        File[] files = this.folder.listFiles();

        if (files != null) {
            while (!existe && i < files.length ) {
                File archFolio = files[i];
                String nombreArch = archFolio.getName();


                if (nombreArch.equals(cod+".txt")) {

                    existe = true;
                }
                i++;
            }
        }

        return existe;
    }

    public void insert(Folio fol, IConexion ic) throws LecturaArchivoException {

        String archivo = this.folder.getPath()+"/" + fol.getCodigo() + ".txt";
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
                    throw new LecturaArchivoException("Error al cerrar el archivo");
                }

            }
        }

    }


    public Folio find(String cod, IConexion ic) throws ConfiguracionException, LecturaArchivoException {

        ConexionArchivo con = (ConexionArchivo) ic;
        Folio fol = null;

        boolean existe = false;
        int i = 0;
        File[] files = this.folder.listFiles();

        if (files != null) {
            while (!existe && i < files.length ) {
                File archFolio = files[i];

                String nombreArch = archFolio.getName();
                if (nombreArch.equals(cod+".txt")) {

                    try {
                        fol = new Folio();
                    } catch (ConfiguracionException e) {
                        throw new ConfiguracionException(e.getMessage());
                    }

                    fol.setCodigo(cod);
                    fol = this.leerFolioDeArchivo(archFolio.getPath());
                    fol.setRevisiones(new DAORevisionesArchivo(cod));
                    existe = true;
                }
                else {
                    i++;
                }
            }
        }
        return fol;
    }

    public void delete(String cod, IConexion ic) {

        ConexionArchivo con = (ConexionArchivo) ic;
        File[] files = this.folder.listFiles();
        int i = 0;
        Boolean encontre = false;

        if (files != null) {
            while (!encontre && i < files.length) {

                File archFolio = files[i];
                String nombreArch = archFolio.getName();

                if (nombreArch.equals(cod+".txt")) {
                    encontre = true;
                    archFolio.delete();

                } else {
                    i++;
                }
            }
        }
    }

    public LinkedList<VoFolio> listarFolios(IConexion ic) throws ConfiguracionException, LecturaArchivoException {

        //ConexionArchivo con = (ConexionArchivo) ic;
        LinkedList<VoFolio> list = new LinkedList<VoFolio>();
        Folio fol = null;
        try {
            fol = new Folio();
        } catch (ConfiguracionException e) {
            throw new ConfiguracionException(e.getMessage());
        }

        for(File archFolio : this.folder.listFiles()){
            fol = this.leerFolioDeArchivo(archFolio.getPath());
            VoFolio VoF = new VoFolio(fol.getCodigo(), fol.getCaratula(), fol.getPaginas());
            list.add(VoF);
        }

        return list;
    }

    public VOFolioMaxRev folioMasRevisado(IConexion ic) throws ConsultaRevisionException {

        VOFolioMaxRev Ret = new VOFolioMaxRev();
        Folio folioTemp = null;
        LinkedList<VoFolio> folios = null;
        try {
            folios = this.listarFolios(ic);

            int CantidadRev = 0;

            for (VoFolio vofolio : folios) {

                //Obtengo el folio a consultar
                folioTemp = this.find(vofolio.getCodigo(), ic);
                //Si el acumulado es mas chico que el folio actual, me quedo con el folio actual y aumento el acumulado
                if (CantidadRev < folioTemp.cantidadRevisiones(ic)) {

                    CantidadRev = folioTemp.cantidadRevisiones(ic);
                    Ret.setCodigo(folioTemp.getCodigo());
                    Ret.setCaratula(folioTemp.getCaratula());
                    Ret.setPaginas(folioTemp.getPaginas());
                    Ret.setCantRevisiones(CantidadRev);
                }

            }
        } catch (ConfiguracionException e) {
            throw new ConsultaRevisionException("Error al obtener el folio");
        } catch (LecturaArchivoException e) {
            throw new ConsultaRevisionException(e.getMessage());
        } catch (ConsultaRevisionException e) {
            throw new ConsultaRevisionException("Error al obtener el folio");
        }

        return Ret;


    }
}
