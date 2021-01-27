package com.example.onlineheaven.fragements;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Vibrator;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.onlineheaven.R;
import com.google.android.material.navigation.NavigationView;
import java.io.IOException;


public class AvatarFragment extends Fragment {

    MediaPlayer mp = new MediaPlayer();
    SharedPreferences sharedPreferences;
    public static final String LOGIN_PREFERENCE = "loginPreference";
    public static final String USER_AVATAR_FIELD = "AVATAR";

    ImageView current_avatar, boy_avatar, girl_avatar, hipster_avatar, cool_avatar, man_avatar, rockstar_avatar, boy_thinking, girl_yelling, bold_man;
    int selectedResourceId;

    public AvatarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_avatar, container, false);
        sharedPreferences = getActivity().getSharedPreferences(LOGIN_PREFERENCE,
                Context.MODE_PRIVATE);

        Integer resourceAvatar = sharedPreferences.getInt(USER_AVATAR_FIELD, 0);

        current_avatar = v.findViewById(R.id.current_avatar);
        current_avatar.setBackgroundResource(resourceAvatar);

        boy_avatar = v.findViewById(R.id.boy_avatar);
        girl_avatar = v.findViewById(R.id.girl_avatar);
        hipster_avatar = v.findViewById(R.id.hipster_avatar);
        cool_avatar = v.findViewById(R.id.cool_avatar);
        man_avatar = v.findViewById(R.id.man_avatar);
        rockstar_avatar = v.findViewById(R.id.rockstar_avatar);
        boy_thinking = v.findViewById(R.id.boy_thinking);
        girl_yelling = v.findViewById(R.id.girl_yelling);
        bold_man = v.findViewById(R.id.bold_man);

        boy_avatar.setOnTouchListener(new OnTouchListener(R.mipmap.boy));
        girl_avatar.setOnTouchListener(new OnTouchListener(R.mipmap.girl));
        hipster_avatar.setOnTouchListener(new OnTouchListener(R.mipmap.hipster));
        cool_avatar.setOnTouchListener(new OnTouchListener(R.mipmap.cool));
        man_avatar.setOnTouchListener(new OnTouchListener(R.mipmap.man));
        rockstar_avatar.setOnTouchListener(new OnTouchListener(R.mipmap.rockstar));
        boy_thinking.setOnTouchListener(new OnTouchListener(R.mipmap.boy_thinking));
        girl_yelling.setOnTouchListener(new OnTouchListener(R.mipmap.girl_yelling));
        bold_man.setOnTouchListener(new OnTouchListener(R.mipmap.bold_man));


        current_avatar.setOnDragListener(new OnDragListner());


        return v;
    }


    public void playSoundEffect(String fileUrl) {

        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = getActivity().getAssets().openFd(fileUrl);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class OnTouchListener implements View.OnTouchListener {
        Integer avatarResourceId;

        public OnTouchListener(Integer avatarResourceId) {
            this.avatarResourceId = avatarResourceId;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    selectedResourceId = avatarResourceId;
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.setVisibility(View.INVISIBLE);
                    v.startDrag(data, shadowBuilder, v, 0);
                    return true;
                case MotionEvent.ACTION_UP:
                    return true;
                default:
                    return false;


            }
        }
    }


    private class OnDragListner implements View.OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(USER_AVATAR_FIELD, selectedResourceId);
                    editor.commit();
                    current_avatar.setBackgroundResource(selectedResourceId);


                    NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                    View headerView = navigationView.getHeaderView(0);

                    ImageView profile_image = headerView.findViewById(R.id.profile_image);
                    profile_image.setBackgroundResource(selectedResourceId);

                    //playing paper change sound effect
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    playSoundEffect("success_sound_effect.mp3");
                    break;


                case DragEvent.ACTION_DRAG_ENDED:
                    View view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);

                    break;


            }
            return true;
        }
    }
}