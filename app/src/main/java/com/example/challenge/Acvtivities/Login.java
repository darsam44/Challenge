package com.example.challenge.Acvtivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.challenge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button regi;
    Button Log;
    Button LogOut_b;
    EditText mEmail, mPassword;
    FirebaseAuth fAuth;

    String Email_log , Password_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        mEmail       =  (EditText) findViewById(R.id.EmailLogin);
        mPassword    =  (EditText)findViewById(R.id.PasswordLogin);
        regi = (Button) findViewById(R.id.b_registar);
        Log = (Button) findViewById(R.id.b_login);
        regi .setOnClickListener(this);
        Log.setOnClickListener(this);
        LogOut_b =  findViewById(R.id.Logout_b);
        LogOut_b.setOnClickListener(this);
    }

    public void Logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext() , Login.class));
        finish();
    }


    @Override
    public void onClick(View view) {
        if ( view == regi) {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        }
        else if ( view == Log){
            SingIn();
//            Intent intent = new Intent(Login.this, Main_Page.class);
//            startActivity(intent);
        }
        else if (view == LogOut_b){
            Logout(view);
        }
    }

    private void SingIn() {

        Email_log = mEmail.getText().toString().trim();
        Password_log  = mPassword.getText().toString().trim();


        if(TextUtils.isEmpty(Email_log)){
            mEmail.setError("Email is Required.");
            return;
        }
        if(TextUtils.isEmpty(Password_log)){
            mPassword.setError("Password is Required.");
            return;
        }
        if(Password_log.length() < 6){
            mPassword.setError("Password Must be >= 6 Characters");
            return;
        }

        fAuth.signInWithEmailAndPassword(Email_log, Password_log).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText( Login.this, "log in succesful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Main_Page.class)  );
                }
                else{
                    Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}