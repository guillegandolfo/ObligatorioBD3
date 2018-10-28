package persistencia;

	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.Properties;

	public class CrearBD
	{
		public static void main (String[] args)
		{
			try
			{
				Properties p = new Properties();
				String archivo = "config/config.properties";
				
				String driver = "";
				String url = "";
				String usuario = "";
				String password = "";
				try {
					p.load(new FileInputStream(archivo));
					
					driver = p.getProperty("driver");
					url = p.getProperty("url");
					usuario = p.getProperty("username");
					password = p.getProperty("password");
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Class.forName(driver);
				Connection con = DriverManager.getConnection(url, usuario, password);
	
				String nuevaBase = "Juridico";
				try {
					nuevaBase = crearBase(con);
					con.close();
				} catch (SQLException ex) {}
				
				try   {
					con = DriverManager.getConnection(url+nuevaBase, usuario, password);
					crearTablas(con);
					insertarDatos(con);
				} catch (SQLException ex) {}

				/* 5. por ultimo, cierro la conexion con la base de datos */
				con.close();
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}	
		}
	

		private static String crearBase(Connection con) throws SQLException {
			
			/***************CREAR BD ESTUDIO******************************/
			String insert = "create database Juridico";
			PreparedStatement pstmt;
			String nombreBd = "Juridico";
			
			pstmt = con.prepareStatement(insert);
			int cantBd;
			cantBd = pstmt.executeUpdate();
		
			pstmt.close();
			System.out.print("Resultado de " + insert + ": ");
			System.out.println(cantBd + " filas afectadas");
				
			return nombreBd;
		}
		
		private static void crearTablas(Connection con) throws SQLException {
			
			/***************CREAR TABLAS******************************/
			
			String createFolios = "create table folios (codigo  VARCHAR(60) not null primary key , caratula VARCHAR(60), paginas int)";
			String createRevisiones = "create table revisiones (numero INT not null primary key, codigoFolio VARCHAR(60), descripcion varchar(60))";
			String foreignkey = "alter table revisiones add foreign key fk_revi(codigoFolio) references folios (codigo)";
			
			PreparedStatement pstmtTables1 = con.prepareStatement(createFolios);
			PreparedStatement pstmtTables2 = con.prepareStatement(createRevisiones);
			PreparedStatement pstmtTables3 = con.prepareStatement(foreignkey);
			
			int cant0 = pstmtTables1.executeUpdate();
			pstmtTables1.close();
			System.out.print("Resultado de " + createFolios + ": ");
			System.out.println(cant0 + " filas afectadas");
			
			int cant1 = pstmtTables2.executeUpdate();
			pstmtTables2.close();
			System.out.print("Resultado de " + createRevisiones + ": ");
			System.out.println(cant1 + " filas afectadas");
			
			int cant2 = pstmtTables3.executeUpdate();
			pstmtTables3.close();
			System.out.print("Resultado de " + foreignkey + ": ");
			System.out.println(cant2 + " filas afectadas");
		}
		
		private static void insertarDatos(Connection con) {
			
			/***************INSERCION DE DATOS*************************/
			String primerTupla = "insert into folios values ('FGH-0015', 'La comuna contra la señora con 38 gatos', 5)";
			String segundaTupla = "insert into folios values ('BBD-1278', 'Adolescentes descontrolados hasta las 5 AM', 2)";
			String tercerTupla = "insert into folios values ('JJ-202', 'Vecinos reclaman por heces de perro en el hall', 9)";
			String cuartaTupla = "insert into folios values ('CEFJ-63', 'Vecinas rivales se tiran macetas con frecuencia', 463)";

			try {
				PreparedStatement pstmtTables1 = con.prepareStatement(primerTupla);
				PreparedStatement pstmtTables2 = con.prepareStatement(segundaTupla);
				PreparedStatement pstmtTables3 = con.prepareStatement(tercerTupla);
				PreparedStatement pstmtTables4 = con.prepareStatement(cuartaTupla);
				
				int cant0 = pstmtTables1.executeUpdate();
				pstmtTables1.close();
				System.out.print("Resultado de " + primerTupla + ": ");
				System.out.println(cant0 + " filas afectadas");
				
				int cant1 = pstmtTables2.executeUpdate();
				pstmtTables2.close();
				System.out.print("Resultado de " + segundaTupla + ": ");
				System.out.println(cant1 + " filas afectadas");
				
				int cant2 = pstmtTables3.executeUpdate();
				pstmtTables3.close();
				System.out.print("Resultado de " + tercerTupla + ": ");
				System.out.println(cant2 + " filas afectadas");
				
				int cant3 = pstmtTables4.executeUpdate();
				pstmtTables4.close();
				System.out.print("Resultado de " + cuartaTupla + ": ");
				System.out.println(cant3 + " filas afectadas");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
