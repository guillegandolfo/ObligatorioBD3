package persistencia.consultas;
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logica.Excepciones.Exc_Persistencia;;

public class DAORevisiones {

	private String codigoFolio;
	
	public DAORevisiones(String codF)
	{
		this.codigoFolio = codF;
	}
	
	public void InsBack( String CodFolio, String Desc ,Connection con) throws Exc_Persistencia{

		try 
		{
			Consultas consulta = new Consultas();
			String query = consulta.existeFolios();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, CodFolio);
			ResultSet rs = pstmt.executeQuery();
			//Consulto si existe 
			if (rs.next()){
				int maxrevisionid;
				query = consulta.MaxFolioId();
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, CodFolio);
				rs = pstmt.executeQuery();
				//Si tiene revisiones, obtengo la mayor y si no es 0
				if (rs.next()){
					 maxrevisionid = rs.getInt(1);
				}else{
					maxrevisionid = 0;
				}
				maxrevisionid ++;
				query = consulta.InsertarRevision();
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1,maxrevisionid );
				pstmt.setString(2, CodFolio);
				pstmt.setString(3, Desc);
				pstmt.executeUpdate();
			}
			
			rs.close();
			pstmt.close();
			con.close();	
				
		}
		catch(SQLException e) {
			throw new Exc_Persistencia("Error Al Insertar Revision");
		}
		
		}
		
	
	public int Largo(Connection con) throws Exc_Persistencia{
		
		
		
		return 2;
	}
	
	
	
}
