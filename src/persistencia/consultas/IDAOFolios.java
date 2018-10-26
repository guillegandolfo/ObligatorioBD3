package persistencia.consultas;

import java.sql.Connection;
import java.util.LinkedList;
import logica.Excepciones.Exc_Persistencia;
import logica.Objetos.Folio;

public interface IDAOFolios {

    public boolean member(String cod, Connection con) throws Exc_Persistencia;

    public void insert(Folio fol, Connection con) throws Exc_Persistencia;

    public Folio find(String cod, Connection con) throws Exc_Persistencia;
    
    public void delete(String cod, Connection con) throws Exc_Persistencia;

    public LinkedList<Folio> listarFolios(Connection con) throws Exc_Persistencia;
}
