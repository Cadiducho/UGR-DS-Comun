package p3.farmacia.facade;

import p3.farmacia.Database;
import p3.farmacia.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuariosFacade {

    private Connection connection = null;

    public UsuariosFacade() {
        try {
            Database database = new Database();
            this.connection = database.getConnection();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public boolean newUsuario(Usuario u) {
        String insertarUsuario = "INSERT INTO usuarios (nombre, nick, pass, rol, email) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertarUsuario);

            preparedStatement.setString(1, u.getNombre());
            preparedStatement.setString(2, u.getNick());
            preparedStatement.setString(3, u.getPassword());
            preparedStatement.setString(4, u.getRol());
            preparedStatement.setString(5, u.getEmail());
            preparedStatement.execute();

            this.connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean updateUsuario(Usuario u) {
        String updateUser = "UPDATE usuarios SET nombre=?, nick=? WHERE email=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateUser);
            preparedStatement.setString(1, u.getNombre());
            preparedStatement.setString(2, u.getNick());
            preparedStatement.setString(3, u.getEmail());
            preparedStatement.execute();

            this.connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUsuario(Usuario u) {
        String deleteUser = "DELETE FROM usuarios WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteUser);
            preparedStatement.setInt(1, u.getId());
            preparedStatement.execute();

            this.connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Usuario loginUsuario(Usuario u) {
        String loginUser = "SELECT * FROM usuarios WHERE email=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(loginUser);
            preparedStatement.setString(1, u.getEmail());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println("Contraseña en la db:"+rs.getString("pass"));
                System.out.println("Contraseña introducida:"+u.getPassword());

                if (rs.getString("pass").compareTo(u.getPassword())==0) {
                    System.out.println("Login correcto");
                    return new Usuario(rs.getInt("id"), rs.getString("nombre"),
                            rs.getString("nick"),rs.getString("rol"), rs.getString("email"));
                } else {
                    this.connection.close();
                    return null;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        String getProductos = "SELECT id, nombre, nick, rol, email FROM usuarios";
        List<Usuario> usuarios = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getProductos);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("nick"),rs.getString("rol"),rs.getString("email"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public Usuario getUsuario(int id) {
        String getUsuario = "SELECT id, nombre, nick, rol, email FROM usuarios WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getUsuario);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Usuario(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("nick"),rs.getString("rol"),rs.getString("email"));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

