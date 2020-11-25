package com.example.challenge.Acvtivities;

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
import android.widget.Toast;

import com.example.challenge.R;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    String ID;
    String First_Name;
    String Last_Name;
    String User_Name;
    String Password;
    String Email;
    String Phone;
    int ASK;
    int DO;
    int DECLINE;

    ImageView Profile;
    Button Choose;

    private static final int IMAGE_PICK_MODE = 1000;
    private static final int PREMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Profile = findViewById(R.id.image_profile);
        Choose = findViewById(R.id.choose_ima);
        Choose.setOnClickListener(this);

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