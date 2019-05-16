package com.ugr.farmaciads.data;

import java.util.List;

import p3.farmacia.modelo.Farmacia;
import p3.farmacia.modelo.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FarmaciasService {

    @GET("farmacias")
    Call<List<Farmacia>> getFarmacias();

    @GET("usuarios")
    Call<List<Usuario>> getUsuarios();

    @GET("usuarios")
    Call<Usuario> getUserById(@Query("id") Integer id);
}
