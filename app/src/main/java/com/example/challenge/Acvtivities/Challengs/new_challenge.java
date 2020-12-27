package com.example.challenge.Acvtivities.Challengs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge.Acvtivities.Activities.Login;
import com.example.challenge.Acvtivities.Activities.Main_Page;
import com.example.challenge.Acvtivities.Activities.Profile;
import com.example.challenge.Acvtivities.Activities._Friend;
import com.example.challenge.Acvtivities.Videos.AddVideoActivity;
import com.example.challenge.Acvtivities.Videos.VideoActivity;
import com.example.challenge.R;
import com.google.firebase.auth.FirebaseAuth;
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
    String info, spinner_category, spinner_challenge;
    Button addVideosBtu;
    TextView T_info;
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
        T_info = findViewById(R.id.T_info);
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
                                spinner_category = temp;
                                ChallengesByCategory.clear();
                                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                    String s = snapshot.getKey();
                                    ChallengesByCategory.add(s);
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

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                spinner_challenge = parent.getItemAtPosition(i).toString();
             reff.child(spinner_category).child("challenge").child(spinner_challenge).addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String info = snapshot.getValue(String.class);
                    T_info.setText("Info: " + info );
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });

                T_info.setText("Info: " + info);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //init UI Views
        addVideosBtu = findViewById(R.id.addVido);
        addVideosBtu.setOnClickListener(this);
        //handle Click

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
                Intent intent = new Intent(new_challenge.this, Main_Page.class);
                startActivity(intent);
            }
            return true;
            case R.id.I_My_Profile:{
                Intent intent = new Intent(new_challenge.this, Profile.class);
                startActivity(intent);
            }
            return true;
            case R.id.I_My_Friends:{
                Intent intent = new Intent(new_challenge.this, _Friend.class);
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
        if (view == addVideosBtu){
            Intent moveTo = new Intent(view.getContext(),AddVideoActivity.class);
            moveTo.putExtra("Category_Choose", spinner_category);
            moveTo.putExtra("Challenge_Choose", spinner_challenge);
            startActivity(moveTo);
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
                adapter_1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}