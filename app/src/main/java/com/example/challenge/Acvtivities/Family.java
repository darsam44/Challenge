package com.example.challenge.Acvtivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.challenge.R;

import java.util.List;

public class Family extends AppCompatActivity {
    List<String> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
    }
}