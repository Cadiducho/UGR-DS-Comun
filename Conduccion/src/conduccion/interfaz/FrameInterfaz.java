
package conduccion.interfaz;

import conduccion.controladores.Motor;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
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
        
        PanelMonotorizacion monitorizacion = new PanelMonotorizacion(motor);
        new Thread(monitorizacion).start();
        
        PanelCombustible gasolina = new PanelCombustible(motor);
        motor.attach(gasolina);
        PanelRevoluciones revoluciones = new PanelRevoluciones(motor);
        motor.attach(revoluciones);
        PanelVelocimetro velocimetro = new PanelVelocimetro(motor);
        motor.attach(velocimetro);
        PanelDistancia distancia = new PanelDistancia(motor);
        motor.attach(distancia);
        PanelAvisoAceite avisoAceite = new PanelAvisoAceite(motor);
        motor.attach(avisoAceite);
        PanelAvisoFrenos avisoFrenos = new PanelAvisoFrenos(motor);
        motor.attach(avisoFrenos);
        PanelAvisoGeneral avisoGeneral = new PanelAvisoGeneral(motor);
        motor.attach(avisoGeneral);
        
        setTitle("Vehiculo");
        
        JPanel panelGraficas = new JPanel();
        panelGraficas.setLayout(new GridLayout(1, 3));
        panelGraficas.add(velocimetro);
        panelGraficas.add(gasolina);
        panelGraficas.add(revoluciones);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 1));
        panelBotones.add(pedales);
        panelBotones.add(mandos);
        
        JPanel panelAvisos = new JPanel();
        panelAvisos.setLayout(new GridLayout(1, 3));
        panelAvisos.add(avisoAceite);
        panelAvisos.add(avisoFrenos);
        panelAvisos.add(avisoGeneral);
        panelAvisos.setBorder(BorderFactory.createTitledBorder("Avisos"));
        
        JPanel panelMonitorYAvisos = new JPanel();
        panelMonitorYAvisos.setLayout(new GridLayout(3, 1));
        panelMonitorYAvisos.add(monitorizacion);
        panelMonitorYAvisos.add(distancia);
        panelMonitorYAvisos.add(panelAvisos);
        
        
        getContentPane().setLayout(new GridLayout(2, 2));
        getContentPane().add(panelGraficas);
        getContentPane().add(panelMonitorYAvisos);
        getContentPane().add(panelBotones);
        
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
