package com.example.challenge.DataBase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;

    public FirebaseFirestore getFstore() {
        return fstore;
    }

    public void setFstore(FirebaseFirestore fstore) {
        this.fstore = fstore;
    }

    public FirebaseAuth getfAuth() {
        return fAuth;
    }

    public void setfAuth(FirebaseAuth fAuth) {
        this.fAuth = fAuth;
    }
}


