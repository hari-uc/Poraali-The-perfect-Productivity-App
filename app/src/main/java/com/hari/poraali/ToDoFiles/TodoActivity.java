package com.hari.poraali.ToDoFiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hari.poraali.Adapter.ToDoAdapter;
import com.hari.poraali.Interface.DialogCloseListener;
import com.hari.poraali.Model.ToDoModel;
import com.hari.poraali.R;
import com.hari.poraali.RecyclerViewSwipeFunction.RecyclerItemTouchHelper;
import com.hari.poraali.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoActivity extends AppCompatActivity implements DialogCloseListener {
      RecyclerView recyclerView;
      ToDoAdapter taskadapter;
      List<ToDoModel>tasklist;
      DatabaseHandler db;
      FloatingActionButton floatbtn;
      ImageView backbtn;
      boolean[]checkboxState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_todo);
        getSupportActionBar ().hide ();
        db = new DatabaseHandler (this);
        db.openDatabase ();
        tasklist = new ArrayList<> ();
        checkboxState = new boolean[tasklist.size ()];

        backbtn = findViewById(R.id.imagebacktodo);

        recyclerView = findViewById (R.id.recyclev);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));
        taskadapter = new ToDoAdapter (db,this);
        recyclerView.setAdapter (taskadapter);

        floatbtn = findViewById (R.id.fab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper (new RecyclerItemTouchHelper (taskadapter));
        itemTouchHelper.attachToRecyclerView (recyclerView);


        tasklist = db.getAllTasks ();
        Collections.reverse (tasklist);
        taskadapter.setTasks (tasklist);


        floatbtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance ().show (getSupportFragmentManager (),AddNewTask.TAG);
            }

        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        tasklist = db.getAllTasks ();
        Collections.reverse (tasklist);
        taskadapter.setTasks (tasklist);
        taskadapter.notifyDataSetChanged ();
    }



}