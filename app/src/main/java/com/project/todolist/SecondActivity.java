package com.project.todolist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;

public class SecondActivity extends Activity{



    ArrayList arrayList;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        adapter = new RecyclerViewAdapter(this, arrayList);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
    }







}
