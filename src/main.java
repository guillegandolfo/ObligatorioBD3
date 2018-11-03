import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import logica.excepciones.ConexionBDException;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.Exc_Persistencia;
import persistencia.config.Propiedades;


public class main
{
	public static void main (final String[] args) throws ConexionBDException, IOException, Exc_Persistencia
	{
	try {
				Propiedades p = new Propiedades();
				String driver = p.getDriver();
				String url = p.buscar("url");
				String usuario = p.buscar("usuario");
				String password = p.buscar("password");

				// 1. cargo dinamicamente el driver de MySQL 
				Class.forName(driver);

				// 2. una vez cargado el driver, me conecto con la base de datos 
				Connection con = DriverManager.getConnection(url, usuario, password);
				
				PreparedStatement pstmt = con.prepareStatement("CREATE DataBase Juridico");
				pstmt.execute();
				System.out.println("Creo la base de datos");
				
				pstmt = con.prepareStatement("CREATE Table Juridico.Folios(Codigo Varchar(60) not null, caratula Varchar(60) not null, paginas int not null, primary key (Codigo))");
				pstmt.execute();
				System.out.println("Creo la tabla Folios");
				
				pstmt = con.prepareStatement("CREATE Table Juridico.Revisiones(\r\n" + 
						"numero int not null,\r\n" + 
						"codigoFolio Varchar(60) not null,\r\n" + 
						"descripcion Varchar(60) not null,\r\n" + 
						"primary key (numero, codigoFolio),\r\n" + 
						"CONSTRAINT fk_codigoFolio FOREIGN KEY (codigoFolio) REFERENCES Juridico.Folios (Codigo)\r\n" + 
						");");
				pstmt.execute();
				System.out.println("Creo la tabla Revisiones");
				
				String insert = "INSERT INTO Juridico.Folios VALUES (?,?,?)";
				pstmt = con.prepareStatement(insert);
				pstmt.setString(1, "FGH-0015");
				pstmt.setString(2, "La comuna contra la señora con 38 gatos");
				pstmt.setInt(3, 5);
				pstmt.executeUpdate();
				System.out.println("Inserte primer tupla");	

				insert = "INSERT INTO Juridico.Folios VALUES (?,?,?)";
				pstmt = con.prepareStatement(insert);
				pstmt.setString(1, "BBD-1278");
				pstmt.setString(2, "Adolescentes descontrolados hasta las 5 AM");
				pstmt.setInt(3, 2);
				pstmt.executeUpdate();
				System.out.println("Inserte segunda tupla");					
				
				insert = "INSERT INTO Juridico.Folios VALUES (?,?,?)";
				pstmt = con.prepareStatement(insert);
				pstmt.setString(1, "JJ-202 ");
				pstmt.setString(2, "Vecinos reclaman por heces de perro en el hall");
				pstmt.setInt(3, 9);
				pstmt.executeUpdate();
				System.out.println("Inserte tercer tupla");		
				
				insert = "INSERT INTO Juridico.Folios VALUES (?,?,?)";
				pstmt = con.prepareStatement(insert);
				pstmt.setString(1, "CEFJ-63");
				pstmt.setString(2, "Vecinas rivales se tiran macetas con frecuencia");
				pstmt.setInt(3, 463);
				pstmt.executeUpdate();
				System.out.println("Inserte cuarta tupla");			

				pstmt.close();
				con.close();
				
				
				System.out.println("EXITO!!!");	
				
			}
			catch (SQLException  e) {	
				e.printStackTrace();
				//throw new ConexionBDException("No se ha podido crear la base de datos correctamente");
			}
			catch (ClassNotFoundException  e){	
				e.printStackTrace();
			}
			catch (Exc_Persistencia  e){	
				e.printStackTrace();
			}

	}

}
