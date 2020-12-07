package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Main_Page extends AppCompatActivity implements View.OnClickListener {
    FireBaseData data;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    DatabaseReference Reff;

    String ID;
    String First_Name;
    String Last_Name;
    String User_Name;
    String Email;
    String Phone;

    EditText Edit_Search;
    Button B_Challenge;
    Button B_Funny;
    Button B_Family;
    Button B_Scary;
    Button b_search;
    ImageView LogOut;
    ImageView B_Admin;
    ImageButton B_Profile1;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);

        //data
        data = new FireBaseData();
        Reff = FirebaseDatabase.getInstance().getReference();
        fAuth = data.getfAuth();


        Edit_Search = findViewById(R.id.Edit_Search);
        b_search = findViewById(R.id.b_search);
        b_search.setOnClickListener(this);
        B_Challenge = (Button) findViewById(R.id.new_challenge);
        B_Challenge.setOnClickListener(this);
        B_Funny = (Button) findViewById(R.id.MoveToFunny);
        B_Funny.setOnClickListener(this);
        B_Family = (Button) findViewById(R.id.MoveToFamily);
        B_Family.setOnClickListener(this);
        B_Scary = (Button) findViewById(R.id.MoveToScarry);
        B_Scary.setOnClickListener(this);
        B_Profile1 = findViewById(R.id.B_Profile);
        B_Profile1.setOnClickListener(this);
        LogOut = findViewById(R.id.singOut_mainpage);
        LogOut.setOnClickListener(this);
        B_Admin = findViewById(R.id.B_toAdmin);
        B_Admin.setOnClickListener(this);


        CheckIfAdmin();
    }

    @Override
    public void onClick(View view) {
    if ( view == B_Challenge){
        Intent intent = new Intent(Main_Page.this, new_challenge.class);
        startActivity(intent);
    }
    else if (view == B_Family){
            Intent intent = new Intent(Main_Page.this, Family.class);
            startActivity(intent);
        }
    else if (view == B_Funny){
        Intent intent = new Intent(Main_Page.this, Funny.class);
        startActivity(intent);
    }
    else if (view == B_Scary){
        Intent intent = new Intent(Main_Page.this, Scary.class);
        startActivity(intent);
    }
    else if (view == B_Profile1){
        Intent intent = new Intent(Main_Page.this, Profile.class);
        startActivity(intent);
    }
    else if(view == LogOut){
            Logout(view);
    }
    else if (view == B_Admin){
        Intent intent = new Intent(Main_Page.this, Admin.class);
        startActivity(intent);
    }
    else if (b_search == view){
        CheckifthereUser(view);
    }
    }


    private void CheckIfAdmin() {
        ID = fAuth.getCurrentUser().getUid();
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(ID);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String flag = snapshot.child("IsAdmin").getValue().toString();

                if ( flag.compareTo("yes") == 0){
                    B_Admin.setVisibility(View.VISIBLE);
                    //Toast.makeText(Main_Page.this, "" + flag, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CheckifthereUser(View view) {
        String User_Name_Search = Edit_Search.getText().toString();
        if(TextUtils.isEmpty(User_Name_Search)){
            Edit_Search.setError("UserName is Required.");
            return;
        }

        Reff.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                boolean flag = false;
              for(DataSnapshot snapshot : datasnapshot.getChildren()  ){
                  ID = snapshot.getKey();
                  First_Name = snapshot.child("First_Name").getValue(String.class);
                  Last_Name = snapshot.child("Last_Name").getValue(String.class);
                  User_Name = snapshot.child("User_Name").getValue(String.class);
                  Email = snapshot.child("Email").getValue(String.class);
                  Phone = snapshot.child("Phone").getValue(String.class);

                  if ( User_Name.equals(User_Name_Search) ){
                      flag = true;
                      Intent pro = new Intent(view.getContext() , Other_user_profile.class);
                      pro.putExtra( "ID" , ID);
                      pro.putExtra("First_Name" ,First_Name);
                      pro.putExtra("Last_Name", Last_Name);
                      pro.putExtra("Email", Email);
                      pro.putExtra("User_Name", User_Name);
                      pro.putExtra("Phone", Phone);
                      startActivity(pro);
                  }
              }
              if ( !flag){
                  Toast.makeText(Main_Page.this , "The user name is not exist " , Toast.LENGTH_LONG).show();
              }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void Logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext() , Login.class));
        finish();
    }
}