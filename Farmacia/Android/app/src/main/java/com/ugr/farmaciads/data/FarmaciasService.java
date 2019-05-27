package com.ugr.farmaciads.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.squareup.moshi.Types;
import com.ugr.farmaciads.MainActivity;
import com.ugr.farmaciads.ui.mapa.MapaFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Farmacia;

public class FarmaciasService {

    public interface FarmaciasCallback {
        void then(List<Farmacia> farmacias);
    }

    public void getFarmacias(Context context, FarmaciasCallback callback) {
        RestConnector restConnector = new RestConnector();
        Request request = new Request.Builder()
                .url(MainActivity.HOST + "/farmacias")
                .build();

        final List<Farmacia> farmacias = new ArrayList<>();
        try {
            restConnector.run(request,
                    Types.newParameterizedType(List.class, Farmacia.class),
                    (ApiResponse<List<Farmacia>> response) -> {

                        if (response.getSuccess()) {
                            List<Farmacia> resultFarmacias = response.getResult();
                            farmacias.addAll(resultFarmacias);
                            Log.e("SERVICe", "OBTENIDAS FARMACIAS" + resultFarmacias.toString());
                            callback.then(resultFarmacias);
                        } else {
                            if (context != null) {
                                Toast.makeText(context, "Error: No success", Toast.LENGTH_SHORT).show();
                            }
                            Log.wtf("FarmaciasService", "No es success");
                        }
                    });
        } catch (IOException e) {
            Log.e("FarmaciasService", "IOException obteniendo las farmacias", e);
            if (context != null) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
