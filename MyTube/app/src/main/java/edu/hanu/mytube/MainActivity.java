package edu.hanu.mytube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton playBtn;
    EditText videoLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = findViewById(R.id.play_btn);
        videoLink = findViewById(R.id.link_to_video);

        playBtn.setOnClickListener(view -> {
            String link = videoLink.getText().toString();
//            Toast.makeText(MainActivity.this, link, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);

            intent.putExtra("link",link);

            startActivity(intent);
        });



    }
}