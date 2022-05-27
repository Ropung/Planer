package com.example.planer_0724;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Activity_Youtube extends YouTubeBaseActivity {

    private static final String TAG = "Activity_Youtube - ";

    YouTubePlayer.OnInitializedListener listener;
    Button youtubeBtn;
    YouTubePlayerView playerView;


    private static String API_KEY = "AIzaSyCSOjpaHmbkM-jn5ntrF9cegNMz0Z1S1Y4";
    private static String videoId = "9okpK8dm04o";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youtubeBtn = findViewById(R.id.youtubeBtn);
        playerView = findViewById(R.id.youTubePlayerView);
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youtubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerView.initialize(API_KEY,listener);
            }
        });

    }
}