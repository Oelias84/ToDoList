package com.project.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;

public class SecondActivity extends Activity {


    private ArrayList<String> data = new ArrayList<> ();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.second_activity);
        RecyclerView rcv = findViewById(R.id.recycler_view);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        data.add ("Ofir Need to work better");


        rcv.setAdapter(new RecyclerViewAdapter(data));

    }







}
