package com.ugr.farmaciads.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.squareup.moshi.Types;
import com.ugr.farmaciads.MainActivity;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Usuario;

public class LoginService {

    public interface LoginCallback {
        void sucess(Usuario usuario);
        void error();
    }

    public void login(String email, String password, Context context, LoginCallback callback) {
        Log.w("LOGIN", "Iniciando petición de login");
        RestConnector restConnector = new RestConnector();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,
                "{" +
                        "    \"email\": \"" + email + "\"," +
                        "    \"password\": \""+ md5(password) + "\"" +
                        " }");
        Request request = new Request.Builder()
                .method("POST", body)
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
            Log.e("FarmaciasService", "IOException iniciando sesión", e);
            if (context != null) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
