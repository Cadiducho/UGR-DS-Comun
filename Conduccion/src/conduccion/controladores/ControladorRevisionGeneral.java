
package conduccion.controladores;


public class ControladorRevisionGeneral {
    private int rotacionesGeneral;
    
    public ControladorRevisionGeneral() {
        rotacionesGeneral = 0;
    }
    
    public void monotorizar(int revoluciones) {
        rotacionesGeneral += revoluciones;
       if(rotacionesGeneral >= 5e9)
            System.out.println("Aviso: Revision General");
    }
}