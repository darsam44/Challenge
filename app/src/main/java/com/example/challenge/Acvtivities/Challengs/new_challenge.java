package com.example.challenge.Acvtivities.Challengs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.challenge.Acvtivities.Activities.Main_Page;
import com.example.challenge.Acvtivities.Videos.AddVideoActivity;
import com.example.challenge.R;

import java.util.List;

public class new_challenge extends AppCompatActivity implements View.OnClickListener {
    List<String> categories;
    List<String> challenges;
    String info;
    ImageView home;
    Button addVideosBtu,VideoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_challenge);
        home = findViewById(R.id.image_home_challenge);
        home.setOnClickListener(this);

        //init UI Views
        addVideosBtu = findViewById(R.id.addVido);
        addVideosBtu.setOnClickListener(this);
        //handle Click
        VideoActivity =findViewById(R.id.VideoActivity);
        VideoActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if ( view == home){
            Intent intent = new Intent( new_challenge.this , Main_Page.class);
            startActivity(intent);
        }
        else if (view == addVideosBtu){
            startActivity(new Intent(new_challenge.this, AddVideoActivity.class));

        }
        else if (view == VideoActivity){
            startActivity(new Intent(new_challenge.this, com.example.challenge.Acvtivities.Videos.VideoActivity.class));

        }

    }
}