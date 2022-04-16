package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.QuotesActivity.QuotesActivity;
import com.example.myapplication.R;
import com.example.myapplication.ToDoFiles.TodoActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    Button btn1,btn2,btn3,btn4;
    TextView textView;
    LottieAnimationView lottienews,lottietodo,lottienotes,lottiequotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getSupportActionBar ().hide ();
        setContentView (R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById (R.id.navigation_view);

        btn1 = findViewById (R.id.button1);
        btn2 = findViewById (R.id.button2);
        btn3 = findViewById (R.id.button3);
        btn4 = findViewById (R.id.button4);
        textView = findViewById (R.id.textnickname);
        lottienews  = findViewById (R.id.lottienews);
        lottietodo = findViewById (R.id.lottietodo);
        lottienotes = findViewById (R.id.lottienotes);
        lottiequotes = findViewById (R.id.lottiequotes);

        lottienews.loop (true);
        lottienews.playAnimation ();

        lottietodo.loop (true);
        lottietodo.playAnimation ();

        lottienotes.loop (true);
        lottienotes.playAnimation ();

        lottiequotes.loop(true);
        lottiequotes.playAnimation ();



        SharedPreferences retive =getSharedPreferences ("login",MODE_PRIVATE);
        String user_nick_name =retive.getString ("NAME","");
        textView.setText ("Hello" +" " + user_nick_name +" "+ "👋");

        // to set user nick name in menu drawer profile
        NavigationView nav_view = (NavigationView)findViewById (R.id.navigation_view);
        View header = nav_view.getHeaderView (0);
        TextView nicknametext = header.findViewById (R.id.userName);
        nicknametext.setText (user_nick_name);







        btn1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent quotesIntent = new Intent (MainActivity.this,QuotesActivity.class);
                startActivity (quotesIntent);
            }
        });

        btn2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent todoIntent = new Intent (MainActivity.this,TodoActivity.class);
                startActivity (todoIntent);
            }
        });

        btn3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent notesIntent = new Intent (MainActivity.this,NotesActivity.class);
                startActivity (notesIntent);

            }
        });

        btn4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent newsIntent  = new Intent (MainActivity.this,EntrepreneurNewsActivity.class);
                startActivity (newsIntent);
            }
        });

        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }

        });
        navigationView.setNavigationItemSelectedListener (new NavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked (true);
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (id)
                {
                    case R.id.home:
//                        replaceFragment (new HomeFragment ());break;
//                        Intent intent = new Intent (getApplicationContext (),MainActivity.class);
//                        startActivity (intent);
                        Toast.makeText(MainActivity.this, "You Are already in Home Page", Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
//                        replaceFragment (new SettingsFragment ());break;
                        Intent intent1 = new Intent (getApplicationContext (),SettingsActivity.class);
                        intent1.putExtra ("Name",user_nick_name);
                        startActivity (intent1);
                        Toast.makeText (MainActivity.this,"selected settings",Toast.LENGTH_SHORT).show ();break;
                    case R.id.devGroup:
                        Intent intent4 = new Intent (getApplicationContext (), DevelopersActivity.class);
                        startActivity (intent4);
                        break;
                    case R.id.nav_about:
                        Intent intent2 = new Intent (getApplicationContext (), AboutUs.class);
                        startActivity (intent2);
                        break;
                    case R.id.nav_privacy_policy:
                        Intent intent3 = new Intent (getApplicationContext (), PrivacyPolicyActivity.class);
                        startActivity (intent3);
                        break;

                    default:
                        return true;

                }
                return true;

            }
        });





    }
//    private void replaceFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager ();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
//        fragmentTransaction.replace (R.id.framelayout,fragment);
//        fragmentTransaction.commit ();
//    }






}