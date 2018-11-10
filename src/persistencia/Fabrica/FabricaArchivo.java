package persistencia.fabrica;

import logica.excepciones.ConfiguracionException;
import persistencia.daos.*;
import persistencia.poolConexiones.IPoolConexiones;
import persistencia.poolConexiones.PoolConexionesArchivo;


public class FabricaArchivo implements FabricaAbstracta{

    public IDAOFolios crearIDAOFolio() {
        return new DAOFoliosArchivo();
    }

    public IDAORevisiones crearIDAORevisiones(String codFolio) {
        return new DAORevisionesArchivo(codFolio);
    }

    
    public IPoolConexiones crearIPoolConexiones() throws ConfiguracionException {
        return new PoolConexionesArchivo();
    }

    
    
    
}
