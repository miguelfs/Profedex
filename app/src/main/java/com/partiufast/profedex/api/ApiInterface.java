package com.partiufast.profedex.api;

import com.partiufast.profedex.data.ProfessorResponse;
import com.partiufast.profedex.data.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lgos on 30/06/16.
 */
public interface ApiInterface {
    @GET("/professors")
    Call<ProfessorResponse> getProfessors();

    @FormUrlEncoded
    @POST("/login")
    Call<User> login(@Field("email") String username, @Field("password") String password);
}
