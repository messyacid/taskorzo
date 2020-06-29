package com.example.taskorzo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class habitRecylerAdapter extends RecyclerView.Adapter<recycleAdapter.MyViewHolder> {

    Context context;
    ArrayList habitTitle, habitDescription;

    habitRecylerAdapter(Context mcontext, ArrayList mhabitTitle, ArrayList mhabitDescription) {
        context = mcontext;
        habitTitle = mhabitTitle;
        habitDescription = mhabitDescription;
    }


    @NonNull
    @Override
    public recycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.habit_row, parent, false);
        return new recycleAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
