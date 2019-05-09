package p3.farmacia.facade;

import p3.farmacia.Database;
import p3.farmacia.modelo.Order;
import p3.farmacia.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderFacade {

    private Connection connection = null;

    public OrderFacade() {
        try {
            Database database = new Database();
            this.connection = database.getConnection();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean createOrder(Order o) {
        String insertarOrder = "INSERT INTO orders (fk_producto, precio, fk_farmacia, fk_usuario) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertarOrder);
            for (Producto p : o.getProductos()) {
                preparedStatement.setInt(1, p.getId());
                preparedStatement.setFloat(2, p.getPrecio());
                preparedStatement.setInt(3, o.getFarmacia());
                preparedStatement.setInt(4, o.getUsuario());

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

            this.connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Order> getOrders(){
        String getOrders = "SELECT * FROM orders";
        ArrayList<Order> orders = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getOrders);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                orders.add(new Order(rs.getInt("id"), rs.getInt("fk_producto"),
                        rs.getFloat("precio"), rs.getInt("fk_farmacia"), rs.getInt("fk_usuario")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getOrder(int id){
        String getOrders = "SELECT * FROM orders WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getOrders);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Order(rs.getInt("id"), rs.getInt("fk_producto"),
                        rs.getFloat("precio"), rs.getInt("fk_farmacia"), rs.getInt("fk_usuario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
