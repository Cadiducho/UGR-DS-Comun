
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
        
        PanelPedales pedales = new PanelPedales(motor);
        new Thread(pedales).start();
        
        PanelMandos mandos = new PanelMandos(motor, pedales);
        new Thread(mandos).start();
        
        PanelMonotorizacion monotorizacion = new PanelMonotorizacion(motor);
        new Thread(monotorizacion).start();
        
        PanelCombustible gasolina = new PanelCombustible(motor);
        motor.attach(gasolina);
        PanelRevoluciones revoluciones = new PanelRevoluciones(motor);
        motor.attach(revoluciones);
        PanelVelocimetro velocimetro = new PanelVelocimetro(motor);
        motor.attach(velocimetro);
        PanelDistancia distancia = new PanelDistancia(motor);
        motor.attach(distancia);
        
        setTitle("Vehiculo");
        
        JPanel panelGraficas = new JPanel();
        panelGraficas.setLayout(new GridLayout(1, 3));
        panelGraficas.add(velocimetro);
        panelGraficas.add(gasolina);
        panelGraficas.add(revoluciones);
        //panelGraficas.add(distancia);
        
        getContentPane().setLayout(new GridLayout(2, 1));
        getContentPane().add(panelGraficas);
        getContentPane().add(pedales);
        getContentPane().add(mandos);
        getContentPane().add(monotorizacion);
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
