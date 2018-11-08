package persistencia.Fabrica;

import logica.excepciones.ConfiguracionException;
import logica.excepciones.Exc_Persistencia;
import persistencia.daos.DAOFoliosArchivo;
import persistencia.daos.DAORevisiones;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;
import persistencia.poolConexiones.IPoolConexiones;
import persistencia.poolConexiones.PoolConexionesArchivo;


public class FabricaArchivo implements FabricaAbstracta{

    public IDAOFolios crearIDAOFolio() {
        return new DAOFoliosArchivo();
    }

    public IDAORevisiones crearIDAORevisiones(String codFolio) {
        return (IDAORevisiones) new DAORevisiones(codFolio);
    }

    
    public IPoolConexiones crearIPoolConexiones() throws ConfiguracionException {
        return new PoolConexionesArchivo();
    }

    
    
    
}
