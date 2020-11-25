package com.example.challenge.Acvtivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.challenge.R;

public class Main_Page extends AppCompatActivity implements View.OnClickListener {
    Button B_Challenge;
    Button B_Funny;
    Button B_Family;
    Button B_Scary;
    ImageButton B_Profile1;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);

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
    }
}