package com.example.challenge.Acvtivities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.challenge.R;

public class EditProfile extends AppCompatActivity {
    String ID;
    String First_Name;
    String Last_Name;
    String User_Name;
    String Email;
    String Phone;

    EditText e_Last , e_First, e_Email , e_Phone , e_Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent dataFromProfile = getIntent();
        First_Name =dataFromProfile.getStringExtra("First_Name");
        Last_Name =dataFromProfile.getStringExtra("Last_Name");
        User_Name =dataFromProfile.getStringExtra("User_Name");
        Email =dataFromProfile.getStringExtra("Email");
        Phone =dataFromProfile.getStringExtra("Phone");

        e_First = findViewById(R.id.FirstName_p_Edit);
        e_Last = findViewById(R.id.LastName_p_Edit);
        e_Username = findViewById(R.id.UserName_p_Edit);
        e_Email = findViewById(R.id.Email_p_Edit);
        e_Phone = findViewById(R.id.Phone_p_Edit);

        e_First.setText(First_Name);
        e_Last.setText(Last_Name);
        e_Username.setText(User_Name);
        e_Email.setText(Email);
        e_Phone.setText(Phone);

    }
}