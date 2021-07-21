package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.QuestionDAO;
import com.example.myapplication.ultility.LoadingPopup;

public class LogInActivity extends AppCompatActivity {
    Dialog loadingDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



    }

    public void Login(View v){
        //Code login



        QuestionDAO.getAllQuestion();
        loadingDiag = LoadingPopup.loadingDialog(this);
        loadingDiag.show();
        new Handler().postDelayed(this::openList,2000);

    }

    public void openList(){
        loadingDiag.dismiss();
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        LogInActivity.this.startActivity(intent);

    }
}