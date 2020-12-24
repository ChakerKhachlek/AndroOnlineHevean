package com.example.onlineheaven.fragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.onlineheaven.MainActivity;
import com.example.onlineheaven.Message;
import com.example.onlineheaven.R;
import com.example.onlineheaven.model.User;
import com.example.onlineheaven.retrofit.ApiInterface;
import com.example.onlineheaven.retrofit.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditInfoFragement extends Fragment {
    Integer userId;
    String oldUserName;
    String oldUserEmail;


    EditText userEmail;
    EditText userName;
    Button editProfileButton;


    EditText newPassword;
    EditText confirmNewPassword;
    Button updatePasswordButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_info_fragement, container, false);
        userEmail = v.findViewById(R.id.EditProfileEmail);
        userName = v.findViewById(R.id.EditProfileName);
        editProfileButton = v.findViewById(R.id.updateInfoButton);

        newPassword = v.findViewById(R.id.EditProfilePassword);
        confirmNewPassword = v.findViewById(R.id.EditProfileConfirmPassword);
        updatePasswordButton = v.findViewById(R.id.updatePasswordButton);


        userId = getArguments().getInt("userID");
        getUserInfo(userId);


        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });


        return v;

    }


    public void updateProfile() {

        String inputUs=userName.getText().toString();
        String inputEm=userEmail.getText().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if ((inputUs.contentEquals(oldUserName)) && (inputEm.contentEquals(oldUserEmail))) {
            Message.shortMessage(getActivity(), "Nothing to update");
        } else if (!(inputEm.matches(emailPattern))) {
            Message.longMessage(getActivity(), "Email must be valid ");
        } else {

            ApiInterface apiClient = RetroFitClient.getRetroFitClient();

            Call<User> callUser = apiClient.updateUserInfo(userId,inputUs,inputEm);
            callUser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User userData = response.body();
                    if((userData.getUsername().contentEquals("erroremail") )){
                        Message.longMessage(getActivity(),"Email already taken");

                    }else {
                        Message.longMessage(getActivity(), "Profile updated " + response.body().getUsername());
                        ((MainActivity) getActivity()).userProfileName.setText(userData.getUsername());
                        ((MainActivity) getActivity()).userProfileEmail.setText(userData.getEmail());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }


    public void updatePassword() {
        String newpass=newPassword.getText().toString();
        String conpass=confirmNewPassword.getText().toString();

        if(newpass.contentEquals("")){
            Message.longMessage(getActivity(),"Choose a password first");
        }
        else if(!(newpass.contentEquals(conpass))){
            Message.longMessage(getActivity(),"Password & confirm password must match !");
        }else{
            ApiInterface apiClient = RetroFitClient.getRetroFitClient();

            Call<User> callUser = apiClient.updateUserPassword(userId,newpass);
            callUser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User userData = response.body();
                    if((userData.getUsername().contentEquals("samepassword") )){
                        Message.longMessage(getActivity(),"Pick a new password !");

                    }else {
                        Message.longMessage(getActivity(), "Password updated " + response.body().getUsername());

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }

    }

    public void getUserInfo(int userID) {
        ApiInterface apiClient = RetroFitClient.getRetroFitClient();

        Call<User> callUser = apiClient.getUserData(userID);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User userData = response.body();
                oldUserName = userData.getUsername();
                oldUserEmail = userData.getEmail();

                userEmail.setText(oldUserEmail);
                userName.setText(oldUserName);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}