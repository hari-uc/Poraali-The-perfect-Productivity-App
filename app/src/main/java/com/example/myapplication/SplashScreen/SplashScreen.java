package com.example.myapplication.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.R;

import java.util.Random;

public class SplashScreen extends AppCompatActivity {
    Intent splash;
    ImageView splashimg;
    private View decorview;

    final Random randomNum= new Random ();





    int ppl_imgs[] = {R.drawable.girish,R.drawable.sridhar,R.drawable.sundhar,
            R.drawable.kamala, R.drawable.pepsi};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_splash_screen);

        decorview = getWindow ().getDecorView ();
        decorview.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener () {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility ==0)
                    decorview.setSystemUiVisibility (hidesystemBars ());

            }
        });


//        this.getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar ().hide ();
//





        splashimg = findViewById (R.id.splash_img);
        splashimg.setImageResource (ppl_imgs[randomNum.nextInt (ppl_imgs.length-1)]);

        Handler handler = new Handler ();
        handler.postDelayed (this::changeintent,1500);
    }

    public void changeintent(){
        splash = new Intent (getApplicationContext (), MainActivity.class);
        startActivity (splash);
        finish ();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged (hasFocus);
        if (hasFocus){
            decorview.setSystemUiVisibility (hidesystemBars ());
        }
    }

    private int hidesystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

}