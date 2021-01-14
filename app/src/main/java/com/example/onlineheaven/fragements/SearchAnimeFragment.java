package com.example.onlineheaven.fragements;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onlineheaven.Message;
import com.example.onlineheaven.R;
import com.example.onlineheaven.adapter.FavouriteRecyclerAdapter;
import com.example.onlineheaven.adapter.SearchRecyclerAdapter;
import com.example.onlineheaven.model.Anime;
import com.example.onlineheaven.retrofit.ApiInterface;
import com.example.onlineheaven.retrofit.RetroFitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchAnimeFragment extends Fragment {

    RecyclerView searchResultRecycler;
    String querry;
    TextView no_result_text;

    private int waitingTime = 500;
    private CountDownTimer cntr;


    private SearchRecyclerAdapter searchRecyclerAdapter;


    public SearchAnimeFragment() {
        // Required empty public constructor
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:

                item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        SearchView searchView = (SearchView) item.getActionView();

                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return true;
                            }


                            @Override
                            public boolean onQueryTextChange(String newText) {

                                //handle typing delais : it will wait 0.5
                                if (cntr != null) {
                                    cntr.cancel();
                                }
                                cntr = new CountDownTimer(waitingTime, 200) {

                                    public void onTick(long millisUntilFinished) {
                                        Log.d("TIME", "seconds remaining: " + millisUntilFinished / 1000);
                                    }

                                    public void onFinish() {
                                        Log.d("FINISHED", "DONE");
                                        getQueryResults(getView(), newText);
                                    }
                                };
                                cntr.start();

                                return false;
                            }
                        });
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        Fragment fragment = new HomeFragement();
                        //setting the default fragement (Home)
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                                new HomeFragement()).commit();

                        return true;
                    }
                });


        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_anime, container, false);


        querry = getArguments().getString("querry");
        Message.shortMessage(getContext(), querry);

        no_result_text = v.findViewById(R.id.no_result_text);

        getQueryResults(v, querry);


        return v;
    }


    public void getQueryResults(View v, String query) {
        ApiInterface apiClient = RetroFitClient.getRetroFitClient();

        Call<List<Anime>> callSearchAnimes = apiClient.searchAnimes(query);
        callSearchAnimes.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                if (response.body().size() > 0) {

                    setResultRecycler(v, response.body());
                } else {
                    setResultRecycler(v, new ArrayList<Anime>());
                    no_result_text.setVisibility(View.VISIBLE);

                }


                Log.d("aaa", response.body() + "");

            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {

            }
        });
    }


    public void setResultRecycler(View v, List<Anime> resultAnimeList) {
        searchResultRecycler = v.findViewById(R.id.search_results);
        searchRecyclerAdapter = new SearchRecyclerAdapter(getContext(), resultAnimeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        searchResultRecycler.setLayoutManager(layoutManager);
        searchResultRecycler.setAdapter(searchRecyclerAdapter);

    }
}