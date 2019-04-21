package conduccion.interfaz;

import conduccion.controladores.Motor;
import conduccion.observer.Observador;
import eu.hansolo.steelseries.gauges.Radial;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PanelRevoluciones extends JPanel implements Observador {

    private final Motor motor;
    final Radial gauge = new Radial();
    
    public PanelRevoluciones(Motor motor) {
        this.motor = motor;
        
        this.setSize(50, 100);
        //this.setBorder(BorderFactory.createLineBorder(Color.green));
        
        JPanel panel = new JPanel() {
            @Override 
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };

        
        gauge.setTitle("Revoluciones");
        gauge.setUnitString("RPM");
        gauge.setLedVisible(false);
        gauge.setLcdVisible(false);
        gauge.setMinValue(1200);
        gauge.setMaxValue(3500);

        panel.setLayout(new BorderLayout());
        panel.add(gauge, BorderLayout.CENTER);
        
        this.add(panel);
    }

    @Override
    public void actualizar() {
        //System.out.println("Tengo " + motor.getRevoluciones() + "RPM");
        gauge.setValueAnimated(motor.getRevoluciones());
    }
    
}
