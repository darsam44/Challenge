package com.example.challenge.Acvtivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.challenge.R;

public class Registar extends AppCompatActivity {
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
    }
}