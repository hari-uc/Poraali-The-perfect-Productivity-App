package com.example.myapplication.listeners;

import com.example.myapplication.Entity.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
