package p3.farmacia.rest;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.javalin.Context;
import io.javalin.Javalin;
import p3.farmacia.facade.ProductoFacade;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Producto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ProductosController implements Controller {

    @Override
    public void setUpRoutes(Javalin app) {
        app.routes(() -> {
            path("/productos", () -> {
                get(this::getProductos);
                put(this::addProducto);
                patch(this::updateProducto);
                delete(this::deleteProducto);
                path("/:id", () -> {
                    get(this::getProducto);
                });
            });
        });
    }

    private void getProducto(Context ctx) {
        ProductoFacade pf = new ProductoFacade();
        String id = ctx.pathParam("id");
        try {
            int idi = Integer.parseInt(id);
            Producto producto = pf.getProducto(idi);
            if (producto != null) {
                ctx.result(ApiResponse.builder().success(true).result(producto).build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Producto no encontrado").build().toJson());
            }
        } catch (NumberFormatException ex) {
            ctx.result(ApiResponse.builder().success(false).message("ID invalida").build().toJson());
        }
    }

    private void getProductos(Context ctx) {
        ProductoFacade pf = new ProductoFacade();
        List<Producto> productos = pf.getProductos();

        if (productos.size() > 0) {
            Type type = Types.newParameterizedType(List.class, Producto.class);
            ctx.result(ApiResponse.builder().success(true).result(productos).build().toJson(type));
        } else {
            ctx.result(ApiResponse.builder().success(false).message("No hay productos").build().toJson());
        }
    }

    private void addProducto(Context ctx) {
        ProductoFacade pf = new ProductoFacade();
        JsonAdapter<Producto> adapter = new Moshi.Builder().build().adapter(Producto.class);
        try {
            Producto producto = adapter.fromJson(ctx.body());
            if (producto == null) {
                ctx.result(ApiResponse.builder().success(false).message("Datos de producto inválidos").build().toJson());
                return;
            }

            boolean ok = pf.newProducto(producto);
            if (ok) {
                ctx.result(ApiResponse.builder().success(true).message("Producto creada").result(producto).build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Fallo en la base de datos").build().toJson());
            }
        } catch (IOException e) {
            e.printStackTrace();
            ctx.result(ApiResponse.builder().success(false).message("IOException").build().toJson());
        }
    }

    private void updateProducto(Context ctx) {
        ProductoFacade pf = new ProductoFacade();
        JsonAdapter<Producto> adapter = new Moshi.Builder().build().adapter(Producto.class);
        try {
            Producto producto = adapter.fromJson(ctx.body());
            if (producto == null) {
                ctx.result(ApiResponse.builder().success(false).message("Datos de producto inválidos").build().toJson());
                return;
            }

            boolean ok = pf.updateProducto(producto);
            if (ok) {
                ctx.result(ApiResponse.builder().success(true).message("Producto actualizado").result(producto).build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Fallo en la base de datos").build().toJson());
            }
        } catch (IOException e) {
            e.printStackTrace();
            ctx.result(ApiResponse.builder().success(false).message("IOException").build().toJson());
        }
    }

    private void deleteProducto(Context ctx) {
        ProductoFacade pf = new ProductoFacade();
        JsonAdapter<Producto> adapter = new Moshi.Builder().build().adapter(Producto.class);
        try {
            Producto producto = adapter.fromJson(ctx.body());
            if (producto == null) {
                ctx.result(ApiResponse.builder().success(false).message("Datos de producto inválidos").build().toJson());
                return;
            }

            boolean ok = pf.deleteProducto(producto);
            if (ok) {
                ctx.result(ApiResponse.builder().success(true).message("Producto eliminado").build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Fallo en la base de datos").build().toJson());
            }
        } catch (IOException e) {
            e.printStackTrace();
            ctx.result(ApiResponse.builder().success(false).message("IOException").build().toJson());
        }
    }

}
