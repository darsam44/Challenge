package com.example.challenge.Acvtivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

import com.example.challenge.R;

public class Registar extends AppCompatActivity implements View.OnClickListener {
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

        home = findViewById(R.id.image_home_re);
        home.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == home){
            Intent intent = new Intent( Registar.this , Login.class);
            startActivity(intent);
        }
    }
}