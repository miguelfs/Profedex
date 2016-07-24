package com.partiufast.profedex.api;

import com.partiufast.profedex.data.CommentResponse;
import com.partiufast.profedex.data.Message;
import com.partiufast.profedex.data.ProfessorResponse;
import com.partiufast.profedex.data.RatingResponse;
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
    @GET("/professor")
    Call<ProfessorResponse> getProfessors(@Query("sort_by") String sort,
                                          @Query("order") String order,
                                          @Query("start") int start,
                                          @Query("limit") int limit);
    @FormUrlEncoded
    @POST("/professor")
    Call<Message> createProfessor(@Field("name") String name,
                                  @Field("email") String email,
                                  @Field("description") String description,
                                  @Field("room") String room);

    @GET("/professor/{id}/comment")
    Call<CommentResponse> getComments(@Path("id") int professorID);

    @FormUrlEncoded
    @POST("/professor/{id}/comment")
    Call<Message> createComment(@Path("id") int professorID,
                                @Field("user_id") int userID,
                                @Field("comment") String comment);

    @GET("/professor/{id}/rating")
    Call<RatingResponse> getRatings(@Path("id") int professorID);

    @FormUrlEncoded
    @POST("/professor/{id}/rating")
    Call<Message> createRating(@Path("id") int professorID,
                               @Field("user_id") int userID,
                               @Field("rating_type_id") int ratingType,
                               @Field("rating_value") float rating);

    @FormUrlEncoded
    @POST("/login")
    Call<User> login(@Field("email") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/register")
    Call<Message> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);
}
