package edu.hanu.myanim_tut03;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton star_btn, ball_btn;
    ImageView star_img, ball_img, character;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        star_img = findViewById(R.id.star_img);
        ball_img = findViewById(R.id.ball_img);
        star_btn = findViewById(R.id.star_btn);
        ball_btn = findViewById(R.id.ball_btn);

//        default chracter
        character = star_img;

    }

    public void onClick(View v){

        star_img = findViewById(R.id.star_img);
        ball_img = findViewById(R.id.ball_img);

        switch (v.getId()){
            case (R.id.ball_btn):
                star_img.animate().alpha(0).setDuration(1000);
                ball_img.animate().alpha(1).setDuration(1000);
                character = ball_img;
                break;
            case (R.id.star_btn):
                star_img.animate().alpha(1).setDuration(1000);
                ball_img.animate().alpha(0).setDuration(1000);
                character = star_img;
                break;

            case (R.id.jump):
                character.animate().translationYBy(-200f).setDuration(700).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        character.animate().translationYBy(200f).setDuration(400);
                    }
                });
                break;
            case (R.id.spin):
                character.animate().rotationBy(720).setDuration(1500);
                break;
            case (R.id.turn_around):
                character.animate().rotationYBy(540).setDuration(1200);
                break;
            case (R.id.clap):
                if(mediaPlayer != null){
                    mediaPlayer.reset();
                }
                mediaPlayer = MediaPlayer.create(this, R.raw.clapping);
                mediaPlayer.start();
                break;
            default:
                break;
        }
    }

}