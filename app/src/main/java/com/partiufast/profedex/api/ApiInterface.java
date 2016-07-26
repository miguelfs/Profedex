package com.partiufast.profedex.api;

import com.partiufast.profedex.data.CommentResponse;
import com.partiufast.profedex.data.Message;
import com.partiufast.profedex.data.PicturePath;
import com.partiufast.profedex.data.ProfessorResponse;
import com.partiufast.profedex.data.RatingResponse;
import com.partiufast.profedex.data.User;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @Multipart
    @POST("/professor")//@PartMap() Map<String, RequestBody> data,
    Call<Message> createProfessorPic( @Part("name") RequestBody name,
                                      @Part("email") RequestBody email,
                                      @Part("room") RequestBody room,
                                      @Part("description") RequestBody description,
                                      @Part MultipartBody.Part pic);

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
    @POST("/professor/{id}/comment/{comment_id}/vote")
    Call<Message> voteComment(@Path("id") int professorID,
                              @Path("comment_id") int commentID,
                              @Field("user_id") int userID,
                              @Field("vote_val") int value);

    @Multipart
    @POST("/professor/{id}/picture")
    Call<Message> upload(@Part("description") RequestBody description,
                         @Part MultipartBody.Part file);

    @GET("/professor/{id}/picture")
    Call<List<PicturePath>> getPictureList(@Path("id") int professorID);

    @FormUrlEncoded
    @POST("/login")
    Call<User> login(@Field("email") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/register")
    Call<Message> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);
}
