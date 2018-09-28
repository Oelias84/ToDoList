package com.project.todolist;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class SecondActivity extends AppCompatActivity {


    //change here to the new recView
    private List<Item> itemList = new ArrayList<> ();
    private boolean create = true;

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
                if (create){
                    for(DataSnapshot g : dataSnapshot.getChildren ()) {
                        Item oldItem = g.getValue (Item.class);
                        boolean result = false;
                            if (oldItem != null)
                                if (!adapter.list.contains (oldItem) && !itemList.contains (oldItem))
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
                    Toast.makeText (getBaseContext (), "Task - " +position+ " was removed ", Toast.LENGTH_SHORT).show ();

                } if (direction == ItemTouchHelper.LEFT){
                    adapter.moveToEnd (position);
                    Toast.makeText (getBaseContext (), "- Move to end - "+ position,Toast.LENGTH_SHORT).show ();
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
                        Calendar cal ;

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

    public void setAlarm(Date dateOfAlarm){
        long month = dateOfAlarm.getTime ();
        Toast.makeText (this,String.valueOf (month),Toast.LENGTH_LONG).show ();

        AlarmManager alarmManager = (AlarmManager) getSystemService (ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance ();
        calendar.add (Calendar.SECOND,5);

        Intent intent = new Intent ("toDoda.action.DISPLAY_NOTIFICATION");
        PendingIntent broadcast = PendingIntent.getBroadcast (this,100,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact (AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis (),broadcast);


    }


}