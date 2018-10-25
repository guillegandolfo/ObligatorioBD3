package persistencia;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import persistencia.consultas.Consultas;
import persistencia.datos.Propiedades;
import logica.Excepciones.Exc_Persistencia;
import logica.VO.VORevision;
import logica.VO.VoFolio;



public class Fachada extends UnicastRemoteObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Fachada instancia;
	private String driver;
	private String url;
	private String usuario;
	private String password;

	
	public static Fachada getInstancia() throws Exc_Persistencia, ClassNotFoundException{
		if(instancia == null){
			try {
				instancia = new Fachada();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return instancia;
	}
	
	private Fachada() throws RemoteException, Exc_Persistencia, ClassNotFoundException{
		Propiedades p = new Propiedades();
		String driver = p.buscar("driver");
		String url = p.buscar("url");
		String usuario = p.buscar("usuario");
		String password = p.buscar("password");
		Class.forName(this.driver);
	}


	public String getDriver() {
		return driver;
	}


	public String getUrl() {
		return url;
	}


	public String getUsuario() {
		return usuario;
	}


	public String getPassword() {
		return password;
	}


	public void agregarFolio(VoFolio VoF) throws SQLException{

		Connection con = DriverManager.getConnection(url, usuario, password);
		Consultas consulta = new Consultas();
		String query = consulta.existeFolios();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, VoF.getCodigo());
		ResultSet rs = pstmt.executeQuery();
		//Consulto si existe 
		if (rs.next()){
			//no hago nada por que existe el Folio
		}else{
			query = consulta.insertarFolio();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, VoF.getCodigo());
			pstmt.setString(2, VoF.getCaratula());
			pstmt.setInt(3, VoF.getPaginas());
			pstmt.executeUpdate();
		}
	
		rs.close();
		pstmt.close();
		con.close();
		
	}
	
	public void agregarRevision( String CodFolio, String Desc ) throws SQLException{

		Connection con = DriverManager.getConnection(url, usuario, password);
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

	public void borrarFolioRevisiones(String codigo) throws SQLException{
		
		Connection con = DriverManager.getConnection(url, usuario, password);
		Consultas consulta = new Consultas();
		String query = consulta.existeFolios();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, codigo);
		ResultSet rs = pstmt.executeQuery();
		//Consulto si existe 
		if (rs.next()){
			//Elimino las revisiones
			query = consulta.eliminarRevisiones();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, codigo);
			pstmt.executeUpdate();
			
			//Elimino el Folio
			query = consulta.eliminarFolio();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, codigo);
			pstmt.executeUpdate();
		}
		rs.close();
		pstmt.close();
		con.close();
	}
	
	public String darDescripcion(String codigo, int numero) throws SQLException{
		
		Connection con = DriverManager.getConnection(url, usuario, password);
		Consultas consulta = new Consultas();
		String query = consulta.getDescripcion();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, codigo);
		pstmt.setInt(2, numero);
		ResultSet rs = pstmt.executeQuery();
		String Resultado = "";
		if (rs.next()){
			Resultado = rs.getString(1);
		}
		
		return Resultado;
	}

	public LinkedList <VoFolio> listarFolios() throws SQLException{
		
		Connection con = DriverManager.getConnection(url, usuario, password);
		LinkedList <VoFolio> Lista = new LinkedList <VoFolio>();
		Statement stmt;
		stmt = con.createStatement();
		Consultas consulta = new Consultas();
		String query = consulta.listarFolios();
		ResultSet rs = stmt.executeQuery(query);
		
		//Recorro resultados
		while (rs.next())
		{
			String Codigo = rs.getString("codigo");
			String Caratula = rs.getString("caratula");
			int Paginas = rs.getInt("paginas");
			
			VoFolio Folio = new VoFolio(Codigo, Caratula, Paginas);
			Lista.add(Folio);
		}
		return Lista;
	}

	public LinkedList <VORevision> listarRevisiones(String Codigo) throws SQLException{
		
		Connection con = DriverManager.getConnection(url, usuario, password);
		LinkedList <VORevision> Lista = new LinkedList <VORevision>();
		Consultas consulta = new Consultas();
		String query = consulta.existeFolios();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, Codigo);
		ResultSet rs = pstmt.executeQuery();
		//Consulto si existe Folio
		if (rs.next()){
			
			query = consulta.listarRevisiones();		
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, Codigo);
			rs = pstmt.executeQuery();		
			
			//Recorro resultados
			while (rs.next()){
				int Numero = rs.getInt("numero");
				String CodigoFolio = rs.getString("codigoFolio");
				String Descripcion = rs.getString("descripcion");
				
				VORevision Revision = new VORevision(Numero, CodigoFolio, Descripcion);
				Lista.add(Revision);
			}
		}

		return Lista;
	}
}

