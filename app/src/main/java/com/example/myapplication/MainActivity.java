package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.controller.UpdateMainItemActivity;
import com.example.myapplication.dao.QuestionDAO;
import com.example.myapplication.model.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static FirebaseAuth mAuth;
    public static FirebaseFirestore db;
    public static FirebaseUser user;
    public static Context context;
    ArrayAdapter adapter ;
    QuestionDAO questionDAO = new QuestionDAO();
    private int pos = -1;
    private ArrayList<Question> list ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       list = QuestionDAO.listQuestion;

       adapter = new com.example.myapplication.controller.adapter.ArrayAdapter(this,0,QuestionDAO.listQuestion);
//       listQuestion = QuestionDAO.listQuestion.get(0);


//        Log.e("qua be main" , ""+QuestionDAO.listQuestion.get(0).getAnswer());
        ListView listView = findViewById(R.id.listView);
        registerForContextMenu(listView);
        listView.setAdapter(adapter);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                pos = position;
//                Toast.makeText(MainActivity.this,"vi tri "+pos,Toast.LENGTH_SHORT);
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("gia tri cua bien",""+position);
                pos = position;
                Log.e("gia tri trong object ",""+QuestionDAO.listQuestion.get(position).getId());

                Question question = new Question(QuestionDAO.listQuestion.get(position).getId()
                        ,QuestionDAO.listQuestion.get(position).getImageURL()
                        ,QuestionDAO.listQuestion.get(position).getAnswer()
                        ,QuestionDAO.listQuestion.get(position).getLevel()
                        ,QuestionDAO.listQuestion.get(position).isStatus());
                addFragment(question);
            }
        });

    }

    /**
     * Add and update button action
     */
    ActivityResultLauncher<Intent> update = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                        //update an item base on position
//                        updateAdapter();
                }
            });


    /**
     * add button click action
     * @param
     */
    public void addFragment(Question question) {
        Log.e("gia tri trc khi update ",""+question.getAnswer());
        Intent intent = new Intent(this, UpdateMainItemActivity.class);
        intent.putExtra("ques",question);
        update.launch(intent);
    }

    public void updateAdapter(){
        adapter = new ArrayAdapter(this,0,QuestionDAO.listQuestion);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }



}