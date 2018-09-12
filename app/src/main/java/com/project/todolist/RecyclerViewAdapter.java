package com.project.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>{



    private ArrayList<String> toDoListText;


    public RecyclerViewAdapter(ArrayList<String> toDoListText) {
        this.toDoListText = toDoListText;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layou_todo_item, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.recycleViewTxt.setText (toDoListText.get (position));


    }

    @Override
    public int getItemCount() {
        return toDoListText.size();
    }




}

class ViewHolder extends RecyclerView.ViewHolder {

    TextView recycleViewTxt;

    public ViewHolder(View itemView) {
        super (itemView);
        recycleViewTxt = itemView.findViewById (R.id.todoTxt);


    }
}

