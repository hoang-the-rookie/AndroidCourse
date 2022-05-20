package edu.hanu.mynotes.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.mynotes.models.Note;

public class NoteCursorWrapper extends CursorWrapper {
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public List<Note> getNotes(){

        List<Note> notes = new ArrayList<>();

        moveToFirst();

        while(!isAfterLast()){
            Note note = getNote();
            notes.add(note);

            moveToNext();
        }

        return notes;
    }

    public Note getNote(){

        int id = getInt(getColumnIndex(DbSchema.NotesTable.Cols.ID));
        String title = getString(getColumnIndex(DbSchema.NotesTable.Cols.TITLE));
        String content = getString(getColumnIndex(DbSchema.NotesTable.Cols.CONTENT));

        Note note = new Note(id,title,content);

        return note;
    }
}
