package p3.farmacia.rest;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.javalin.Context;
import io.javalin.Javalin;
import p3.farmacia.facade.UsuariosFacade;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Usuario;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UsuariosController implements Controller {

    @Override
    public void setUpRoutes(Javalin app) {
        app.routes(() -> {
            path("/usuarios", () -> {
                get(this::getUsuarios);
                put(this::addUser);
                post(this::login);
                patch(this::updateUser);
                delete(this::deleteUser);
                path("/:id", () -> {
                    get(this::getUser);
                });
            });
        });
    }

    private void login(Context ctx) {
        UsuariosFacade uf = new UsuariosFacade();
        JsonAdapter<Usuario> adapter = new Moshi.Builder().build().adapter(Usuario.class);
        try {
            Usuario usuario = adapter.fromJson(ctx.body());
            if (usuario == null) {
                ctx.result(ApiResponse.builder().success(false).message("Datos de usuario inválidos").build().toJson());
                return;
            }

            Usuario loggedUser = uf.loginUsuario(usuario);
            if (loggedUser != null) {
                ctx.result(ApiResponse.builder().success(true).message("Login con éxito").result(loggedUser).build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Fallo en el login").build().toJson());
            }
        } catch (IOException e) {
            e.printStackTrace();
            ctx.result(ApiResponse.builder().success(false).message("IOException").build().toJson());
        }
    }

    private void getUser(Context ctx) {
        UsuariosFacade uf = new UsuariosFacade();
        String id = ctx.pathParam("id");
        try {
            int idi = Integer.parseInt(id);
            Usuario usuario = uf.getUsuario(idi);
            if (usuario != null) {
                ctx.result(ApiResponse.builder().success(true).result(usuario).build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Usuario no encontrado").build().toJson());
            }
        } catch (NumberFormatException ex) {
            ctx.result(ApiResponse.builder().success(false).message("ID invalida").build().toJson());
        }
    }

    private void getUsuarios(Context ctx) {
        UsuariosFacade uf = new UsuariosFacade();
        List<Usuario> usuarios = uf.getUsuarios();

        if (usuarios.size() > 0) {
            Type type = Types.newParameterizedType(List.class, Usuario.class);
            ctx.result(ApiResponse.builder().success(true).result(usuarios).build().toJson(type));
        } else {
            ctx.result(ApiResponse.builder().success(false).message("No hay usuarios").build().toJson());
        }
    }

    private void addUser(Context ctx) {
        UsuariosFacade uf = new UsuariosFacade();
        JsonAdapter<Usuario> adapter = new Moshi.Builder().build().adapter(Usuario.class);
        try {
            Usuario usuario = adapter.fromJson(ctx.body());
            if (usuario == null) {
                ctx.result(ApiResponse.builder().success(false).message("Datos de usuario inválidos").build().toJson());
                return;
            }

            boolean ok = uf.newUsuario(usuario);
            if (ok) {
                ctx.result(ApiResponse.builder().success(true).message("Usuario creado").result(usuario).build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Fallo en la base de datos").build().toJson());
            }
        } catch (IOException e) {
            e.printStackTrace();
            ctx.result(ApiResponse.builder().success(false).message("IOException").build().toJson());
        }
    }

    private void updateUser(Context ctx) {
        UsuariosFacade uf = new UsuariosFacade();
        JsonAdapter<Usuario> adapter = new Moshi.Builder().build().adapter(Usuario.class);
        try {
            Usuario usuario = adapter.fromJson(ctx.body());
            if (usuario == null) {
                ctx.result(ApiResponse.builder().success(false).message("Datos de usuario inválidos").build().toJson());
                return;
            }

            boolean ok = uf.updateUsuario(usuario);
            if (ok) {
                ctx.result(ApiResponse.builder().success(true).message("Usuario actualizado").result(usuario).build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Fallo en la base de datos").build().toJson());
            }
        } catch (IOException e) {
            e.printStackTrace();
            ctx.result(ApiResponse.builder().success(false).message("IOException").build().toJson());
        }
    }

    private void deleteUser(Context ctx) {
        UsuariosFacade uf = new UsuariosFacade();
        JsonAdapter<Usuario> adapter = new Moshi.Builder().build().adapter(Usuario.class);
        try {
            Usuario usuario = adapter.fromJson(ctx.body());
            if (usuario == null) {
                ctx.result(ApiResponse.builder().success(false).message("Datos de usuario inválidos").build().toJson());
                return;
            }

            boolean ok = uf.deleteUsuario(usuario);
            if (ok) {
                ctx.result(ApiResponse.builder().success(true).message("Usuario eliminado").result(usuario).build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Fallo en la base de datos").build().toJson());
            }
        } catch (IOException e) {
            e.printStackTrace();
            ctx.result(ApiResponse.builder().success(false).message("IOException").build().toJson());
        }
    }
}
