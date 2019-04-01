package conduccion.interfaz;

import conduccion.controladores.Motor;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class PanelMandos extends JPanel implements Runnable {
    
    private final Motor motor;
    
    private final JToggleButton botonArrancar;
    private final JButton botonAcelerar;
    private final JButton botonFrenar;
    
    public PanelMandos(Motor motor) {
        this.motor = motor;
        
        this.botonArrancar = new JToggleButton("Arrancar", false);
        this.botonAcelerar = new JButton("Acelerar");
        this.botonAcelerar.setEnabled(false);
        this.botonFrenar = new JButton("Frenar");
        
        this.add(botonArrancar);
        this.add(botonAcelerar);
        this.add(botonFrenar);
        
        this.botonArrancar.addActionListener((ActionEvent e) -> {
            motor.arrancar();
            botonAcelerar.setEnabled(true);
        });
        
        this.setSize(500, 150);
        this.setBorder(BorderFactory.createEmptyBorder(0,10,10,10)); 
    }

    @Override
    public void run() {
        while (true) {
            // Solo procesar el motor 4 veces por segundo
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                System.err.println("Error procesando los pedales: ");
                System.err.println(ex.toString());
            }
            
            if (this.botonAcelerar.getModel().isPressed()) {
                motor.acelerar();
            }
            
            if (this.botonAcelerar.getModel().isPressed()) {
                motor.frenar();
            }
        }
    }
}
