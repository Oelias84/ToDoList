package com.project.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SecondActivity extends AppCompatActivity {


    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private int counter;
    //change here to the new recView
    private ArrayList<Item> data = new ArrayList<> ();

    // RecyclerViewAdapter ;
    private MyRecAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.second_activity);

        //getting old tasks to do.. TODO : add authentication to user.
        preferences = getSharedPreferences ("start", MODE_PRIVATE);
        editor = preferences.edit ();
        counter = preferences.getInt ("counter", 0);
        for (int i = 1; i < counter; i++) {
            String ttl = preferences.getString (String.valueOf (i),"");
            String[] parts = ttl.split ("&");
            data.add (new Item (parts[0],parts[1]));
        }
        if (counter == 0)
            adapter = new MyRecAdapter ();
        else
            adapter = new MyRecAdapter (data);

        @SuppressLint("ResourceType")
        Toolbar toolbar = findViewById (R.layout.toolbar);
        setSupportActionBar (toolbar);

        RecyclerView rcv = findViewById (R.id.recycler_view);
        rcv.setLayoutManager (new LinearLayoutManager (this));


        rcv.setAdapter (adapter);


    }

    public void addBtn(View btn) {

        @SuppressLint("InflateParams") View v = getLayoutInflater ().inflate (R.layout.fragment_add_btn_dialog, null, false);
        final EditText titleEdt = v.findViewById (R.id.title_edit_txt),
                descriptionEdt = v.findViewById (R.id.description_edit_txt);

        new AlertDialog.Builder (this)
                .setView (v)
                .setPositiveButton ("done", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String desc = descriptionEdt.getText().toString ();
                        String ttl = titleEdt.getText ().toString ();
                        Item data = new Item (ttl, desc);
                        adapter.add (data);
                        counter++;
                        //Saving tasks local
                        editor.putInt ("counter", counter);
                        editor.putString (String.valueOf (counter) , ttl + "&" + desc);
                        editor.apply ();
                    }
                })
                .setNegativeButton ("Cancel", null)
                .show ();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy ();

    }

}