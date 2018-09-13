package com.project.todolist;

import android.content.Intent;
import android.os.Bundle;


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    private LoginOrSighupDialog loginOrSighupDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginOrSighupDialog = new LoginOrSighupDialog ();

        loginOrSighupDialog.show (getFragmentManager (),"start");


    }

    public void btnSecond(View view)  {

    }

    public void dialog1(View view) {
        loginOrSighupDialog.show (getFragmentManager (),"start");

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

