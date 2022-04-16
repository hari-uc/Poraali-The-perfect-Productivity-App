package com.example.myapplication.Adapter;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ToDoFiles.AddNewTask;
import com.example.myapplication.Model.ToDoModel;
import com.example.myapplication.R;
import com.example.myapplication.ToDoFiles.TodoActivity;
import com.example.myapplication.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todoList;
    private TodoActivity todoActivity;
    private DatabaseHandler db;




    public ToDoAdapter(DatabaseHandler db, TodoActivity todoActivity){
        this.db = db;
        this.todoActivity = todoActivity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.todo_row_template,parent,false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        db.openDatabase ();
        final ToDoModel item = todoList.get (position);
        holder.task.setText (item.getTask ());
        holder.task.setChecked (toBoolean (item.getStatus ()));

        if (holder.task.isChecked () == true){
            holder.task.setPaintFlags (holder.task.getPaintFlags ()| Paint.STRIKE_THRU_TEXT_FLAG);

        }


        if (position == 0) {
            ObjectAnimator animationLeft = ObjectAnimator.ofFloat(holder.itemView, "translationX", 0f, 80f, 0f, -80f, 0f);
            animationLeft.setDuration(2000);
            animationLeft.start();
        }

        holder.task.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked ()){
                    db.updateStatus (item.getId (),1);
                    compoundButton.setChecked (true);
                    compoundButton.setPaintFlags (compoundButton.getPaintFlags () | Paint.STRIKE_THRU_TEXT_FLAG);
                }else {
                    db.updateStatus (item.getId (),0);
                    compoundButton.setChecked (false);
                    compoundButton.setPaintFlags (compoundButton.getPaintFlags() & ~ Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });
    }
    @Override
    public int getItemCount(){
        return todoList.size ();
    }


    private boolean toBoolean(int n){
        return n!=0;
    }


    public void setTasks(List<ToDoModel> todoList){
        this.todoList = todoList;
        notifyDataSetChanged ();
    }
    public Context getContext(){
        return todoActivity;
    }

    public void deleteItem(int position){
        ToDoModel item = todoList.get (position);
        db.deleteTask (item.getId ());
        todoList.remove (position);
        notifyItemRemoved (position);
    }

    public void editItem(int position){
        ToDoModel item = todoList.get (position);
        Bundle bundle = new Bundle ();
        bundle.putInt ("id",item.getId ());
        bundle.putString ("task",item.getTask ());
        AddNewTask fragment = new AddNewTask ();
        fragment.setArguments (bundle);
        fragment.show (todoActivity.getSupportFragmentManager (),AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task = view.findViewById (R.id.todo_checkbox);
            this.setIsRecyclable (false);
            task.setChecked (false);
        }
    }



}
