
package conduccion.controladores;

public class ControladorCombustible extends Controlador {
    private int litrosCombustible;
    
    public ControladorCombustible() {
        litrosCombustible = 700;
    }
    
    public int getCombustible() {
        return litrosCombustible;
    }
    public int consumirGasolina(int revoluciones) {
        if (litrosCombustible > 0) {
            litrosCombustible -= ((revoluciones) * 5e-11);
        }
        
        return litrosCombustible;
    }
}
