package p3.farmacia.facade;

import p3.farmacia.Database;
import p3.farmacia.modelo.Farmacia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmaciaFacade {

    private Connection connection = null;

    public FarmaciaFacade() {
        try {
            Database database = new Database();
            this.connection = database.getConnection();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean newFarmacia(Farmacia f) {
        String insertarFarmacia = "INSERT INTO farmacias"
                + "(nombre, latitud, longitud) VALUES"
                + "(?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertarFarmacia);

            preparedStatement.setString(1, f.getNombre());
            preparedStatement.setFloat(2, f.getLatitud());
            preparedStatement.setFloat(3, f.getLongitud());


            preparedStatement.execute();
            this.connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean updateFarmacia(Farmacia f) {

        String updateFarmacia = "UPDATE farmacias SET nombre=?, latitud=?, longitud=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateFarmacia);
            preparedStatement.setString(1, f.getNombre());
            preparedStatement.setFloat(2, f.getLatitud());
            preparedStatement.setFloat(3, f.getLongitud());
            preparedStatement.setInt(4, f.getId());
            preparedStatement.execute();

            this.connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFarmacia(Farmacia p) {
        String deleteFarmacia = "DELETE FROM farmacias WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteFarmacia);
            preparedStatement.setInt(1, p.getId());
            preparedStatement.execute();

            this.connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Farmacia> getFarmacias() {
        String getFarmacias = "SELECT * FROM farmacias";
        List<Farmacia> farmacias = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getFarmacias);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                farmacias.add(new Farmacia(rs.getInt("id"), rs.getString("nombre"),
                        rs.getFloat("latitud"), rs.getFloat("longitud"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmacias;
    }

    public Farmacia getFarmacia(int id) {
        String getFarmacias = "SELECT * FROM farmacias WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getFarmacias);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Farmacia(rs.getInt("id"), rs.getString("nombre"),
                        rs.getFloat("latitud"), rs.getFloat("longitud"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
