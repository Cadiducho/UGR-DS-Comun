package p3.farmacia.rest;

import com.squareup.moshi.Types;
import io.javalin.Context;
import io.javalin.Javalin;
import p3.farmacia.facade.OrderFacade;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Order;

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

    }
}
