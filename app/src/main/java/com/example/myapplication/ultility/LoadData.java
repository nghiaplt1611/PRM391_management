package com.example.myapplication.ultility;

import com.example.myapplication.dao.UserDAO;
import com.google.firebase.auth.FirebaseUser;

public class LoadData {
    public static void loadUserData(FirebaseUser user){
        UserDAO.getUserInfo(user.getEmail());
    }

//    public static void loadQuestion(){
//        QuestionDAO.loadQuestion();
//    }
}
