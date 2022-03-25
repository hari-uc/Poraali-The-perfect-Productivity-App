package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.github.matteobattilana.weather.PrecipType;
import com.github.matteobattilana.weather.WeatherView;

public class FocusMode extends AppCompatActivity {

    WeatherView weatherView;
    RadioButton snowbtn,rainbtn,clearbtn;
    MediaPlayer mediaPlayer;
    Button btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_focus_mode);
        getSupportActionBar ().hide ();

        weatherView = findViewById (R.id.weatherview);
        snowbtn = findViewById (R.id.Snow);
        rainbtn = findViewById (R.id.Rain);
        clearbtn = findViewById (R.id.Clear);
        btn = findViewById (R.id.playbtn);


        rainbtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                weatherView.setWeatherData (PrecipType.RAIN);
                weatherView.setAngle (15);



            }
        });

        snowbtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                weatherView.setWeatherData (PrecipType.SNOW);
                weatherView.setAngle (-35);


            }
        });

        clearbtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                weatherView.setWeatherData (PrecipType.CLEAR);

            }
        });





    }
    public void play(View v){
        if (mediaPlayer ==null){
            mediaPlayer = MediaPlayer.create (this,R.raw.rain);
        }
        mediaPlayer.start ();
    }




}