package com.example.myapplication.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.QuestionDAO;
import com.example.myapplication.dao.ScorcesDAO;
import com.example.myapplication.ultility.AlertDialogBuilder;
import com.example.myapplication.ultility.LoadData;
import com.example.myapplication.ultility.LoadingPopup;
import com.example.myapplication.ultility.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    Dialog loadingDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();
    }

    public void onButtonLoginClick(View view) {
        EditText txtEmail = (EditText) findViewById(R.id.txt_email_login);
        EditText txtPassword = (EditText) findViewById(R.id.txt_password_login);

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (Validation.checkNullData(new String[]{email, password})) {
            if (email.isEmpty()) {
                txtEmail.setError("Email cannot be empty! Please input!");
            }
            if (password.isEmpty()) {
                txtPassword.setError("Password cannot be empty! Please input!");
            }

        } else {

            MainActivity.mAuth = FirebaseAuth.getInstance();
            MainActivity.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (txtEmail.getText().toString().trim().equals("gtwadmin@gmail.com")){
                            MainActivity.user = MainActivity.mAuth.getCurrentUser();

                            AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                            builder.setMessage("Login successfully");
                            builder.setTitle("Notification!");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Director", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    LoginAsAd();
                                }
                            });
                            // Create the Alert dialog
                            AlertDialog alertDialog = builder.create();

                            // Show the Alert Dialog box
                            alertDialog.show();
                        }else {
                            MainActivity.user = MainActivity.mAuth.getCurrentUser();

                            AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                            builder.setMessage("Login successfully");
                            builder.setTitle("Notification!");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Question Management", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Login();
                                }
                            });
                            // Create the Alert dialog
                            AlertDialog alertDialog = builder.create();

                            // Show the Alert Dialog box
                            alertDialog.show();
                        }


                    } else {
                        AlertDialog dialog = AlertDialogBuilder.showAlertDialog("Alert!", "Email or password is invalid!!!", LogInActivity.this);
                        dialog.show();
                    }
                }
            });


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText txtEmail = (EditText) findViewById(R.id.txt_email_login);
        EditText txtPassword = (EditText) findViewById(R.id.txt_password_login);
        txtPassword.setText("");
        txtEmail.setText("");
        txtEmail.setError(null);
        txtPassword.setError(null);

    }

    public void hideEmailError(View view) {
        EditText txtEmail = (EditText) findViewById(R.id.txt_email_login);
        txtEmail.setError(null);
    }

    public void hidePasswordError(View view) {
        EditText txtPassword = (EditText) findViewById(R.id.txt_password_login);
        txtPassword.setError(null);
    }


    public void Login() {
        QuestionDAO.getAllQuestion();
        LoadData.loadUserData(MainActivity.user);
        loadingDiag = LoadingPopup.loadingDialog(this);
        loadingDiag.show();
        new Handler().postDelayed(this::openList, 2000);
    }

    public void LoginAsAd(){
        ScorcesDAO.getScores();
        LoadData.loadUserData(MainActivity.user);
        loadingDiag = LoadingPopup.loadingDialog(this);
        loadingDiag.show();
        new Handler().postDelayed(this::openScore, 2000);
    }

    public void openList() {
        loadingDiag.dismiss();
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        LogInActivity.this.startActivity(intent);
        finish();
    }

    public void openScore(){
        loadingDiag.dismiss();
        Intent intent = new Intent(LogInActivity.this, UpdateGameScoreActivity.class);
        LogInActivity.this.startActivity(intent);
        finish();
    }
}