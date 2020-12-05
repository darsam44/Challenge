package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Main_Page extends AppCompatActivity implements View.OnClickListener {
    FireBaseData data;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;

    String ID;

    Button B_Challenge;
    Button B_Funny;
    Button B_Family;
    Button B_Scary;
    ImageView LogOut;
    ImageView B_Admin;
    ImageButton B_Profile1;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);

        //data
        data = new FireBaseData();

        fAuth = data.getfAuth();

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



//        DocumentReference df = fstore.collection("Users").document(ID);
//        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.d("TAG" , "onSuccess:" + documentSnapshot.getData());
//
//                if ( documentSnapshot.getString("IsAdmin") != null) {
//                    B_Admin.setVisibility(View.VISIBLE);
//                }
//            }
//        });


    }

    public void Logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext() , Login.class));
        finish();
    }
}