package com.example.myapplication.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.QuestionDAO;
import com.example.myapplication.model.Question;
import com.example.myapplication.ultility.LoadingPopup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMainItemActivity extends AppCompatActivity {

    public String imgUri= "";
    StorageReference storageReference;
    ProgressDialog progressDialog;
    Dialog loadingDiag;
    private ImageView pic;
    private EditText answer, level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_main_item);
        getSupportActionBar().hide();
        Init();
    }

    public void Init() {
        pic = findViewById(R.id.img_add);
        answer = findViewById(R.id.etxt_add_answer);
        level = findViewById(R.id.etxt_add_level);
    }

    public void onAddButtonClick(View view) {

        if (imgUri.isEmpty()) {
            Notification("Chua co Hinh");
        } else if (answer.getText().toString().isEmpty()) {
            Notification("Chua co answer");
        } else if (level.getText().toString().isEmpty()) {
            Notification("Chua co level");
        } else {
            Question question = new Question();
            question.setAnswer(answer.getText().toString().toUpperCase());
            question.setImageURL(imgUri);
            question.setLevel(Integer.valueOf(level.getText().toString()));
            question.setStatus(true);

            QuestionDAO dao = new QuestionDAO();
            QuestionDAO.addQuestion(question);
            Intent intent = new Intent(AddMainItemActivity.this, MainActivity.class);
            AddMainItemActivity.this.startActivity(intent);
            QuestionDAO.getAllQuestion();
            loadingDiag = LoadingPopup.loadingDialog(AddMainItemActivity.this);
            loadingDiag.show();

            new Handler().postDelayed(AddMainItemActivity.this::closeUpdate,2000);
        }
    }

    public void closeUpdate(){
        loadingDiag.dismiss();
        finish();
    }
    public void Notification(String message) {
        MainActivity.user = MainActivity.mAuth.getCurrentUser();

        AlertDialog.Builder builder = new AlertDialog.Builder(AddMainItemActivity.this);
        builder.setMessage(message);
        builder.setTitle("Notification!");
        builder.setCancelable(false);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    public void openFileChooser(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData().toString();
            Picasso.get().load(imgUri).into(pic);
        }
    }

    public void uploadPic(View view) {
        progressDialog = new ProgressDialog(this);
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date();
        String filename = format.format(now);

        storageReference = FirebaseStorage.getInstance().getReference(filename);
        storageReference.putFile(Uri.parse(imgUri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddMainItemActivity.this, "Successfully Uploaded Image!!!", Toast.LENGTH_LONG);
                Picasso.get().load(imgUri).into(pic);
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imgUri = uri.toString();
                    }
                });
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isComplete()) {
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(AddMainItemActivity.this, "Failed to upload!!!", Toast.LENGTH_SHORT);
            }
        });
    }
}