package persistencia.consultas;

import java.sql.Connection;

import logica.Excepciones.Exc_Persistencia;

public interface IDAOFolios {

public boolean member(String cod, Connection con) throws Exc_Persistencia;


}
