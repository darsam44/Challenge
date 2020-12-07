package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.DatabaseConfig;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    //UI views
    FloatingActionButton addVideosBtu;
    private RecyclerView videosRv;
    //change actionbar titleF
    private ArrayList<ModelVideo> videoArrayList;
    //adapter
    private AdapterVideo adapterVideo;
    //data
    FireBaseData data;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //data
        data = new FireBaseData();
        fAuth = data.getfAuth();

        //change actiobar title
        setTitle("Videos");

        //init UI Views
        addVideosBtu = findViewById(R.id.addVideosBtn);
        videosRv = findViewById(R.id.videosRv);


        //function call, loadvibes
        loadVideosFromFirebase();

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

    private void loadVideosFromFirebase() {
        videoArrayList = new ArrayList<>();
        String ID = fAuth.getCurrentUser().getUid();
        //db reffernce
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(ID).child("Videos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    ModelVideo modelVideo = ds.getValue(ModelVideo.class);
                    videoArrayList.add(modelVideo);
                }
                //setup adapter
                adapterVideo = new AdapterVideo(VideoActivity.this, videoArrayList);
                //set adapter to recyclerview
                videosRv.setAdapter(adapterVideo);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}