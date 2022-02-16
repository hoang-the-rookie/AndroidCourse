package edu.hanu.tut02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button exe1,exe2,exe3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFrag(new HelloFriend());

        exe1 = findViewById(R.id.exe1);
        exe2 = findViewById(R.id.exe2);
        exe3 = findViewById(R.id.exe3);

        exe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFrag(new HelloFriend());
            }
        });

        exe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFrag(new WelcomeBack());
            }
        });

        exe3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFrag(new CurrencyConverter());
            }
        });

    }

    private void replaceFrag(Fragment fragment){
        FragmentTransaction fragTrans =getSupportFragmentManager().beginTransaction();
        fragTrans.addToBackStack(null);
        fragTrans.replace(R.id.fragmentSide,fragment);
        fragTrans.commit();
    }
}