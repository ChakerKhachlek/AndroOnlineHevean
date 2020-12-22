package com.example.onlineheaven;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;





import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class VideoPlayerActivity extends AppCompatActivity {

    private PlayerView videoPlayer;
    private SimpleExoPlayer simpleExoPlayer;
    private static final String File_URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoPlayer =findViewById(R.id.exo_player);
        setUpExoPlayer(getIntent().getStringExtra("url"));


    }

    private void setUpExoPlayer(String url){

        simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(this);
        videoPlayer.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,"OnlineHeaven"));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}