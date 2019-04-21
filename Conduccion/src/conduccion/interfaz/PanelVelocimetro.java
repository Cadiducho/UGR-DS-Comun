package conduccion.interfaz;

import conduccion.controladores.Motor;
import conduccion.observer.Observador;
import eu.hansolo.steelseries.gauges.DigitalRadial;
import eu.hansolo.steelseries.gauges.Radial;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PanelVelocimetro extends JPanel implements Observador {

    private final Motor motor;
    final Radial gauge = new Radial();
    
    public PanelVelocimetro(Motor motor) {
        this.motor = motor;
        
        this.setPreferredSize(new Dimension(310, 310));
//        this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        
        JPanel panel = new JPanel() {
            @Override 
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };

        
        gauge.setTitle("Velocidad");
        gauge.setUnitString("Km/h");
        
        gauge.setMaxValue(220);
        gauge.setLedVisible(false);

        panel.setLayout(new BorderLayout());
        panel.add(gauge, BorderLayout.CENTER);
        
        this.add(panel);
    }

    @Override
    public void actualizar() {
        //System.out.println("Tengo " + motor.getVelocidad() + "km/h");
        gauge.setValueAnimated(motor.getVelocidad());
    }
    
}
