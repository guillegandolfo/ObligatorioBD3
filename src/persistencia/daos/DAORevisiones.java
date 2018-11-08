package persistencia.daos;
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import logica.vo.VORevision;
import logica.excepciones.Exc_Persistencia;
import logica.objetos.Revision;
import persistencia.consultas.Consultas;
import persistencia.poolConexiones.Conexion;
import persistencia.poolConexiones.IConexion;

public class DAORevisiones {

	private String codigoFolio;
	
	public DAORevisiones(String codF)
	{
		this.codigoFolio = codF;
	}
	
	public String getCodigoFolio() {
		return codigoFolio;
	}

	public void setCodigoFolio(String codigoFolio) {
		this.codigoFolio = codigoFolio;
	}

	public void InsBack(String Desc ,IConexion ic) throws Exc_Persistencia{

		try 
		{
        	Conexion c = (Conexion) ic;
            Connection con = c.getConexion();
			Consultas consulta = new Consultas();
			String query = consulta.existeFolios();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, this.codigoFolio);
			ResultSet rs = pstmt.executeQuery();
			//Consulto si existe 
			if (rs.next()){
				int maxrevisionid;
				query = consulta.MaxFolioId();
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, this.codigoFolio);
				rs = pstmt.executeQuery();
				//Si tiene revisiones, obtengo la mayor y si no es 0
				if (rs.next()){
					 maxrevisionid = rs.getInt(1);
				}else{
					maxrevisionid = 0;
				}
				maxrevisionid ++;
				query = consulta.InsertarRevision();
				PreparedStatement pstmt2 = con.prepareStatement(query);
				pstmt2.setInt(1,maxrevisionid );
				pstmt2.setString(2, this.codigoFolio);
				pstmt2.setString(3, Desc);
				pstmt2.executeUpdate();
			}
			
			rs.close();
			pstmt.close();	
				
		}
		catch(SQLException e) {
			throw new Exc_Persistencia("Error Al Insertar Revision");
		}
		
		}
		
	
	public int Largo(IConexion ic) throws Exc_Persistencia{
    	
		int Cantidad = 0;
		try{
			Conexion c = (Conexion) ic;
	        Connection con = c.getConexion();
	        Consultas consulta = new Consultas();
			String query = consulta.MaxFolioId();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, this.codigoFolio);
			ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()){
	        	Cantidad = rs.getInt(1);
	        }
			
            rs.close();
            pstmt.close();
          
	        } catch (SQLException e) {
	            throw new Exc_Persistencia("Error en la conexion");
	        }
			return Cantidad;
		}

	
	public VORevision kEsimo(int numero, IConexion ic) throws Exc_Persistencia{
    	
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
	}
	
	public LinkedList <VORevision> listarRevisiones(IConexion ic) throws SQLException, Exc_Persistencia{
		
		LinkedList <VORevision> Lista = new LinkedList <VORevision>();
		try{
			Conexion c = (Conexion) ic;
	        Connection con = c.getConexion();
	        
			Consultas consulta = new Consultas();
			String query = consulta.existeFolios();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, this.codigoFolio);
			ResultSet rs = pstmt.executeQuery();
			//Consulto si existe Folio
			if (rs.next()){
				
				query = consulta.listarRevisiones();		
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, this.codigoFolio);
				rs = pstmt.executeQuery();		
				
				//Recorro resultados
				while (rs.next()){
					int numero = rs.getInt("numero");
					String codigoFolio = rs.getString("codigoFolio");
					String descripcion = rs.getString("descripcion");
					
					VORevision Revision = new VORevision(numero, codigoFolio, descripcion);
					Lista.add(Revision);
				}
			}
			
		    rs.close();
		    pstmt.close();
	      
	    } catch (SQLException e) {
	        throw new Exc_Persistencia("Error en la conexion");
	    }
			
		return Lista;
	}	
		
	public void borrarRevisiones(IConexion ic) throws Exc_Persistencia{
    	
		try{
		Conexion c = (Conexion) ic;
        Connection con = c.getConexion();
		Consultas consulta = new Consultas();
		String query = consulta.existeFolios();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, this.codigoFolio);
		ResultSet rs = pstmt.executeQuery();
		//Consulto si existe 
		if (rs.next()){
			//Elimino la revision
			query = consulta.eliminarRevisiones();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, this.codigoFolio);
			pstmt.executeUpdate();
			
		}
		rs.close();
		pstmt.close();
      
        } catch (SQLException e) {
            throw new Exc_Persistencia("Error en la conexion");
        }
	}

	
}
	
