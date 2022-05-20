package edu.hanu.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.hanu.mynotes.adapters.NoteAdapter;
import edu.hanu.mynotes.db.DbHelper;
import edu.hanu.mynotes.db.NoteManager;
import edu.hanu.mynotes.models.Constants;
import edu.hanu.mynotes.models.Note;

public class MainActivity extends AppCompatActivity {

//    init ref
    private List<Note> notes;
    private NoteAdapter adapter;
    private RecyclerView rvNotes;
    private NoteManager noteManager;
    int theme = 0;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("THEME",theme);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        theme = savedInstanceState.getInt("THEME");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        set theme
        Log.d("THEME_CREATE", theme+"");
        if(theme != 0) setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//    get recycler view
        rvNotes = findViewById(R.id.rv_notes);

//      set layout
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

//        data set
        noteManager = NoteManager.getInstance(this);
        notes = noteManager.all();

//        init note adapter
        adapter = new NoteAdapter(notes);

//        set adpter
        rvNotes.setAdapter(adapter);

    }


    //        customize menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case(R.id.btn_add):
//
                Intent intent = new Intent(MainActivity.this, ModifyNote.class);
                intent.putExtra("THEME", theme);
                startActivityForResult(intent, Constants.RC_NEW_NOTE);

                return true;
            case R.id.btn_change_theme:
//                TODO change theme
                Toast.makeText(MainActivity.this, "I tried to change theme", Toast.LENGTH_SHORT).show();
//                setTheme(R.style.yellowTheme);
                theme = R.style.yellowTheme;
                Log.d("THEME_RAW", R.style.yellowTheme+"");
                Log.d("THEME", theme+"");
                recreate();
                break;
            default:

        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Constants.RC_NEW_NOTE || requestCode == Constants.RC_EDIT_NOTE){
            notes.clear();
            notes.addAll(noteManager.all());

            adapter.notifyDataSetChanged();
        }

    }
}