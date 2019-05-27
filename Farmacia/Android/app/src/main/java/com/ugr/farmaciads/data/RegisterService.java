package com.ugr.farmaciads.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.squareup.moshi.Types;
import com.ugr.farmaciads.MainActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Usuario;

public class RegisterService {

    public interface RegisterCallback {
        void sucess(Usuario usuario);
        void error();
    }

    public void register(String email, String nombre, String nick, String password, Context context, RegisterCallback callback) {
        RestConnector restConnector = new RestConnector();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,
                "{\n" +
                        "    \"email\": \"" + email + "\",\n" +
                        "    \"nick\": \"" + nick + "\",\n" +
                        "    \"nombre\": \"" + nombre + "\",\n" +
                        "    \"rol\": \"Usuario\",\n" +
                        "    \"password\": \"" + password + "\"\n" +
                        " }");
        Request request = new Request.Builder()
                .method("PUT", body)
                .url(MainActivity.HOST + "/usuarios")
                .build();

        try {
            restConnector.run(request,
                    Types.newParameterizedType(Usuario.class),
                    (ApiResponse<Usuario> response) -> {
                        if (response.getSuccess()) {
                            Usuario resultUsuario = response.getResult();
                            callback.sucess(resultUsuario);
                        } else {
                            callback.error();
                            Log.wtf("FarmaciasService", "No es success");
                        }
                    });
        } catch (IOException e) {
            Log.e("FarmaciasService", "IOException registrando usuario", e);
            if (context != null) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
