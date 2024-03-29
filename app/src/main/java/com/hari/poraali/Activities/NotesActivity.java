package com.hari.poraali.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hari.poraali.Adapter.NotesAdapter;
import com.hari.poraali.Entity.Note;
import com.hari.poraali.R;
import com.hari.poraali.Utils.NotesDatabase;
import com.hari.poraali.listeners.NotesListener;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesListener {

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    public static final int REQUEST_CODE_SHOW_NOTES = 3;
    public static final int REQUEST_CODE_SELECT_IMAGE = 4;
    public static final int REQUEST_CODE_STORAGE_PERMISSION = 5;

    private RecyclerView notesRecylerView;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;
    ImageView  back_btn;

    private int noteClickedPosition = -1;

    private AlertDialog dialogAddUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_notes);
//        requestWindowFeature (Window.FEATURE_NO_TITLE);
//        this.getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar ().hide ();

        ImageView ImageAddNoteMain = findViewById (R.id.imageaddnotemain);
        back_btn = findViewById(R.id.imagebacknotes);

        ImageAddNoteMain.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivityForResult (
                        new Intent (getApplicationContext (), CreateNoteActivity.class),REQUEST_CODE_ADD_NOTE
                );
            }
        });

        notesRecylerView = findViewById (R.id.notesrecycle);
        notesRecylerView.setLayoutManager (
                new StaggeredGridLayoutManager (2,StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<> ();
        notesAdapter = new NotesAdapter (noteList,this);
        notesRecylerView.setAdapter (notesAdapter);

        getNotes (REQUEST_CODE_SHOW_NOTES,false);

        EditText inputSearch = findViewById (R.id.inputsearch);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        inputSearch.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                notesAdapter.cancelTimer ();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (noteList.size () != 0){
                    notesAdapter.searchNotes (editable.toString ());
                }

            }
        });

        findViewById (R.id.addbtn).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivityForResult (
                        new Intent (getApplicationContext (), CreateNoteActivity.class),
                        REQUEST_CODE_ADD_NOTE
                );

            }
        });

        findViewById (R.id.imageaddbtn).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission (
                        getApplicationContext (), Manifest.permission.READ_EXTERNAL_STORAGE
                )!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions (
                            NotesActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                }else {
                    selectImage ();
                }

            }
        });

        findViewById (R.id.linkaddbtn).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                showAddUrlDialog ();
            }
        });

    }
    private void selectImage(){
        Intent intent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity (getPackageManager ())!=null){
            startActivityForResult (intent,REQUEST_CODE_SELECT_IMAGE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length>0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage ();
            }else {
                Toast.makeText (this,"Permission Denied",Toast.LENGTH_SHORT).show ();
            }
        }
    }
    private String getPathfromUri(Uri contenturi){
        String filepath;
        Cursor cursor = getContentResolver ()
                .query (contenturi,null,null,null,null);
        if (cursor == null){
            filepath = contenturi.getPath ();
        }else{
            cursor.moveToFirst ();
            int index = cursor.getColumnIndex ("_data");
            filepath = cursor.getString (index);
            cursor.close ();
        }
        return filepath;
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent (getApplicationContext (),CreateNoteActivity.class);
        intent.putExtra ("isVieworUpdate",true);
        intent.putExtra ("note",note);
        startActivityForResult (intent,REQUEST_CODE_UPDATE_NOTE);

    }

    private void getNotes(final int requestCode, final boolean isNoteDeleted){
        @SuppressLint ("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>>{

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase
                        .getDatabase (getApplicationContext ())
                        .noteDao ().getAllNotes ();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute (notes);
                if (requestCode == REQUEST_CODE_SHOW_NOTES){
                    noteList.addAll (notes);
                    notesAdapter.notifyDataSetChanged ();
                }else if (requestCode == REQUEST_CODE_ADD_NOTE){
                    noteList.add (0,notes.get (0));
                    notesAdapter.notifyItemInserted (0);
                    notesRecylerView.smoothScrollToPosition (0);
                }else if (requestCode == REQUEST_CODE_UPDATE_NOTE){
                    noteList.remove (noteClickedPosition);


                    if (isNoteDeleted){
                        notesAdapter.notifyItemRemoved (noteClickedPosition);
                    }else{
                        noteList.add (noteClickedPosition,notes.get(noteClickedPosition));
                        notesAdapter.notifyItemChanged (noteClickedPosition);

                    }
                }
            }
        }

        new GetNotesTask ().execute ();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK){
            getNotes (REQUEST_CODE_ADD_NOTE,false);
        }else if(requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode ==RESULT_OK){
            if (data != null){
                getNotes (REQUEST_CODE_UPDATE_NOTE,data.getBooleanExtra ("isNoteDeleted",false));
            }
        }else if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode ==RESULT_OK){
            if (data != null){
                Uri selectedimageUri = data.getData ();
                if (selectedimageUri != null){
                    try {
                        String selectedimagepath = getPathfromUri (selectedimageUri);
                        Intent intent = new Intent (getApplicationContext (),CreateNoteActivity.class);
                        intent.putExtra ("isFromQuichActions",true);
                        intent.putExtra ("quickActionType","image");
                        intent.putExtra ("imagePath",selectedimagepath);
                        startActivityForResult (intent,REQUEST_CODE_ADD_NOTE);
                    } catch (Exception exception){
                        Toast.makeText (this,exception.getMessage (),Toast.LENGTH_SHORT).show ();
                    }
                }
            }
        }
    }

    private void showAddUrlDialog(){
        if (dialogAddUrl == null){
            AlertDialog.Builder builder = new AlertDialog.Builder (NotesActivity.this);
            View view  = LayoutInflater.from(this).inflate (
                    R.layout.layout_add_url,
                    (ViewGroup) findViewById (R.id.layoutaddurlcontainer)
            );
            builder.setView (view);

            dialogAddUrl  = builder.create ();
            if (dialogAddUrl.getWindow () != null){
                dialogAddUrl.getWindow ().setBackgroundDrawable (new ColorDrawable (0));
            }
            final EditText inputURL = view.findViewById (R.id.inputurl);
            inputURL.requestFocus ();

            view.findViewById (R.id.textadd).setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    if (inputURL.getText ().toString ().trim ().isEmpty ()){
                        Toast.makeText (NotesActivity.this,"Please Enter URl",Toast.LENGTH_SHORT).show ();
                    }else if (!Patterns.WEB_URL.matcher (inputURL.getText ().toString ()).matches ()){
                        Toast.makeText (NotesActivity.this,"Enter Valid URl",Toast.LENGTH_SHORT).show ();
                    }else {
                        dialogAddUrl.dismiss ();
                        Intent intent = new Intent (getApplicationContext (),CreateNoteActivity.class);
                        intent.putExtra ("isFromQuichActions",true);
                        intent.putExtra ("quickActionType","url");
                        intent.putExtra ("url",inputURL.getText ().toString ());
                        startActivityForResult (intent,REQUEST_CODE_ADD_NOTE);
                    }
                }
            });

            view.findViewById (R.id.textcancel).setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    dialogAddUrl.dismiss ();
                }
            });
        }
        dialogAddUrl.show ();



    }
}