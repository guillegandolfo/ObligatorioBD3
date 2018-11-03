package persistencia.Fabrica;

import logica.excepciones.ConfiguracionException;
import logica.excepciones.Exc_Persistencia;
import persistencia.daos.DAOFolios;
import persistencia.daos.DAORevisiones;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;
import persistencia.poolConexiones.IPoolConexiones;
import persistencia.poolConexiones.PoolConexiones;


public class FabricaArchivo implements FabricaAbstracta{

    public IDAOFolios crearIDAOFolio() {
        return new DAOFolios();
    }

    public IDAORevisiones crearIDAORevisiones(String codFolio) {
        return (IDAORevisiones) new DAORevisiones(codFolio);
    }

    @Override
    public IPoolConexiones crearIPoolConexiones() throws ConfiguracionException, Exc_Persistencia {
        return new PoolConexiones();
    }

    
    
    
}
