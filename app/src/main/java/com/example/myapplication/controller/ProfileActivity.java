package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.ultility.AlertDialogBuilder;
import com.example.myapplication.ultility.GetAvatarResource;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData(){
        TextView txtPlayerName = (TextView) findViewById(R.id.txt_profile_name);
        TextView txtRole = (TextView) findViewById(R.id.txt_profile_role);
        TextView txtYearOfBirth = (TextView) findViewById(R.id.txt_profile_yob);
        ImageView avatar = (ImageView) findViewById(R.id.img_profile_image);

        txtPlayerName.setText(UserDAO.account.getFullName());
        txtRole.setText("Question Manager");
        txtYearOfBirth.setText(String.valueOf(UserDAO.account.getYearOfBirth()));
        avatar.setImageDrawable(getResources().getDrawable(GetAvatarResource.getAvatarImageID(UserDAO.account.getAvatar())));
    }

    public void onButtonChangePass(View view){
        MainActivity.mAuth = FirebaseAuth.getInstance();
        MainActivity.user = MainActivity.mAuth.getCurrentUser();
        MainActivity.mAuth.sendPasswordResetEmail(MainActivity.user.getEmail());
        AlertDialogBuilder.showAlertDialog("Notification!", "A email message has been sent to " + MainActivity.user.getEmail()+ " for verification. Please check it!", this);
        finish();
    }

    public void onButtonEditProfile(View view){
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        this.startActivity(intent);
    }

    public void onButtonChangeAvatar(View view){
//        Intent intent = new Intent(this, ChangeAvatarActivity.class);
//        this.startActivity(intent);
    }

    public void returnToUserMenuIntent(View view){
        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        this.startActivity(intent);
        finish();
    }


}