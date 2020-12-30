package com.example.onlineheaven;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

import com.example.onlineheaven.fragements.EditInfoFragement;
import com.example.onlineheaven.fragements.FavouriteAnimesFragement;
import com.example.onlineheaven.fragements.HistoryFragment;
import com.example.onlineheaven.fragements.HomeFragement;
import com.example.onlineheaven.fragements.LocalisationFragment;
import com.example.onlineheaven.fragements.SearchAnimeFragment;

import com.example.onlineheaven.model.User;
import com.example.onlineheaven.retrofit.ApiInterface;
import com.example.onlineheaven.retrofit.RetroFitClient;
import com.google.android.material.navigation.NavigationView;


import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    public TextView userProfileName;
    public TextView userProfileEmail;

    Boolean con=true;
    Fragment fragment;

    public final static int REQUEST_LOGIN = 1;
    public static final String LOGIN_PREFERENCE = "loginPreference";
    public static final String USER_ID_FIELD = "ID";
    SharedPreferences sharedPreferences;



    @Override
    protected void onResume() {
        super.onResume();

            checkLogin();

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

                                Bundle searchBundle = new Bundle();
                                searchBundle.putCharSequence("querry", query);

                                Fragment searchFragment = new SearchAnimeFragment();
                                searchFragment.setArguments(searchBundle);

                                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container, searchFragment).commit();


                                return true;
                            }


                            @Override
                            public boolean onQueryTextChange(String newText) {

                                return false;
                            }
                        });
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {

                                Fragment fragment = new HomeFragement();
                                //setting the default fragement (Home)
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                                        new HomeFragement()).commit();


                        return true;
                    }
                });


        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.toolbar_menu, menu);

        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            drawer = findViewById(R.id.drawer_layout);

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);


            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            //getting the drawer header TextViews
            View headerView = navigationView.getHeaderView(0);

            userProfileName = headerView.findViewById(R.id.userName);
            userProfileEmail = headerView.findViewById(R.id.userEmail);



        //setting the default fragement (Home)
        getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                new HomeFragement()).commit();


    }




    @Override
    public void onBackPressed() {



            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else if (!(fragment instanceof HomeFragement)) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                        new HomeFragement()).commit();
            } else {

            }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.home_item:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                        new HomeFragement()).commit();

                fragment = new HomeFragement();

                break;

            case R.id.edit_profile_item:
                Bundle bundleEdit = new Bundle();
                bundleEdit.putInt("userID", sharedPreferences.getInt(USER_ID_FIELD, 0));

                Fragment editFragement = new EditInfoFragement();
                fragment = editFragement;
                editFragement.setArguments(bundleEdit);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container, editFragement).commit();

                break;

            case R.id.favourites_profile_item:
                Bundle bundleFavourite = new Bundle();
                bundleFavourite.putInt("userID", sharedPreferences.getInt(USER_ID_FIELD, 0));
                Fragment favouriteFragement = new FavouriteAnimesFragement();
                fragment = favouriteFragement;
                favouriteFragement.setArguments(bundleFavourite);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container, favouriteFragement).commit();
                break;

            case R.id.historique_profile_item:

                Bundle bundleHistory = new Bundle();
                bundleHistory.putInt("userID", sharedPreferences.getInt(USER_ID_FIELD, 0));

                Fragment historyFragement = new HistoryFragment();

                fragment = historyFragement ;
                historyFragement.setArguments(bundleHistory);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container, historyFragement).commit();

                break;

            case R.id.localisation_item:
                Bundle bundleLocalisation= new Bundle();
                bundleLocalisation.putInt("userID", sharedPreferences.getInt(USER_ID_FIELD, 0));

                Fragment localisationFragement = new LocalisationFragment();

                fragment = localisationFragement ;
                localisationFragement.setArguments(bundleLocalisation);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container, localisationFragement).commit();

                break;

            case R.id.website_item:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://onlineaheavenplatform.azurewebsites.net/forum/home"));
                startActivity(browserIntent);
                break;


            case R.id.logout_profile_item:
                Message.longMessage(this, "Logout");
                logout();
                break;






        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void checkLogin() {
        sharedPreferences = getSharedPreferences(LOGIN_PREFERENCE,
                Context.MODE_PRIVATE);

        //if user not logged in
        if (!(sharedPreferences.contains(USER_ID_FIELD))) {
            Message.shortMessage(getApplicationContext(), "Login");

            Intent redirectLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(redirectLogin, REQUEST_LOGIN);
        } else {

            setLoginInfo(sharedPreferences.getInt(USER_ID_FIELD, 0));
        }

    }

    public void setLoginInfo(int userID) {
        ApiInterface apiClient = RetroFitClient.getRetroFitClient();

        Call<User> callUser = apiClient.getUserData(userID);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User userData = response.body();
                userProfileName.setText(userData.getUsername());
                userProfileEmail.setText(userData.getEmail());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {


            }
        });

    }


    public void logout() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_ID_FIELD);
        editor.commit();

        Intent redirectLogin = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(redirectLogin, REQUEST_LOGIN);


    }


}