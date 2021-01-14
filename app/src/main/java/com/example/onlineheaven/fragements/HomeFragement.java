package com.example.onlineheaven.fragements;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineheaven.ErrorActivity;
import com.example.onlineheaven.MainActivity;
import com.example.onlineheaven.Message;
import com.example.onlineheaven.R;
import com.example.onlineheaven.adapter.BannerAnimesPagerAdapter;
import com.example.onlineheaven.adapter.MainRecyclerAdapter;
import com.example.onlineheaven.model.Anime;
import com.example.onlineheaven.model.Category;
import com.example.onlineheaven.retrofit.ApiInterface;
import com.example.onlineheaven.retrofit.RetroFitClient;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragement extends Fragment {
    BannerAnimesPagerAdapter bannerAnimesPagerAdapter;
    TabLayout indicatorTab, categoryTab;
    ViewPager bannerAnimesViewPager;

    List<Anime> topRatedBannerList = new ArrayList<Anime>();
    List<Anime> lastAddedBannerList = new ArrayList<Anime>();

    Timer sliderTimer;

    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;


    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;


    public HomeFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home_fragement, container, false);

        indicatorTab = v.findViewById(R.id.tab_indicator);
        categoryTab = v.findViewById(R.id.tabLayout);
        nestedScrollView = v.findViewById(R.id.nested_scroll);
        appBarLayout = v.findViewById(R.id.appbar);


        sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        indicatorTab.setupWithViewPager(bannerAnimesViewPager, true);


        //setting the banner data tabs Lists
        setBannerData(v);

        //setting the main recycler data tabs Lists
        setMainRecyclerData(v);


        //on tab change selected data
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 1:
                        setScrollDefaultState();
                        setBannerAnimesPagerAdapter(v, topRatedBannerList);
                        return;

                    default:
                        setScrollDefaultState();
                        setBannerAnimesPagerAdapter(v, lastAddedBannerList);
                        return;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }

    public class AutoSlider extends TimerTask {

        @Override
        public void run() {


            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (bannerAnimesViewPager.getCurrentItem() < topRatedBannerList.size() - 1) {
                        bannerAnimesViewPager.setCurrentItem(bannerAnimesViewPager.getCurrentItem() + 1);
                    } else {
                        bannerAnimesViewPager.setCurrentItem(0);
                    }
                });
            }
        }
    }


    private void setBannerAnimesPagerAdapter(View v, List<Anime> bannerAnimesList) {


        bannerAnimesViewPager = v.findViewById(R.id.banner_viewPager);
        bannerAnimesPagerAdapter = new BannerAnimesPagerAdapter(getContext(), bannerAnimesList);
        bannerAnimesViewPager.setAdapter(bannerAnimesPagerAdapter);
        indicatorTab.setupWithViewPager(bannerAnimesViewPager);

    }

    public void setMainRecycler(View v, List<Category> allCategoryList) {
        mainRecycler = v.findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(getContext(), allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);
    }

    private void setScrollDefaultState() {

        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0, 0);
        appBarLayout.setExpanded(true);
    }

    public void setBannerData(View v) {
        ApiInterface apiClient = RetroFitClient.getRetroFitClient();


        Call<List<Anime>> callTopAnimes = apiClient.getTopRatedAnimes();
        callTopAnimes.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {


                topRatedBannerList = response.body();


            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {

                Message.shortMessage(getActivity(), "Not Connected");


            }
        });


        Call<List<Anime>> callLastAnimes = apiClient.getLatestAnimes();
        callLastAnimes.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {

                lastAddedBannerList = response.body();
                setBannerAnimesPagerAdapter(v, lastAddedBannerList);

            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {

            }
        });


    }

    public void setMainRecyclerData(View v) {
        ApiInterface apiClient = RetroFitClient.getRetroFitClient();


        Call<List<Category>> call = apiClient.getAllData();
        call.enqueue(new Callback<List<Category>>() {

            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.body() != null) {
                    setMainRecycler(v, response.body());
                    Message.shortMessage(getContext(), "Anime data loaded !");
                } else {
                    Message.shortMessage(getContext(), "Problem loading data");
                    Intent intent = new Intent(getActivity(), ErrorActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }


            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {


            }
        });
    }
}