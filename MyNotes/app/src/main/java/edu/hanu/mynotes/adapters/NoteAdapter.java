package edu.hanu.mynotes.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.hanu.mynotes.ModifyNote;
import edu.hanu.mynotes.R;
import edu.hanu.mynotes.db.DbHelper;
import edu.hanu.mynotes.db.NoteManager;
import edu.hanu.mynotes.models.Constants;
import edu.hanu.mynotes.models.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private Context context;
    private View itemView;
    private TextView tvNote;
    private Intent intent;


    //    view holder
    protected class NoteHolder extends RecyclerView.ViewHolder {

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Note note){
//            get ref to text view
            tvNote = itemView.findViewById(R.id.et_note);

//            set title for note
            tvNote.setText(note.getTitle());

//            handle click
            itemView.setOnClickListener(view ->{
                intent = new Intent(itemView.getContext(), ModifyNote.class);
                intent.putExtra("NOTE",note);

                ((Activity) context).startActivityForResult(intent, Constants.RC_EDIT_NOTE);
            });

            itemView.setOnLongClickListener(view -> {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Confirmation")
                        .setMessage("Are you sure to delete \"" + note.getTitle() + "\" ?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            NoteManager noteManager = NoteManager.getInstance(context);
                            boolean deleteSuccess = noteManager.delete(note.getId());

//                            remove from list and notify change
                            if (deleteSuccess) {
                                notes.remove(note);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return false;
            });
        }
    }

    private List<Note> notes;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        context
        context = parent.getContext();

//        inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        itemView = inflater.inflate(R.layout.note,parent,false);


        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        //        get data at position
        Note note = notes.get(position);

//        bind data to view
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }




//

}
