package com.example.myapplication.dao;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.MainActivity;
import com.example.myapplication.model.Question;
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
import java.util.Random;

public class QuestionDAO {

    public static Question question;
    public static ArrayList<Question> listQuestion = new ArrayList<>();


    public static void getAllQuestion(){
        MainActivity.db = FirebaseFirestore.getInstance();
        MainActivity.db.collection("questions").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                System.out.println("qua day trc");
                    listQuestion.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Question ques = document.toObject(Question.class);
                        listQuestion.add(ques);
                        Log.e("du lieu"," so " +ques.getAnswer());
                    }
            }
        });

    }

    public static void updateQuestion(Question question){
        Log.e("co chay qua update","");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", String.valueOf(question.getId()));
        map.put("answer", question.getAnswer());
        map.put("imageURL", question.getImageURL());
        MainActivity.db.collection("questions").document(question.getId()).update(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull  Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.e("update: ","");
                        }else {
                            Log.e("failed!","");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("loi luc update",""+e.getMessage());
            }
        });
    }

    public static void getQuestion(String id){
        MainActivity.db = FirebaseFirestore.getInstance();
        DocumentReference docRef = MainActivity.db.collection("questions").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                question = documentSnapshot.toObject(Question.class);
            }
        });
    }






}
