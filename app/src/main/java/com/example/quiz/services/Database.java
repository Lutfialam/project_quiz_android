package com.example.quiz.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    public DatabaseReference instance = FirebaseDatabase.getInstance().getReference();
}
