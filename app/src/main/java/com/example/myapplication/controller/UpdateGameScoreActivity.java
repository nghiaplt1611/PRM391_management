package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.dao.ScorcesDAO;
import com.example.myapplication.model.Scores;

import java.util.HashMap;

public class UpdateGameScoreActivity extends AppCompatActivity {

    Scores scores;
    EditText change ;
    EditText hint ;
    EditText initial ;
    EditText pass ;
    EditText wrong ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_game_score);

        LoadData();

    }

    public void LoadData(){
//        ScorcesDAO.getScores();
        scores = ScorcesDAO.scores;

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> "+scores.getChangeQuestionScore());

        change = findViewById(R.id.txt_score_update_change_question);
        hint = findViewById(R.id.txt_score_update_hint);
        initial = findViewById(R.id.txt_score_update_initial);
        pass = findViewById(R.id.txt_score_update_passlevel);
        wrong = findViewById(R.id.txt_score_update_wrong);

        change.setText(String.valueOf(scores.getChangeQuestionScore()));
        hint.setText(String.valueOf(scores.getHintScore()));
        initial.setText(String.valueOf(scores.getInitialScore()));
        pass.setText(String.valueOf(scores.getPassedLevelScore()));
        wrong.setText(String.valueOf(scores.getWrongAnswerScore()));

    }

    public void updateScores(View v){
        HashMap map = new HashMap();
        map.put("changeQuestionScore",Integer.parseInt(change.getText().toString()));
        map.put("hintScore",Integer.parseInt(hint.getText().toString()));
        map.put("initialScore",Integer.parseInt(initial.getText().toString()));
        map.put("passedLevelScore",Integer.parseInt(pass.getText().toString()));
        map.put("wrongAnswerScore",Integer.parseInt(wrong.getText().toString()));
        ScorcesDAO.updateScores(map);
    }


}