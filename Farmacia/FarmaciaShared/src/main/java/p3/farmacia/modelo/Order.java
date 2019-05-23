package p3.farmacia.modelo;

/**
 * Modelo de datos para una orden de compra
 */
public class Order {

    private Integer id;
    private Integer fk_producto;
    private Integer cantidad;
    private Float precio;
    private Integer farmacia;
    private Integer usuario;

    public Order(Integer id, Integer fk_producto, Float precio, Integer farmacia, Integer usuario) {
        this.id = id;
        this.fk_producto = fk_producto;
        this.precio = precio;
        this.farmacia = farmacia;
        this.usuario = usuario;
    }

    public void setFk_producto(Integer fk_producto) {
        this.fk_producto = fk_producto;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public void setFarmacia(Integer farmacia) {
        this.farmacia = farmacia;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public Integer getFk_producto() {
        return fk_producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Float getPrecio() {
        return precio;
    }

    public Integer getFarmacia() {
        return farmacia;
    }

    public Integer getUsuario() {
        return usuario;
    }
}
