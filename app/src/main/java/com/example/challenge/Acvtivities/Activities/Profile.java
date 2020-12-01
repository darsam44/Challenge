package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    String ID;
    String First_Name;
    String Last_Name;
    String User_Name;
    String Email;
    String Phone;
    FirebaseAuth Fauf;
    FirebaseFirestore FStore;
    int ASK;
    int DO;
    int DECLINE;;
    FireBaseData data;
    ImageView Profile;
    TextView First_Name_t , Last_Name_t , Email_t , UserName_t , Phone_t;
    Button Choose,addVideosBtu;

    private static final int IMAGE_PICK_MODE = 1000;
    private static final int PREMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //data
        data = new FireBaseData();
        FStore = data.getFstore();
        Fauf = data.getfAuth();

        // for pick image
        Profile = findViewById(R.id.image_profile);
        Choose = findViewById(R.id.choose_ima);
        Choose.setOnClickListener(this);

        // for profile text
        Last_Name_t = findViewById(R.id.LastName_p);
        First_Name_t = findViewById(R.id.FirstName_p);
        Email_t = findViewById(R.id.Email_p);
        Phone_t = findViewById(R.id.Phone_p);
        UserName_t = findViewById(R.id.UserName_p);


        ID = Fauf.getCurrentUser().getUid();
        System.out.println(ID);

        DocumentReference DoucStore = FStore.collection("Users").document(ID);
        DoucStore.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                First_Name_t.setText(value.getString("First_Name"));
                Toast.makeText(Profile.this, "" + value.getString("First_Name"), Toast.LENGTH_SHORT).show();
                Last_Name_t.setText(value.getString("Last_Name"));
                Email_t.setText(value.getString("Email"));
                Phone_t.setText(value.getString("Phone"));
                UserName_t.setText(value.getString("User_Name"));
            }
        });

        //init UI Views
        addVideosBtu = findViewById(R.id.addVido);
        addVideosBtu.setOnClickListener(this);
        //handle Click



    }

    @Override
    public void onClick(View view) {
        if (view == Choose){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    // premission not granted ask for it
                    String [] premission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    //show pop up for runtime premission
                    requestPermissions(premission , PREMISSION_CODE);
                }
                else{
                    // premission already granted
                    PickImageFromGallery();
                }
            }
            else{
                // system os is less the marshmelo
                PickImageFromGallery();
            }
        }
        if (view == addVideosBtu){
            startActivity(new Intent(Profile.this, AddVideoActivity.class));
        }


    }

    private void PickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent , IMAGE_PICK_MODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PREMISSION_CODE:{
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    PickImageFromGallery();
                }
                else{
                    //permission was denied
                    Toast.makeText(this, "Permission denied" , Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //handle resulet of image pick
    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && resultCode == IMAGE_PICK_MODE) {
            //set image to ImageView
            Profile.setImageURI(data.getData());
        }
    }
}