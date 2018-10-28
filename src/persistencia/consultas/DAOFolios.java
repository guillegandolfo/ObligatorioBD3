package persistencia.consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import logica.excepciones.Exc_Persistencia;
import logica.objetos.Folio;
import persistencia.consultas.Consultas;

public class DAOFolios implements IDAOFolios{

    public DAOFolios() throws Exc_Persistencia {
    }

    public boolean member(String cod, Connection con) throws Exc_Persistencia {

        boolean existeFolio = false;
        try {
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
            throw new Exc_Persistencia("Error en la conexion");
        }

        return existeFolio;
    }

    public void insert(Folio fol, Connection con) throws Exc_Persistencia {

        try {
        	Consultas consulta = new Consultas();

        	boolean existe = this.member(fol.getCodigo(), con);
    		//Consulto si existe 
        	if (existe){
        		String query = consulta.insertarFolio();
        		PreparedStatement pstmt = con.prepareStatement(query);
    			pstmt.setString(1, fol.getCodigo());
    			pstmt.setString(2, fol.getCaratula());
    			pstmt.setInt(3, fol.getPaginas());
    			pstmt.executeUpdate();
        	
        		pstmt.close();
        	}
        	
        } catch (SQLException ex) {
            throw new Exc_Persistencia("Error de conexion");
        }
    }

    public Folio find(String cod, Connection con) throws Exc_Persistencia {

        Folio folio = null;

        try {
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
            throw new Exc_Persistencia("Error de conexion");
        }

        return folio;
    }

    public void delete(String cod, Connection con) throws Exc_Persistencia {

        try {

    		boolean existe = this.member(cod, con);
    		//Consulto si existe 
    		if (existe){
    			//Elimino las revisiones
        		Consultas consulta = new Consultas();
    			String query = consulta.eliminarRevisiones();
    			PreparedStatement pstmt = con.prepareStatement(query);
    			pstmt.setString(1, cod);
    			pstmt.executeUpdate();
    			
    			//Elimino el Folio
    			query = consulta.eliminarFolio();
    			pstmt = con.prepareStatement(query);
    			pstmt.setString(1, cod);
    			pstmt.executeUpdate();
    			pstmt.close();
    			
    		}
    		
        } catch (SQLException e) {
            throw new Exc_Persistencia("Error de conexion");
        }
    }

    public LinkedList<Folio> listarFolios(Connection con) throws Exc_Persistencia {

    	LinkedList<Folio> Lista = new LinkedList <Folio>();

        try {
        	Consultas consulta = new Consultas();
            PreparedStatement pstmt = con.prepareStatement(consulta.listarFolios());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
    			String Codigo = rs.getString("codigo");
    			String Caratula = rs.getString("caratula");
    			int Paginas = rs.getInt("paginas");
    			
    			Folio folio = new Folio(Codigo, Caratula, Paginas);
    			Lista.add(folio);
    		}

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new Exc_Persistencia("Error de conexion");
        }

		return Lista;
    }
    
    
}

