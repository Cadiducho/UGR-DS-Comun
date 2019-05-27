package com.ugr.farmaciads.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.squareup.moshi.Types;
import com.ugr.farmaciads.MainActivity;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Producto;

public class ProductosService {

    public interface ProductosCallback {
        void then(List<Producto> productos);
    }

    public void getProductos(Context context, ProductosCallback callback) {
        RestConnector restConnector = new RestConnector();
        Request request = new Request.Builder()
                .url(MainActivity.HOST + "/productos")
                .build();

        try {
            restConnector.run(request,
                    Types.newParameterizedType(List.class, Producto.class),
                    (ApiResponse<List<Producto>> response) -> {

                        if (response.getSuccess()) {
                            List<Producto> productos = response.getResult();

                            callback.then(productos);
                        } else {
                            Toast.makeText(context, "Error: No success", Toast.LENGTH_SHORT).show();
                            Log.wtf("Productos", "No es success");
                        }
                    });
        } catch (IOException e) {
            Log.e("FarmaciasService", "IOException obteniendo los productos", e);
            if (context != null) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
