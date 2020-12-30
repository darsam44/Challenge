package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.Acvtivities.Videos.VideoActivity;
import com.example.challenge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    String ID;
    String First_Name;
    String Last_Name;
    String User_Name;
    String Email;
    String Phone;
    FirebaseAuth Fauf;
    FireBaseData data;
    StorageReference storageReference;

    ImageView Profile_images;
    TextView First_Name_t , Last_Name_t , Email_t , UserName_t , Phone_t;
    Button Choose , Edit_Text;
    Button Video_Profole;
    Button B_DelteProfile;
    Button seeFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //data
        data = new FireBaseData();
        Fauf = data.getfAuth();
        storageReference = FirebaseStorage.getInstance().getReference();

        // Video_Profole//
        Video_Profole= findViewById(R.id.Video_Profole);
        Video_Profole.setOnClickListener(this);

        // for pick image
        Profile_images = findViewById(R.id.image_profile);
        Choose = findViewById(R.id.choose_ima);
        Choose.setOnClickListener(this);

        // for profile text
        Last_Name_t = findViewById(R.id.LastName_p);
        First_Name_t = findViewById(R.id.FirstName_p);
        Email_t = findViewById(R.id.Email_p);
        Phone_t = findViewById(R.id.Phone_p);
        UserName_t = findViewById(R.id.UserName_p);
        Edit_Text = findViewById(R.id.b_EditText);
        Edit_Text.setOnClickListener(this);
        B_DelteProfile = findViewById(R.id.b_delete);
        B_DelteProfile.setOnClickListener(this);
        seeFriend = findViewById(R.id.seeFriend);
        seeFriend.setOnClickListener(this);

        ID = Fauf.getCurrentUser().getUid();
        LoadImageProfile();
        ShowAllRefernce();

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.I_Logout: Logout();
                return true;
            case R.id.I_Main_page:{
                Intent intent = new Intent(Profile.this, Main_Page.class);
                startActivity(intent);
            }
            return true;
            case R.id.I_My_Profile:{
                Intent intent = new Intent(Profile.this, Profile.class);
                startActivity(intent);
            }
            return true;
            case R.id.I_My_Friends:{
                Intent intent = new Intent(Profile.this, _Friend.class);
                startActivity(intent);
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void Logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext() , Login.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        if ( Choose == view){
            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGalleryIntent , 1000);
        }
        else if ( view == Edit_Text){
            SendToEditProfile(view);
        }
        else if (view == Video_Profole){
            startActivity(new Intent(Profile.this, VideoActivity.class));
        }
        else if ( B_DelteProfile == view){
            Delete_Profile(view);
        }
        else if (view == seeFriend) {
            startActivity(new Intent(Profile.this, _Friend.class));
        }
    }

    private void Delete_Profile(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Profile.this);
        dialog.setTitle("Are you sure you want to delete your profile?");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users");
                reff.child(ID).removeValue();
                Fauf.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Profile.this , "this Profile is delete" , Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this , "this Profile is not delete" , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

    }

    private void ShowAllRefernce() {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(ID);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                First_Name = snapshot.child("First_Name").getValue().toString();
                Last_Name =  snapshot.child("Last_Name").getValue().toString();
                Email =  snapshot.child("Email").getValue().toString();
                User_Name = snapshot.child("User_Name").getValue().toString();
                Phone = snapshot.child("Phone").getValue().toString();

                First_Name_t.setText(First_Name);
                Last_Name_t.setText(Last_Name);
                Email_t.setText(Email);
                UserName_t.setText(User_Name);
                Phone_t.setText(Phone);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SendToEditProfile(View view) {
        Intent pro = new Intent(view.getContext() , EditProfile.class);
        pro.putExtra("First_Name" ,First_Name);
        pro.putExtra("Last_Name", Last_Name);
        pro.putExtra("Email", Email);
        pro.putExtra("User_Name", User_Name);
        pro.putExtra("Phone", Phone);
        startActivity(pro);
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

    //handle resulet of image pick
    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                //set image to ImageView
                Uri imageUri = data.getData(); // get the URI of the image
                uploadImageToFireBase(imageUri); // upload the URI to firebAse
            }
        }
    }

    private void uploadImageToFireBase(Uri imageUri) {
        //upload the image to FireBase Storge
        StorageReference fileRef = storageReference.child("Users/"+ID+"profile.jpg"); // save the image im the Users-> ID -> profile.jpg
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
           fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
                   Picasso.get().load(uri).into(Profile_images); // put the image at imageview
               }
           });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this , " Fail uploaded Image" , Toast.LENGTH_LONG).show();
            }
        });
    }
}