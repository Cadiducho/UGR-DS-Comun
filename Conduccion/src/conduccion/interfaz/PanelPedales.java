package conduccion.interfaz;

import conduccion.controladores.Motor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelPedales extends JPanel implements Runnable {
    
    private final Motor motor;
    
    private final JButton pedalAcelerar;
    private final JButton pedalFrenar;
    
    
    public PanelPedales(Motor motor) {
        this.motor = motor;
        
        this.pedalAcelerar = new JButton("Pedal acelerar");
        this.pedalAcelerar.setEnabled(false);
        this.pedalFrenar = new JButton("Pedal frenar");
        this.pedalFrenar.setEnabled(false);
        
        this.add(pedalAcelerar);
        this.add(pedalFrenar);
        
        this.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
    }
    
    public void setPedalAcelerar(boolean estado) {
        this.pedalAcelerar.setEnabled(estado);
    }
    
    public void setPedalFrenar(boolean estado) {
        this.pedalFrenar.setEnabled(estado);
    }
    
    public boolean isPressedPedalAcelerar() {
        return this.pedalAcelerar.getModel().isPressed();
    }
    
    public boolean isPressedPedalFrenar() {
        return this.pedalFrenar.getModel().isPressed();
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
            
            if (this.pedalAcelerar.getModel().isPressed()) {
                motor.acelerar();
            }
            
            if (this.pedalFrenar.getModel().isPressed()) {
                motor.frenar();
            }
        }
    }
}
