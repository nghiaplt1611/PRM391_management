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
import android.content.DialogInterface;
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

import com.example.myapplication.controller.AddMainItemActivity;
import com.example.myapplication.controller.LogInActivity;
import com.example.myapplication.controller.ProfileActivity;
import com.example.myapplication.controller.UpdateMainItemActivity;
import com.example.myapplication.dao.QuestionDAO;
import com.example.myapplication.model.Question;
import com.example.myapplication.ultility.CheckNetworkConnection;
import com.example.myapplication.ultility.LoadData;
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
    private int pos = -1;
    private ArrayList<Question> list ;
    private ArrayList<Question> searchList ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        context = getApplicationContext();
        checkConnection();
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        if (user != null){
            LoadData.loadUserData(user);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Welcome back! My manager!!!");
            builder.setTitle("Automatic logged in!");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setList();
                    dialog.cancel();
                    //loadingDiag.cancel();
                    //finish();
                    //userMainMenuIntent(findViewById(android.R.id.content).getRootView());
                }
            });
            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
        }
        else{
            Intent intent = new Intent(this, LogInActivity.class);
            this.startActivity(intent);
            finish();
        }
    }

    public void onButtonLogoutClick(View view){
        MainActivity.mAuth.signOut();
        MainActivity.mAuth = null;
        MainActivity.user = null;
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finishAffinity();
    }

    public void onButtonProfileClick(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    public void onButtonAddQuestionClick(View view){
        Intent intent = new Intent(this, AddMainItemActivity.class);
        this.startActivity(intent);
    }

    public void checkConnection() {
        if (!CheckNetworkConnection.isConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please check your Internet connection before playing game!");
            builder.setTitle("Alert!");
            builder.setCancelable(false);
            builder.setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(MainActivity.this, SlashScreenActivity.class);
//                    startActivity(intent);
//                    finish();
                }
            });
            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
        }
    }

    public void setList(){

        list = QuestionDAO.listQuestion;

        adapter = new com.example.myapplication.controller.adapter.ArrayAdapter(this,0,list);
        ListView listView = findViewById(R.id.listView);
        registerForContextMenu(listView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("gia tri cua bien",""+position);
                pos = position;
                Log.e("gia tri trong object ",""+list.get(position).getId());

                Question question = new Question(list.get(position).getId()
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
                        if (result.getResultCode() ==0){
                            updateAdapter();
                        }else if (result.getResultCode() ==1){

                        }
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
        Log.e(">>>>>>>>>>>>>>>","co vo");
        list = QuestionDAO.listQuestion;
        adapter = new com.example.myapplication.controller.adapter.ArrayAdapter(this,0,list);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }


}