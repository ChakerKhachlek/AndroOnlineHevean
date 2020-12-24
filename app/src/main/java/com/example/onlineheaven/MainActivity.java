package com.example.onlineheaven;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.onlineheaven.fragements.HomeFragement;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

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
                Message.longMessage(this,"profile edit");
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
            Message.longMessage(getApplicationContext(), sharedPreferences.getInt(USER_ID_FIELD, 0) + "");
            setLoginInfo(sharedPreferences.getInt(USER_ID_FIELD, 0));
        }

    }

    public void setLoginInfo(int userID){


    }

    public void logout() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_ID_FIELD);
        editor.commit();

        Intent redirectLogin = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(redirectLogin, REQUEST_LOGIN);


    }




}