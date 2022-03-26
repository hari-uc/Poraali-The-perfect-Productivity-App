package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entity.Note;
import com.example.myapplication.R;

import java.util.List;

public class NotesAdapter  extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder>{

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder (
                LayoutInflater.from (parent.getContext ()).inflate (
                        R.layout.item_container_note,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.setNote (notes.get (position));

    }

    @Override
    public int getItemCount() {
        return notes.size ();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private List<Note> notes;


    static class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textSubtitle, textDateTime;


        NoteViewHolder(@NonNull View itemView) {
            super (itemView);
            textTitle = itemView.findViewById (R.id.texttitle);
            textSubtitle = itemView.findViewById (R.id.textSubtitle);
            textDateTime = itemView.findViewById (R.id.textDateTime);
        }

        void setNote(Note note){
            textTitle.setText (note.getTitle ());
            if (note.getSubtitle ().trim ().isEmpty ()){
                textSubtitle.setVisibility (View.GONE);
            }else {
                textSubtitle.setText (note.getSubtitle ());
            }
            textDateTime.setText (note.getDate_time ());
        }
    }
}
