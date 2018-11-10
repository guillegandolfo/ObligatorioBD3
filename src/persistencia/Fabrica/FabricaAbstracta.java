package persistencia.fabrica;

import logica.excepciones.ConfiguracionException;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;
import persistencia.poolConexiones.IPoolConexiones;

//Cambio
public interface FabricaAbstracta {

    public IDAOFolios crearIDAOFolio();

    public IDAORevisiones crearIDAORevisiones(String codFolio);
    
	IPoolConexiones crearIPoolConexiones() throws ConfiguracionException;


}
