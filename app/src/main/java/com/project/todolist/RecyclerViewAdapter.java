package com.project.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder>{



    private ArrayList<String> toDoListArray;


    public RecyclerViewAdapter(ArrayList<String> toDoListArray) {
        this.toDoListArray = toDoListArray;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_todo_item, parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }





    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        // todo list text view



    }

    @Override
    public int getItemCount() {
        return toDoListArray.size();
    }

}

    class MyViewHolder extends RecyclerView.ViewHolder {


    TextView txtTitle;
    TextView txtDes;

    public MyViewHolder(View itemView) {
        super (itemView);
        txtTitle = itemView.findViewById (R.id.txt_Title);
        txtDes = itemView.findViewById(R.id.txt_Des);
    }


    }

