package p3.farmacia.modelo;

/**
 * Modelo de datos para una Farmacia
 */
public class Farmacia {

    private final Integer id;
    private String nombre;
    private Float latitud;
    private Float longitud;

    public Farmacia(Integer id, String nombre, Float latitud, Float longitud) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Float getLatitud() {
        return latitud;
    }

    public Float getLongitud() {
        return longitud;
    }
}
