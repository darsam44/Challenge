package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText mFirstName,mLastName, mUserName, mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    ImageView home;

    FireBaseData data;
    FirebaseAuth fAuth;

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

        //data
        data = new FireBaseData();
        fAuth = data.getfAuth();

        mFirstName   =  (EditText) findViewById(R.id.FirstName);
        mLastName    =  (EditText) findViewById(R.id.LastName);
        mUserName    =  (EditText) findViewById(R.id.UserName);
        mEmail       =  (EditText) findViewById(R.id.Email);
        mPassword    =  (EditText)findViewById(R.id.Password);
        mPhone       =  (EditText) findViewById(R.id.PhoneNumber);
        mRegisterBtn =  findViewById(R.id.Register);
        home = findViewById(R.id.image_home_re);
        home.setOnClickListener(this);



        mRegisterBtn.setOnClickListener(this);

        // check if their is already connected user
        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), Login.class)  );
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

    boolean flag = true;
        if(TextUtils.isEmpty(Email)){
            mEmail.setError("Email is Required.");
            flag = false;
        }
        if(TextUtils.isEmpty(Last_Name)){
            mLastName.setError("LastName is Required.");
            flag = false;
        }
        if(TextUtils.isEmpty(First_Name)){
            mFirstName.setError("FirstName is Required.");
            flag = false;
        }
        if(TextUtils.isEmpty(User_Name)){
            mUserName.setError("UserName is Required.");
            flag = false;
        }
        if(TextUtils.isEmpty(Phone)){
            mPhone.setError("Phone is Required.");
            flag = false;
        }
        if(TextUtils.isEmpty(Password)){
            mPassword.setError("Password is Required.");
            flag = false;
        }
        if(Password.length() < 6){
            mPassword.setError("Password Must be >= 6 Characters");
            flag = false;
        }
        if ( !flag){
            return;
        }

        Query userNameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("User_Name").equalTo(User_Name);
        userNameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            if ( snapshot.getChildrenCount() >0){
                Toast.makeText(Register.this , "Choose a diffrent username already exist" , Toast.LENGTH_LONG).show();
            }
            else{
                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText( Register.this, "User created", Toast.LENGTH_SHORT).show();
                            ID = data.GetcurrentID();

                            //storing all the information of the users after completed the regiteration
                            Map<String, Object> user = new HashMap<>();
                            user.put("First_Name", First_Name);
                            user.put("Last_Name", Last_Name);
                            user.put("Email", Email);
                            user.put("User_Name", User_Name);
                            user.put("Phone", Phone);
                            user.put( "IsAdmin" , "no");

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            reference.child(ID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Video details added to database
                                    Toast.makeText(Register.this, "succseful to registar ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Login.class)  );

                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}