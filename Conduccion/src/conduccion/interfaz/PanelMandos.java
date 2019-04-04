package conduccion.interfaz;

import conduccion.controladores.Modo;
import conduccion.controladores.Motor;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class PanelMandos extends JPanel implements Runnable{
    
    private final Motor motor;
    private final PanelPedales pedales;
    
    private final JToggleButton botonArrancar;
    private final JToggleButton botonMantener;
    private final JToggleButton botonReiniciar;
    private final JToggleButton botonApagarAutomatico;
    private final JToggleButton botonAcelerar;
    
    public PanelMandos(Motor motor, PanelPedales pedales) {
        this.motor = motor;
        this.pedales = pedales;
        
        this.botonArrancar = new JToggleButton("Arrancar", false);
        this.botonMantener = new JToggleButton("Mantener", false);
        this.botonReiniciar = new JToggleButton("Reiniciar modo automatico", false);
        this.botonApagarAutomatico = new JToggleButton("Apagar modo automatico", false);
        this.botonAcelerar = new JToggleButton("acelerar en modo automatico", false);
        
        //Solo se muestran si el motor esta arrancado
        this.botonMantener.setEnabled(false);
        this.botonReiniciar.setEnabled(false);
        this.botonApagarAutomatico.setEnabled(false);
        this.botonAcelerar.setEnabled(false);
        
        //Permite que no se puedan tener varias opciones a la vez
        ButtonGroup grupoBotones= new ButtonGroup();
	grupoBotones.add(botonMantener);
	grupoBotones.add(botonReiniciar);
        grupoBotones.add(botonApagarAutomatico);
	grupoBotones.add(botonAcelerar);
        
        this.add(botonArrancar);
        this.add(botonMantener);
        this.add(botonReiniciar);
        this.add(botonApagarAutomatico);
        this.add(botonAcelerar);
        
        this.botonArrancar.addActionListener((ActionEvent e) -> {
            if (!motor.getArrancado()) {
                motor.arrancar();
                pedales.setPedalAcelerar(true);
                botonArrancar.setText("Apagar");
            }
            else {
                motor.arrancar();
                pedales.setPedalAcelerar(false);
                botonArrancar.setText("Encender");       
            }
        });
        
        this.botonAcelerar.addActionListener((ActionEvent e) -> {
            motor.acelerar();
            motor.setModo(Modo.AUTOMATICO);            
        });
        
        this.botonMantener.addActionListener((ActionEvent e) -> {
            if (motor.getModo() == Modo.MANUAL) {
                motor.activarModoAutomatico();
            }
        });
        
        this.botonReiniciar.addActionListener((ActionEvent e) -> {
            if (motor.getModo() == Modo.MANUAL) {
                motor.reactivarModoAutomatico();
            }          
        });
        
        this.botonApagarAutomatico.addActionListener((ActionEvent e) -> {
            if (motor.getModo() == Modo.AUTOMATICO) {
                motor.apagarModoAutomatico();
            }
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
            
            if (this.pedales.isPressedPedalAcelerar()) {
                System.out.println("entro");
                this.botonAcelerar.setEnabled(true);
            }
        }
    }
}
