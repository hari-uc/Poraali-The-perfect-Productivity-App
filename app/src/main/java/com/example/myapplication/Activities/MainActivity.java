package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.QuotesActivity.QuotesActivity;
import com.example.myapplication.R;
import com.example.myapplication.ToDoFiles.TodoActivity;

public class MainActivity extends AppCompatActivity {
    Intent todointent, quoteintent, focusintent, notesintent;
    Button btn1,btn2,btn3,btn4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
//        requestWindowFeature (Window.FEATURE_NO_TITLE);
//        this.getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar ().hide ();
        setContentView (R.layout.activity_main);

        btn1 = findViewById (R.id.button1);
        btn2 = findViewById (R.id.button2);
        btn3 = findViewById (R.id.neumorphButton2);
        btn4 = findViewById (R.id.neumorphButton4);

        btn1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                todointent = new Intent (getApplicationContext (), TodoActivity.class);
                startActivity (todointent);
            }
        });

        btn2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                quoteintent = new Intent (getApplicationContext (), QuotesActivity.class);
                startActivity (quoteintent);
            }
        });

        btn3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                focusintent = new Intent (getApplicationContext (),EntrepreneurNewsActivity.class);
                startActivity (focusintent);
            }
        });

        btn4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                notesintent = new Intent (getApplicationContext (), NotesActivity.class);
                startActivity (notesintent);
            }
        });















    }




}