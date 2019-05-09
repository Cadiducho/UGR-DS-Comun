package p3.farmacia.modelo;

import java.util.List;

/**
 * Modelo de datos para una orden de compra
 */
public class Order {

    private int id;
    private List<Producto> productos;
    private int fk_producto;
    private int cantidad;
    private float precio;
    private int farmacia;
    private int usuario;

    public Order(int id, int fk_producto, float precio, int farmacia, int usuario) {
        this.id = id;
        this.fk_producto = fk_producto;
        this.precio = precio;
        this.farmacia = farmacia;
        this.usuario = usuario;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void setFk_producto(int fk_producto) {
        this.fk_producto = fk_producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setFarmacia(int farmacia) {
        this.farmacia = farmacia;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public int getFk_producto() {
        return fk_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public int getFarmacia() {
        return farmacia;
    }

    public int getUsuario() {
        return usuario;
    }
}
