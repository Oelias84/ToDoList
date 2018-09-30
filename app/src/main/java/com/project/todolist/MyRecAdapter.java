package com.project.todolist;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MyRecAdapter extends RecyclerView.Adapter<MyRecAdapter.RecViewHolder> {



    public List<Item> list ;


    public MyRecAdapter (List<Item> lists){
        this.list = lists;
    }

    boolean add(Item item) {
        if (!list.contains (item)) {
            list.add (item);
            notifyItemInserted (list.size ());
            return true;
        }
        return false;
    }

    void moveToEnd(int position){
        if (position != list.size ()){
            list.add (list.size () , list.get (position));
            list.remove (position);
            notifyItemChanged (position);
            notifyItemMoved (position, list.size () + 1);
        }
    }

    void remove(int position){
        list.remove (position);
        notifyItemRemoved (position);

    }

    void editItem(int position, String ttl, String desc){
        list.get (position).setTtl (ttl);
        list.get (position).setDesc (desc);
        notifyItemChanged (position);
    }

    @Override
    public MyRecAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecViewHolder holder, int position) {


        final Item item = list.get(holder.getAdapterPosition ());
        if (item != null) {
            holder.bind (item);
            holder.itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    if (item.getDesc () != null) {
                        if (!item.getDesc ().isEmpty ()) {
                            boolean expanded = item.isExpanded ();
                            item.setExpanded (!expanded);
                            notifyItemChanged (holder.getAdapterPosition ());
                        }
                    }
                }
            });
        }
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
            if (item != null) {
                subItem.setVisibility (item.isExpanded () ? View.VISIBLE : View.GONE);
                ttl.setText (item.getTtl ());
                desc.setText (item.getDesc ());
            }
        }
    }


}
