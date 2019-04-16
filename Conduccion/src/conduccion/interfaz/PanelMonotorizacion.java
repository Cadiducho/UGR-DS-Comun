
package conduccion.interfaz;

import conduccion.controladores.Motor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelMonotorizacion extends JPanel implements Runnable, ActionListener {
    private final Motor motor;
    
    private final JButton actualizarAceite;
    private final JButton actualizarFrenos;
    private final JButton actualizarGeneral;
    
    public PanelMonotorizacion(Motor motor) {
        this.motor = motor;
        
        actualizarAceite = new JButton("Actualizar aceite");
        actualizarFrenos = new JButton("Actualizar frenos");
        actualizarGeneral = new JButton("Actualizar revision general");
        
        this.add(actualizarAceite);
        this.add(actualizarFrenos);
        this.add(actualizarGeneral);
       
        this.setSize(500, 150);
        this.setBorder(BorderFactory.createEmptyBorder(0,10,10,10)); 
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        //Se establece que al pulsar el boton se actualice la temperatura
        if (event.getSource() == actualizarAceite)
        {
            System.out.println("Me has pulsado pero paso de ti porque me da la gana");
           motor.getControladorAceite().actualizar();
        }
        else if (event.getSource() == actualizarFrenos)
            motor.getControladorPastillasFreno().actualizar();
        else if (event.getSource() == actualizarGeneral)
            motor.getControladorRevisionGeneral().actualizar();
    }
        
    @Override
    public void run() {
        while (true) {
            // Solo procesar el motor 4 veces por segundo
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.err.println("Error procesando la monotorizacion: ");
                System.err.println(ex.toString());
            }
            
            if (actualizarAceite.getModel().isPressed())
               motor.getControladorAceite().actualizar();
            else if (actualizarFrenos.getModel().isPressed())
                motor.getControladorPastillasFreno().actualizar();
            else if (actualizarGeneral.getModel().isPressed())
                motor.getControladorRevisionGeneral().actualizar();
            
            if (this.motor.getVelocidad() > 0) {
                this.actualizarAceite.setEnabled(false);
                this.actualizarFrenos.setEnabled(false);
                this.actualizarGeneral.setEnabled(false);
            }
            
            else {
                this.actualizarAceite.setEnabled(true);
                this.actualizarFrenos.setEnabled(true);
                this.actualizarGeneral.setEnabled(true);
            }        
        }
    }
}
