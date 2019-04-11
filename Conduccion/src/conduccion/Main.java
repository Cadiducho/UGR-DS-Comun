package conduccion;

import conduccion.controladores.ControladorDistancia;
import conduccion.controladores.ControladorVelocidad;
import conduccion.controladores.Motor;
import conduccion.interfaz.FrameInterfaz;

public class Main {

    public static void main(String[] args) {
        ControladorVelocidad controladorVelocidad = new ControladorVelocidad();
        ControladorDistancia controladorDistancia = new ControladorDistancia();
        
        Motor motor = new Motor(controladorVelocidad, controladorDistancia);
        new Thread(motor).start();
        
        FrameInterfaz interfaz = new FrameInterfaz(motor);
        interfaz.setVisible(true);
    }

}
