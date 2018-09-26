package com.project.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class SecondActivity extends AppCompatActivity {


    //change here to the new recView
    List<Item> itemList = new ArrayList<> ();

    // RecyclerViewAdapter ;
    private MyRecAdapter adapter;

    //User + Database
    private FirebaseUser user;
    private DatabaseReference myRef = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.second_activity);

        //Auth User + Database -
        user = FirebaseAuth.getInstance ().getCurrentUser ();
        if (user != null) {
            myRef = FirebaseDatabase.getInstance ().getReference ();
            gettingMessagesFromFireBase (myRef);
        }

        //Building doToList
        adapter = new MyRecAdapter (itemList);

        //Toolbar clear
        @SuppressLint("ResourceType")
        Toolbar toolbar = findViewById (R.layout.toolbar);
        setSupportActionBar (toolbar);

        //Setting RecyclerView
        RecyclerView rcv = findViewById (R.id.recycler_view);
        rcv.setLayoutManager (new LinearLayoutManager (this));
        rcv.setAdapter (adapter);

        //Setting Swipe abilities
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper (createHelperCallBack());
        itemTouchHelper.attachToRecyclerView (rcv);

    }

    private void gettingMessagesFromFireBase(final DatabaseReference myRef) {

        myRef.child (user.getUid ()).addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot g : dataSnapshot.getChildren ()) {
                    Item oldItem = g.getValue (Item.class);
                    boolean result = false;
                    if (oldItem != null){
                            result = adapter.add (oldItem);
                        Toast.makeText (SecondActivity.this, "Added was "+result, Toast.LENGTH_SHORT).show ();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    // Setting swipe ability remove + move to end of list
    private ItemTouchHelper.Callback createHelperCallBack() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback
                (0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition ();
                // when swiping right (Done) -
                if (direction == ItemTouchHelper.RIGHT) {

                    adapter.remove (position);
                    if (myRef != null) {
                        myRef.child (user.getUid ()).child(itemList.get (position).getTtl ()).removeValue ();
                    }
                    Toast.makeText (getBaseContext (), "Task - " +position+1+ " was removed ", Toast.LENGTH_SHORT).show ();
                } if (direction == ItemTouchHelper.LEFT){
                    /*adapter.moveToEnd (position);
                    Toast.makeText (getBaseContext (), "- Move to end - "+ position+1,Toast.LENGTH_SHORT).show ();*/
                }
            }
        };
        return simpleCallback;
    }



    public void addBtn(final View btn) {
        @SuppressLint("InflateParams") View v = getLayoutInflater ().inflate (R.layout.dailog_add_btn_dialog, null, false);
        final EditText titleEdt = v.findViewById (R.id.title_edit_txt),
                descriptionEdt = v.findViewById (R.id.description_edit_txt);

        new AlertDialog.Builder (this)
                .setView (v)
                .setPositiveButton ("done", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        String desc = descriptionEdt.getText().toString ();
                        String ttl = titleEdt.getText ().toString ();
                        if (ttl.isEmpty ()){
                            Toast.makeText (SecondActivity.this, "title needs to be filed", Toast.LENGTH_SHORT).show ();
                            addBtn (btn);
                        }else {
                        Item itemData = new Item (ttl, desc);
                        if (myRef != null) {
                            myRef.child (user.getUid ()).child (itemData.getTtl ()).setValue (itemData); }
                        }
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