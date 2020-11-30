package com.example.challenge.Acvtivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.challenge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddVidoe extends AppCompatActivity {

    //actionbar
    private ActionBar actionBar;

    //UI Views
    private EditText titleET;
    private VideoView videoView;
    private Button uploadVideoBtn;
    private FloatingActionButton pickVideoFad;

    private  static  final int VIDEO_PICK_GALLERY_code   =100;
    private  static  final int VIDEO_PICK_Cmara_code  =101;
    private  static  final int Cmara_REQUEST_code=102;

    private  String [] cameraP;
    private  Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        // init actionbar
        actionBar = getSupportActionBar();

        //title
        actionBar.setTitle("add new Video");
        //add back Button
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //init UI Views
        titleET = findViewById(R.id.titleET);
        videoView = findViewById(R.id.videoView);
        uploadVideoBtn = findViewById(R.id.uploadVideoBtn);
        pickVideoFad = findViewById(R.id.pickVideoFad);


        //Camera Permissions
        cameraP= new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_APN_SETTINGS};

        uploadVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        uploadVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPickDialog();
            }
        });
    }

    private void videoPickDialog() {
        String [] options ={"Camera","Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Video from").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    //Camera
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }else {
                        videoPickCamera();
                    }
                }
                else if(i==1){
                    //gllery
                    videoPickGallery();
                }
            }
        }).show();
    }


    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraP,VIDEO_PICK_Cmara_code);
    }
    private boolean checkCameraPermission(){
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean result2 = ContextCompat.checkSelfPermission(this,Manifest.permission.WAKE_LOCK)== PackageManager.PERMISSION_GRANTED;

        return result1 && result2;
    }


    private  void videoPickGallery(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select videos"),VIDEO_PICK_GALLERY_code);
    }

    private  void videoPickCamera(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(Intent.createChooser(intent,"select videos"),VIDEO_PICK_GALLERY_code);
    }

    private void setVideoToVideoView(){
        MediaController mediaController =new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);

        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.setOnPreparedListener((new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.pause();
            }
        }));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Cmara_REQUEST_code:
                if(grantResults.length>0){
                    boolean camreAcc  = grantResults[0] == getPackageManager().PERMISSION_GRANTED;
                    boolean storageAcc = grantResults[1] == getPackageManager().PERMISSION_GRANTED;
                    if(camreAcc&&storageAcc){
                        //both PERMISSIONs allowed
                        videoPickCamera();
                    }
                    else{
                        //both or one of those denied
                        Toast.makeText(this,"Camera Storage",Toast.LENGTH_SHORT).show();

                    }

                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
            if(requestCode == VIDEO_PICK_GALLERY_code){
                videoUri = data.getData();
                setVideoToVideoView();
            }
            else if (requestCode == VIDEO_PICK_Cmara_code){
                videoUri = data.getData();
                setVideoToVideoView();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go to previous activity on clic back button on actionbar
        return super.onSupportNavigateUp();
    }
}