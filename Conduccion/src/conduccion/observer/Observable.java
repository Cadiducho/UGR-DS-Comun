package conduccion.observer;

import java.util.ArrayList;

public abstract class Observable {
    
    private final ArrayList<Observador> observadores = new ArrayList<>();
    
    public synchronized void attach(Observador observer) {
        observadores.add(observer);
    }
    
    public synchronized void detach(Observador observer) {
        observadores.remove(observer);
    }
    
    public synchronized void notificar() {
        observadores.forEach(Observador::actualizar);
    }
    
}
