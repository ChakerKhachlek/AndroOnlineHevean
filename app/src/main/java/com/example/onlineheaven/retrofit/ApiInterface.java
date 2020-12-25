package com.example.onlineheaven.retrofit;


import com.example.onlineheaven.model.Anime;
import com.example.onlineheaven.model.Category;
import com.example.onlineheaven.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    //returns the last 4 added animes
    @GET("animes/latest")
    public Call<List<Anime>> getLatestAnimes();

    //returns the top 4 rated animes
    @GET("animes/rated")
    public Call<List<Anime>> getTopRatedAnimes();

    //returns all categories with their animes
    @GET("animes/data")
    public Call<List<Category>> getAllData();

    //returns all categories only
    @GET("categories")
    public Observable<List<Category>> getCategories();

    //returns category animes
    @GET("category/{id}")
    public Call<List<Anime>> getCategoryAnimes(@Path("id") Integer id);


    //return user if inserted else it a fake user with a username contains error
    @FormUrlEncoded
    @POST("user/register")
    Call<User> registerUser(@Field("username") String var1,
                          @Field("email") String var2,
                          @Field("password") String var3,
                          @Field("confirmPassword") String var4
                          );

    //return user values if  else it a fake user with a username contains error
    @FormUrlEncoded
    @POST("user/login")
    Call<User> loginUser(@Field("email") String var2,
                            @Field("password") String var3
    );

    //getuserdata
    @GET("user/find/{id}")
    public Call<User> getUserData(@Path("id") Integer id);


    //return user if inserted else it a fake user with a username contains error
    @FormUrlEncoded
    @POST("user/updateInfo/{id}")
    Call<User> updateUserInfo(@Path("id") Integer id,
                            @Field("username") String var1,
                            @Field("email") String var2
    );

    //return user if inserted else it a fake user with a username contains error
    @FormUrlEncoded
    @POST("user/updatePassword/{id}")
    Call<User> updateUserPassword(@Path("id") Integer id,
                              @Field("password") String var1
    );

    //returns user favourite animes
    @GET("user/{id}/favourites")
    public Call<List<Anime>> getuserFavouriteAnimes(@Path("id") Integer id);

    //add an anime to user favourite animes
    @GET("user/{userid}/addfavourite/{animeid}")
    public Call<Anime> addFavouriteAnime(@Path("userid") Integer userid,
                                              @Path("animeid") Integer animeid);


    //remove an anime from user favourite animes
    @GET("user/{userid}/removefavourite/{animeid}")
    public Call<Anime> removeFavouriteAnime(@Path("userid") Integer userid,
                                    @Path("animeid") Integer animeid);

    //checkifuserfavouriteanime
    @GET("user/{userid}/favourite/{animeid}")
    public Call<Anime> isFavourite(@Path("userid") Integer userid,
                                         @Path("animeid") Integer animeid);






















}
