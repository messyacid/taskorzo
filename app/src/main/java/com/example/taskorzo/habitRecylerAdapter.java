package com.example.taskorzo;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class habitRecylerAdapter extends RecyclerView.Adapter<habitRecylerAdapter.MyViewHolder> {

    Context context;
    ArrayList habitTitle, habitDescription;



    habitRecylerAdapter(Context mcontext, ArrayList mhabitTitle, ArrayList mhabitDescription) {
        context = mcontext;
        habitTitle = mhabitTitle;
        habitDescription = mhabitDescription;
    }


    habitRecylerAdapter.onItemClickListner onItemClickListner;

    public void setOnItemClickListner(habitRecylerAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner{
        void onClick(String elementClicked);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater layoutInflater = LayoutInflater.from(context);
       View view = layoutInflater.inflate(R.layout.habit_row, parent, false);
       return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.recylerTitleTextView.setText(String.valueOf(habitTitle.get(position)));
        holder.recylerDescriptionTextView.setText(String.valueOf(habitDescription.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onClick((String) habitTitle.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return habitTitle.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recylerTitleTextView, recylerDescriptionTextView;
        ConstraintLayout habitContainer;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recylerTitleTextView = itemView.findViewById(R.id.habitRecyleTitleTextView);
            recylerDescriptionTextView = itemView.findViewById(R.id.habitRecycleDescriptionTextView);
            habitContainer = itemView.findViewById(R.id.habit_task_container);
        }
    }
}
