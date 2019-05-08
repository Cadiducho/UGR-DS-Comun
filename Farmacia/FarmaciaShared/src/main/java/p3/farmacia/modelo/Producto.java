package p3.farmacia.modelo;

/**
 * Modelo de datos para un Producto
 */
public class Producto {

    private String nombre;
    private int cantidad;
    private float precio;
    private String imagen;
    private int id;

    public Producto(int id, String nombre, int cantidad, float precio, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public int getId() {
        return id;
    }
}
