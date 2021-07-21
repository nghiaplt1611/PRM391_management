package com.example.myapplication.ultility;

import android.app.AlertDialog;
import android.content.Context;

public class AlertDialogBuilder {
    public static AlertDialog showAlertDialog(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
