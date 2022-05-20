package edu.hanu.languagepreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView title;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        get ref
        title = findViewById(R.id.title);

//        get language ref
        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String lang = sharedPreferences.getString("PREF_LANG", null);

//        if not set
        if (lang == null) {
//            show dialog for choosing language
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_btn_speak_now)
                    .setTitle("Choose a language")
                    .setMessage("Which language would you like?")
                    .setPositiveButton("Vietnamese", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences.edit().putString("PREF_LANG","Vietnamese").apply();
                            title.setText(getResources().getString(R.string.title_vn));
                        }
                    })
                    .setNegativeButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences.edit().putString("PREF_LANG","English").apply();
                            title.setText(getResources().getString(R.string.title_en));
                        }
                    })
                    .show();

//            handle option click events
//                save language pref - SharedPreferences
//                update language

        }
    }

    //        customize menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

//    handle menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case(R.id.lang_vn):
                Toast.makeText(MainActivity.this, "You choose Vietnamese", Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putString("PREF_LANG","Vietnamese").apply();
                title.setText(getResources().getString(R.string.title_vn));
                break;
            case(R.id.lang_en):
                Toast.makeText(MainActivity.this, "You choose English", Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putString("PREF_LANG","English").apply();
                title.setText(getResources().getString(R.string.title_en));
                break;
            case(R.id.lang_default):
            default:
                sharedPreferences.edit().putString("PREF_LANG",null).apply();
                title.setText("Cart");
                break;
        }

        return false;
    }
}