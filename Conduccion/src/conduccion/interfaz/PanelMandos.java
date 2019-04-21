package conduccion.interfaz;

import conduccion.controladores.Modo;
import conduccion.controladores.Motor;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
    private final ButtonGroup grupoBotones;
    
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
        grupoBotones= new ButtonGroup();
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
                pedales.setPedalFrenar(true);
                botonArrancar.setText("Apagar");
            }
            else {
                motor.arrancar();
                pedales.setPedalAcelerar(false);
                pedales.setPedalFrenar(false);
                botonArrancar.setText("Arrancar");   
                this.botonAcelerar.setEnabled(false);
                this.botonApagarAutomatico.setEnabled(false);
                this.botonMantener.setEnabled(false);
                this.botonReiniciar.setEnabled(false);
                grupoBotones.clearSelection();
                
            }
        });
        
        this.botonApagarAutomatico.addActionListener((ActionEvent e) -> {
            if (motor.getModo() == Modo.AUTOMATICO) {
                motor.apagarModoAutomatico();
            }
            this.botonMantener.setEnabled(false);
            this.botonAcelerar.setEnabled(false);
        });
        
        
        this.botonAcelerar.addActionListener((ActionEvent e) -> {
            this.botonMantener.setEnabled(true);
            this.botonApagarAutomatico.setEnabled(true);
               
            motor.activarModoAutomatico();
        });
        
        this.botonMantener.addActionListener((ActionEvent e) -> {
            this.botonReiniciar.setEnabled(true);
            motor.mantenerModoAutomatico();
        });
            
        this.botonReiniciar.addActionListener((ActionEvent e) -> {
            motor.reactivarModoAutomatico();
        });

        this.setBorder(BorderFactory.createEmptyBorder(0,10,10,10)); 
    }
    
    @Override
    public void run() {
        while (true) {
            // Solo procesar el motor 4 veces por segundo
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                System.err.println("Error procesando los mandos: ");
                System.err.println(ex.toString());
            }
            
            if (this.pedales.isPressedPedalAcelerar()) {
                grupoBotones.clearSelection();
                this.botonAcelerar.setEnabled(true);
                this.botonApagarAutomatico.setEnabled(false);
            }
            
            if (this.pedales.isPressedPedalFrenar()) {
                if(this.motor.getModo() == Modo.AUTOMATICO) {
                    this.botonApagarAutomatico.setSelected(true);
                    this.botonMantener.setEnabled(false);
                    this.botonAcelerar.setEnabled(false);
                    this.motor.apagarModoAutomatico();
                }
                
            }
            
            if(this.botonAcelerar.isSelected())
                motor.acelerar();
        }
    }
}
