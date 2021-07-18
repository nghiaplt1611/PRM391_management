package com.example.myapplication.controller;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.QuestionDAO;
import com.example.myapplication.model.Question;
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
import java.util.HashMap;

public class UpdateMainItemActivity extends AppCompatActivity {

    Question question = new Question();
    private ImageView pic ;
    public Uri imgUri ;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_main_item);

        Intent data = getIntent();
        Question question = (Question) data.getSerializableExtra("ques");


        pic = findViewById(R.id.img_update_image);
        EditText answer = findViewById(R.id.update_txt_answer);
        EditText level = findViewById(R.id.update_txt_level);

        Picasso.get().load(question.getImageURL()).into(pic);
        answer.setText(question.getAnswer());
        level.setText(String.valueOf(question.getLevel()));

        Button updateBut = findViewById(R.id.btn_update_confirm);
        Button cancelUp = findViewById(R.id.btn_update_cancel);
        Button choosePic = findViewById(R.id.btn_update_image);
        Button uploadImg = findViewById(R.id.btn_upload_img);

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPic();
            }
        });

        choosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        updateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateMainItemActivity.this,"bam nut roi",Toast.LENGTH_SHORT);
//                level.setText("vailone");
                question.setAnswer(answer.getText().toString());
                question.setLevel(Integer.valueOf(level.getText().toString()));
//                update(question);
//                QuestionDAO.updateQuestion(question);
                HashMap map = new HashMap();
                map.put("answer",question.getAnswer());
                map.put("level",question.getLevel());
                MainActivity.db.collection("questions").document(question.getId()).update(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.e("update: ","");
                                }else {
                                    Log.e("failed!","");
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("loi luc update",""+e.getMessage());
                    }
                });
            }
        });

        cancelUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public  void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imgUri = data.getData();
            Picasso.get().load(imgUri).into(pic);
        }
    }

    private void uploadPic(){

        progressDialog = new ProgressDialog(this);
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date();
        String filename = format.format(now);

        storageReference = FirebaseStorage.getInstance().getReference(filename);
        storageReference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UpdateMainItemActivity.this,"Successfully Uploaded!!!",Toast.LENGTH_SHORT);
                if (progressDialog.isShowing()){
                    Picasso.get().load(imgUri).into(pic);
                    imgUri = taskSnapshot.getUploadSessionUri();
                    System.out.println("link "+imgUri);
                    progressDialog.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(UpdateMainItemActivity.this,"Failed to upload!!!",Toast.LENGTH_SHORT);
            }
        });
    }

}