package com.example.myapplication.QuotesActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.myapplication.R;

public class QuotesActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    RadioButton btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_quotes);
        getSupportActionBar ().hide ();

        frameLayout = findViewById (R.id.framelayout);
        btn1 = findViewById (R.id.englishbtn);
        btn2 = findViewById (R.id.tamilbtn);



        btn1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager ();
                fragmentManager.beginTransaction ()
                        .replace (R.id.framelayout, EnglishQuote.class,null)
                        .setReorderingAllowed (true)
                        .commit ();
            }
        });
        btn2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager ();
                fragmentManager.beginTransaction ()
                        .replace (R.id.framelayout, TamilQuotes.class,null)
                        .setReorderingAllowed (true)
                        .commit ();
            }
        });



    }
}