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
import android.widget.Toast;

import com.example.challenge.Acvtivities.Challengs.new_challenge;
import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.Acvtivities.Videos.VideoActivityChallenge;
import com.example.challenge.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Main_Page extends AppCompatActivity implements View.OnClickListener {
    FireBaseData data;
    FirebaseAuth fAuth;
    DatabaseReference reference_Users;


    String ID;
    String First_Name;
    String Last_Name;
    String User_Name;
    String Email;
    String Phone;
    String IsAdmin;

    EditText Edit_Search;
    Button B_Challenge;
    Button B_Funny;
    Button B_Family;
    Button B_Scary;
    Button B_Excited;
    Button B_Profile1;
    Button LogOut;
    ImageView b_search;
    ImageView B_Admin;

     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);

        //data
        data = new FireBaseData();
        reference_Users = data.getReference_Users();
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
        B_Excited = findViewById(R.id.MoveToExcited);
        B_Excited.setOnClickListener(this);
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
        if (view == B_Challenge) {
            Intent intent = new Intent(Main_Page.this, new_challenge.class);
            startActivity(intent);
        } else if (view == B_Family) {
            B_Family.getText().toString().trim();
            Intent pro = new Intent(view.getContext(), VideoActivityChallenge.class);
            pro.putExtra("type", B_Family.getText().toString().trim());
            startActivity(pro);
        } else if (view == B_Funny) {
            Intent pro = new Intent(view.getContext(), VideoActivityChallenge.class);
            pro.putExtra("type", B_Funny.getText().toString().trim());
            startActivity(pro);
        } else if (view == B_Scary) {
            Intent pro = new Intent(view.getContext(), VideoActivityChallenge.class);
            pro.putExtra("type", B_Scary.getText().toString().trim());
            startActivity(pro);
        } else if (view == B_Excited) {
            Intent pro = new Intent(view.getContext(), VideoActivityChallenge.class);
            pro.putExtra("type", B_Excited.getText().toString().trim());
            startActivity(pro);
        }
        else if (view == B_Profile1) {
            Intent intent = new Intent(Main_Page.this, Profile.class);
            startActivity(intent);
        }else if (view == LogOut) {
            Logout();
        } else if (view == B_Admin) {
            Intent intent = new Intent(Main_Page.this, Admin.class);
            startActivity(intent);
        } else if (b_search == view) {
            CheckifthereUser(view);
        }
    }


    private void CheckIfAdmin() {
        ID = fAuth.getCurrentUser().getUid();
        if ( ID == null){
            Logout();
        }
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(ID);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("IsAdmin").getValue() != null){
                    String flag = snapshot.child("IsAdmin").getValue().toString();

                    if ( flag.compareTo("yes") == 0){
                        B_Admin.setVisibility(View.VISIBLE);
                        //Toast.makeText(Main_Page.this, "" + flag, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Logout();
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

        reference_Users.addListenerForSingleValueEvent(new ValueEventListener() {
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
                  IsAdmin = snapshot.child("IsAdmin").getValue(String.class);

                  if ( User_Name.equals(User_Name_Search) ){
                      flag = true;
                      Intent pro = new Intent(view.getContext() , Other_user_profile.class);
                      pro.putExtra( "ID" , ID);
                      pro.putExtra("First_Name" ,First_Name);
                      pro.putExtra("Last_Name", Last_Name);
                      pro.putExtra("Email", Email);
                      pro.putExtra("User_Name", User_Name);
                      pro.putExtra("Phone", Phone);
                      pro.putExtra("IsAdmin" , IsAdmin);
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

    public void Logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext() , Login.class));
        finish();
    }
}