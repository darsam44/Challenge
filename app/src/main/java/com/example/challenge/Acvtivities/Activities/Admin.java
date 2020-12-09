package com.example.challenge.Acvtivities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.challenge.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity implements View.OnClickListener {
    Button newChallenge, newCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        newChallenge = findViewById(R.id.newChallenge);
        newCategory = findViewById(R.id.newCategory);
        newCategory.setOnClickListener((View.OnClickListener) this);
        newChallenge.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        if(v == newCategory){
            Intent intent = new Intent(Admin.this, NewChalengeOrCategory.class);
            startActivity(intent);
        }
        else if (v == newChallenge){
            Intent intent = new Intent(Admin.this, NewChalengeOrCategory.class);
            startActivity(intent);
        }

    }
}