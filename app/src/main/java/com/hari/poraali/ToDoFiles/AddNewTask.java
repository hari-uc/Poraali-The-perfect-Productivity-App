package com.hari.poraali.ToDoFiles;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.hari.poraali.Interface.DialogCloseListener;
import com.hari.poraali.Model.ToDoModel;
import com.hari.poraali.R;
import com.hari.poraali.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";


    private EditText newtasktext;
    private Button newTaskSaveBtn;
    private DatabaseHandler db;

    public static AddNewTask newInstance() {
        return new AddNewTask ();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setStyle (STYLE_NORMAL, R.style.DialogStyle);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.new_task, container, false);
        getDialog ().getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        newtasktext = getView ().findViewById (R.id.tasktext);
        newTaskSaveBtn = getView ().findViewById (R.id.save_btn);


        boolean isUpdate = false;
        final Bundle bundle = getArguments ();
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString ("task");
            newtasktext.setText (task);

            if (task.length () > 0)
                newTaskSaveBtn.setTextColor (ContextCompat.getColor (getContext (), R.color.teal_700));
        }

        db = new DatabaseHandler (getActivity ());
        db.openDatabase ();

        newtasktext.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString ().equals ("")) {

                    newTaskSaveBtn.setTextColor (Color.GRAY);
                    newTaskSaveBtn.setEnabled (false);
                }
                else {
                    newTaskSaveBtn.setEnabled (true);
                    newTaskSaveBtn.setTextColor (ContextCompat.getColor (getContext (), R.color.teal_700));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final boolean finalIsUpdate = isUpdate;
        newTaskSaveBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                String text = newtasktext.getText ().toString ();
                if (finalIsUpdate) {
                    db.updateTask (bundle.getInt ("id"), text);
                } else {
                    ToDoModel task = new ToDoModel ();
                    task.setTask (text);
                    task.setStatus (0);
                    db.insertTask (task);
                }
                dismiss ();
            }
        });

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity ();
        if (activity instanceof DialogCloseListener)
            ((DialogCloseListener) activity).handleDialogClose (dialog);

    }
}



