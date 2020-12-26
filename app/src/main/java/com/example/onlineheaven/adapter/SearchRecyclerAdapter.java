package com.example.onlineheaven.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineheaven.AnimeDetails;
import com.example.onlineheaven.R;
import com.example.onlineheaven.model.Anime;

import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder> {
    Context context;
    List<Anime> searchAnimesResults;


    public SearchRecyclerAdapter(Context context,List<Anime> searchAnimesResults){
        this.context=context;
        this.searchAnimesResults=searchAnimesResults;

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.search_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Glide.with(context).load(searchAnimesResults.get(position).getImage_url()).into(holder.animeImage);
        holder.animeName.setText(searchAnimesResults.get(position).getName());

        holder.animeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, AnimeDetails.class);
                i.putExtra("animeId", searchAnimesResults.get(position).getId());
                i.putExtra("animeName", searchAnimesResults.get(position).getName());
                i.putExtra("animeDescription", searchAnimesResults.get(position).getDescription());
                i.putExtra("animeReleaseDate", searchAnimesResults.get(position).getRelease_date());
                i.putExtra("animeRating", searchAnimesResults.get(position).getRating());
                i.putExtra("animeDuration", searchAnimesResults.get(position).getDuration());
                i.putExtra("animeImageUrl", searchAnimesResults.get(position).getImage_url());
                i.putExtra("animeFile", searchAnimesResults.get(position).getFile_url());
                context.startActivity(i);
            }

        });
    }

    @Override
    public int getItemCount() {
        return searchAnimesResults.size();
    }



    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView animeName;
        ImageView animeImage;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            animeName=itemView.findViewById(R.id.search_item_name);
            animeImage=itemView.findViewById(R.id.search_item_image);

        }


    }
}
