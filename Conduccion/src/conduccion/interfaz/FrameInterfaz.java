
package conduccion.interfaz;

import conduccion.controladores.Motor;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameInterfaz extends JFrame {
    
    private final Motor motor;

    public FrameInterfaz(Motor motor) {
        this.motor = motor;
        
        PanelMandos mandos = new PanelMandos(motor);
        new Thread(mandos).start();
        
        PanelCombustible gasolina = new PanelCombustible(motor);
        motor.attach(gasolina);
        PanelRevoluciones revoluciones = new PanelRevoluciones(motor);
        motor.attach(revoluciones);
        PanelVelocimetro velocimetro = new PanelVelocimetro(motor);
        motor.attach(velocimetro);
        
        setTitle("Vehiculo");
        
        JPanel panelGraficas = new JPanel();
        panelGraficas.setLayout(new GridLayout(1, 3));
        panelGraficas.add(velocimetro);
        panelGraficas.add(gasolina);
        panelGraficas.add(revoluciones);
        
        getContentPane().setLayout(new GridLayout(2, 1));
        getContentPane().add(panelGraficas);
        getContentPane().add(mandos);
        this.setResizable(false);
        this.pack();
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }
}
