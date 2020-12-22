package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.challenge.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewChalengeOrCategory extends AppCompatActivity implements View.OnClickListener {
    ImageView plus_cat,BackHomeNewChalengeOrCategory,BackAdmin;
    EditText edit_cat;
    String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_chalenge_or_category);

        plus_cat = findViewById(R.id.plus_cat);
        edit_cat = findViewById(R.id.edit_cat);
        plus_cat.setOnClickListener(this);
        BackHomeNewChalengeOrCategory =findViewById(R.id.BackHomeNewChalengeOrCategory);
        BackHomeNewChalengeOrCategory.setOnClickListener(this);
        BackAdmin=findViewById(R.id.BackAdmin);
        BackAdmin.setOnClickListener(this);
}


    @Override
    public void onClick(View v) {
        if(v == plus_cat){
            AddCategoryAsAdmin(v);
            SystemClock.sleep(2000);
//            setTextForCheckboxes();
        }
        if (v == BackHomeNewChalengeOrCategory){
            Intent intent = new Intent(NewChalengeOrCategory.this, Main_Page.class);
            startActivity(intent);
        }
        if (v == BackAdmin) {
            Intent intent = new Intent(NewChalengeOrCategory.this, Admin.class);
            startActivity(intent);
        }


    }





    private void AddCategoryAsAdmin(View v) {
        category = edit_cat.getText().toString().trim();
        if(TextUtils.isEmpty(category)){
            edit_cat.setError("You must write a category!");
            return;
        }
        Query categoryQuery = FirebaseDatabase.getInstance().getReference().child("Categories").equalTo(category);
        categoryQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int temp = (int) snapshot.getChildrenCount();
                if (temp >0){
                    Toast.makeText(NewChalengeOrCategory.this, "Category Already Exist, Please Change", Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String, Object> Cat = new HashMap<>();
                    Cat.put("Chall" , "i");
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categories");
                    reference.child(category).setValue(Cat).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
//                            edit_chal.setText("");
                            Toast.makeText(NewChalengeOrCategory.this, "Category Successfully Added", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewChalengeOrCategory.this, "Category not Added", Toast.LENGTH_SHORT).show();

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