
package conduccion.controladores;

public class ControladorDistancia extends Controlador{
    double distancia;
    
    public ControladorDistancia() {
        this.distancia = 0;
    }
    
    public double calcularDistancia(int velocidad) {
        this.distancia = (velocidad*0.25/3600);
        return this.distancia;
    }
}
