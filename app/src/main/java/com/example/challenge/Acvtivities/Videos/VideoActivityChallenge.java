package com.example.challenge.Acvtivities.Videos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.challenge.Acvtivities.Activities.Main_Page;
import com.example.challenge.Acvtivities.Activities.Profile;
import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class VideoActivityChallenge extends AppCompatActivity implements View.OnClickListener {
    //UI views
    FloatingActionButton addVideosBtu;
    private RecyclerView videosRv;
    //change actionbar titleF
    private ArrayList<ModelVideo> videoArrayList;
    //adapter
    private AdapterVideo adapterVideo;

    String typi;
    ImageView P_Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //data
//        data = new FireBaseData();
//        fAuth = data.getfAuth();

        P_Home = findViewById(R.id.P_home_Video_challenge);
        P_Home.setOnClickListener(this);

        setTitle("Videos");

        //init UI Views
        videosRv = findViewById(R.id.videosRv);


        //function call, loadvibes
        loadVideosFromFirebase();
    }
    //handle Click
    public void onClick(View v){
        // start activiy to add videos
        if (P_Home == v) {
            Intent in = new Intent(VideoActivityChallenge.this, Main_Page.class);
            startActivity(in);
        }
    }

    private void loadVideosFromFirebase() {
        videoArrayList = new ArrayList<>();
        Intent dataFromProfile = getIntent();
        typi = dataFromProfile.getStringExtra("tyip");
        if (!typi.isEmpty()) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories").child(typi);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //get data
                        ModelVideo modelVideo = ds.getValue(ModelVideo.class);
                        videoArrayList.add(modelVideo);
                    }
                    //setup adapter
                    adapterVideo = new AdapterVideo(VideoActivityChallenge.this, videoArrayList);
                    //set adapter to recyclerview
                    videosRv.setAdapter(adapterVideo);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }
}