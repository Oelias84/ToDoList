package com.project.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class MyRecAdapter extends RecyclerView.Adapter<MyRecAdapter.RecViewHolder> {

    //private List<Item> list;

    private ArrayList<Item> list;

    public MyRecAdapter (ArrayList<Item> lists){
        this.list = lists;
    }

    public void add(Item item) {
        list.add (item);
        notifyDataSetChanged ();
    }

    public MyRecAdapter() {
        list = new ArrayList<> ();
    }

    @Override
    public MyRecAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, final int position) {
        final Item item = list.get(position);

        holder.bind(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean expanded = item.isExpanded();
                item.setExpanded(!expanded);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0: list.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder{

        private TextView ttl;
        private TextView desc;
        private View subItem;

        public RecViewHolder(View itemView) {
            super(itemView);

            ttl = itemView.findViewById(R.id.item_title);
            desc = itemView.findViewById(R.id.sub_item_desc);
            subItem = itemView.findViewById(R.id.sub_item);
        }

        private void bind(Item item) {
            boolean expanded = item.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            ttl.setText(item.getTtl());
            desc.setText("Description: " + item.getDesc());
        }
    }


}
