
package conduccion.controladores;


public class ControladorAceite extends Controlador {
    private int rotacionesAceite;
    
    public ControladorAceite() {
        rotacionesAceite = 0;
    }
    
    public void actualizar() {
        rotacionesAceite = 0;
    }
    
    public void monotorizar(int revoluciones) {
        rotacionesAceite += revoluciones;
       if(rotacionesAceite >= 5e6)
            System.out.println("Aviso: Cambio de aceite");
    }
}
