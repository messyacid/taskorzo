package com.example.taskorzo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddHabitDialogFragment extends DialogFragment {
    private EditText editHabitTitle, editHabitDescription;
    private FancyButton addButton,cancelButton;
    String[] newHabit;

    public interface OnHabitSelected  {
        void sendHabit(String[] newHabit);
    }

    public OnHabitSelected onHabitSelected;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_habit_dialog, container, false);

        editHabitTitle = view.findViewById(R.id.editTitleTextHabit);
        editHabitDescription = view.findViewById(R.id.editDescriptionTextHabit);
        addButton = view.findViewById(R.id.buttonAddHabit);
        cancelButton = view.findViewById(R.id.buttonCancelHabit);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitTitle = editHabitTitle.getText().toString().trim();
                String habitDescription = editHabitDescription.getText().toString().trim();

                if(!habitTitle.equals("")){
                    newHabit = new String[]{habitTitle, habitDescription};
                    onHabitSelected.sendHabit(newHabit);

                }
                dismiss();
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
            onHabitSelected = (OnHabitSelected) getTargetFragment();
        } catch (ClassCastException e ) {
            Log.e("Error", e.getMessage());
        }
    }
}