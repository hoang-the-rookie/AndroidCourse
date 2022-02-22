package edu.hanu.a1_1901040087;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button hiraBtn, kataBtn;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //default view as hiragana
        replaceFrag(new hiragana());

        // declare button
        hiraBtn = findViewById(R.id.hira_btn);
        kataBtn = findViewById(R.id.kata_btn);

        //
        hiraBtn.setOnClickListener(view -> replaceFrag(new hiragana()));
        kataBtn.setOnClickListener(view -> replaceFrag(new katakana()));

    }

    private void replaceFrag(Fragment fragment){
        FragmentTransaction fragTrans =getSupportFragmentManager().beginTransaction();
        fragTrans.setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in,R.anim.slide_out);
        fragTrans.addToBackStack(null);
        fragTrans.replace(R.id.fragment_field,fragment);
        fragTrans.commit();
    }


    public void playSound(View v){
        String name = v.getResources().getResourceEntryName(v.getId());
        int resourcePath = this.getResources().getIdentifier(name,"raw",this.getPackageName());
        if(mediaPlayer != null){
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this,resourcePath);
        mediaPlayer.start();
    }
}