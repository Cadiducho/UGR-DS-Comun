package p3.farmacia.rest;

import com.squareup.moshi.Types;
import io.javalin.Context;
import io.javalin.Javalin;
import p3.farmacia.facade.UsuariosFacade;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Usuario;

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

    }

    private void updateUser(Context ctx) {

    }

    private void deleteUser(Context ctx) {

    }
}
