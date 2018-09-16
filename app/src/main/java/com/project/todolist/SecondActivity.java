package com.project.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {


    ArrayList<Item> data = new ArrayList<> ();
    RecyclerViewAdapter adapter = new RecyclerViewAdapter ();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.second_activity);

        @SuppressLint("ResourceType")
        Toolbar toolbar = findViewById (R.layout.toolbar);
        setSupportActionBar (toolbar);


        RecyclerView rcv = findViewById (R.id.recycler_view);
        rcv.setLayoutManager (new LinearLayoutManager (this));


        rcv.setAdapter (adapter);


    }

    public void addBtn(View btn) {

        View v = getLayoutInflater ().inflate (R.layout.fragment_add_btn_dialog, null, false);
        final EditText titleEdt = v.findViewById (R.id.title_edit_txt),
                descriptionEdt = v.findViewById (R.id.description_edit_txt);

        new AlertDialog.Builder (this)
                .setView (v)
                .setPositiveButton ("done", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String des = descriptionEdt.getText ().toString ();
                        String title = titleEdt.getText ().toString ();
                        Item data = new Item (title, des);
                        adapter.add (data);
                    }
                })
                .setNegativeButton ("Cancel", null)
                .show ();
    }
}
