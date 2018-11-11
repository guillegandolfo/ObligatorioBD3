package persistencia.Fabrica;

import logica.excepciones.ConfiguracionException;
import persistencia.daos.DAOFolios;
import persistencia.daos.DAORevisiones;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;
import persistencia.poolConexiones.IPoolConexiones;
import persistencia.poolConexiones.PoolConexiones;


//Cambio

public class FabricaMySQL implements FabricaAbstracta {

    public IDAOFolios crearIDAOFolio() {
        return new DAOFolios();
    }

    public IDAORevisiones crearIDAORevisiones(String codFolio) {
        return (IDAORevisiones) new DAORevisiones(codFolio);
    }


    public IPoolConexiones crearIPoolConexiones() throws ConfiguracionException {
        return new PoolConexiones();
    }


}
