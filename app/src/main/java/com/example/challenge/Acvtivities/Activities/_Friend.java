package com.example.challenge.Acvtivities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.challenge.Acvtivities.DATA.FireBaseData;
import com.example.challenge.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class _Friend extends AppCompatActivity {

    ListView list_Friend;
    ArrayList<String> my_arrlist =new ArrayList<>();
    FireBaseData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___friend);

        list_Friend = findViewById(R.id.List_Friend2);

        String ID=data.GetcurrentID();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(ID).child("friend");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    my_arrlist.add(ds.getValue().toString());
                }
                ;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,my_arrlist);
        list_Friend.setAdapter(arrayAdapter);

        list_Friend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String []  friend_Edit=my_arrlist.get(position).toString().split(":");
                CheckifthereUser(friend_Edit[0]);

            }
        });

    }
    private void CheckifthereUser(String view) {
//        String User_Name_Search = view;
//
//        data.getReference_Users().addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                boolean flag = false;
//                for(DataSnapshot snapshot : datasnapshot.getChildren()  ){
//                 String   ID = snapshot.getKey();
//                 String   First_Name = snapshot.child("First_Name").getValue(String.class);
//                 String   Last_Name = snapshot.child("Last_Name").getValue(String.class);
//                 String User_Name = snapshot.child("User_Name").getValue(String.class);
//                 String Email = snapshot.child("Email").getValue(String.class);
//                 String Phone = snapshot.child("Phone").getValue(String.class);
//                 String IsAdmin = snapshot.child("IsAdmin").getValue(String.class);
//
//                    if ( User_Name.equals(User_Name_Search) ){
//                        flag = true;
//                        Intent pro = new Intent(view, Other_user_profile.class);
//                        pro.putExtra( "ID" , ID);
//                        pro.putExtra("First_Name" ,First_Name);
//                        pro.putExtra("Last_Name", Last_Name);
//                        pro.putExtra("Email", Email);
//                        pro.putExtra("User_Name", User_Name);
//                        pro.putExtra("Phone", Phone);
//                        pro.putExtra("IsAdmin" , IsAdmin);
//                        startActivity(pro);
//                    }
//                }
//                if ( !flag){
//                    Toast.makeText(Main_Page.this , "The user name is not exist " , Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
    }
}