package com.example.onlineheaven;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AnimeDetails extends AppCompatActivity {

    ImageView animeImage;
    TextView animeName;
    TextView animeDescription;
    TextView animeReleaseDate;
    TextView animeRating;
    Button playBoutton;

    String aName,aRating,aRelease,aDescription,aImage,aId,aFileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

        animeImage= findViewById(R.id.anime_image);
        animeName= findViewById(R.id.anime_name);
        animeDescription= findViewById(R.id.anime_description);
        animeReleaseDate= findViewById(R.id.anime_release_date);
        animeRating=findViewById(R.id.anime_rating);
        playBoutton= findViewById(R.id.play_button);

        //getting data from bundle

        aId=getIntent().getStringExtra("animeId");
        aName=getIntent().getStringExtra("animeName");
        aDescription=getIntent().getStringExtra("animeDescription");
        aRelease=getIntent().getStringExtra("animeReleaseDate");
        aRating=getIntent().getStringExtra("animeRating");
        aImage=getIntent().getStringExtra("animeImageUrl");
        aFileUrl=getIntent().getStringExtra("animeFile");


        //setting data to layout

        Glide.with(this).load(aImage).into(animeImage);
        animeName.setText(aName);
        animeDescription.setText(aDescription);
        animeReleaseDate.setText(aRelease);
        animeRating.setText(aRating+"/5.0");

        playBoutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AnimeDetails.this,VideoPlayerActivity.class);
                i.putExtra("url",aFileUrl);
                startActivity(i);

            }
        });


    }
}