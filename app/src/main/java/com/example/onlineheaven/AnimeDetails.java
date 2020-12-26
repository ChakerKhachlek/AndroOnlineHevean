package com.example.onlineheaven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onlineheaven.model.Anime;
import com.example.onlineheaven.model.User;
import com.example.onlineheaven.retrofit.ApiInterface;
import com.example.onlineheaven.retrofit.RetroFitClient;
import com.example.onlineheaven.sqllite.DatabaseHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeDetails extends AppCompatActivity {

    ImageView animeImage;
    TextView animeName;
    TextView animeDescription;
    TextView animeReleaseDate;
    TextView animeDuration;
    TextView animeRating;
    Button playBoutton;

    Button isFavouriteButton;
    Button notFavouriteButton;

    String aName;
    String aRating;
    String aRelease;
    String aDescription;
    String aDuration;
    String aImage;
    int aId;
    String aFileUrl;

    public static final String LOGIN_PREFERENCE = "loginPreference";
    public static final String USER_ID_FIELD = "ID";
    SharedPreferences sharedPreferences;
    Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

        animeImage = findViewById(R.id.anime_image);
        animeName = findViewById(R.id.anime_name);
        animeDescription = findViewById(R.id.anime_description);
        animeReleaseDate = findViewById(R.id.anime_release_date);
        animeDuration = findViewById(R.id.anime_duration);
        animeRating = findViewById(R.id.anime_rating);
        playBoutton = findViewById(R.id.play_button);

        isFavouriteButton = findViewById(R.id.isFavouriteButton);
        notFavouriteButton = findViewById(R.id.notFavouriteButton);

        //getting data from bundle

        aId = getIntent().getIntExtra("animeId", 0);
        aName = getIntent().getStringExtra("animeName");
        aDescription = getIntent().getStringExtra("animeDescription");
        aRelease = getIntent().getStringExtra("animeReleaseDate");
        aRating = getIntent().getStringExtra("animeRating");
        aDuration = getIntent().getStringExtra("animeDuration");
        aImage = getIntent().getStringExtra("animeImageUrl");
        aFileUrl = getIntent().getStringExtra("animeFile");


        //setting data to layout

        Glide.with(this).load(aImage).into(animeImage);
        animeName.setText(aName);
        animeDescription.setText(aDescription);
        animeReleaseDate.setText(aRelease);
        animeDuration.setText(aDuration);
        animeRating.setText(aRating + "/5.0");

        playBoutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new
                        DatabaseHelper(getApplicationContext());


                db.insert(userID,aName);


                Intent i = new Intent(AnimeDetails.this, VideoPlayerActivity.class);
                i.putExtra("url", aFileUrl);
                startActivity(i);

            }
        });

        sharedPreferences = getSharedPreferences(LOGIN_PREFERENCE,
                Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt(USER_ID_FIELD, 0);

        getFavouriteStatus();

        isFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromFavorite();

            }
        });

        notFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToFavourite();

            }
        });


    }


    public void getFavouriteStatus() {

        ApiInterface apiClient = RetroFitClient.getRetroFitClient();
        Call<Anime> favouriteCall = apiClient.isFavourite(userID, aId);

        favouriteCall.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {


                if (response.body().getName().contentEquals("isFavourite")) {
                    isFavouriteButton.setVisibility(View.VISIBLE);


                } else {
                    notFavouriteButton.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {

            }
        });


    }


    public void addToFavourite() {
        ApiInterface apiClient = RetroFitClient.getRetroFitClient();
        Call<Anime> addFavouriteCall = apiClient.addFavouriteAnime(userID, aId);

        addFavouriteCall.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {


                if (response.body().getName().contentEquals("addedToFavourite")) {
                    notFavouriteButton.setVisibility(View.INVISIBLE);
                    isFavouriteButton.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {

            }
        });
    }

    public void removeFromFavorite(){
        ApiInterface apiClient = RetroFitClient.getRetroFitClient();
        Call<Anime> addFavouriteCall = apiClient.removeFavouriteAnime(userID, aId);

        addFavouriteCall.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {


                if (response.body().getName().contentEquals("removedFromFavourite")) {
                    isFavouriteButton.setVisibility(View.INVISIBLE);
                    notFavouriteButton.setVisibility(View.VISIBLE);
                    // Reload current fragment




                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {

            }
        });
    }


}