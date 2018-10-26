package persistencia.consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logica.Excepciones.Exc_Persistencia;
import logica.Objetos.Folio;
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

    public void insert(Folio fol, IConexion ic) throws ExceptionPersistencia {
        /*Inicializo las variables*/
        Conexion con = (Conexion) ic;
        Connection c = con.getConexion();

        /*Hago la consulta a la base de datos*/
        try {
            PreparedStatement pstmt = c.prepareStatement(Consultas.INGRESAR_NINIO);
            pstmt.setInt(1, ninio.getCedula());
            pstmt.setString(2, ninio.getNombre());
            pstmt.setString(3, ninio.getApellido());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            throw new ExceptionPersistencia(ExceptionPersistencia.OBTENER_DATOS);
        }
    }

    public Ninio find(int cedulaNinio, IConexion ic) throws ExceptionPersistencia {
        /*Inicializo las variables*/
        Ninio ninio = null;
        Conexion con = (Conexion) ic;
        Connection c = con.getConexion();

        /*Hago la consulta a la base de datos*/
        try {
            PreparedStatement pstmt = c.prepareStatement(Consultas.OBTENER_NINIO);
            pstmt.setInt(1, cedulaNinio);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ninio = new Ninio(rs.getInt("cedula"), rs.getString("nombre"), rs.getString("apellido"), new DAOJuguetes(rs.getInt("cedula")));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new ExceptionPersistencia(ExceptionPersistencia.OBTENER_DATOS);
        }

        return ninio;
    }

    public void delete(int cedulaNinio, IConexion ic) throws ExceptionPersistencia {
        /*Inicializo las variables*/
        Conexion con = (Conexion) ic;
        Connection c = con.getConexion();

        /*Hago la consulta a la base de datos*/
        try {
            /*TODO antes tengo que borrar los juguetes del ninio*/
            this.find(cedulaNinio, ic).borrarJuguetes(ic);
            System.out.println("Despues borrar juguetes");
            PreparedStatement pstmt = c.prepareStatement(Consultas.BORRAR_NINIO);
            pstmt.setInt(1, cedulaNinio);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new ExceptionPersistencia(ExceptionPersistencia.BORRAR_DATOS);
        }
    }

    public List<VONinio> listarNinios(IConexion ic) throws ExceptionPersistencia {
        /*Inicializo las variables*/
        Conexion con = (Conexion) ic;
        Connection c = con.getConexion();
        List<VONinio> ret = new ArrayList<>();

        /*Hago la consulta a la base de datos*/
        try {
            PreparedStatement pstmt = c.prepareStatement(Consultas.LISTAR_NINIOS);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int cedula = rs.getInt("cedula");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                ret.add(new VONinio(cedula, nombre, apellido));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new ExceptionPersistencia(ExceptionPersistencia.OBTENER_DATOS);
        }

        /*Devuelvo el resultado*/
        return ret;
    }
    
    
}

