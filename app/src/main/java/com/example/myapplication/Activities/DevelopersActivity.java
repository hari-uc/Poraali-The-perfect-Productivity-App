package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.DevelopersAdapter;
import com.example.myapplication.Model.DeveloperModel;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.wajahatkarim3.easyflipview.EasyFlipView;

public class DevelopersActivity extends AppCompatActivity {
    RecyclerView rv;
    DevelopersAdapter mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        getSupportActionBar().hide();



        rv = findViewById(R.id.dev_recycle);
        rv.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<DeveloperModel> options =
                new FirebaseRecyclerOptions.Builder<DeveloperModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("teammembers"), DeveloperModel.class)
                        .build();


        mainAdapter = new DevelopersAdapter(options);
        rv.getRecycledViewPool().clear();
        mainAdapter.notifyDataSetChanged();
        rv.setAdapter(mainAdapter);

        rv.setItemAnimator(null);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

}