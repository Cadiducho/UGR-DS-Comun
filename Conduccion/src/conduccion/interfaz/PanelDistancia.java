
package conduccion.interfaz;

import conduccion.controladores.Motor;
import conduccion.observer.Observador;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelDistancia extends JPanel implements Observador {
    private final Motor motor;
    private final JLabel label;
    private final JLabel distancia;
    private final DecimalFormat df = new DecimalFormat("#.####");
    
    public PanelDistancia(Motor motor) {
        this.motor = motor;
        
        label = new JLabel("He recorrido un total de:");
        distancia = new JLabel("0 kilómetros");
        
        this.add(label);
        this.add(distancia);
    }
    
    @Override
    public void actualizar() {
        //System.out.println("Tengo " + motor.getDistancia() + "km");
        distancia.setText(df.format(motor.getDistancia()) + " kilómetros");
    }
}
