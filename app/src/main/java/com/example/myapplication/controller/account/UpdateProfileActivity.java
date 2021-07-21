package com.example.myapplication.controller.account;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.utilities.GetAvatarResource;
import com.example.myapplication.utilities.Validation;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().hide();
        loadData();
    }

    public void loadData(){
        CircleImageView avatar = (CircleImageView) findViewById(R.id.img_profile_image);
        TextView txtRole = (TextView) findViewById(R.id.txt_profile_update_role);
        EditText txtFullName = (EditText) findViewById(R.id.txt_profile_update_name);
        EditText txtYearOfBirth = (EditText) findViewById(R.id.txt_profile_update_yob);

        avatar.setImageResource(GetAvatarResource.getAvatarImageID(UserDAO.account.getAvatar()));
        txtRole.setText("Question Manager");
        txtFullName.setText(UserDAO.account.getFullName());
        txtYearOfBirth.setText(String.valueOf(UserDAO.account.getYearOfBirth()));
    }

    public void onButtonUpdateClick(View view){
        EditText txtFullName = (EditText) findViewById(R.id.txt_profile_update_name);
        EditText txtYearOfBirth = (EditText) findViewById(R.id.txt_profile_update_yob);

        String fullName = txtFullName.getText().toString();
        String yearOfBirth = txtYearOfBirth.getText().toString();

        if (Validation.checkNullData(new String[]{fullName, yearOfBirth})){
            if (fullName.isEmpty()){
                txtFullName.setError("Full name cannot be empty! Please input!");
            }

            if (yearOfBirth.isEmpty()){
                txtYearOfBirth.setError("Year of birth cannot be empty! Please input!");
            }
        }
        else if (!Validation.checkUpdateFormat(fullName, yearOfBirth)){
            if (!Validation.checkFullNameFormat(fullName)){
                txtFullName.setError("Full name must be in correct format. (Ex: Tri Thanh)");
            }

            if (!Validation.checkYearOfBirth(yearOfBirth)){
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                txtYearOfBirth.setError("Year of birth must be a positive number between 1900 and " + currentYear);
            }
        }
        else {
            UserDAO.account.setFullName(fullName);
            UserDAO.account.setYearOfBirth(Integer.parseInt(yearOfBirth));
            UserDAO.updateProfile(UserDAO.account.getId(), UserDAO.account.getFullName(), UserDAO.account.getYearOfBirth());

            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
            builder.setMessage("Updated successfully!");
            builder.setTitle("Notification!");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
        }
    }

    public void hideFullNameError(View view){
        EditText txtFullName = findViewById(R.id.txt_profile_update_name);
        txtFullName.setError(null);
    }

    public void hideYearOfBirthError(View view){
        EditText txtYearOfBirth = findViewById(R.id.txt_profile_update_yob);
        txtYearOfBirth.setError(null);
    }
}