package com.example.onlineheaven;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.onlineheaven.fragements.EditInfoFragement;
import com.example.onlineheaven.fragements.HomeFragement;
import com.example.onlineheaven.model.Anime;
import com.example.onlineheaven.model.User;
import com.example.onlineheaven.retrofit.ApiInterface;
import com.example.onlineheaven.retrofit.RetroFitClient;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    public TextView userProfileName;
    public TextView userProfileEmail;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //getting the drawer header TextViews
        View headerView = navigationView.getHeaderView(0);

        userProfileName=headerView.findViewById(R.id.userName);
        userProfileEmail=headerView.findViewById(R.id.userEmail);


        //setting the default fragement (Home)
        getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                new HomeFragement()).commit();

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

        super.onBackPressed();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.home_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                        new HomeFragement()).commit();
                break;

            case R.id.edit_profile_item:
                Bundle bundle = new Bundle();
                bundle.putInt("userID",sharedPreferences.getInt(USER_ID_FIELD, 0));

                Fragment editFragement =new EditInfoFragement();
                editFragement.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container, editFragement).commit();

                break;

            case R.id.favourites_profile_item:
                Message.longMessage(this,"favourite");
                break;

            case R.id.logout_profile_item:
                Message.longMessage(this,"Logout");
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

    public void setLoginInfo(int userID){
        ApiInterface apiClient = RetroFitClient.getRetroFitClient();

        Call<User> callUser=apiClient.getUserData(userID);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User userData=response.body();
                userProfileName.setText(userData.getUsername());
                userProfileEmail.setText(userData.getEmail());

                Message.shortMessage(getApplicationContext(),"Welcome to Online Heaven "+response.body().getUsername());
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