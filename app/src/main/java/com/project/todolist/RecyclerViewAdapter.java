//package com.project.todolist;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//
//public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
//
//
//    private ArrayList<Item> items;
//
//    public void add(Item item) {
//        items.add (item);
//        notifyDataSetChanged ();
//    }
//
//    public RecyclerViewAdapter() {
//        items = new ArrayList<> ();
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
//        View item = inflater.inflate (R.layout.layout_todo_item, parent, false);
//        return new MyViewHolder (item);
//    }
//
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Item item = items.get (position);
//        holder.txtTitle.setText (item.ttl);
//        holder.txtDes.setText (item.desc);
//
//    }

//    @Override
//    public int getItemCount() {
//        return items.size ();
//    }

//}

//class MyViewHolder extends RecyclerView.ViewHolder {
//
//    public TextView txtTitle;
//    public TextView txtDes;
//
//    public MyViewHolder(View itemView) {
//        super (itemView);
//
//        txtTitle = itemView.findViewById (R.id.txt_Title);
//        txtDes = itemView.findViewById (R.id.txt_Des);
//
//
//    }
//}


//class Item {
//    String ttl;
//    String desc;
//
//    public Item(String ttl, String desc) {
//        this.ttl = ttl;
//        this.desc = desc;
//    }
//}
