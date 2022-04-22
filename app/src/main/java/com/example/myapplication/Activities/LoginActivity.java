package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText user_text;
    Button login_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        getSupportActionBar().hide();
        setContentView (R.layout.activity_login);

        user_text = findViewById (R.id.usernicknametxt);
        login_btn = findViewById (R.id.loginbtn);

        SharedPreferences retrive = getApplicationContext ().getSharedPreferences ("SHAREDPREFERENCE",MODE_PRIVATE);
//        SharedPreferences.Editor retriveEditor = retrive.edit ();
        String userNmae =retrive.getString ("NAME",null);


        if (userNmae!= null){
            Intent loginintent = new Intent (getApplicationContext (),MainActivity.class);
//            loginintent.putExtra ("key",userNmae);
            startActivity (loginintent);
        }

        login_btn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (user_text.getText ().toString ().trim ().length () == 0){
                    Toast.makeText (getApplicationContext (),"Please Enter Nick Name",Toast.LENGTH_SHORT).show ();
                }else if (user_text.getText ().toString ().trim ().length ()>8){
                    Toast.makeText (getApplicationContext (),"Please Enter Name with 8 Characters!!",Toast.LENGTH_SHORT).show ();

                }
                else {
                    onLoginSuccess ();
                    finish();
                }




            }

        });

    }
    public void onLoginSuccess(){
        String usr_text =user_text.getText ().toString ();

        sharedPreferences = getApplicationContext ().getSharedPreferences ("login",MODE_PRIVATE);
        SharedPreferences.Editor insertedEditor = sharedPreferences.edit ();
        insertedEditor.putBoolean ("isLoginKey",true);
        insertedEditor.putString ("NAME",usr_text);
        insertedEditor.commit ();


//        Bundle bundle = new Bundle();
//        bundle.putString("Name","hari");
        Intent loginintent2 = new Intent (getApplicationContext (),MainActivity.class);
//        loginintent2.putExtra ("NAME",usr_text);
        startActivity (loginintent2);
    }
}