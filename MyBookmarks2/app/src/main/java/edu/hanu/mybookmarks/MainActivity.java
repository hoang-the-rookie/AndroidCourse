package edu.hanu.mybookmarks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        Fragment bookmarksFragment = new BookmarksFragment();

        manager.beginTransaction()
                .replace(R.id.fl_main, bookmarksFragment)
                .commit();

    }
}