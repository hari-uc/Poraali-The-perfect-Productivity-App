package com.hari.poraali.QuotesActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.hari.poraali.R;

public class QuotesActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    RadioButton btn1, btn2;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_quotes);
        getSupportActionBar ().hide ();

        frameLayout = findViewById (R.id.framelayout);
        btn1 = findViewById (R.id.englishbtn);
        btn2 = findViewById (R.id.tamilbtn);
        imgback = findViewById(R.id.imagebackquotes);



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

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }
}