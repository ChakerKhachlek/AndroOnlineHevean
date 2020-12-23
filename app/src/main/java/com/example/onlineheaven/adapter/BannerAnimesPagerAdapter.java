package com.example.onlineheaven.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.onlineheaven.AnimeDetails;
import com.example.onlineheaven.R;
import com.example.onlineheaven.model.Anime;

import java.util.List;

public class BannerAnimesPagerAdapter extends PagerAdapter {

    Context context;
    List<Anime> bannerAnimesList;

    public BannerAnimesPagerAdapter(Context context, List<Anime> bannerAnimesList) {
        this.context = context;
        this.bannerAnimesList = bannerAnimesList;
    }

    @Override
    public int getCount() {
        Integer limit=6;
        if(bannerAnimesList.size() > limit){
            return limit;
        }
        else
        {
            return bannerAnimesList.size();
        }

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate((R.layout.banner_anime_layout),null);
        ImageView bannerImage = view.findViewById(R.id.banner_image);
        TextView bannerTitle = view.findViewById(R.id.banner_title);
        TextView bannerRating = view.findViewById(R.id.banner_rating);
        //we will use glide library (it can fetch an image from url and set it to image view)
        //but we need to add glide dependency in gradle
        Glide.with(context).load(bannerAnimesList.get(position).getImage_url()).into(bannerImage);
        bannerTitle.setText(bannerAnimesList.get(position).getName());
        bannerRating.setText(bannerAnimesList.get(position).getRating());
        container.addView(view);

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, AnimeDetails.class);
                i.putExtra("animeId", bannerAnimesList.get(position).getId());
                i.putExtra("animeName", bannerAnimesList.get(position).getName());
                i.putExtra("animeDescription", bannerAnimesList.get(position).getDescription());
                i.putExtra("animeRating", bannerAnimesList.get(position).getRating());
                i.putExtra("animeDuration", bannerAnimesList.get(position).getDuration());
                i.putExtra("animeReleaseDate", bannerAnimesList.get(position).getRelease_date());
                i.putExtra("animeImageUrl", bannerAnimesList.get(position).getImage_url());
                i.putExtra("animeFile", bannerAnimesList.get(position).getFile_url());
                context.startActivity(i);
            }
        });
        return view;

    }
}
