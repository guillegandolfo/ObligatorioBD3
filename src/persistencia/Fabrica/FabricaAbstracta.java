package persistencia.Fabrica;

import logica.excepciones.ConfiguracionException;
import logica.excepciones.Exc_Persistencia;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;
import persistencia.poolConexiones.IPoolConexiones;


public interface FabricaAbstracta {

    public IDAOFolios crearIDAOFolio();

    public IDAORevisiones crearIDAORevisiones(String codFolio);
    
	IPoolConexiones crearIPoolConexiones() throws ConfiguracionException, Exc_Persistencia;


}
