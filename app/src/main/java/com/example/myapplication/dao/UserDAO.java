package com.example.myapplication.dao;

import androidx.annotation.NonNull;

import com.example.myapplication.MainActivity;
import com.example.myapplication.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserDAO {
    public static Account account = new Account();

    public static boolean checkEmailVerified(){
        return MainActivity.user.isEmailVerified();
    }

    public static void getUserInfo(String email){
        MainActivity.db = FirebaseFirestore.getInstance();
        MainActivity.db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                account = document.toObject(Account.class);
                            }
                        }
                    }
                });

    }

    public static void updateAvatar(String id, int avatar){
        MainActivity.db = FirebaseFirestore.getInstance();
        DocumentReference docRef = MainActivity.db.collection("users").document(id);

        docRef.update("avatar", avatar).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

    public static void updateProfile(String id, String fullName, int yearOfBirth){
        MainActivity.db = FirebaseFirestore.getInstance();
        DocumentReference docRef = MainActivity.db.collection("users").document(id);

        docRef.update("fullName", fullName, "yearOfBirth", yearOfBirth).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

}
