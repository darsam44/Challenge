package com.example.challenge.Acvtivities.Challengs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.challenge.Acvtivities.Activities.Main_Page;
import com.example.challenge.Acvtivities.Videos.AddVideoActivity;
import com.example.challenge.Acvtivities.Videos.VideoActivity;
import com.example.challenge.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class new_challenge extends AppCompatActivity implements View.OnClickListener {
    List<String> categories;
    List<String> challenges;
    String info;
    ImageView home;
    Button addVideosBtu;
    Spinner spinner, spinner2;
    List<String> ChallengesByCategory;
    List<String> Categories;
    DatabaseReference reff;
    ArrayAdapter<String> adapter_1;
    ArrayAdapter<String>  adapter_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_challenge);

       reff = FirebaseDatabase.getInstance().getReference("Categories");
        home = findViewById(R.id.image_home_challenge);
        home.setOnClickListener(this);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        Categories = new ArrayList<>();
        ChallengesByCategory =  new ArrayList<>();
        AddCategoriesToArray();
        adapter_1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Categories);
        adapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_1);


        //check it
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j < Categories.size(); j++){
                    String temp = Categories.get(j);
                    if (adapterView.getItemAtPosition(i).equals(temp)) {
                        reff.child(temp).child("challenge").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                ChallengesByCategory.clear();
                                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                    String s = snapshot.getKey();
                                    ChallengesByCategory.add(s);
                                    System.out.println( "this is:  " +  s);
                                }
                                fillSpinner();
                                adapter_2.notifyDataSetChanged();
                                return;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //init UI Views
        addVideosBtu = findViewById(R.id.addVido);
        addVideosBtu.setOnClickListener(this);
        //handle Click

    }

    @Override
    public void onClick(View view) {
        if ( view == home){
            Intent intent = new Intent( new_challenge.this , Main_Page.class);
            startActivity(intent);
        }
        else if (view == addVideosBtu){
            startActivity(new Intent(new_challenge.this, AddVideoActivity.class));

        }
    }


    public void fillSpinner(){
    adapter_2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ChallengesByCategory);
    adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner2.setAdapter(adapter_2);
    }


    private void AddCategoriesToArray() {
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    String s = snapshot.getKey();
                    Categories.add(s);
                }
                System.out.println("finish to: ");
                adapter_1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}