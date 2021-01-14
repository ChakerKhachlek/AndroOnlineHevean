package com.example.onlineheaven;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.onlineheaven.model.User;
import com.example.onlineheaven.retrofit.ApiInterface;
import com.example.onlineheaven.retrofit.RetroFitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;

    Button goRegister;

    public static final String LOGIN_PREFERENCE = "loginPreference";
    public static final String USER_ID_FIELD = "ID";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        loginButton = findViewById(R.id.loginButton);

        goRegister = findViewById(R.id.goRegister);


        sharedPreferences = getSharedPreferences(LOGIN_PREFERENCE,
                Context.MODE_PRIVATE);

        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }


    public void loginUser() {

        String emai = email.getText().toString().trim();
        String pass = password.getText().toString().trim();


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emai.isEmpty() || pass.isEmpty()) {
            Message.longMessage(getApplicationContext(), "All fields are required ");
        } else if (!(emai.matches(emailPattern))) {
            Message.longMessage(getApplicationContext(), "Email must be valid !");
        } else {

            ApiInterface apiClient = RetroFitClient.getRetroFitClient();
            Call<User> loginCall = apiClient.loginUser(emai, pass);
            loginCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    //verif if user else it will return an email error
                    if ((response.body().getUsername().contentEquals("notfound"))) {
                        Message.longMessage(getApplicationContext(), "Email does not exist ...");

                    } else if ((response.body().getUsername().contentEquals("wrongpassword"))) {
                        Message.longMessage(getApplicationContext(), "Wrong password ...");

                    } else {

                        //LoggedInStatus Logic
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(USER_ID_FIELD, response.body().getId());
                        editor.commit();


                        setResult(RESULT_OK);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        }
    }


}