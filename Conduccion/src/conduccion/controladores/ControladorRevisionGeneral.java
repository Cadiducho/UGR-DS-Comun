
package conduccion.controladores;


public class ControladorRevisionGeneral {
    private int rotacionesGeneral;
    
    public ControladorRevisionGeneral() {
        rotacionesGeneral = 0;
    }
    
    public void actualizar() {
        rotacionesGeneral = 0;
    }
    
    public String monotorizar(int revoluciones) {
        String aviso = "";
        rotacionesGeneral += revoluciones;
        if(rotacionesGeneral >= 5e9)
            aviso = "Aviso: Revision General";
        return aviso;
    }
}