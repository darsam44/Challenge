package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
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

public class AddCategory extends AppCompatActivity implements View.OnClickListener {
    ImageView plus_cat, BackHomeFromAddCategory, BackAdminFromAddCategory;
    EditText edit_cat;
    String category;
    ArrayList<String>  ChallengeSpinner;
    HashMap<String, String> ChallengeSpinnerData;
    ArrayList<String> WhichCheckBox;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        plus_cat = findViewById(R.id.plus_cat);
        edit_cat = findViewById(R.id.edit_cat);
        plus_cat.setOnClickListener(this);
        BackHomeFromAddCategory = findViewById(R.id.BackHomeFromAddCategory);
        BackHomeFromAddCategory.setOnClickListener(this);
        BackAdminFromAddCategory = findViewById(R.id.BackAdminFromAddCategory);
        BackAdminFromAddCategory.setOnClickListener(this);
        ChallengeSpinner = new ArrayList<>();
        ChallengeSpinnerData = new HashMap<>();
        WhichCheckBox = new ArrayList<>();
        checkBox1 = findViewById(R.id.checkBox11);
        checkBox2 = findViewById(R.id.checkBox22);
        checkBox3 = findViewById(R.id.checkBox33);
        checkBox4 = findViewById(R.id.checkBox44);
        checkBox5 = findViewById(R.id.checkBox55);
        checkBox6 = findViewById(R.id.checkBox66);
        checkBox7 = findViewById(R.id.checkBox77);
        checkBox8 = findViewById(R.id.checkBox88);
        checkBox9 = findViewById(R.id.checkBox99);
        checkBox10 = findViewById(R.id.checkBox10);
        setTextForCheckboxes();
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        checkBox4.setOnClickListener(this);
        checkBox5.setOnClickListener(this);
        checkBox6.setOnClickListener(this);
        checkBox7.setOnClickListener(this);
        checkBox8.setOnClickListener(this);
        checkBox9.setOnClickListener(this);
        checkBox10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == plus_cat) {
            AddCategoryAsAdmin(v);
        }
        if (v == BackHomeFromAddCategory) {
            Intent intent = new Intent(AddCategory.this, Main_Page.class);
            startActivity(intent);
        }
        if (v == BackAdminFromAddCategory) {
            Intent intent = new Intent(AddCategory.this, Admin.class);
            startActivity(intent);
        }
        if (v == checkBox1) {
            if (checkBox1.isChecked()) {
                WhichCheckBox.add(checkBox1.getText().toString().trim());
            } else WhichCheckBox.remove(checkBox1.getText().toString().trim());
        }
        if (v == checkBox2) {
            if (checkBox2.isChecked()) WhichCheckBox.add(checkBox2.getText().toString().trim());
            else WhichCheckBox.remove(checkBox2.getText().toString().trim());
        }
        if (v == checkBox3) {
            if (checkBox3.isChecked()) WhichCheckBox.add(checkBox3.getText().toString().trim());
            else WhichCheckBox.remove(checkBox3.getText().toString().trim());
        }
        if (v == checkBox4) {
            if (checkBox4.isChecked()) WhichCheckBox.add(checkBox4.getText().toString().trim());
            else WhichCheckBox.remove(checkBox4.getText().toString().trim());
        }
        if (v == checkBox5) {
            if (checkBox5.isChecked()) WhichCheckBox.add(checkBox5.getText().toString().trim());
            else WhichCheckBox.remove(checkBox5.getText().toString().trim());
        }
        if (v == checkBox6) {
            if (checkBox6.isChecked()) WhichCheckBox.add(checkBox6.getText().toString().trim());
            else WhichCheckBox.remove(checkBox6.getText().toString().trim());
        }
        if (v == checkBox7) {
            if (checkBox7.isChecked()) WhichCheckBox.add(checkBox7.getText().toString().trim());
            else WhichCheckBox.remove(checkBox7.getText().toString().trim());
        }
        if (v == checkBox8) {
            if (checkBox8.isChecked()) WhichCheckBox.add(checkBox8.getText().toString().trim());
            else WhichCheckBox.remove(checkBox8.getText().toString().trim());
        }
        if (v == checkBox9) {
            if (checkBox9.isChecked()) WhichCheckBox.add(checkBox9.getText().toString().trim());
            else WhichCheckBox.remove(checkBox9.getText().toString().trim());
        }
        if (v == checkBox10) {
            if (checkBox10.isChecked()) WhichCheckBox.add(checkBox10.getText().toString().trim());
            else WhichCheckBox.remove(checkBox10.getText().toString().trim());
        }
    }

    private void AddCategoryAsAdmin(View v) {
        category = edit_cat.getText().toString().trim();
        if (TextUtils.isEmpty(category)) {
            edit_cat.setError("You must write a category!");
            return;
        }
        if (WhichCheckBox.isEmpty()) {
            Toast.makeText(AddCategory.this, "You Must Fill At Least One Challenge", Toast.LENGTH_SHORT).show();
            return;
        }
        Query categoryQuery = FirebaseDatabase.getInstance().getReference().child("Categories").equalTo(category);
        categoryQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int temp = (int) snapshot.getChildrenCount();
                if (temp > 0) {
                    Toast.makeText(AddCategory.this, "Category Already Exist, Please Change", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> Cat = new HashMap<>();
                    for (int i = 0; i < WhichCheckBox.size(); i++)
                        Cat.put(WhichCheckBox.get(i), ChallengeSpinnerData.get(WhichCheckBox.get(i)));
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categories");
                    reference.child(category).child("challenge").setValue(Cat).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddCategory.this, "Category Successfully Added", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddCategory.this, "Category not Added", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setTextForCheckboxes() {
        ChallengeSpinner.clear();
        ChallengeSpinnerData.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categories");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    for (DataSnapshot ds : snapshot.child("challenge").getChildren()) {
                        //get data
                        if (!ChallengeSpinner.contains(ds.getKey())) {
                            ChallengeSpinner.add(ds.getKey());
                            ChallengeSpinnerData.put(ds.getKey(), ds.getValue().toString());
                        }
                    }
                }

                addTextToCheckBox();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addTextToCheckBox() {
        if (!ChallengeSpinner.isEmpty()) {
            for (int i = 0; i < ChallengeSpinner.size(); i++) {
                switch (i) {
                    case 0: {
                        checkBox1.setVisibility(View.VISIBLE);
                        checkBox1.setText(ChallengeSpinner.get(i));
                    }
                    break;
                    case 1: {
                        checkBox2.setVisibility(View.VISIBLE);
                        checkBox2.setText(ChallengeSpinner.get(i));
                    }
                    break;
                    case 2: {
                        checkBox3.setVisibility(View.VISIBLE);
                        checkBox3.setText(ChallengeSpinner.get(i));
                    }
                    break;
                    case 3: {
                        checkBox4.setVisibility(View.VISIBLE);
                        checkBox4.setText(ChallengeSpinner.get(i));
                    }
                    break;
                    case 4: {
                        checkBox5.setVisibility(View.VISIBLE);
                        checkBox5.setText(ChallengeSpinner.get(i));
                    }
                    break;
                    case 5: {
                        checkBox6.setVisibility(View.VISIBLE);
                        checkBox6.setText(ChallengeSpinner.get(i));
                    }
                    break;
                    case 6: {
                        checkBox7.setVisibility(View.VISIBLE);
                        checkBox7.setText(ChallengeSpinner.get(i));
                    }
                    break;
                    case 7: {
                        checkBox8.setVisibility(View.VISIBLE);
                        checkBox8.setText(ChallengeSpinner.get(i));
                    }
                    break;
                    case 8: {
                        checkBox9.setVisibility(View.VISIBLE);
                        checkBox9.setText(ChallengeSpinner.get(i));
                    }
                    break;
                    case 9: {
                        checkBox10.setVisibility(View.VISIBLE);
                        checkBox10.setText(ChallengeSpinner.get(i));
                    }
                    break;
                    default:
                        break;
                }
            }
        }
    }
}