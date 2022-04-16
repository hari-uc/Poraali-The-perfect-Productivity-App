package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

public class DevelopersActivity extends AppCompatActivity {
    EasyFlipView kumarsir,santhoshanna,karthikanna,harikrishnan;
    TextView harilinkedin;
    ImageView back_bttn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_developers);
        getSupportActionBar ().hide ();

        kumarsir = findViewById (R.id.kumarsir);
        santhoshanna = findViewById (R.id.santhoshanna);
        karthikanna = findViewById (R.id.karthikanna);
        harikrishnan = findViewById (R.id.harikrishnan);
        back_bttn = findViewById(R.id.imagebackdevelopers);



        harikrishnan.setAutoFlipBack (false);
        karthikanna.setAutoFlipBack (false);
        santhoshanna.setAutoFlipBack (false);
        kumarsir.setAutoFlipBack (false);

        back_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}