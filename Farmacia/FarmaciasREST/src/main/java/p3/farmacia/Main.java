package p3.farmacia;

import io.javalin.Javalin;
import p3.farmacia.modelo.Farmacia;
import p3.farmacia.rest.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.createRoutes();
    }

    private final Javalin app;

    private Main() {
        app = Javalin.create().start(8080);
    }

    private void createRoutes() {

        ArrayList<Controller> controladores = new ArrayList<>();
        controladores.add(new FarmaciasController());
        controladores.add(new OrdersController());
        controladores.add(new ProductosController());
        controladores.add(new UsuariosController());

        controladores.forEach(c -> c.setUpRoutes(app));

        app.get("/", ctx -> ctx.result("Hello World"));


        System.out.println("Rest server iniciado");
    }
}
