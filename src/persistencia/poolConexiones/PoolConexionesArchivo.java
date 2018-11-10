package persistencia.poolConexiones;

public class PoolConexionesArchivo implements IPoolConexiones{
    private int escritores;
    private int lectores;
    
    public PoolConexionesArchivo(){
       this.escritores = 0;
       this.lectores = 0;
    }
    
    public IConexion obtenerConexion(boolean modifica) {
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

    public void liberarConexion(IConexion con, boolean ok) {
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
