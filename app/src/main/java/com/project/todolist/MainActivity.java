package com.project.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    private LoginOrSighupDialog loginOrSighupDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginOrSighupDialog = new LoginOrSighupDialog ();

    }

    public void dialog1(View view) {
        loginOrSighupDialog.show (getFragmentManager (),"start");

    }

    public void moveToSecond(View view) {
        Intent i = new Intent (this,SecondActivity.class);
        startActivity (i);
    }
}

