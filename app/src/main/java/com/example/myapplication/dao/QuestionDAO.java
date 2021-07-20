package com.example.myapplication.dao;

import androidx.annotation.NonNull;

import com.example.myapplication.MainActivity;
import com.example.myapplication.model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class QuestionDAO {
    public static Question question;
    public static ArrayList<Question> listQuestion = new ArrayList<>();

    public static void getQuestion(String id){
        MainActivity.db = FirebaseFirestore.getInstance();
        DocumentReference docRef = MainActivity.db.collection("questions").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                question = documentSnapshot.toObject(Question.class);
                getAllQuestionsInLevel(question.getLevel());
            }
        });
    }

    public static void getAllQuestionsInLevel(int level){
        MainActivity.db = FirebaseFirestore.getInstance();
        MainActivity.db.collection("questions")
                .whereEqualTo("level", level)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listQuestion.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Question ques = document.toObject(Question.class);
                                listQuestion.add(ques);
                            }
                        }
                    }
                });
    }
}
