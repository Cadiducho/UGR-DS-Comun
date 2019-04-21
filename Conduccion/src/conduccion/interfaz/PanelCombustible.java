package conduccion.interfaz;

import conduccion.controladores.Motor;
import conduccion.observer.Observador;
import eu.hansolo.steelseries.gauges.Radial;
import eu.hansolo.steelseries.gauges.Radial2Top;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PanelCombustible extends JPanel implements Observador {

    private final Motor motor;
    final Radial2Top gauge = new Radial2Top();
    
    public PanelCombustible(Motor motor) {
        this.motor = motor;
        
        this.setSize(50, 100);
        //this.setBorder(BorderFactory.createLineBorder(Color.RED));
        
        JPanel panel = new JPanel() {
            @Override 
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };

        
        gauge.setTitle("Combustible");
        gauge.setUnitString("litros");
        
        gauge.setMaxValue(700);
        gauge.setLedVisible(false);
        gauge.setLedVisible(false);

        panel.setLayout(new BorderLayout());
        panel.add(gauge, BorderLayout.CENTER);
        
        this.add(panel);
    }
    
    @Override
    public void actualizar() {
        //System.out.println("Tengo " + motor.getLitosCombustible() + "L de combustible");
        gauge.setValue(motor.getLitosCombustible());
    }
    
}
