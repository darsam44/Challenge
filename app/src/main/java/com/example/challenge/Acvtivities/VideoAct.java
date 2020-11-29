package com.example.challenge.Acvtivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.challenge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VideoAct extends AppCompatActivity {

    //UI views
    FloatingActionButton addVideosBtu;
    //change actiobar title

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2);

        //change actiobar title
        setTitle("Videos");

        //init UI Views
        addVideosBtu = findViewById(R.id.VieosBoton);

        //handle Click
        addVideosBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start activiy to add videos
                startActivity(new Intent(VideoAct.this,AddVido.class));

            }
        });

    }
}