package com.example.onlineheaven.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineheaven.AnimeDetails;
import com.example.onlineheaven.R;
import com.example.onlineheaven.model.Anime;
import com.example.onlineheaven.retrofit.ApiInterface;
import com.example.onlineheaven.retrofit.RetroFitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder> {

    Context context;
    List<Anime> favouriteAnimesList;

    public final static int REQUEST_LOGIN = 1;
    public static final String LOGIN_PREFERENCE = "loginPreference";
    public static final String USER_ID_FIELD = "ID";
    SharedPreferences sharedPreferences;

    public FavouriteRecyclerAdapter(Context context, List<Anime> favouriteAnimesList) {

        this.context = context;
        this.favouriteAnimesList = favouriteAnimesList;
        sharedPreferences = context.getSharedPreferences(LOGIN_PREFERENCE,
                Context.MODE_PRIVATE);
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

        holder.favouriteAnimeImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage("Remove from favourites");
                alertbox.setTitle("Unlove");
                alertbox.setIcon(R.drawable.not_favourite);
                alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        removeFromFavorite(sharedPreferences.getInt(USER_ID_FIELD, 0),favouriteAnimesList.get(position).getId());

                        removeItem(favouriteAnimesList.get(position));

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertbox.show();

                return false;
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

    public void removeItem(Anime item){
        int currPosition = favouriteAnimesList.indexOf(item);
        favouriteAnimesList.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    public void removeFromFavorite(Integer userId,Integer aId){
        ApiInterface apiClient = RetroFitClient.getRetroFitClient();
        Call<Anime> addFavouriteCall = apiClient.removeFavouriteAnime(userId, aId);

        addFavouriteCall.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {


                if (response.body().getName().contentEquals("removedFromFavourite")) {
                    Log.d("aaa",aId+" removed from favourite");
                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {

            }
        });
    }


}
