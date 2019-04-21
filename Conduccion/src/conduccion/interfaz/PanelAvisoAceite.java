
package conduccion.interfaz;

import conduccion.controladores.Motor;
import conduccion.observer.Observador;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelAvisoAceite extends JPanel implements Observador {
    private final Motor motor;
    private final JLabel labelAviso;
    
    public PanelAvisoAceite(Motor motor) {
        this.motor = motor;
        
        labelAviso = new JLabel();
        this.add(labelAviso);
        this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }

    @Override
    public void actualizar() {
        labelAviso.setText(motor.getAvisoAceite());
    }
}
