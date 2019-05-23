package p3.farmacia.modelo;

/**
 * Modelo de datos abstracto de un Usuario
 */
public class Usuario {

    private String nombre;
    private String nick;
    private String rol;
    private String password;
    private String email;
    private Integer id;

    public Usuario() {
    }

    public Usuario(String nombre, String nick, String rol) {
        this.nombre = nombre;
        this.nick = nick;
        this.rol = rol;
    }

    public Usuario(String nombre, String nick, String rol, String email) {
        this.nombre = nombre;
        this.nick = nick;
        this.rol = rol;
        this.email = email;
    }
    public Usuario(String nombre, String nick, String rol, String password, String email) {
        this.nombre = nombre;
        this.nick = nick;
        this.rol = rol;
        this.password = password;
        this.email = email;
    }

    public Usuario(Integer id, String nombre, String nick, String rol, String password, String email) {
        this.nombre = nombre;
        this.nick = nick;
        this.rol = rol;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public Usuario(Integer id, String nombre, String nick, String rol, String email) {
        this.nombre = nombre;
        this.nick = nick;
        this.rol = rol;
        this.email = email;
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNick() {
        return nick;
    }

    public String getRol() {
        return rol;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }
}
