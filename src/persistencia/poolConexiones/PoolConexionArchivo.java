package persistencia.poolConexiones;

import logica.excepciones.PersistenciaException;


public class PoolConexionArchivo implements IPoolConexiones{
    private int escritores;
    private int lectores;
    
    public PoolConexionArchivo(){
       this.escritores = 0;
       this.lectores = 0;
    }
    
    @Override
    public IConexion obtenerConexion(boolean modifica) throws PersistenciaException {
        IConexion ret = null;
        synchronized (this) {
            while(ret == null){
                if(modifica){
                    if(escritores > 0 || lectores > 0){
                        try{
                            this.wait();
                        } catch (InterruptedException ex) {}
                    }
                    else{
                        ret = new ConexionArchivo(modifica);
                        this.escritores++;
                    }
                }
                else{
                    if(escritores > 0){
                        try{
                            this.wait();
                        } catch (InterruptedException ex) {}
                    }
                    else{
                        ret = new ConexionArchivo(modifica);
                        this.lectores++;
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public void liberarConexion(IConexion con, boolean ok) throws PersistenciaException {
        synchronized(this){
            if(((ConexionArchivo) con).getModifica()){
                this.escritores--;
            }
            else{
                this.lectores--;
            }
            this.notifyAll();
        }
    }

}
