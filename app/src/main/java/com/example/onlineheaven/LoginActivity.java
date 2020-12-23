package com.example.onlineheaven;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.onlineheaven.model.User;
import com.example.onlineheaven.retrofit.ApiInterface;
import com.example.onlineheaven.retrofit.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;

    Button goRegister ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.loginEmail);
        password=findViewById(R.id.loginPassword);
        loginButton=findViewById(R.id.loginButton);

        goRegister=findViewById(R.id.goRegister);

        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
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


    public void loginUser(){

        String emai = email.getText().toString().trim();
        String pass = password.getText().toString().trim();


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(emai.isEmpty() || pass.isEmpty() ){
            Message.longMessage(getApplicationContext(),"All fields are required ");
        }else if(!(emai.matches(emailPattern))){
            Message.longMessage(getApplicationContext(),"Email must be valid !");
        }
        else {

            ApiInterface apiClient = RetroFitClient.getRetroFitClient();
            Call<User> loginCall = apiClient.loginUser(emai, pass);
            loginCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    //verif if user else it will return an email error
                    if((response.body().getUsername().contentEquals("notfound") )){
                        Message.longMessage(getApplicationContext(),"Email does not exist ...");

                    }else if((response.body().getUsername().contentEquals("wrongpassword") )){
                        Message.longMessage(getApplicationContext(),"Wrong password ...");

                    }
                    else{

                        Message.longMessage(getApplicationContext(), "Welcome to Online Heaven again " + response.body().getUsername());
                        // TODO LoggedInStatus Logic
                        Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(homeIntent);
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