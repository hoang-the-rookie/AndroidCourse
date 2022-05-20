package edu.hanu.mynotes.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.mynotes.models.Constants;
import edu.hanu.mynotes.models.Note;

public class NoteManager {

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor results;
    List<Note> notes;

    private static NoteManager instance;

    private static SQLiteStatement statement;
    private static final String INSERT = " INSERT INTO " + DbSchema.NotesTable.NAME + "(title, content) VALUES(?,?);";
    private static final String UPDATE = " UPDATE " + DbSchema.NotesTable.NAME + " SET title = ?, content = ? WHERE id = ?;";

    public static NoteManager getInstance(Context context){

        if(instance == null){
            instance = new NoteManager(context);
        }

        return instance;
    }

    private NoteManager(Context context) {
        this.dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        notes = new ArrayList<>();
    }

    //    get all
    public List<Note> all(){
        results = db.rawQuery("SELECT * FROM notes", null);

        NoteCursorWrapper cursor = new NoteCursorWrapper(results);

        return cursor.getNotes();
    }

//    add
    public boolean add(Note note){
         statement = db.compileStatement(INSERT);

        statement.bindString(1,note.getTitle());
        statement.bindString(2,note.getContent());

        long id = statement.executeInsert();

        if(id > 0){
            note.setId(id);
            return true;
        }

        return false;

    }
    
//    update
    public boolean update(Note note){
        statement = db.compileStatement(UPDATE);

        statement.bindString(1, note.getTitle());
        statement.bindString(2, note.getContent());
        statement.bindLong(3, note.getId());

        long id = statement.executeUpdateDelete();

        return id == 1;
    }

//    delete
    public boolean delete(long id){
        int result = db.delete(DbSchema.NotesTable.NAME, "id = ?",new String[]{id+""});
        return result > 0;
    }

}
