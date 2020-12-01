package com.example.challenge.Acvtivities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.challenge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VideoActivity extends AppCompatActivity {

    //UI views
    FloatingActionButton addVideosBtu;
    //change actionbar titleF

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //change actiobar title
        setTitle("Videos");

        //init UI Views
        addVideosBtu = findViewById(R.id.addVideosBtn);

        //handle Click
        addVideosBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start activiy to add videos
                if (v==addVideosBtu) {
                    startActivity(new Intent(VideoActivity.this, AddVideoActivity.class));
                }
            }
        });

    }
}