package com.example.taskorzo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycleAdapter extends RecyclerView.Adapter<recycleAdapter.MyViewHolder> {

    Context context;
    ArrayList taskTitle,taskDescription;

    recycleAdapter(Context context, ArrayList taskTitle, ArrayList taskDescription) {
        this.context = context;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
    }

    @NonNull
    @Override
    public recycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyle_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleAdapter.MyViewHolder holder, int position) {

        holder.recylerTitleTextView.setText(String.valueOf(taskTitle.get(position)));
        holder.recylerDescriptionTextView.setText(String.valueOf(taskDescription.get(position)));
    }

    @Override
    public int getItemCount() {
      return taskDescription.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recylerTitleTextView, recylerDescriptionTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recylerTitleTextView = itemView.findViewById(R.id.recyleTitleTextView);
            recylerDescriptionTextView = itemView.findViewById(R.id.recycleDescriptionTextView);
        }
    }
}
