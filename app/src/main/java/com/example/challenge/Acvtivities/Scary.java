package com.example.challenge.Acvtivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.challenge.R;

import java.util.List;

public class Scary extends AppCompatActivity implements View.OnClickListener {
    List<String> categories;
    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scary);

        home = findViewById(R.id.image_home_scarry);
        home.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == home) {
            Intent intent = new Intent(Scary.this, Main_Page.class);
            startActivity(intent);
        }
    }
}