package com.example.onlineheaven;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity {
    BannerAnimesPagerAdapter bannerAnimesPagerAdapter;
    TabLayout indicatorTab ,categoryTab;
    ViewPager bannerAnimesViewPager;


    List<Anime> topRatedBannerList =new ArrayList<Anime>();
    List<Anime> lastAddedBannerList =new ArrayList<Anime>();


    Timer sliderTimer;

    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;

    Button logoutButton;


    public final static int REQUEST_LOGIN=1;
    public static final String LOGIN_PREFERENCE = "loginPreference";
    public static final String USER_ID_FIELD = "ID";
    SharedPreferences sharedPreferences;

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indicatorTab =findViewById(R.id.tab_indicator);
        categoryTab =findViewById(R.id.tabLayout);
        nestedScrollView =findViewById(R.id.nested_scroll);
        appBarLayout =findViewById(R.id.appbar);

        logoutButton=findViewById(R.id.logoutButton);

        sliderTimer=new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(),4000,6000);
        indicatorTab.setupWithViewPager(bannerAnimesViewPager,true);

        //setting the banner data tabs Lists
        setBannerData();

        //setting the main recycler data tabs Lists
        setMainRecyclerData();

        //on tab change selected data
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch(tab.getPosition()){
                    case 1 :
                        setScrollDefaultState();
                        setBannerAnimesPagerAdapter(topRatedBannerList);
                        return;

                    default:
                        setScrollDefaultState();
                        setBannerAnimesPagerAdapter(lastAddedBannerList);
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




        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }


    private void checkLogin(){
        sharedPreferences = getSharedPreferences(LOGIN_PREFERENCE,
                Context.MODE_PRIVATE);

        //if user not logged in
        if (!(sharedPreferences.contains(USER_ID_FIELD))) {
            Message.shortMessage(getApplicationContext(),"Login");

            Intent redirectLogin=new Intent(MainActivity.this,LoginActivity.class);
            startActivityForResult(redirectLogin,REQUEST_LOGIN);
        }
        else{
            Message.longMessage(getApplicationContext(),sharedPreferences.getInt(USER_ID_FIELD,0)+"");
        }

    }

    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_ID_FIELD);
        editor.commit();

        Intent redirectLogin=new Intent(MainActivity.this,LoginActivity.class);
        startActivityForResult(redirectLogin,REQUEST_LOGIN);
    }

    private void setBannerAnimesPagerAdapter(List<Anime> bannerAnimesList){


    bannerAnimesViewPager = findViewById(R.id.banner_viewPager);
    bannerAnimesPagerAdapter= new BannerAnimesPagerAdapter(this,bannerAnimesList);
    bannerAnimesViewPager.setAdapter(bannerAnimesPagerAdapter);
    indicatorTab.setupWithViewPager(bannerAnimesViewPager);

    }



    class AutoSlider extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(() -> {
                if(bannerAnimesViewPager.getCurrentItem() < topRatedBannerList.size() -1){
                    bannerAnimesViewPager.setCurrentItem(bannerAnimesViewPager.getCurrentItem()+1);
                }else {
                    bannerAnimesViewPager.setCurrentItem(0);
                }
            });
        }

    }

    public void setMainRecycler(List<Category> allCategoryList){
        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter=new MainRecyclerAdapter(this,allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);
    }

    private void setScrollDefaultState(){

        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }

    public void setBannerData(){
        ApiInterface apiClient = RetroFitClient.getRetroFitClient();

        Call<List<Anime>> callTopAnimes=apiClient.getTopRatedAnimes();
        callTopAnimes.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                topRatedBannerList =response.body();
            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {

            }
        });


        Call<List<Anime>> callLastAnimes=apiClient.getLatestAnimes();
        callLastAnimes.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                lastAddedBannerList =response.body();
                setBannerAnimesPagerAdapter(lastAddedBannerList);

            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {

            }
        });


    }

public void setMainRecyclerData(){
    ApiInterface apiClient = RetroFitClient.getRetroFitClient();
    Call<List<Category>> call=apiClient.getAllData();
    call.enqueue(new Callback<List<Category>>() {

        @Override
        public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
            setMainRecycler(response.body());

            Message.shortMessage(getApplicationContext(),"Anime data loaded !");
        }

        @Override
        public void onFailure(Call<List<Category>> call, Throwable t) {
            Message.shortMessage(getApplicationContext(),"Problem loading data");
        }
    });
}


}