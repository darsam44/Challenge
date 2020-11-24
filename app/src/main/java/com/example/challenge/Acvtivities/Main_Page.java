package com.example.challenge.Acvtivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.challenge.R;

public class Main_Page extends AppCompatActivity implements View.OnClickListener {
    Button Chalege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);

        Chalege = (Button) findViewById(R.id.new_challenge);
        Chalege.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    if ( view == Chalege){
        Intent intent = new Intent(Main_Page.this, new_challenge.class);
        startActivity(intent);
    }
    }
}