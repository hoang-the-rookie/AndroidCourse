package edu.hanu.mytube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class PlayerActivity extends AppCompatActivity {

    VideoView videoView;
    String link;
    int videoCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        videoView = findViewById(R.id.videoView);

        Intent intent = getIntent();
        link = intent.getStringExtra("link");

        videoView.setVideoPath(link);
        videoView.setMediaController(new MediaController(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        videoView.seekTo(1);
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        videoCurrentPosition = videoView.getCurrentPosition();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("POSITION", videoCurrentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);

        int videoCurrentPosition = saveInstanceState.getInt("POSITION");
        videoView.seekTo(videoCurrentPosition);
    }
}