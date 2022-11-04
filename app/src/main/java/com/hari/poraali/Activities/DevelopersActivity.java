package com.hari.poraali.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hari.poraali.Adapter.DevelopersAdapter;
import com.hari.poraali.Model.DeveloperModel;
import com.hari.poraali.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

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