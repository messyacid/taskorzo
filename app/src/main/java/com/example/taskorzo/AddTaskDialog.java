package com.example.taskorzo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class AddTaskDialog extends AppCompatDialogFragment {
    private EditText editTaskTitle, editTaskDescription;
    private AddTaskDialogListner listner;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.add_task_dailog, null);

        builder.setView(view).setTitle("Add New Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = editTaskTitle.getText().toString().trim();
                String description = editTaskDescription.getText().toString().trim();
                listner.applyTexts(title, description);
            }
        });

        editTaskTitle = view.findViewById(R.id.editTaskTitle);
        editTaskDescription = view.findViewById(R.id.editTaskDesc);

        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listner = (AddTaskDialogListner) context;
        } catch (ClassCastException e) {
           listner = (AddTaskDialogListner) context;
        }
    }

    public interface AddTaskDialogListner {
        void applyTexts(String title, String description);
    }

}
