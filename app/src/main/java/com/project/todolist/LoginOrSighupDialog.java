package com.project.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import static android.content.ContentValues.TAG;

public class LoginOrSighupDialog extends DialogFragment {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static final String PREFS = "PrefsFile";
    boolean rMe ;

    private View view;
    private EditText loginPass, loginEmail;

    private String email, pass;
    private CheckBox rememberMe;
    private FirebaseAuth mAuth ;
    private ProgressBar progressBar;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        viewInit ();

        rMe = preferences.getBoolean ("RememberMe",true);
        if (rMe){
            loginEmail.setText (preferences.getString ("email",null));
            loginPass.setText (preferences.getString ("pass",null));
            login ();

        }

        AlertDialog.Builder alert = new AlertDialog.Builder (getActivity ());
        alert.setView (view);
        alert.setPositiveButton ("Login", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                email = loginEmail.getText ().toString ();
                pass = loginPass.getText ().toString ();
                progressBar.setVisibility (View.VISIBLE);
                getActivity ().getWindow ().addFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                setRememberMe ();
                if (rMe){
                    editor.putString ("email",email);
                    editor.putString ("pass",pass);
                }
                login ();
            }
        }).setNegativeButton ("Register", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                email = loginEmail.getText ().toString ();
                pass = loginPass.getText ().toString ();
                progressBar.setVisibility (View.VISIBLE);
                getActivity ().getWindow ().addFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                registerUser ();
            }
        });
        return alert.create ();
    }

    public void setRememberMe(){
        rMe = preferences.getBoolean ("RememberMe", rememberMe.isChecked ());
        editor.putBoolean ("RememberMe", rMe);
        editor.apply ();
    }



    private void viewInit(){

        preferences = getActivity ().getPreferences (Context.MODE_PRIVATE);
        editor = preferences.edit ();
        view = getActivity ().getLayoutInflater ().inflate (R.layout.dailog_login_signup,null);

        rememberMe = view.findViewById (R.id.CB_RememberMe);
        loginEmail = view.findViewById (R.id.editTxtEmail);
        loginPass = view.findViewById (R.id.editTxtPass);

        /*
        btnLogin = view.findViewById (R.id.loginBtn);
        btnRegister = view.findViewById (R.id.registerBtn);
        */

        mAuth = FirebaseAuth.getInstance ();
        progressBar = view.findViewById (R.id.progressBar);



        /*btnLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

            }
        });

        btnRegister.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {


            }
        });*/

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
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                                getActivity ().getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                editor.apply ();
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
            Toast.makeText (getContext (), "Email/Password is Empty", Toast.LENGTH_SHORT).show ();
            progressBar.setVisibility (View.GONE);
        }

    }

    @Override
    public void onDestroy() {
        getActivity ().getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        super.onDestroy ();
    }

    public void moveToTodo() {
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
                                Toast.makeText (getContext (), "Unsuccessful wrong details", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });
        }else {
            Toast.makeText (getContext (), "Email/Password is Empty", Toast.LENGTH_SHORT).show ();
            progressBar.setVisibility (View.GONE);
            getActivity ().getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }

    }

}
