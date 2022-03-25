package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Entity.Note;
import com.example.myapplication.R;
import com.example.myapplication.Utils.NotesDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    private TextView textDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_create_note);

        ImageView imageback = findViewById (R.id.imageback);
        imageback.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                onBackPressed ();
            }
        });


        inputNoteTitle = findViewById (R.id.inputnotetitle);
        inputNoteSubtitle  =findViewById (R.id.inputnotesubtitle);
        inputNoteText = findViewById (R.id.inputnote);
        textDateTime  = findViewById (R.id.textdatetime);

        textDateTime.setText (
                new SimpleDateFormat("EEEE,dd MMMM HH:mm a", Locale.getDefault ())
                .format (new Date ())
        );


        ImageView imageSave  =findViewById (R.id.imagesave);
        imageSave.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                saveNote ();
            }
        });
    }

    private void saveNote(){
        if (inputNoteTitle.getText ().toString ().trim ().isEmpty ()){
            Toast.makeText (this,"Note Title Can't be Empty",Toast.LENGTH_SHORT).show ();
            return;
        }else if(inputNoteSubtitle.getText ().toString ().trim ().isEmpty ()
        && inputNoteText.getText ().toString ().trim ().isEmpty ()){
            Toast.makeText (this,"Note Can't be Empty",Toast.LENGTH_SHORT).show ();
            return;
        }

        final Note note = new Note();
        note.setTitle (inputNoteTitle.getText ().toString ());
        note.setSubtitle (inputNoteSubtitle.getText ().toString ());
        note.setNoteText (inputNoteText.getText ().toString ());
        note.setDate_time (textDateTime.getText ().toString ());


        @SuppressLint ("StaticFieldLeak")
        class saveNoteTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getDatabase (getApplicationContext ()).noteDao ().insertNote (note);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute (unused);
                Intent intent  =new Intent ();
                setResult (RESULT_OK,intent);
                finish ();
            }
        }

        new saveNoteTask ().execute ();

    }
}