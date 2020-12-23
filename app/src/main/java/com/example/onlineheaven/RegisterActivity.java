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

public class RegisterActivity extends AppCompatActivity {

    EditText username;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button registerButton;

    Button goLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.registerUsername);
        email=findViewById(R.id.registerEmail);
        password=findViewById(R.id.registerPassword);
        confirmPassword=findViewById(R.id.registerConfirmPassword);
        registerButton=findViewById(R.id.registerButton);

        goLogin=findViewById(R.id.goLogin);

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent =new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }


    public void registerUser(){
        String usern = username.getText().toString().trim();
        String emai = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String cpass = confirmPassword.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(usern.isEmpty() || emai.isEmpty() || pass.isEmpty() || cpass.isEmpty()){
            Message.longMessage(getApplicationContext(),"All fields are required ");
        }else if(!(pass.contentEquals(cpass))){
            Message.longMessage(getApplicationContext(),"Password & Confirm Password Must Match !");
        }else if(!(emai.matches(emailPattern))){
            Message.longMessage(getApplicationContext(),"Email must be valid !");
        }
        else{

            ApiInterface apiClient = RetroFitClient.getRetroFitClient();
            Call<User> registerCall=apiClient.registerUser(usern,emai,pass,cpass);
            registerCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {


                          //verif if user else it will return an email error
                         if((response.body().getUsername().contentEquals("error") )){
                             Message.longMessage(getApplicationContext(),"Email already taken");

                         }else {

                             Message.longMessage(getApplicationContext(), "Welcome to Online Heaven " + response.body().getUsername());
                             // TODO LoggedInStatus Logic
                             Intent homeIntent = new Intent(RegisterActivity.this, MainActivity.class);
                             startActivity(homeIntent);
                             finish();
                         }



                }



                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Message.longMessage(getApplicationContext(),"An Error Has Occurred ...");
                }
            });


        }

    }
}