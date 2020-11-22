package com.example.challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.challenge.Acvtivities.Main_Page;
import com.example.challenge.Acvtivities.Registar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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
            Intent intent = new Intent(MainActivity.this, Registar.class);
            startActivity(intent);
        }
        else if ( view == Log){
            Intent intent = new Intent(MainActivity.this, Main_Page.class);
            startActivity(intent);
        }
    }
}