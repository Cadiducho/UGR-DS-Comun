package p3.farmacia.rest;

import com.squareup.moshi.Types;
import io.javalin.Context;
import io.javalin.Javalin;
import p3.farmacia.facade.FarmaciaFacade;
import p3.farmacia.facade.ProductoFacade;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Farmacia;
import p3.farmacia.modelo.Producto;

import java.lang.reflect.Type;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class FarmaciasController implements Controller {

    @Override
    public void setUpRoutes(Javalin app) {
        app.routes(() -> {
            path("/farmacias", () -> {
                get(this::getFarmacias);
                put(this::addFarmacia);
                patch(this::updateFarmacia);
                delete(this::deleteFarmacia);
                path("/:id", () -> {
                    get(this::getFarmacia);
                });
            });
        });
    }

    private void getFarmacia(Context ctx) {
        FarmaciaFacade ff = new FarmaciaFacade();
        String id = ctx.pathParam("id");
        try {
            int idi = Integer.parseInt(id);
            Farmacia farmacia = ff.getFarmacia(idi);
            if (farmacia != null) {
                ctx.result(ApiResponse.builder().success(true).result(farmacia).build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Farmacia no encontrada").build().toJson());
            }
        } catch (NumberFormatException ex) {
            ctx.result(ApiResponse.builder().success(false).message("ID invalida").build().toJson());
        }
    }

    private void getFarmacias(Context ctx) {
        FarmaciaFacade ff = new FarmaciaFacade();
        List<Farmacia> farmacias = ff.getFarmacias();

        if (farmacias.size() > 0) {
            Type type = Types.newParameterizedType(List.class, Farmacia.class);
            ctx.result(ApiResponse.builder().success(true).result(farmacias).build().toJson(type));
        } else {
            ctx.result(ApiResponse.builder().success(false).message("No hay farmacias").build().toJson());
        }
    }

    private void addFarmacia(Context ctx) {

    }

    private void updateFarmacia(Context ctx) {

    }

    private void deleteFarmacia(Context ctx) {

    }
}
