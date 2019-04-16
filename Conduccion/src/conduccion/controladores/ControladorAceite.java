
package conduccion.controladores;


public class ControladorAceite extends Controlador {
    private int rotacionesAceite;
    
    public ControladorAceite() {
        rotacionesAceite = 0;
    }
    
    public void actualizar() {
        rotacionesAceite = 0;
    }
    
    public String monotorizar(int revoluciones) {
        String aviso = "";
        rotacionesAceite += revoluciones;
       if(rotacionesAceite >= 5e6)
            aviso = "Aviso: Cambio de aceite";
       return aviso;
    }
}
