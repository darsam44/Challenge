package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    FireBaseData data;
    Button regi;
    Button Log;
    EditText mEmail, mPassword;
    TextView forgotPass;
    FirebaseAuth fAuth;

    String Email_log , Password_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // make new data object
        data = new FireBaseData();
        fAuth = data.getfAuth();


        mEmail =  (EditText) findViewById(R.id.EmailLogin);
        mPassword  =  (EditText)findViewById(R.id.PasswordLogin);
        regi = (Button) findViewById(R.id.b_registar);
        Log = (Button) findViewById(R.id.b_login);
        regi .setOnClickListener(this);
        Log.setOnClickListener(this);
        forgotPass = findViewById(R.id.ForgotPass);
        forgotPass.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), Main_Page.class)  );
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if ( view == regi) {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        }
        else if ( view == Log){
            SingIn();
        }
        else if (view ==forgotPass){
            ResetYourPass(view);
        }
    }

    private void ResetYourPass(View view) {
        EditText resetMail = new EditText((view.getContext()));
        AlertDialog.Builder passwordReset = new AlertDialog.Builder(view.getContext());
        passwordReset.setTitle("Reset Password?");
        passwordReset.setMessage("Enter Your Email To Recived Reset Link");
        passwordReset.setView(resetMail);
        passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //extract the email and send the link
               String mail = resetMail.getText().toString();
               fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                   Toast.makeText(Login.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(Login.this, "Error! Reset Link Not Sent To Your Email" + e.getMessage(), Toast.LENGTH_SHORT).show();

                   }
               }) ;
            }
        });
        passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        passwordReset.create().show();
    }

    private void SingIn() {
        Email_log = mEmail.getText().toString().trim();
        Password_log  = mPassword.getText().toString().trim();
        boolean flag = false;
        if(TextUtils.isEmpty(Email_log)){
            mEmail.setError("Email is Required.");
            flag = true;
        }
        if(TextUtils.isEmpty(Password_log)){
            mPassword.setError("Password is Required.");
            flag = true;
        }
        if(Password_log.length() < 6){
            mPassword.setError("Password Must be >= 6 Characters");
            flag = true;
        }
        if (flag) return;

        fAuth.signInWithEmailAndPassword(Email_log, Password_log).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText( Login.this, "log in succesful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Main_Page.class));
                }
                else{
                    Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}