package persistencia.poolConexiones;

public class ConexionArchivo implements IConexion{
    private boolean modifica;
    
    public ConexionArchivo(boolean modifica){
        this. modifica = modifica;
    }
    
    public boolean getModifica(){
        return this.modifica;
    }
}
