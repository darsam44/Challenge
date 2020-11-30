package com.example.challenge.Acvtivities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.challenge.R;

import java.util.List;

public class new_challenge extends AppCompatActivity implements View.OnClickListener {
    List<String> categories;
    List<String> challenges;
    String info;
    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_challenge);
        home = findViewById(R.id.image_home_challenge);
        home.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if ( view == home){
            Intent intent = new Intent( new_challenge.this , Main_Page.class);
            startActivity(intent);
        }
    }
}