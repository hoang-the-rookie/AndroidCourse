package edu.hanu.uiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_menu;
    LinearLayout overlay_menu,overlay_menu_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        get ref
        btn_menu = findViewById(R.id.btn_menu);
        overlay_menu = findViewById(R.id.overlay_menu);
        overlay_menu_back = findViewById(R.id.overlay_menu_back);

//        hide overlay menu
        overlay_menu.setAlpha(0);
        overlay_menu_back.setAlpha(0);

//        handle menu button
        btn_menu.setOnClickListener(view -> {
//            animation

            btn_menu.animate().rotationBy(180).setDuration(300);
            overlay_menu_back.animate().alpha(1).setDuration(500);
            overlay_menu.animate().translationX(-(overlay_menu.getWidth()/2)).alpha(1).setDuration(400);

//            prevent from clicking layer behind
            overlay_menu.setClickable(true);
            overlay_menu_back.setClickable(true);

//        hide overlay menu
        overlay_menu_back.setOnClickListener(v -> hideOverlayMenu());
        });

    }

    private void hideOverlayMenu(){
        //            animation
        btn_menu.animate().rotationBy(-180).setDuration(300);
        overlay_menu_back.animate().alpha(0).setDuration(300);
        overlay_menu.animate().translationX(overlay_menu.getWidth()/2).alpha(0).setDuration(200);

//            allow to click on layer behind
        overlay_menu.setClickable(false);
        overlay_menu_back.setClickable(false
        );
    }
}