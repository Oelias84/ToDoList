package com.project.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog1 (new View (this));


    }


    public void btnSecond(View view)  {

        Intent i = new Intent (this,SecondActivity.class);
        startActivity(i);

    }

    public void dialog1(View view) {

        AlertDialog dialog = new AlertDialog.Builder(this).create();

        View dialogView = getLayoutInflater().inflate(R.layout.dailogloginsignup, null, false);
        dialog.setView(dialogView); //set as dialog's view


        dialogView.findViewById(R.id.loginBtn).setOnClickListener(new Dialog2Listener(dialog));
        dialogView.findViewById(R.id.registerBtn).setOnClickListener(new Dialog2Listener(dialog));


        dialog.show();

    }

    private class Dialog2Listener implements View.OnClickListener{
        private AlertDialog dialog;

        public Dialog2Listener(AlertDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View view) { //when login/register button is clicked
            String msg = view.getId() == R.id.loginBtn ? "Login" : "Register";
            if (msg.contains ("Login")){
                btnSecond (view);
            }
            dialog.dismiss(); //dismiss alert dialog
        }
    }


}

