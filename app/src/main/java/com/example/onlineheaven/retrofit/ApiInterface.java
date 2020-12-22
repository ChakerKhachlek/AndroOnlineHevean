package com.example.onlineheaven.retrofit;


import com.example.onlineheaven.model.Anime;
import com.example.onlineheaven.model.Category;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    //returns the last 4 added animes
    @GET("animes/latest")
    public Call<List<Anime>> getLatestAnimes();

    //returns the top 4 rated animes
    @GET("animes/rated")
    public Call<List<Anime>> getTopRatedAnimes();


    //returns all categories with their animes
    @GET("data")
    public Call<List<Category>> getAllData();

    //returns all categories only
    @GET("categories")
    public Observable<List<Category>> getCategories();

    //returns
    @GET("animes")
    public Call<List<Anime>> getAnimes();

    @GET("category/{id}")
    public Call<List<Anime>> getCategoryAnimes(@Path("id") Integer id);



















}
