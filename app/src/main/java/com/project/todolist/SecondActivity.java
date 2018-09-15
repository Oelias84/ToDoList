package com.project.todolist;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {


    private ArrayList<String> data = new ArrayList<> ();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.second_activity);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // load recyclerView.
        RecyclerView rcv = findViewById(R.id.recycler_view);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        data.add("workworkwork");
        data.add("workwsorkwork");
        data.add("workwodsdrkwork");


        rcv.setAdapter(new RecyclerViewAdapter(data));



    }

    public void addBtn(View view) {

         //load the fragment (temporary).
        AddBtnDialogFragment fragment = new AddBtnDialogFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.second_activity,fragment,"addBtn");
        transaction.commit();
    }
}
