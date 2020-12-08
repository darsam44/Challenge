package com.example.challenge.Acvtivities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Other_user_profile extends AppCompatActivity implements View.OnClickListener {
    String ID;
    String First_Name;
    String Last_Name;
    String User_Name;
    String Email;
    String Phone;

    //data
    FireBaseData data;
    FirebaseAuth fAuth;
    StorageReference storageReference;

    TextView T_first, T_last , T_Email , T_Phone , T_user_name;
    ImageView Profile_images, Home_Other;

    Button Video_Other_user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        //data
        data = new FireBaseData();
        fAuth = data.getfAuth();
        storageReference = FirebaseStorage.getInstance().getReference();

        Home_Other= findViewById(R.id.Home_Other);
        Home_Other.setOnClickListener(this);
        Profile_images = findViewById(R.id.O_image_profile);

        // Video_Other_user_profile//
        Video_Other_user_profile=findViewById(R.id.Video_Other_user_profile);
        Video_Other_user_profile.setOnClickListener(this);


        Intent dataFromProfile = getIntent();
        ID = dataFromProfile.getStringExtra("ID");
        First_Name =dataFromProfile.getStringExtra("First_Name");
        Last_Name =dataFromProfile.getStringExtra("Last_Name");
        User_Name =dataFromProfile.getStringExtra("User_Name");
        Email =dataFromProfile.getStringExtra("Email");
        Phone =dataFromProfile.getStringExtra("Phone");

        T_first = findViewById(R.id.O_FirstName_p);
        T_last = findViewById(R.id.O_LastName_p);
        T_Email = findViewById(R.id.O_Email_p);
        T_Phone = findViewById(R.id.O_Phone_p);
        T_user_name = findViewById(R.id.O_UserName_p);

        T_first.setText(First_Name);
        T_last.setText(Last_Name);
        T_Email.setText(Email);
        T_user_name.setText(User_Name);
        T_Phone.setText(Phone);
        LoadImageProfile();
    }

    @Override
    public void onClick(View view) {
        if (view == Home_Other){
            Intent in = new Intent(Other_user_profile.this , Main_Page.class);
            startActivity(in);
        }
        else if (view ==Video_Other_user_profile ){
            startActivity(new Intent(Other_user_profile.this, VideoActivity.class));
        }
    }

    // Load image from firebase to the imageview
    private void LoadImageProfile() {
        StorageReference profileRef = storageReference.child("Users/"+ID+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Profile_images);
            }
        });
    }
}