package com.example.planer_0724;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.security.Provider;

public class Frag_Youtube extends Fragment {

    private static final String TAG = "Frag_Youtube";

    ViewGroup viewGroup;
    Button youtube_move;

    private static String API_KEY = "AIzaSyCSOjpaHmbkM-jn5ntrF9cegNMz0Z1S1Y4";
    private static String videoId = "YcL38njdCBE";


    Activity_Youtube activity_youtube;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.frag_youtube, container, false);

        youtube_move = (Button) viewGroup.findViewById(R.id.youtube_move);


        youtube_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_Youtube.class);
                startActivity(intent);
            }
        });

        return viewGroup;
    }
}