package com.example.myapplication.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.WebviewAdapter;
import com.example.myapplication.Model.NewsModel;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EntrepreneurNewsActivity extends AppCompatActivity {

    RecyclerView rv;
    WebviewAdapter mainAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrepreneur_news);
//        requestWindowFeature (Window.FEATURE_NO_TITLE);
//        this.getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar ().hide ();


        rv = findViewById(R.id.recyclev);
        rv.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<NewsModel> options =
                new FirebaseRecyclerOptions.Builder<NewsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("newsdata"), NewsModel.class)
                        .build();


        mainAdapter = new WebviewAdapter(options);
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