package com.example.challenge.Acvtivities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.challenge.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Admin extends AppCompatActivity implements View.OnClickListener {
    Button newChallenge, newCategory;
    ImageView BackHomeAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        newChallenge = findViewById(R.id.newChallenge);
        newCategory = findViewById(R.id.newCategory);
        BackHomeAdmin =findViewById(R.id.BackHomeAdmin);
        BackHomeAdmin.setOnClickListener(this);
        newCategory.setOnClickListener( this);
        newChallenge.setOnClickListener( this);
    }

    @Override
    public void onClick(View v) {
        if(v == newCategory){
            Intent intent = new Intent(Admin.this, AddCategory.class);
            startActivity(intent);
        }
        else if (v == newChallenge){
            Intent intent = new Intent(Admin.this, AddChallenge.class);
            startActivity(intent);
        }
        else if (v == BackHomeAdmin){
            Intent intent = new Intent(Admin.this, Main_Page.class);
            startActivity(intent);
        }

    }
}