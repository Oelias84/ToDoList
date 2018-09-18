package com.project.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {


    //Remember me
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final String START_PREF = "startPref";
    private static final String REMEMBER_ME ="RememberMe";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";

    private CheckBox rememberMe;

    //Certification for login
    private EditText loginPass, loginEmail;
    private String email, pass;

    //Connection with fireBase
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;



    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance ();

    }

    public void moveToSecond() {
        Intent i = new Intent (this, SecondActivity.class);
        startActivity (i);
    }
    public void moveToSecond(View view) {
        Intent i = new Intent (this, SecondActivity.class);
        startActivity (i);
    }

    @SuppressLint({"InflateParams", "CommitPrefEdits"})
    public void login_RegisterAlert(View view) {


        //initialising Alert view
        view = getLayoutInflater ().inflate (R.layout.dailog_login_signup, null, false);

        rememberMe = view.findViewById (R.id.CB_RememberMe);
        loginEmail = view.findViewById (R.id.editTxtEmail);
        loginPass = view.findViewById (R.id.editTxtPass);
        progressBar = view.findViewById (R.id.progressBar);

        //getting prefs for rMe
        preferences = this.getSharedPreferences (START_PREF , Context.MODE_PRIVATE);
        editor = preferences.edit ();

        //getting remember me setting
        if (preferences.getBoolean (REMEMBER_ME, false))
            rememberMe.setChecked (true);
        else
            rememberMe.setChecked (false);

        loginEmail.setText (preferences.getString (KEY_EMAIL, ""));
        loginPass.setText (preferences.getString (KEY_PASS, ""));

        //Create Login - Register Alert
        AlertDialog alertDialog = new AlertDialog.Builder (this).setView (view)
                .setPositiveButton ("Login",null)
                .setNeutralButton ("Register", null).show ();

        //positive login btn
        alertDialog.getButton (alertDialog.BUTTON_POSITIVE).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                email = loginEmail.getText ().toString ();
                pass = loginPass.getText ().toString ();
                getWindow ().addFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                setRememberMe ();
                if (rememberMe.isChecked ()) {
                    editor.putString (KEY_EMAIL, email.trim ());
                    editor.putString (KEY_PASS, pass.trim ());
                } else {
                    editor.putString (KEY_EMAIL, "");
                    editor.putString (KEY_PASS, "");
                }
                login ();
            }
        });

        //neutral btn register
        alertDialog.getButton (alertDialog.BUTTON_NEUTRAL).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                email = loginEmail.getText ().toString ();
                pass = loginPass.getText ().toString ();
                getWindow ().addFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                setRememberMe ();
                if (rememberMe.isChecked ()) {
                    editor.putString (KEY_EMAIL, email);
                    editor.putString (KEY_PASS, pass);
                }
                registerUser ();
            }
        });
    }

    private void login() {
        if (!email.isEmpty () && !pass.isEmpty ()) {
            progressBar.setVisibility (View.VISIBLE);
            mAuth.signInWithEmailAndPassword (email, pass)
                    .addOnCompleteListener (this, new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility (View.GONE);
                            if (task.isSuccessful ()) {
                                setRememberMe ();
                                Log.d (TAG, "signInWithEmail:success");
                                Toast.makeText (getBaseContext (), "Login Successful", Toast.LENGTH_SHORT).show ();
                                getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                editor.apply ();
                                moveToSecond ();
                            } else {
                                getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                // If sign in fails, display a message to the user.
                                Log.w (TAG, "signInWithEmail:failure", task.getException ());
                                Toast.makeText (getBaseContext (), "Authentication failed.", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });
        } else {
            Toast.makeText (this, "Email/Password is Empty", Toast.LENGTH_SHORT).show ();
            progressBar.setVisibility (View.GONE);
        }

    }

    private void registerUser() {
        if (!email.isEmpty () && !pass.isEmpty ()) {
            progressBar.setVisibility (View.VISIBLE);
            mAuth.createUserWithEmailAndPassword (email, pass)
                    .addOnCompleteListener (this, new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility (View.INVISIBLE);
                            if (task.isSuccessful ()) {
                                getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText (getBaseContext (), "Create user with Email : Success", Toast.LENGTH_SHORT).show ();
                                moveToSecond ();
                                editor.apply ();
                            } else {
                                getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText (getBaseContext (), "Unsuccessful wrong details", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });
        } else {
            Toast.makeText (this, "Email/Password is Empty", Toast.LENGTH_SHORT).show ();
            getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }

    }

    private void setRememberMe() {
        editor.putBoolean (REMEMBER_ME, rememberMe.isChecked ());
        editor.apply ();
    }

    @Override
    protected void onDestroy() {
        editor.apply ();
        super.onDestroy ();
    }
}

