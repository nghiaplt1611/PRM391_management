package com.example.myapplication.dao;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.MainActivity;
import com.example.myapplication.controller.UpdateMainItemActivity;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Scores;
import com.example.myapplication.ultility.LoadingPopup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ScorcesDAO {
    public static Scores scores;
    public static void getScores(){
        MainActivity.db = FirebaseFirestore.getInstance();
        MainActivity.db.collection("scores").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        scores = documentSnapshot.toObject(Scores.class);
                    }
                }
            }
        });
    }

    public static void updateScores(HashMap map){
        MainActivity.db = FirebaseFirestore.getInstance();
        MainActivity.db.collection("scores").document("Uz0h0KJyvo7pmWDiOvdO").update(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Log.e("Complete","hahahhadhadadada");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FAILE","hahahhadhadadada");
            }
        });
    }
}
