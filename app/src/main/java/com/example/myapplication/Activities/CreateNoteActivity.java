package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Entity.Note;
import com.example.myapplication.R;
import com.example.myapplication.Utils.NotesDatabase;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    private TextView textDateTime;
    private String selectedNoteColor;
    private View viewSubtitleindicator;

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
        viewSubtitleindicator  = findViewById (R.id.viewsubtitleindicator);

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

        selectedNoteColor = "#333333";

        initMiscellaneous ();
        setSubtitleIndicatorColor ();
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
        note.setColor (selectedNoteColor);


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

    private void initMiscellaneous(){
        final LinearLayout layoutMiscellaneous = findViewById (R.id.layoutmiscellenous);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from (layoutMiscellaneous);
        layoutMiscellaneous.findViewById (R.id.textMiscellaneous).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState () != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState (BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    bottomSheetBehavior.setState (BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        final ImageView imagecolor1 = layoutMiscellaneous.findViewById (R.id.imagecolor1);
        final ImageView imagecolor2 = layoutMiscellaneous.findViewById (R.id.imagecolor2);
        final ImageView imagecolor3 = layoutMiscellaneous.findViewById (R.id.imagecolor3);
        final ImageView imagecolor4 = layoutMiscellaneous.findViewById (R.id.imagecolor4);
        final ImageView imagecolor5 = layoutMiscellaneous.findViewById (R.id.imagecolor5);

        layoutMiscellaneous.findViewById (R.id.viewcolor1).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#333333";
                imagecolor1.setImageResource (R.drawable.ic_done);
                imagecolor2.setBackgroundResource (0);
                imagecolor3.setBackgroundResource (0);
                imagecolor4.setImageResource (0);
                imagecolor5.setImageResource (0);
                setSubtitleIndicatorColor ();
            }
        });

        layoutMiscellaneous.findViewById (R.id.viewcolor2).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#FDBE3B";
                imagecolor1.setImageResource (0);
                imagecolor2.setBackgroundResource (R.drawable.ic_done);
                imagecolor3.setBackgroundResource (0);
                imagecolor4.setImageResource (0);
                imagecolor5.setImageResource (0);
                setSubtitleIndicatorColor ();
            }
        });

        layoutMiscellaneous.findViewById (R.id.viewcolor3).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#FF4842";
                imagecolor1.setImageResource (0);
                imagecolor2.setBackgroundResource (0);
                imagecolor3.setBackgroundResource (R.drawable.ic_done);
                imagecolor4.setImageResource (0);
                imagecolor5.setImageResource (0);
                setSubtitleIndicatorColor ();
            }
        });

        layoutMiscellaneous.findViewById (R.id.viewcolor4).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#3A52Fc";
                imagecolor1.setImageResource (0);
                imagecolor2.setBackgroundResource (0);
                imagecolor3.setBackgroundResource (0);
                imagecolor4.setImageResource (R.drawable.ic_done);
                imagecolor5.setImageResource (0);
                setSubtitleIndicatorColor ();
            }
        });

        layoutMiscellaneous.findViewById (R.id.viewcolor5).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#000000";
                imagecolor1.setImageResource (0);
                imagecolor2.setBackgroundResource (0);
                imagecolor3.setBackgroundResource (0);
                imagecolor4.setImageResource (0);
                imagecolor5.setImageResource (R.drawable.ic_done);
                setSubtitleIndicatorColor ();
            }
        });

    }

    private void setSubtitleIndicatorColor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleindicator.getBackground ();
        gradientDrawable.setColor (Color.parseColor (selectedNoteColor));
    }
}