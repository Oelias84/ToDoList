package com.project.todolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> todoText;
    private Context context;
    private Button optionBtn;

    public RecyclerViewAdapter(Context context, ArrayList<String> todoText) {
        this.todoText = todoText;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layou_todo_item, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: called.");
        holder.todoText.setText(todoText.get(position));

        // view option btn click function:
        holder.optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "yay", Toast.LENGTH_SHORT).show();
            }
        });

        // view click function:
        holder.todoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on" + todoText.get(position));
                Toast.makeText(context, todoText.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoText.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView todoText;
        Button optionBtn;
        RelativeLayout todoLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.todoTxt);
            optionBtn = itemView.findViewById(R.id.optionBtn);
            todoLayout = itemView.findViewById(R.id.todoLayout);



        }
    }
}
