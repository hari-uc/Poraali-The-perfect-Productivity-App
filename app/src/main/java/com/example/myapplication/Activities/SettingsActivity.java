package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class SettingsActivity extends AppCompatActivity {
    SwitchCompat switchCompat;
    TextView textView;
    SharedPreferences sharedPreferences = null;
    ImageView backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_settings);
        getSupportActionBar ().hide ();

        switchCompat = findViewById (R.id.switchdark);
        textView = findViewById (R.id.settingsusername);
        textView.setText (getIntent ().getStringExtra ("Name"));
        backbtn = findViewById(R.id.backbtn);


        sharedPreferences = getSharedPreferences ("night",0);
        boolean booleanvalue = sharedPreferences.getBoolean ("night_mode",false);

        if (booleanvalue){
            AppCompatDelegate.setDefaultNightMode (AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked (true);
        }


        switchCompat.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {

                if (ischecked){
                    AppCompatDelegate.setDefaultNightMode (AppCompatDelegate.MODE_NIGHT_YES);
                    switchCompat.setChecked (true);
                    SharedPreferences.Editor editor = sharedPreferences.edit ();
                    editor.putBoolean ("night_mode",true);
                    editor.commit ();
                }else {
                    AppCompatDelegate.setDefaultNightMode (AppCompatDelegate.MODE_NIGHT_NO);
                    switchCompat.setChecked (false);
                    SharedPreferences.Editor editor = sharedPreferences.edit ();
                    editor.putBoolean ("night_mode",false);
                    editor.commit ();
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}