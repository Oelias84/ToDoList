package com.project.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SecondActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Change here to the new recView
    private boolean create = true;

    //RecyclerViewAdapter ;
    private MyRecAdapter adapter;

    //User + Database
    private FirebaseUser user = null;
    private DatabaseReference myRef = null;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Auth User + Database -
        user = FirebaseAuth.getInstance ().getCurrentUser ();
        if (user != null) {
            myRef = FirebaseDatabase.getInstance ().getReference ();
            gettingMessagesFromFireBase (myRef);
        }

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Drawer for hamburgerView
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Building doToList
        adapter = new MyRecAdapter (new ArrayList<Item>());

        //Setting RecyclerView
        RecyclerView rcv = findViewById (R.id.recycler_view);
        rcv.setLayoutManager (new LinearLayoutManager(this));
        rcv.setAdapter (adapter);

        //Setting Swipe abilities
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper (createHelperCallBack());
        itemTouchHelper.attachToRecyclerView (rcv);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.nav_exit) {
            if (user != null) {
                user = null;
                myRef = null;
                Intent signOut = new Intent(this, MainActivity.class);
                signOut.putExtra("signOut", 10);
                startActivity(signOut);
                finish();
            }else {
                finish ();
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //getting oldTodo's from fireBase
    private void gettingMessagesFromFireBase(final DatabaseReference myRef) {

        myRef.child (user.getUid ()).addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (create){
                    for(DataSnapshot g : dataSnapshot.getChildren ()) {
                        Item oldItem = g.getValue (Item.class);
                        boolean result = false;
                        if (oldItem != null)
                            if (!adapter.list.contains (oldItem))
                                adapter.add (oldItem);

                    }
                    create = false;
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
                    if (myRef != null) {
                        myRef.child (user.getUid ()).child(adapter.list.get (position).getTtl ()).removeValue ();
                    }
                    adapter.remove (position);
                    Toast.makeText (getBaseContext (), "- Task - " +(position +1)+ " was removed ", Toast.LENGTH_SHORT).show ();

                } if (direction == ItemTouchHelper.LEFT){
                    adapter.moveToEnd (position);
                    Toast.makeText (getBaseContext (), "- Move to end - "+(1+position),Toast.LENGTH_SHORT).show ();
                }
            }
        };
        return simpleCallback;
    }

    //add btn to RecyclerView
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
                            adapter.add (itemData);
                            if (myRef != null)
                                myRef.child (user.getUid ()).child (itemData.getTtl ()).setValue (itemData);
                        }
                    }
                })
                .setNegativeButton ("Cancel", null)
                .show ();
    }

    public void hamburgBtn(View view) {
        drawer.openDrawer(GravityCompat.START);
    }

}


