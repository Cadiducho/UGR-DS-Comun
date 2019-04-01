package conduccion.interfaz;

import conduccion.controladores.Motor;
import conduccion.observer.Observador;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PanelRevoluciones extends JPanel implements Observador {

    private final Motor motor;
    
    public PanelRevoluciones(Motor motor) {
        this.motor = motor;
        
        this.setSize(50, 100);
        this.setBorder(BorderFactory.createLineBorder(Color.green));
    }

    @Override
    public void actualizar() {
        System.out.println("Tengo " + motor.getRevoluciones() + "RPM");
    }
    
}
