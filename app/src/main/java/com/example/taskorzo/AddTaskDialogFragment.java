package com.example.taskorzo;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import org.jetbrains.annotations.NotNull;

import io.ghyeok.stickyswitch.widget.StickySwitch;
import mehdi.sakout.fancybuttons.FancyButton;


public class AddTaskDialogFragment extends DialogFragment {
    private EditText editTaskTitle, editTaskDescription;
    private FancyButton addButton,cancelButton;
    StickySwitch stickySwitch;
    String[] newTask;

    public interface OnTaskSelected  {
        void sendTask(String[] newTask);
        void sendHabit(int habitValue);
    }

    public OnTaskSelected onTaskSelected;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.add_task_dailog, container, false);

       editTaskTitle = view.findViewById(R.id.editTitleText);
       editTaskDescription = view.findViewById(R.id.editDescriptionText);
       addButton = view.findViewById(R.id.buttonAddTask);
       cancelButton = view.findViewById(R.id.buttonCancelTask);
       stickySwitch = view.findViewById(R.id.switch_habit_or_not);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = editTaskTitle.getText().toString().trim();
                String taskDescription = editTaskDescription.getText().toString().trim();

                if(!taskTitle.equals("")){
                    newTask = new String[]{taskTitle, taskDescription};
                    onTaskSelected.sendTask(newTask);

                }
                dismiss();
           }
       });

        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String s) {
                if (direction == StickySwitch.Direction.RIGHT) {
                    onTaskSelected.sendHabit(1);
                } else {
                    onTaskSelected.sendHabit(0);
                }

            }
        });

       cancelButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getDialog().dismiss();
           }
       });


       return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onTaskSelected = (OnTaskSelected) getTargetFragment();
        } catch (ClassCastException e ) {
            Log.e("Error", e.getMessage());
        }
    }
}
