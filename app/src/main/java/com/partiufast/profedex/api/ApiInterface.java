package com.partiufast.profedex.api;

import com.partiufast.profedex.data.ProfessorResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lgos on 30/06/16.
 */
public interface ApiInterface {
    @GET("/professors")
    Call<ProfessorResponse> getProfessors();

}
