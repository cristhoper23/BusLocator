package com.cristhoper.buslocator.services;

import com.cristhoper.buslocator.models.Transport;
import com.cristhoper.buslocator.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Cris on 20/11/2017.
 */

public interface ApiService {

    String API_BASE_URL = "https://empresas-app-mrpapita.c9users.io";

    //https://bus-locator-cristhoper23.c9users.io
    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<Usuario> iniciarSesion(@Field("username") String username,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/v1/register")
    Call<ResponseMessage> registrarUsuario(@Field("username") String username,
                                           @Field("password") String password,
                                           @Field("email") String email,
                                           @Field("fullname") String fullname);

    @GET("api/v1/empresas")
    Call<List<Transport>> getAllTransportes();
}
