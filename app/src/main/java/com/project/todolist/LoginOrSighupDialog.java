package com.project.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class LoginOrSighupDialog extends DialogFragment {

    private View view;
    private EditText loginPass, loginEmail;
    private Button btnLogin, btnRegister;
    private String email, pass;
    private FirebaseAuth mAuth ;
    private ProgressBar progressBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        viewInit ();
        AlertDialog.Builder alert = new AlertDialog.Builder (getActivity ());
        alert.setView (view);
        return alert.create ();
    }



    private void viewInit(){
        view = getActivity ().getLayoutInflater ().inflate (R.layout.dailogloginsignup,null);
        loginEmail = view.findViewById (R.id.editTxtEmail);
        loginPass = view.findViewById (R.id.editTxtPass);
        btnLogin = view.findViewById (R.id.loginBtn);
        btnRegister = view.findViewById (R.id.registerBtn);
        mAuth = FirebaseAuth.getInstance ();
        progressBar = view.findViewById (R.id.progressBar);


        btnLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                email = loginEmail.getText ().toString ();
                pass = loginPass.getText ().toString ();
                progressBar.setVisibility (View.VISIBLE);
                getActivity ().getWindow ().addFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                login ();
            }
        });

        btnRegister.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                email = loginEmail.getText ().toString ();
                pass = loginPass.getText ().toString ();
                progressBar.setVisibility (View.VISIBLE);
                getActivity ().getWindow ().addFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                registerUser ();

            }
        });

    }

    private void login() {
        if (!email.isEmpty () && !pass.isEmpty ()){
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(getActivity (), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility (View.GONE);

                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(getContext (), "Successful", Toast.LENGTH_SHORT).show();
                                getActivity ().getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                moveToTodo ();

                            } else {
                                progressBar.setVisibility (View.GONE);
                                getActivity ().getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getContext (), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            Toast.makeText (getContext (), "Please fill in data", Toast.LENGTH_SHORT).show ();
            progressBar.setVisibility (View.GONE);
        }

    }

    private void moveToTodo() {
        Intent i = new Intent (getContext (),SecondActivity.class);
        startActivity(i);
    }

    public void registerUser() {
        if (!email.isEmpty () && !pass.isEmpty ()){
            mAuth.createUserWithEmailAndPassword (email,pass)
                    .addOnCompleteListener (getActivity (), new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful ()){
                                getActivity ().getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText (getContext (), "Create user with Email : Success", Toast.LENGTH_SHORT).show ();
                                progressBar.setVisibility (View.GONE);
                                moveToTodo ();
                            }else {
                                getActivity ().getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                progressBar.setVisibility (View.GONE);
                                Toast.makeText (getContext (), "UnSuccessful", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });
        }else {
            Toast.makeText (getContext (), "Please fill in data", Toast.LENGTH_SHORT).show ();
            progressBar.setVisibility (View.GONE);
            getActivity ().getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }

    }

}
