package p3.farmacia.modelo;

/**
 * Modelo de datos para una Farmacia
 */
public class Farmacia {

    private final int id;
    private String nombre;
    private float latitud;
    private float longitud;

    public Farmacia(int id, String nombre, float latitud, float longitud) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }
}
