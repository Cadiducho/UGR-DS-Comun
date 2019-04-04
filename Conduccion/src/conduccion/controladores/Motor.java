package conduccion.controladores;

import conduccion.observer.Observable;

public class Motor extends Observable implements Runnable {
    
    private int revoluciones;
    private int revolucionesAutomatico;
    private int litrosCombustible;
    private boolean arrancado;
    private Modo modo;

    public Motor() {
        this.arrancado = false;
        this.revoluciones = 0;
        this.revolucionesAutomatico = 0;
        this.litrosCombustible = 700;
        this.modo = Modo.MANUAL;
    }
    
    public void arrancar() {
        if (!arrancado) {
            this.arrancado = true;
            this.revoluciones = 2000;
            System.out.println("Motor arrancado");
        } else {
            this.arrancado = false;
            this.revolucionesAutomatico = 0;
            System.out.println("Motor parado");
        }
    }
    
    public void acelerar() {
        if (arrancado && litrosCombustible > 0) {
           this.revoluciones += 20;
        }
    }
    
    public void frenar() {
        this.revoluciones -= 7;
    }

    public void activarModoAutomatico() {
        if(modo == Modo.MANUAL) {
            this.modo = Modo.AUTOMATICO;
            this.revolucionesAutomatico = this.revoluciones;
            System.out.println("Motor automatico, rev: " + this.revoluciones);
        }      
    }
    
    public void reactivarModoAutomatico() {
        if(modo == Modo.MANUAL) {
            this.modo = Modo.AUTOMATICO;

            System.out.println("Motor automatico reiniciado, rev: " + this.revoluciones);
            while(this.revoluciones < this.revolucionesAutomatico) {
                acelerar();
                System.out.println("Tengo " + getRevoluciones() + "RPM");
            }
        }
    }
    
    public void apagarModoAutomatico() {
        if(modo == Modo.AUTOMATICO) {
            this.modo = Modo.MANUAL;
            System.out.println("Motor automatico apagado, rev: " + this.revoluciones);
        }     
    }
    
    public int getRevoluciones() {
        return this.revoluciones;
    }
    
    public void setRevoluciones( int revoluciones)
    {
        this.revoluciones = revoluciones;
    }

    public int getLitosCombustible() {
        return litrosCombustible;
    }
    
    public Modo getModo() {
        return modo;
    }
    
    public void setModo(Modo modo) {
        this.modo = modo;
    }
    
    public boolean getArrancado() {
        return arrancado;
    }
        
    private void aplicarRozamiento() {
        if (revoluciones >= 3) {
            if (modo == Modo.AUTOMATICO)
                revoluciones = revolucionesAutomatico;
            
            revoluciones -= 3;
        }
    }    

    private void consumirGasolina() {
        if (litrosCombustible > 0) {
            litrosCombustible -= ((revoluciones) * 5e-11);
        }
    }
    
    @Override
    public void run() {
        
        while (true) {
            // Solo procesar el motor 4 veces por segundo
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                System.err.println("Error procesando el motor: ");
                System.err.println(ex.toString());
            }

            // Procesamiento del motor
            aplicarRozamiento();
            
            if (arrancado) {
                consumirGasolina();
            }
            
            this.notificar();
        }
    }
    
}
