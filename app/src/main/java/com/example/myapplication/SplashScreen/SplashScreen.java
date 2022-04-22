package com.example.myapplication.SplashScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Activities.LoginActivity;
import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class SplashScreen extends AppCompatActivity {
    TextView textView;
    ImageView splashimg;
    private View decorview;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();
    private DatabaseReference childdbreference = reference.child("img");

    final Random randomNum= new Random ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_splash_screen);
        splashimg = findViewById(R.id.splash_img);
        textView = findViewById(R.id.spashimageurl);
        decorview = getWindow ().getDecorView ();
        decorview.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener () {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility ==0)
                    decorview.setSystemUiVisibility (hidesystemBars ());

            }

        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null & datac != null) && (wifi.isConnected() | datac.isConnected())){
            childdbreference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String message = snapshot.getValue(String.class);
                    textView.setText(message);

                    Picasso.get()
                            .load(message)
                            .into(splashimg);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    splashimg.setImageResource(R.drawable.poraalispash);


                }
            });

        }else{
            splashimg.setImageResource(R.drawable.poraalispash);
        }




        Handler handler = new Handler ();
        handler.postDelayed (this::changeintent,5000);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        childdbreference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String message = snapshot.getValue(String.class);
//                textView.setText(message);
//
//                Picasso.get()
//                        .load(message)
//                        .into(splashimg);
//
//                Handler handler = new Handler ();
//                handler.postDelayed (this::changeintent,3500);
//
//
//
//
//            }
//
//            private void changeintent() {
//                if (getSharedPreferences("login",0).getBoolean("isLoginKey",false)){
//                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
//                    startActivity(i);
//                    finish();
//                }else{
//                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


    public void changeintent(){
        if (getSharedPreferences("login",0).getBoolean("isLoginKey",false)){
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
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