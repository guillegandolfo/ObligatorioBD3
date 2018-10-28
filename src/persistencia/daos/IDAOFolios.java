package persistencia.daos;

import java.util.LinkedList;
import persistencia.poolConexiones.IConexion;
import logica.excepciones.Exc_Persistencia;
import logica.objetos.Folio;

public interface IDAOFolios {

    public boolean member(String cod, IConexion ic) throws Exc_Persistencia;

    public void insert(Folio fol, IConexion ic) throws Exc_Persistencia;

    public Folio find(String cod, IConexion ic) throws Exc_Persistencia;
    
    public void delete(String cod, IConexion ic) throws Exc_Persistencia;

    public LinkedList<Folio> listarFolios(IConexion ic) throws Exc_Persistencia;
}
