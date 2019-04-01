package conduccion.controladores;

import conduccion.observer.Observable;

public class Motor extends Observable implements Runnable {
    
    private int revoluciones;
    private int litrosCombustible;
    private boolean arrancado;

    public Motor() {
        this.arrancado = false;
        this.revoluciones = 0;
        this.litrosCombustible = 700;
    }
    
    public void arrancar() {
        if (!arrancado) {
            this.arrancado = true;
            this.revoluciones = 2000;
            System.out.println("Motor arrancado");
        } else {
            this.arrancado = false;
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

    public int getRevoluciones() {
        return revoluciones;
    }

    public int getLitosCombustible() {
        return litrosCombustible;
    }
    
    private void aplicarRozamiento() {
        if (revoluciones >= 3) {
            revoluciones -= 3;
        }
    }    

    private void consumirGasolina() {
        if (litrosCombustible > 0) {
            litrosCombustible -= ((revoluciones) * 5e-11);
        }
    }

    public boolean getArrancado()
    {
        return arrancado;
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
