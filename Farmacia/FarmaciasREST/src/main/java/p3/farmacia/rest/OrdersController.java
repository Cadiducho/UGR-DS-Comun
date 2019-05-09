package p3.farmacia.rest;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.javalin.Context;
import io.javalin.Javalin;
import p3.farmacia.facade.OrderFacade;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Order;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class OrdersController implements Controller {

    @Override
    public void setUpRoutes(Javalin app) {
        app.routes(() -> {
            path("/orders", () -> {
                get(this::getOrders);
                put(this::addOrder);
                path("/:id", () -> {
                    get(this::getOrder);
                });
            });
        });
    }

    private void getOrder(Context ctx) {
        OrderFacade of = new OrderFacade();
        String id = ctx.pathParam("id");
        try {
            int idi = Integer.parseInt(id);
            Order order = of.getOrder(idi);
            if (order != null) {
                ctx.result(ApiResponse.builder().success(true).result(order).build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Order no encontrada").build().toJson());
            }
        } catch (NumberFormatException ex) {
            ctx.result(ApiResponse.builder().success(false).message("ID invalida").build().toJson());
        }
    }

    private void getOrders(Context ctx) {
        OrderFacade of = new OrderFacade();
        List<Order> orders = of.getOrders();

        if (orders.size() > 0) {
            Type type = Types.newParameterizedType(List.class, Order.class);
            ctx.result(ApiResponse.builder().success(true).result(orders).build().toJson(type));
        } else {
            ctx.result(ApiResponse.builder().success(false).message("No hay orders").build().toJson());
        }
    }

    private void addOrder(Context ctx) {
        OrderFacade of = new OrderFacade();
        JsonAdapter<Order> adapter = new Moshi.Builder().build().adapter(Order.class);
        try {
            Order order = adapter.fromJson(ctx.body());
            if (order == null) {
                ctx.result(ApiResponse.builder().success(false).message("Datos de order inválidos").build().toJson());
                return;
            }

            boolean ok = of.createOrder(order);
            if (ok) {
                ctx.result(ApiResponse.builder().success(true).message("Order creada").build().toJson());
            } else {
                ctx.result(ApiResponse.builder().success(false).message("Fallo en la base de datos").build().toJson());
            }
        } catch (IOException e) {
            e.printStackTrace();
            ctx.result(ApiResponse.builder().success(false).message("IOException").build().toJson());
        }
    }
}
