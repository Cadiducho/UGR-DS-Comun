
package conduccion.controladores;

public class ControladorPastillasFreno extends Controlador {
    private int rotacionesFreno;
    
    public ControladorPastillasFreno() {
        rotacionesFreno = 0;
    }
    
    public void actualizar() {
        rotacionesFreno = 0;
    }
    
    public String monotorizar(int revoluciones) {
        String aviso = "";
        rotacionesFreno += revoluciones;
        
        if(rotacionesFreno >= 5e8)
            aviso = "Aviso: Cambio de pastillas de freno";
        return aviso;
    }
}
