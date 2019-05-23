package com.ugr.farmaciads.data;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import p3.farmacia.modelo.ApiCallback;
import p3.farmacia.modelo.ApiResponse;

public class RestConnector {

    private final OkHttpClient client = new OkHttpClient();
    private final Moshi moshi = new Moshi.Builder().build();

    public <T> void run(Request request, Type resultType, ApiCallback<ApiResponse<T>> callback) throws IOException {
        post(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.w("REST", "FAILURE", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JsonAdapter<ApiResponse<T>> resAdapter = moshi.adapter(Types.newParameterizedType(ApiResponse.class, resultType));
                callback.then(resAdapter.fromJson(response.body().source()));
                Log.w("REST", response.body().string());
            }
        });

    }

    Call post(Request request, Callback okhttpCallback) {
        Call call = client.newCall(request);
        call.enqueue(okhttpCallback);
        return call;
    }

}
