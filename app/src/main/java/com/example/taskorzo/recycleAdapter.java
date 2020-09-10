package com.example.taskorzo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class recycleAdapter extends RecyclerView.Adapter<recycleAdapter.MyViewHolder> {
    Context context;

    ArrayList taskTitle,taskDescription;
    int mExpandedPosition = -1;

    onItemClickListner onItemClickListner;

    public void setOnItemClickListner(recycleAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner{
        void onClick(String buttonClicked);
    }


    recycleAdapter(Context mcontext, ArrayList mtaskTitle, ArrayList mtaskDescription) {
        context = mcontext;
        taskTitle = mtaskTitle;
        taskDescription = mtaskDescription;
    }

    @NonNull
    @Override
    public recycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyle_row, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final recycleAdapter.MyViewHolder holder, final int position) {

        holder.recylerTitleTextView.setText(String.valueOf(taskTitle.get(position)));
        holder.recylerDescriptionTextView.setText(String.valueOf(taskDescription.get(position)));


        //Adding appearance and disappearance of two buttons getting hectic now
        final boolean isExpanded = position==mExpandedPosition;
        holder.expandedlinearLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.recylerTitleTextView.setVisibility(isExpanded?View.GONE:View.VISIBLE);
        holder.recylerDescriptionTextView.setVisibility(isExpanded?View.GONE:View.VISIBLE);
        holder.itemView.setActivated(isExpanded);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(position);
            }

        });

        holder.mark_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onClick((String) taskTitle.get(holder.getAdapterPosition()));
                mExpandedPosition = isExpanded ? -1:position;
            }
        });



    }

    @Override
    public int getItemCount() {
      return taskDescription.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recylerTitleTextView, recylerDescriptionTextView;
        FancyButton mark_complete;
        ConstraintLayout taskContainer;
        LinearLayout expandedlinearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recylerTitleTextView = itemView.findViewById(R.id.recyleTitleTextView);
            recylerDescriptionTextView = itemView.findViewById(R.id.recycleDescriptionTextView);
            taskContainer = itemView.findViewById(R.id.task_container);
            mark_complete = itemView.findViewById(R.id.mark_complete);
            expandedlinearLayout = itemView.findViewById(R.id.sub_items);

        }

    }
}
