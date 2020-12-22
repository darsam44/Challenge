package com.example.challenge.Acvtivities.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.challenge.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddChallenge extends AppCompatActivity implements View.OnClickListener{

    ImageView plus_chal,BackHomeFromAddChallenge,BackAdminFromAddChallenge;
    EditText edit_chal, info;
    String challenge, infoChallenge;
    ArrayList<String> SpinnerCategory;
    ArrayList<String> WhichCheckBox;
    CheckBox checkBox1, checkBox2,checkBox3,checkBox4,checkBox5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);

        SpinnerCategory = new ArrayList<>();
        WhichCheckBox  = new ArrayList<>();
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        setTextForCheckboxes();
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        checkBox4.setOnClickListener(this);
        checkBox5.setOnClickListener(this);

        info = findViewById(R.id.info);
        edit_chal= findViewById(R.id.edit_chal);
        plus_chal= findViewById(R.id.plus_chal);
        plus_chal.setOnClickListener(this);
        BackHomeFromAddChallenge =findViewById(R.id.BackHomeFromAddChallenge);
        BackHomeFromAddChallenge.setOnClickListener(this);
        BackAdminFromAddChallenge=findViewById(R.id.BackAdminFromAddChallenge);
        BackAdminFromAddChallenge.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==plus_chal){
            AddChallengeAsAdmin();
        }
        if ( v == checkBox1){
            if (checkBox1.isChecked()) WhichCheckBox.add(checkBox1.getText().toString().trim());
            else WhichCheckBox.remove(checkBox1.getText().toString().trim());
        }
        if ( v == checkBox2){
            if (checkBox2.isChecked()) WhichCheckBox.add(checkBox2.getText().toString().trim());
            else WhichCheckBox.remove(checkBox2.getText().toString().trim());
        }
        if ( v == checkBox3){
            if (checkBox3.isChecked()) WhichCheckBox.add(checkBox3.getText().toString().trim());
            else WhichCheckBox.remove(checkBox3.getText().toString().trim());
        }
        if ( v == checkBox4){
            if (checkBox4.isChecked()) WhichCheckBox.add(checkBox4.getText().toString().trim());
            else WhichCheckBox.remove(checkBox4.getText().toString().trim());
        }
        if ( v == checkBox5){
            if (checkBox5.isChecked()) WhichCheckBox.add(checkBox5.getText().toString().trim());
            else WhichCheckBox.remove(checkBox5.getText().toString().trim());
        }
        if (v == BackHomeFromAddChallenge){
            Intent intent = new Intent(AddChallenge.this, Main_Page.class);
            startActivity(intent);
        }
        if (v == BackAdminFromAddChallenge) {
            Intent intent = new Intent(AddChallenge.this, Admin.class);
            startActivity(intent);
        }
    }
    private void AddChallengeAsAdmin() {
        challenge = edit_chal.getText().toString().trim();
        infoChallenge = info.getText().toString().trim();
        if(TextUtils.isEmpty(challenge)){
            edit_chal.setError("You must write a challenge");
            return;
        }
        if ( WhichCheckBox.isEmpty()){
            Toast.makeText(AddChallenge.this, "You Must Fill At Least One Category", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(infoChallenge)){
            info.setError("You must write explanation to the challenge ");
            return;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categories");
        for (int i=0; i<WhichCheckBox.size();i++){
            reference.child(WhichCheckBox.get(i)).child("challenge").child(challenge).setValue(infoChallenge).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    edit_chal.setText("");
                    info.setText("");
                    Toast.makeText(AddChallenge.this, "Challenge has been added!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void setTextForCheckboxes() {
        SpinnerCategory.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categories");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for ( DataSnapshot snapshot : datasnapshot.getChildren() ){
                    String s = snapshot.getKey();
                    SpinnerCategory.add(s);
                }
                for (int i = 0 ; i < SpinnerCategory.size() ; i++){
                    switch (i) {
                        case 0: {
                            checkBox1.setVisibility(View.VISIBLE);
                            checkBox1.setText(SpinnerCategory.get(i));
                        }
                        break;
                        case 1: {
                            checkBox2.setVisibility(View.VISIBLE);
                            checkBox2.setText(SpinnerCategory.get(i));
                        }
                        break;
                        case 2: {
                            checkBox3.setVisibility(View.VISIBLE);
                            checkBox3.setText(SpinnerCategory.get(i));
                        }
                        break;
                        case 3: {
                            checkBox4.setVisibility(View.VISIBLE);
                            checkBox4.setText(SpinnerCategory.get(i));
                        }
                        break;
                        case 4: {
                            checkBox5.setVisibility(View.VISIBLE);
                            checkBox5.setText(SpinnerCategory.get(i));
                        }
                        break;
                        default : break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}