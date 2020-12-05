package com.example.challenge.Acvtivities.DATA;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBaseData {
   private FirebaseAuth fAuth;


    public FireBaseData() {
        fAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getfAuth() {
        return fAuth;
    }

    public String GetcurrentID(){
        return fAuth.getCurrentUser().getUid();
    }


}