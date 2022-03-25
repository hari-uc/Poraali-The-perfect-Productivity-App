package com.example.myapplication.dao;


import androidx.room.Dao;
import androidx.room.Insert;

import androidx.room.Delete;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.Entity.Note;

import java.util.List;




@Dao
public interface NoteDao {
    @Query ("SELECT * FROM notes ORDER BY id DESC ")
    List<Note>getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);
}
