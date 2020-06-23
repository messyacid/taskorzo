package com.example.taskorzo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

        //Adding animations to task card
        holder.taskContainer.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

    }

    @Override
    public int getItemCount() {
      return taskDescription.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recylerTitleTextView, recylerDescriptionTextView;
        ConstraintLayout taskContainer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recylerTitleTextView = itemView.findViewById(R.id.recyleTitleTextView);
            recylerDescriptionTextView = itemView.findViewById(R.id.recycleDescriptionTextView);
            taskContainer = itemView.findViewById(R.id.task_container);
        }
    }
}
