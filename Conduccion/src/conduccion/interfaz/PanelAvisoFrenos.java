
package conduccion.interfaz;

import conduccion.controladores.Motor;
import conduccion.observer.Observador;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PanelAvisoFrenos extends JPanel implements Observador {
     private final Motor motor;
    
    public PanelAvisoFrenos(Motor motor) {
        this.motor = motor;
        
        this.setPreferredSize(new Dimension(300, 200));
        this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }

    @Override
    public void actualizar() {
        System.out.println(motor.getAvisoFreno());
    }
}
