package conduccion.controladores;

public class ControladorVelocidad extends Controlador {
    int velocidad;
    
    public ControladorVelocidad() {
        this.velocidad = 0;
    }
    
    public int calcularVelocidad(int revoluciones) {
        this.velocidad = (int) (revoluciones*2*Math.PI*60*0.15/1000);
        return this.velocidad;
    }
}
