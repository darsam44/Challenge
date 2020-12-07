package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.DatabaseMetaData;
import java.util.HashMap;

public class AddVideoActivity extends AppCompatActivity {

    FireBaseData data;
    //FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    String ID;
    //actionbar
    private ActionBar actionBar;

    //UI Views
    private EditText titleET;
    private VideoView videoView;
    private Button uploadVideoBtn;
    private FloatingActionButton pickVideoFad;

    private  static  final int VIDEO_PICK_GALLERY_code   =100;
    private  static  final int VIDEO_PICK_CAmara_code =101;
    private  static  final int CAMERA_REQUEST_code =102;

    private  String [] cameraPermission;
    private  Uri videoUri = null;
    private ProgressDialog progressDialog;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vido);

        //data
        data = new FireBaseData();
        //fstore = data.getFstore();
        fAuth = data.getfAuth();

        // init actionbar
        actionBar = getSupportActionBar();

        //title
        //actionBar.setTitle("Add New Video");
        //add back Button
        //actionBar.setDisplayShowHomeEnabled(true);
       // actionBar.setDisplayHomeAsUpEnabled(true);
        //init UI Views
        titleET = findViewById(R.id.titleET);
        videoView = findViewById(R.id.videoView);
        uploadVideoBtn = findViewById(R.id.uploadVideoBtn);
        pickVideoFad = findViewById(R.id.pickVideoFad);


        //setup progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Upload Video");
        progressDialog.setCanceledOnTouchOutside(false);

        //Camera Permissions
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        uploadVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleET.getText().toString().trim();
                if(TextUtils.isEmpty(title)){
                    Toast.makeText(AddVideoActivity.this,"Title is required...", Toast.LENGTH_SHORT).show();
                }
                else if(videoUri==null){
                    // video is not picked
                    Toast.makeText(AddVideoActivity.this,"Pick a video before you can uplode...", Toast.LENGTH_SHORT).show();

                }
                else{
                    //upload video function
                    uploadVideoFirebase();
                }            }
        });


        pickVideoFad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                videoPickDialog();
            }
        });
    }

    private void uploadVideoFirebase() {
        progressDialog.show();
        ID = fAuth.getCurrentUser().getUid();
        //timestamp
        String timestamp = ""+System.currentTimeMillis();

        //file path and name in firebase storage
        String filePathAndName = "Users_Videos/"+ID+ "video_" + timestamp;

        //storage refernce
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);

        //uploadvideo, you can upload any type using this method
        storageReference.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //video uploaded, get url of uploaded video
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());
                Uri downloadUri = uriTask.getResult();
                if(uriTask.isSuccessful()){
                    //url of uploaded video is recieved

                    //now we can video details to our firebase
                    HashMap<String, Object> hashmap = new HashMap<>();
                    hashmap.put("title", "" + title);
                    hashmap.put("timestamp", "" +timestamp);
                    hashmap.put("videoUrl","" + downloadUri);


                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.child(ID).child("Videos").child(timestamp).setValue(hashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Video details added to database
                            progressDialog.dismiss();
                            Toast.makeText(AddVideoActivity.this, "Video Uploaded... ", Toast.LENGTH_SHORT).show();

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddVideoActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed uploding to storage
                progressDialog.dismiss();
                Toast.makeText(AddVideoActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        ActivityCompat.requestPermissions(this, cameraPermission,CAMERA_REQUEST_code);
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
        startActivityForResult(intent,VIDEO_PICK_CAmara_code);
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
            case CAMERA_REQUEST_code:
                if(grantResults.length>0){
                    boolean camreAcc  = grantResults[0] == getPackageManager().PERMISSION_GRANTED;
                    boolean storageAcc = grantResults[1] == getPackageManager().PERMISSION_GRANTED;
                    if(camreAcc&&storageAcc){
                        //both PERMISSIONs allowed
                        videoPickCamera();
                    }
                    else{
                        //both or one of those denied
                        Toast.makeText(this,"Camera & Storage Pemission are required",Toast.LENGTH_SHORT).show();

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
        else if (requestCode == VIDEO_PICK_CAmara_code){
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