package com.example.challenge.Acvtivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText mFirstName,mLastName, mUserName, mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ImageView home;

    String ID;
    String First_Name;
    String Last_Name;
    String User_Name;
    String Password;
    String Email;
    String Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        mFirstName   =  (EditText) findViewById(R.id.FirstName);
        mLastName    =  (EditText) findViewById(R.id.LastName);
        mUserName    =  (EditText) findViewById(R.id.UserName);
        mEmail       =  (EditText) findViewById(R.id.Email);
        mPassword    =  (EditText)findViewById(R.id.Password);
        mPhone       =  (EditText) findViewById(R.id.PhoneNumber);
        mRegisterBtn =  findViewById(R.id.Register);
        home = findViewById(R.id.image_home_re);
        home.setOnClickListener(this);

        fAuth = FirebaseAuth.getInstance();

        mRegisterBtn.setOnClickListener(this);

        // check if their is already connected user
        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),Login.class)  );
                finish();
        }


    }

    @Override
    public void onClick(View view) {
        if (view == home){
            Intent intent = new Intent( Register.this , Login.class);
            startActivity(intent);
        }
        else if (view == mRegisterBtn ){
            CheckIfEmpty();
        }
    }

    private void CheckIfEmpty() {

        Email = mEmail.getText().toString().trim();

        Password  = mPassword.getText().toString().trim();
        First_Name= mFirstName.getText().toString();
        Last_Name= mLastName.getText().toString();
        Phone    = mPhone.getText().toString();
        User_Name = mUserName.getText().toString();

        if(TextUtils.isEmpty(Email)){
            mEmail.setError("Email is Required.");
            return;
        }
        if(TextUtils.isEmpty(Last_Name)){
            mLastName.setError("LastName is Required.");
            return;
        }
        if(TextUtils.isEmpty(First_Name)){
            mFirstName.setError("FirstName is Required.");
            return;
        }
        if(TextUtils.isEmpty(User_Name)){
            mUserName.setError("UserName is Required.");
            return;
        }
        if(TextUtils.isEmpty(Phone)){
            mPhone.setError("Phone is Required.");
            return;
        }

        if(TextUtils.isEmpty(Password)){
            mPassword.setError("Password is Required.");
            return;
        }

        if(Password.length() < 6){
            mPassword.setError("Password Must be >= 6 Characters");
            return;
        }
        //register the user in firebase
        fAuth.createUserWithEmailAndPassword(this.Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                     Toast.makeText( Register.this, "User created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Login.class)  );
                }
                else{
                    Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                }

                }
        });

    }
}