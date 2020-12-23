package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.Acvtivities.Videos.VideoActivity;
import com.example.challenge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Other_user_profile extends AppCompatActivity implements View.OnClickListener {
    String ID_search;
    String ID;
    String First_Name;
    String Last_Name;
    String User_Name;
    String Email;
    String Phone;
    String IsAdmin;

    //data
    FireBaseData data;
    FirebaseAuth fAuth;
    StorageReference storageReference;

    TextView T_first, T_last , T_Email , T_Phone , T_user_name;
    ImageView Profile_images, Home_Other;

    Button Video_Other_user_profile,add_friend;
    CheckBox Check_IsAdmin;

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
        add_friend=findViewById(R.id.add_friend);
        add_friend.setOnClickListener(this);
        Check_IsAdmin = findViewById(R.id.Check_IsAdmin);



        ID = data.GetcurrentID();

        Intent dataFromProfile = getIntent();
        ID_search = dataFromProfile.getStringExtra("ID");
        First_Name =dataFromProfile.getStringExtra("First_Name");
        Last_Name =dataFromProfile.getStringExtra("Last_Name");
        User_Name =dataFromProfile.getStringExtra("User_Name");
        Email =dataFromProfile.getStringExtra("Email");
        Phone =dataFromProfile.getStringExtra("Phone");
        IsAdmin = dataFromProfile.getStringExtra("IsAdmin");
        if (IsAdmin.equals("yes")){
            Check_IsAdmin.setChecked(true);
        }
        Check_IsAdmin.setOnClickListener(this);


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
        CheckIfAdmin();
    }



    @Override
    public void onClick(View view) {
        if (view == Home_Other) {
            Intent in = new Intent(Other_user_profile.this, Main_Page.class);
            startActivity(in);
        } else if (view == Video_Other_user_profile) {
            Intent pro = new Intent(view.getContext(), VideoActivity.class);
            pro.putExtra("ID", ID_search);
            startActivity(pro);
        } else if (view == Check_IsAdmin) {
            setAdmin();
        }
        if (view == add_friend) {
            AddFriend();
        }
    }

    private void AddFriend() {
        HashMap <String,Object> temp = new HashMap<>();
        temp.put("ID_User",ID_search);
        temp.put("User_Name",User_Name);
        temp.put("First_Name",First_Name);
        temp.put("Last_Name",Last_Name);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(ID).child("friend").child(ID_search).setValue(temp);
        //reference.child(ID_search).child("friend").child(ID).setValue(User_Name+":"+First_Name+" "+Last_Name);
        Toast.makeText(Other_user_profile.this , "add friend" , Toast.LENGTH_LONG).show();
    }

    private void setAdmin() {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(ID_search);
        if (Check_IsAdmin.isChecked() ){
            reff.child("IsAdmin").setValue("yes").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Other_user_profile.this , "this Profile is admin now" , Toast.LENGTH_LONG).show();
                }
            });
       }
       else {
            reff.child("IsAdmin").setValue("no").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Other_user_profile.this , "this Profile is not admin anymore" , Toast.LENGTH_LONG).show();
                }
            });
       }
    }

    // Load image from firebase to the imageview
    private void LoadImageProfile() {
        StorageReference profileRef = storageReference.child("Users/"+ID_search+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Profile_images);
            }
        });
    }

    private void CheckIfAdmin() {
        String Cur_ID = fAuth.getCurrentUser().getUid();
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(Cur_ID);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("IsAdmin").getValue() != null){
                    String flag = snapshot.child("IsAdmin").getValue().toString();

                    if ( flag.compareTo("yes") == 0){
                        Check_IsAdmin.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}