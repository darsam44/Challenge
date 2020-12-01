package com.example.challenge.Acvtivities.DATA;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBaseData {
   private FirebaseAuth fAuth;
   private FirebaseFirestore Fstore;

    public FireBaseData() {
        fAuth = FirebaseAuth.getInstance();
        Fstore = FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getfAuth() {
        return fAuth;
    }

    public FirebaseFirestore getFstore() {
        return Fstore;
    }


}
