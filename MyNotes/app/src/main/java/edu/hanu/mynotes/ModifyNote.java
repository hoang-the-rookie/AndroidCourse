package edu.hanu.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.hanu.mynotes.db.NoteManager;
import edu.hanu.mynotes.models.Constants;
import edu.hanu.mynotes.models.Note;

public class ModifyNote extends AppCompatActivity {

    EditText edTitle, edContent;
    String type;
    long id;
    int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(theme != 0) setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_note);

//        get ref
        edTitle = findViewById(R.id.ed_title);
        edContent = findViewById(R.id.ed_content);

//        default id and type
        type = "add";
        id = 0;

//        get data
        Intent intent = getIntent();

        theme = intent.getIntExtra("THEME",0);
        if(theme != 0) recreate();

//        edit
        if(intent.hasExtra("NOTE")){
            Note note = (Note) intent.getSerializableExtra("NOTE");

//        set data
            edTitle.setText(note.getTitle());
            edContent.setText(note.getContent());
            id = note.getId();

            type = "edit";
        }

//        common

    }

    //        customize menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem confirm = menu.findItem(R.id.btn_add);
        confirm.setIcon(ContextCompat.getDrawable(this, R.drawable.check_black));

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case(R.id.btn_add):
                return confirmFinish(type,id);

            case R.id.btn_change_theme:
//                TODO change theme
                break;
            default:

        }

        return false;
    }

    @Override
    public void onBackPressed() {
        confirmFinish(type,id);
        super.onBackPressed();
    }

    //    adding function
    private boolean confirmFinish(String type, long id){
        boolean success = false;

        String title = edTitle.getText().toString();
        String content = edContent.getText().toString();

        if(!title.equals("") || !content.equals("")){

//          handle empty title
            if(title.equals("")){
                title = content.length() >= 100 ? content.substring(0,95) + "..." : content;
            }

            Note note = new Note(id, title.trim(), content.trim());
            NoteManager noteManager = NoteManager.getInstance(this);

            if (type.equals("add")) success = noteManager.add(note);
            if (type.equals("edit"))  success = noteManager.update(note);

            if (success){
                setResult(RESULT_OK);
                finish();
            }

        }

        return success;
    }
}