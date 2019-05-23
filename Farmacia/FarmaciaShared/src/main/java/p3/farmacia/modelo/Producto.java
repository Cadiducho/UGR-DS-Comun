package p3.farmacia.modelo;

/**
 * Modelo de datos para un Producto
 */
public class Producto {

    private String nombre;
    private Integer cantidad;
    private Float precio;
    private String imagen;
    private Integer id;

    public Producto(Integer id, String nombre, Integer cantidad, Float precio, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Float getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public Integer getId() {
        return id;
    }
}
