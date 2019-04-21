package conduccion;

import conduccion.controladores.Motor;
import conduccion.interfaz.FrameInterfaz;

public class Main {

    public static void main(String[] args) {
        
        Motor motor = new Motor();
        new Thread(motor).start();
        
        FrameInterfaz interfaz = new FrameInterfaz(motor);
        interfaz.setVisible(true);
    }

}
