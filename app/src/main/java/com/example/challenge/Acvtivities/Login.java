package com.example.challenge.Acvtivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.challenge.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button regi;
    Button Log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regi = (Button) findViewById(R.id.b_registar);
        Log = (Button) findViewById(R.id.b_login);
        regi .setOnClickListener(this);
        Log.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if ( view == regi) {
            Intent intent = new Intent(Login.this, Registar.class);
            startActivity(intent);
        }
        else if ( view == Log){
            Intent intent = new Intent(Login.this, Main_Page.class);
            startActivity(intent);
        }
    }
}