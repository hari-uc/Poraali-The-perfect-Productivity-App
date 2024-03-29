package com.hari.poraali.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hari.poraali.Entity.Note;
import com.hari.poraali.R;
import com.hari.poraali.Utils.NotesDatabase;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    private TextView textDateTime;
    private ImageView imagenote;
    private View viewSubtitleindicator;
    private TextView textweburl;
    private LinearLayout layoutweburl;
    private Note alreadyAvailableNote;



    private String selectedImagePath;
    private String selectedNoteColor;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;


    private AlertDialog dialogAddUrl;
    private AlertDialog dialogDeleteNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_create_note);
        getSupportActionBar ().hide ();

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
        imagenote = findViewById (R.id.imagenote);
        textweburl = findViewById (R.id.textweburl);
        layoutweburl = findViewById (R.id.layoutweburl);

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
        selectedImagePath = "";

        if(getIntent ().getBooleanExtra ("isVieworUpdate",false)){
            alreadyAvailableNote = (Note) getIntent ().getSerializableExtra ("note");
            setVieworUpdateNote ();
        }

        findViewById (R.id.imageremoveweburl).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                textweburl.setText (null);
                layoutweburl.setVisibility (View.GONE);
            }
        });

        findViewById (R.id.imageremoveImage).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                imagenote.setImageBitmap (null);
                imagenote.setVisibility (View.GONE);
                findViewById (R.id.imageremoveImage).setVisibility (View.GONE);
                selectedImagePath = "";
            }
        });

        if (getIntent ().getBooleanExtra ("isFromQuichActions",false)){
            String type = getIntent ().getStringExtra ("quickActionType");
            if (type != null){
                if (type.equals ("image")){
                    selectedImagePath = getIntent ().getStringExtra ("imagePath");
                    imagenote.setImageBitmap (BitmapFactory.decodeFile (selectedImagePath));
                    imagenote.setVisibility (View.VISIBLE);
                    findViewById (R.id.imageremoveImage).setVisibility (View.VISIBLE);
                }else if (type.equals ("url")){
                    textweburl.setText (getIntent ().getStringExtra ("url"));
                    layoutweburl.setVisibility (View.VISIBLE);
                }
            }
        }

        initMiscellaneous ();
        setSubtitleIndicatorColor ();
    }

    private void setVieworUpdateNote(){
        inputNoteTitle.setText (alreadyAvailableNote.getTitle ());
        inputNoteSubtitle.setText (alreadyAvailableNote.getSubtitle ());
        inputNoteText.setText (alreadyAvailableNote.getNoteText ());
        textDateTime.setText (alreadyAvailableNote.getDate_time ());

        if (alreadyAvailableNote.getImagePath () != null && !alreadyAvailableNote.getImagePath ().trim ().isEmpty ()){
            imagenote.setImageBitmap (BitmapFactory.decodeFile (alreadyAvailableNote.getImagePath ()));
            imagenote.setVisibility (View.VISIBLE);
            findViewById (R.id.imageremoveImage).setVisibility (View.VISIBLE);
            selectedImagePath = alreadyAvailableNote.getImagePath ();
        }

        if (alreadyAvailableNote.getWebLink () != null && !alreadyAvailableNote.getWebLink ().trim ().isEmpty ()){
            textweburl.setText (alreadyAvailableNote.getWebLink ());
            layoutweburl.setVisibility (View.VISIBLE);
        }
    }


    private void saveNote(){
        if (inputNoteTitle.getText ().toString ().trim ().isEmpty ()) {
            Toast.makeText(this, "Note Title Can't be Empty", Toast.LENGTH_SHORT).show();
            return;
        }
//        }else if(inputNoteSubtitle.getText ().toString ().trim ().isEmpty ()
//        && inputNoteText.getText ().toString ().trim ().isEmpty ()){
//            Toast.makeText (this,"Note Can't be Empty",Toast.LENGTH_SHORT).show ();
//            return;
//        }

        final Note note = new Note();
        note.setTitle (inputNoteTitle.getText ().toString ());
        note.setSubtitle (inputNoteSubtitle.getText ().toString ());
        note.setNoteText (inputNoteText.getText ().toString ());
        note.setDate_time (textDateTime.getText ().toString ());
        note.setColor (selectedNoteColor);
        note.setImagePath (selectedImagePath);

        if (layoutweburl.getVisibility () == View.VISIBLE){
            note.setWebLink (textweburl.getText ().toString ());
        }

        if (alreadyAvailableNote != null){
            note.setId (alreadyAvailableNote.getId ());
        }


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

        if (alreadyAvailableNote != null && alreadyAvailableNote.getColor () != null && !alreadyAvailableNote.getColor ().trim ().isEmpty ()){
            switch (alreadyAvailableNote.getColor ()){
                case "#FDBE3B":
                    layoutMiscellaneous.findViewById (R.id.viewcolor2).performClick ();
                    break;
                case "#FF4842":
                    layoutMiscellaneous.findViewById (R.id.viewcolor3).performClick ();
                    break;
                case "#3A52Fc":
                    layoutMiscellaneous.findViewById (R.id.viewcolor4).performClick ();
                    break;
                case "#000000":
                    layoutMiscellaneous.findViewById (R.id.viewcolor5).performClick ();
                    break;
            }
        }


        layoutMiscellaneous.findViewById (R.id.layoutaddimage).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState (BottomSheetBehavior.STATE_COLLAPSED);
                if (ContextCompat.checkSelfPermission (
                        getApplicationContext (), Manifest.permission.READ_EXTERNAL_STORAGE
                )!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions (
                            CreateNoteActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                }else {
                    selectImage ();
                }

            }
        });
        layoutMiscellaneous.findViewById (R.id.layoutaddurl).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState (BottomSheetBehavior.STATE_COLLAPSED);
                showAddUrlDialog ();

            }
        });

        if (alreadyAvailableNote != null){
            layoutMiscellaneous.findViewById (R.id.layoutdeletenote).setVisibility (View.VISIBLE);
            layoutMiscellaneous.findViewById (R.id.layoutdeletenote).setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    bottomSheetBehavior.setState (BottomSheetBehavior.STATE_COLLAPSED);
                    showDeleteNoteDialog ();

                }
            });
        }

    }

    private void showDeleteNoteDialog(){
        if (dialogDeleteNote ==null){
            AlertDialog.Builder builder = new AlertDialog.Builder (CreateNoteActivity.this);
            View view = LayoutInflater.from (this).inflate (
                    R.layout.layout_delete_note,
                    (ViewGroup) findViewById (R.id.layoutaddurlcontainer)
            );
            builder.setView (view);
            dialogDeleteNote = builder.create ();
            if (dialogDeleteNote.getWindow () != null){
                dialogDeleteNote.getWindow ().setBackgroundDrawable (new ColorDrawable (0));
            }
            view.findViewById (R.id.textdeleteNote).setOnClickListener (new View.OnClickListener (){
                @Override
                public void onClick(View v){

                    @SuppressLint("StaticFieldLeak")
                    class DeleteNoteTask extends AsyncTask<Void,Void,Void>{

                        @Override
                        protected Void doInBackground(Void... voids) {
                            NotesDatabase.getDatabase (getApplicationContext ()).noteDao ()
                                    .deleteNote (alreadyAvailableNote);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void unused) {
                            super.onPostExecute (unused);
                            Intent intent = new Intent ();
                            intent.putExtra ("isNoteDeleted",true);
                            setResult (RESULT_OK,intent);
                            finish ();
                        }
                    }
                    new DeleteNoteTask ().execute ();
                }
            });

            view.findViewById (R.id.textcanceldelete).setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    dialogDeleteNote.dismiss ();
                }
            });
        }

        dialogDeleteNote.show ();
    }

    private void setSubtitleIndicatorColor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleindicator.getBackground ();
        gradientDrawable.setColor (Color.parseColor (selectedNoteColor));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE &&  resultCode == RESULT_OK){
            if (data != null){
                Uri selectedImageUri  = data.getData ();
                if (selectedImageUri != null){
                    try {
                        InputStream inputStream = getContentResolver ().openInputStream (selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream (inputStream);
                        imagenote.setImageBitmap (bitmap);
                        imagenote.setVisibility (View.VISIBLE);
                        findViewById (R.id.imageremoveImage).setVisibility (View.VISIBLE);

                        selectedImagePath = getPathfromUri (selectedImageUri);

                    }catch (Exception exception){
                        Toast.makeText (this,exception.getMessage (),Toast.LENGTH_SHORT).show ();
                    }
                }
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

    private void showAddUrlDialog(){
        if (dialogAddUrl == null){
            AlertDialog.Builder builder = new AlertDialog.Builder (CreateNoteActivity.this);
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
                        Toast.makeText (CreateNoteActivity.this,"Please Enter URl",Toast.LENGTH_SHORT).show ();
                    }else if (!Patterns.WEB_URL.matcher (inputURL.getText ().toString ()).matches ()){
                        Toast.makeText (CreateNoteActivity.this,"Enter Valid URl",Toast.LENGTH_SHORT).show ();
                    }else {
                        textweburl.setText (inputURL.getText ().toString ());
                        layoutweburl.setVisibility (View.VISIBLE);
                        dialogAddUrl.dismiss ();
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