package com.example.onlineheaven.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineheaven.AnimeDetails;
import com.example.onlineheaven.R;
import com.example.onlineheaven.model.Anime;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {
    Context context;
    List<Anime> categoryAnimesList;

    public ItemRecyclerAdapter(Context context, List<Anime> categoryItemList) {
        this.context = context;
        this.categoryAnimesList = categoryItemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
    //here we are going to fetch image from server so we use glide library
        Glide.with(context).load(categoryAnimesList.get(position).getImage_url()).into(holder.itemImage);

        holder.itemRating.setText(categoryAnimesList.get(position).getRating());

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, AnimeDetails.class);
                i.putExtra("animeId", categoryAnimesList.get(position).getId());
                i.putExtra("animeName", categoryAnimesList.get(position).getName());
                i.putExtra("animeDescription", categoryAnimesList.get(position).getDescription());
                i.putExtra("animeReleaseDate", categoryAnimesList.get(position).getRelease_date());
                i.putExtra("animeRating", categoryAnimesList.get(position).getRating());
                i.putExtra("animeDuration", categoryAnimesList.get(position).getDuration());
                i.putExtra("animeImageUrl", categoryAnimesList.get(position).getImage_url());
                i.putExtra("animeFile", categoryAnimesList.get(position).getFile_url());


                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.categoryAnimesList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemRating;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage=itemView.findViewById(R.id.item_image);
            itemRating=itemView.findViewById(R.id.item_rating);
        }
    }

}
