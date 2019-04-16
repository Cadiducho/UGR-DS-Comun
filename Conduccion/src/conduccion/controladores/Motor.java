package conduccion.controladores;

import conduccion.observer.Observable;

public class Motor extends Observable implements Runnable {
    
    private ControladorVelocidad controladorVelocidad = new ControladorVelocidad();
    private ControladorDistancia controladorDistancia = new ControladorDistancia();
    private ControladorCombustible controladorCombustible = new ControladorCombustible();
    private ControladorAceite controladorAceite = new ControladorAceite();
    private ControladorPastillasFreno controladorPastillasFreno = new ControladorPastillasFreno();
    private ControladorRevisionGeneral controladorRevisionGeneral = new ControladorRevisionGeneral();
    
    private int revoluciones;
    private int revolucionesAutomatico;
    
    private int velocidad;
    private double distancia;
    private int litrosCombustible;
    
    private boolean arrancado;
    private boolean mantener;
    private Modo modo;
    
    String avisoAceite;
    String avisoFreno;
    String avisoGeneral;
    

    public Motor() {
        this.arrancado = false;
        this.mantener = false;
        this.revoluciones = 0;
        this.velocidad = 0;
        this.distancia = 0;
        this.litrosCombustible = controladorCombustible.getCombustible();
        this.revolucionesAutomatico = 0;
        this.modo = Modo.MANUAL;
        avisoAceite = "";
        avisoFreno = "";
        avisoGeneral = "";
    }
    
    public void arrancar() {
        if (!arrancado) {
            this.arrancado = true;
            this.revoluciones = 2000;
            //OJO! mirar!
            this.velocidad = controladorVelocidad.calcularVelocidad(this.revoluciones);
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
        this.mantener = false;
        this.modo = Modo.AUTOMATICO;
        System.out.println("Motor automatico, rev: " + this.revoluciones);

    }
    
    public void mantenerModoAutomatico() {
        this.mantener = true;
        this.revolucionesAutomatico = this.revoluciones;
        System.out.println("MANTENIENDO " + this.revoluciones);
          
    }
    
    public void reactivarModoAutomatico() {
        this.mantener = true;
        this.modo = Modo.AUTOMATICO;

        System.out.println("Motor automatico reiniciado, rev: " + this.revoluciones);
        while(this.revoluciones < this.revolucionesAutomatico) {
            acelerar();
            System.out.println("Tengo " + getRevoluciones() + "RPM");
        }
        System.out.println("REV: " + this.revoluciones + ", REVAUTO: " + this.revolucionesAutomatico);
        while(this.revoluciones > this.revolucionesAutomatico) {
            frenar();
            System.out.println("Tengo " + getRevoluciones() + "RPM");
        }
        
    }
    
    public void apagarModoAutomatico() {
        this.mantener = false;
        this.modo = Modo.MANUAL;
        System.out.println("Motor automatico apagado, rev: " + this.revoluciones);
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
    
    public int getVelocidad() {
        return this.velocidad;
    }
    
    public double getDistancia() {
        return this.distancia;
    }
             
    public ControladorVelocidad getControladorVelocidad() {
        return controladorVelocidad;
    }
    
    public ControladorAceite getControladorAceite() {
        return controladorAceite;
    }
    
    public ControladorPastillasFreno getControladorPastillasFreno() {
        return controladorPastillasFreno;
    }
    
    public ControladorRevisionGeneral getControladorRevisionGeneral() {
        return controladorRevisionGeneral;
    }
    
    public String getAvisoAceite() {
        return avisoAceite;
    }
    
    public String getAvisoFreno() {
        return avisoFreno;
    }
    
    public String getAvisoGeneral() {
        return avisoGeneral;
    }
    
    private void aplicarRozamiento() {
        if (this.revoluciones >= 3) {
            if (mantener)
                this.revoluciones = this.revolucionesAutomatico;
            else
                this.revoluciones -= 3;
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
                litrosCombustible = controladorCombustible.consumirGasolina(revoluciones);
                avisoAceite = controladorAceite.monotorizar(revoluciones);
                avisoFreno = controladorPastillasFreno.monotorizar(revoluciones);
                avisoGeneral = controladorRevisionGeneral.monotorizar(revoluciones);   
            }
            this.velocidad = this.controladorVelocidad.calcularVelocidad(revoluciones);
            this.distancia += this.controladorDistancia.calcularDistancia(this.velocidad);
            this.notificar();
        }
    }
    
}
