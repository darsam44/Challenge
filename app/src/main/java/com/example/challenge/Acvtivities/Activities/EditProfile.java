package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    ImageView BackHomeActivityEditProfile;
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

    EditText e_Last , e_First, e_Email , e_Phone , e_Username;
    Button saveEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //data
        data = new FireBaseData();
        fAuth = data.getfAuth();

        Intent dataFromProfile = getIntent();
        First_Name =dataFromProfile.getStringExtra("First_Name");
        Last_Name =dataFromProfile.getStringExtra("Last_Name");
        User_Name =dataFromProfile.getStringExtra("User_Name");
        Email =dataFromProfile.getStringExtra("Email");
        Phone =dataFromProfile.getStringExtra("Phone");
        IsAdmin = dataFromProfile.getStringExtra("IsAdmin");

        e_First = findViewById(R.id.FirstName_p_Edit);
        e_Last = findViewById(R.id.LastName_p_Edit);
        e_Username = findViewById(R.id.UserName_p_Edit);
        e_Email = findViewById(R.id.Email_p_Edit);
        e_Phone = findViewById(R.id.Phone_p_Edit);
        saveEdit = findViewById(R.id.b_save);

        e_First.setText(First_Name);
        e_Last.setText(Last_Name);
        e_Username.setText(User_Name);
        e_Email.setText(Email);
        e_Phone.setText(Phone);

        saveEdit.setOnClickListener(this);

        BackHomeActivityEditProfile =findViewById(R.id.BackHomeActivityEditProfile);
        BackHomeActivityEditProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if ( view == saveEdit){
        EditNameOnBAse(view);
        }
        if (view == BackHomeActivityEditProfile){
            Intent intent = new Intent(EditProfile.this, Main_Page.class);
            startActivity(intent);
        }


    }

    private void EditNameOnBAse(View view) {
        if (e_First.getText().toString().isEmpty() || e_Last.getText().toString().isEmpty() ||e_Username.getText().toString().isEmpty() ||
                e_Email.getText().toString().isEmpty() ||e_Phone.getText().toString().isEmpty() ){
            Toast.makeText(EditProfile.this , " ONE or more are empty fill them all" ,Toast.LENGTH_LONG).show();
            return;
        }
        ID = fAuth.getCurrentUser().getUid();
        First_Name = e_First.getText().toString();
        Last_Name = e_Last.getText().toString();
        User_Name = e_Username.getText().toString();
        Email = e_Email.getText().toString();
        Phone = e_Phone.getText().toString();

        Query userNameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("User_Name").equalTo(User_Name);
        userNameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int temp = (int) snapshot.getChildrenCount();
                if ( snapshot.getChildrenCount() >0){
                    Toast.makeText(EditProfile.this , "Choose a diffrent username" , Toast.LENGTH_LONG).show();
                }
                else{
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(ID);

                    Map<String, Object> user = new HashMap<>();
                    user.put("First_Name", First_Name);
                    user.put("Last_Name", Last_Name);
                    user.put("Email", Email);
                    user.put("User_Name", User_Name);
                    user.put("Phone", Phone);
                    user.put( "IsAdmin" , IsAdmin);

                    reference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(getApplicationContext(),Profile.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}