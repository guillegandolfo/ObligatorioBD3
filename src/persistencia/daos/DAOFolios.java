package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import logica.vo.VOFolioMaxRev;
import logica.vo.VoFolio;
import logica.excepciones.Exc_Persistencia;
import logica.excepciones.PersistenciaException;
import logica.objetos.Folio;
import persistencia.consultas.Consultas;
import persistencia.poolConexiones.Conexion;
import persistencia.poolConexiones.IConexion;

public class DAOFolios implements IDAOFolios{

    //public DAOFolios() throws PersistenciaException {}

    public boolean member(String cod, IConexion ic) throws PersistenciaException {

        boolean existeFolio = false;
        try {
        	Conexion c = (Conexion) ic;
            Connection con = c.getConexion();
    		Consultas consulta = new Consultas();
    		String query = consulta.existeFolios();
    		PreparedStatement pstmt = con.prepareStatement(query);
    		pstmt.setString(1, cod);
    		ResultSet rs = pstmt.executeQuery();
    		
            if (rs.next()) {
                existeFolio = true;
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            throw new PersistenciaException("Error en la conexion");
        }

        return existeFolio;
    }

    public void insert(Folio fol, IConexion ic) throws PersistenciaException {

        try {
        	Consultas consulta = new Consultas();
        	//boolean existe = this.member(fol.getCodigo(), ic);
        	Conexion c = (Conexion) ic;
            Connection con = c.getConexion();
    		//Consulto si existe 
        	//if (existe){
        		String query = consulta.insertarFolio();
        		PreparedStatement pstmt = con.prepareStatement(query);
    			pstmt.setString(1, fol.getCodigo());
    			pstmt.setString(2, fol.getCaratula());
    			pstmt.setInt(3, fol.getPaginas());
    			pstmt.executeUpdate();
        	
        		pstmt.close();
        	//}
        	
        } catch (SQLException ex) {
            throw new PersistenciaException("Error de conexion");
        }
    }

    public Folio find(String cod, IConexion ic) throws PersistenciaException {

    	Folio folio = null;

        try {
        	Conexion c = (Conexion) ic;
            Connection con = c.getConexion();
        	Consultas consulta = new Consultas();
            PreparedStatement pstmt = con.prepareStatement(consulta.existeFolios());
            pstmt.setString(1, cod);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                folio = new Folio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new PersistenciaException("Error de conexion");
        }

        return folio;
    }

    public void delete(String cod, IConexion ic) throws PersistenciaException {

        try {

    		boolean existe = this.member(cod, ic);
        	Conexion c = (Conexion) ic;
            Connection con = c.getConexion();
    		//Consulto si existe 
    		if (existe){
    			//Elimino las revisiones
        		Consultas consulta = new Consultas();
    			/*String query = consulta.eliminarRevisiones();
    			PreparedStatement pstmt = con.prepareStatement(query);
    			pstmt.setString(1, cod);
    			pstmt.executeUpdate();
    			*/
    			//Elimino el Folio
    			String query = consulta.eliminarFolio();
    			PreparedStatement pstmt = con.prepareStatement(query);
    			pstmt.setString(1, cod);
    			pstmt.executeUpdate();
    			pstmt.close();
    			
    		}
    		
        } catch (SQLException e) {
            throw new PersistenciaException("Error de conexion");
        }
    }

    public LinkedList<VoFolio> listarFolios(IConexion ic) throws PersistenciaException {

    	LinkedList<VoFolio> Lista = new LinkedList <VoFolio>();

        try {
        	Conexion c = (Conexion) ic;
            Connection con = c.getConexion();
        	Consultas consulta = new Consultas();
            PreparedStatement pstmt = con.prepareStatement(consulta.listarFolios());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
    			String Codigo = rs.getString("codigo");
    			String Caratula = rs.getString("caratula");
    			int Paginas = rs.getInt("paginas");
    			
    			VoFolio folio = new VoFolio(Codigo, Caratula, Paginas);
    			Lista.add(folio);
    		}

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new PersistenciaException("Error de conexion");
        }

		return Lista;
    }
    
    public VOFolioMaxRev folioMasRevisado(IConexion ic) throws PersistenciaException {

    	VOFolioMaxRev folio = null;

        try {
        	Conexion c = (Conexion) ic;
            Connection con = c.getConexion();
        	Consultas consulta = new Consultas();
            PreparedStatement pstmt = con.prepareStatement(consulta.folioMasRevisado());
            ResultSet rs = pstmt.executeQuery();
           //Obtengo el folio mas revisado
            if (rs.next()) {
            	String Codigo = rs.getString(1);
            	int Cantidad = rs.getInt(2);
            	
            	//Obtengo los datos del Folio
            	 pstmt = con.prepareStatement(consulta.existeFolios());
                 pstmt.setString(1, Codigo);
                 rs = pstmt.executeQuery();
                 if (rs.next()) {
                	 folio = new VOFolioMaxRev(Cantidad, Codigo, rs.getString("caratula"), rs.getInt("paginas"));
                     
                 }else{
                	 throw new PersistenciaException("Error al obtener el folio");
                 }
            }else{
            	throw new PersistenciaException("Error al obtener el folio mas revisado");
            }
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            throw new PersistenciaException("Error de conexion");
        }

        return folio;
    }
    
}

