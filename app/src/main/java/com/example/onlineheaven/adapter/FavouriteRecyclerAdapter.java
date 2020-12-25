package com.example.onlineheaven.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineheaven.AnimeDetails;
import com.example.onlineheaven.R;
import com.example.onlineheaven.model.Anime;

import java.util.List;

public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder> {

    Context context;
    List<Anime> favouriteAnimesList;

    public FavouriteRecyclerAdapter(Context context, List<Anime> favouriteAnimesList) {
        this.context = context;
        this.favouriteAnimesList = favouriteAnimesList;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteViewHolder(LayoutInflater.from(context).inflate(R.layout.favourite_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        Glide.with(context).load(favouriteAnimesList.get(position).getImage_url()).into(holder.favouriteAnimeImage);
        holder.favouriteAnimeTitle.setText(favouriteAnimesList.get(position).getName());

        holder.favouriteAnimeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, AnimeDetails.class);
                i.putExtra("animeId", favouriteAnimesList.get(position).getId());
                i.putExtra("animeName", favouriteAnimesList.get(position).getName());
                i.putExtra("animeDescription", favouriteAnimesList.get(position).getDescription());
                i.putExtra("animeReleaseDate", favouriteAnimesList.get(position).getRelease_date());
                i.putExtra("animeRating", favouriteAnimesList.get(position).getRating());
                i.putExtra("animeDuration", favouriteAnimesList.get(position).getDuration());
                i.putExtra("animeImageUrl", favouriteAnimesList.get(position).getImage_url());
                i.putExtra("animeFile", favouriteAnimesList.get(position).getFile_url());


                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.favouriteAnimesList.size();
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {

        ImageView favouriteAnimeImage;
        TextView favouriteAnimeTitle;
        CardView parentLayout;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            favouriteAnimeImage=itemView.findViewById(R.id.favouriteImage);
            favouriteAnimeTitle=itemView.findViewById(R.id.favouriteTitle);
            parentLayout=itemView.findViewById(R.id.favouriteLayout);
        }
    }
}
