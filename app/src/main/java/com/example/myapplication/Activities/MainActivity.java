package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.QuotesActivity.QuotesActivity;
import com.example.myapplication.R;
import com.example.myapplication.ToDoFiles.TodoActivity;
import com.example.myapplication.fragments.SettingsFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

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

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById (R.id.navigation_view);

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
                        Intent intent = new Intent (getApplicationContext (),NotesActivity.class);
                        Toast.makeText(MainActivity.this, "Home is Clicked", Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
                        replaceFragment (new SettingsFragment ());break;
//                        Toast.makeText (MainActivity.this,"selected settings",Toast.LENGTH_SHORT).show ();break;
                    case R.id.devGroup:
                        Intent intent2 = new Intent (getApplicationContext (),NotesActivity.class);
//                        replaceFragment (new MessageFragment ());break;
                        Toast.makeText(MainActivity.this, "Synch is Clicked",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;

                }
                return true;

            }
        });





    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.framelayout,fragment);
        fragmentTransaction.commit ();
    }




}