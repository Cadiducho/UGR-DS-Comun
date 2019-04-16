
package conduccion.controladores;

public class ControladorPastillasFreno extends Controlador {
    private int rotacionesFreno;
    
    public ControladorPastillasFreno() {
        rotacionesFreno = 0;
    }
    
    public void actualizar() {
        rotacionesFreno = 0;
    }
    
    public void monotorizar(int revoluciones) {
        System.out.println("PASTILLAS: "+rotacionesFreno);
        rotacionesFreno += revoluciones;
       if(rotacionesFreno >= 5e8)
            System.out.println("Aviso: Cambio de pastillas de freno");
    }
}
