package com.example.challenge.Acvtivities.DATA;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBaseData {
   private FirebaseAuth fAuth;
   private DatabaseReference reference_Users;
    private DatabaseReference reference_Categories;

    public FireBaseData() {
        reference_Users = FirebaseDatabase.getInstance().getReference("Users");
        reference_Categories = FirebaseDatabase.getInstance().getReference("Categories");
        fAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getfAuth() {
        return fAuth;
    }

    public String GetcurrentID(){
        return fAuth.getCurrentUser().getUid();
    }

    public DatabaseReference getReference_Users() {
        return reference_Users;
    }

    public void setReference_Users(DatabaseReference reference_Users) {
        this.reference_Users = reference_Users;
    }

    public DatabaseReference getReference_Categories() {
        return reference_Categories;
    }

    public void setReference_Categories(DatabaseReference reference_Categories) {
        this.reference_Categories = reference_Categories;
    }
}
