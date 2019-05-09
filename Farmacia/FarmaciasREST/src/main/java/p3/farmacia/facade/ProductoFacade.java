package p3.farmacia.facade;

import p3.farmacia.Database;
import p3.farmacia.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoFacade {

    private Connection connection = null;

    public ProductoFacade() {
        try {
            Database database = new Database();
            this.connection = database.getConnection();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean newProducto(Producto p) {
        String insertarProducto = "INSERT INTO productos "
                + "(id, nombre, cantidad, precio, imagen) VALUES "
                + "(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertarProducto);

            preparedStatement.setInt(1, p.getId());
            preparedStatement.setString(2, p.getNombre());
            preparedStatement.setInt(3, p.getCantidad());
            preparedStatement.setFloat(4, p.getPrecio());
            preparedStatement.setString(5, p.getImagen());

            preparedStatement.execute();
            this.connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean updateProducto(Producto p) {

        String updateProducto = "UPDATE productos SET nombre=?, cantidad=?, precio=?, imagen=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateProducto);
            preparedStatement.setString(1, p.getNombre());
            preparedStatement.setInt(2, p.getCantidad());
            preparedStatement.setFloat(3, p.getPrecio());
            preparedStatement.setString(4, p.getImagen());
            preparedStatement.setInt(5, p.getId());
            preparedStatement.execute();

            this.connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProducto(Producto p) {

        String deleteProducto = "DELETE FROM productos WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteProducto);
            preparedStatement.setInt(1, p.getId());
            preparedStatement.execute();

            this.connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Producto> getProductos() {
        String getProductos = "SELECT * FROM productos";
        List<Producto> productos = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getProductos);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id"), rs.getString("nombre"),rs.getInt("cantidad"),
                        rs.getFloat("precio"),rs.getString("imagen"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public Producto getProducto(int id) {
        String getProductos = "SELECT * FROM productos WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getProductos);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Producto(
                        rs.getInt("id"), rs.getString("nombre"),rs.getInt("cantidad"),
                        rs.getFloat("precio"),rs.getString("imagen"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
