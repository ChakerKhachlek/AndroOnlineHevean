package com.example.onlineheaven.fragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineheaven.Message;
import com.example.onlineheaven.R;
import com.example.onlineheaven.adapter.FavouriteRecyclerAdapter;
import com.example.onlineheaven.adapter.MainRecyclerAdapter;
import com.example.onlineheaven.model.Anime;
import com.example.onlineheaven.retrofit.ApiInterface;
import com.example.onlineheaven.retrofit.RetroFitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavouriteAnimesFragement extends Fragment {
    Integer userId;


    RecyclerView favouriteRecycler;
    FavouriteRecyclerAdapter favouriteRecyclerAdapter;
    private boolean allowRefresh = false;

    public View v;

    public FavouriteAnimesFragement() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //Initialize();
        if (allowRefresh) {
            allowRefresh = false;
            //call your initialization code here
            setUserAnimeFavouriteList(v);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!allowRefresh)
            allowRefresh = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //I made it as local variable so i can update the favourite list when i back from details activity

        v = inflater.inflate(R.layout.fragment_favourite_animes_fragement, container, false);

        userId = getArguments().getInt("userID");


        setUserAnimeFavouriteList(v);


        return v;
    }


    public void setUserAnimeFavouriteList(View v) {

        ApiInterface apiClient = RetroFitClient.getRetroFitClient();

        Call<List<Anime>> callTopAnimes = apiClient.getuserFavouriteAnimes(userId);
        callTopAnimes.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                setFavouriteRecycler(v, response.body());


            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {

            }
        });
    }

    public void setFavouriteRecycler(View v, List<Anime> favouriteAnimeList) {
        favouriteRecycler = v.findViewById(R.id.favourite_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);
        favouriteRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        favouriteRecyclerAdapter = new FavouriteRecyclerAdapter(getContext(), favouriteAnimeList);
        favouriteRecycler.setAdapter(favouriteRecyclerAdapter);
    }
}