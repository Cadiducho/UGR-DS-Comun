package p3.farmacia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    public Database() throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        try {
            String port = "3306";
            String hostname = "dandelion.cadiducho.com";
            String databasename = "ds";
            String username = "ds";
            String password = "ds";

            this.connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + databasename + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }
    public Connection getConnection() {
        return this.connection;
    }
}
