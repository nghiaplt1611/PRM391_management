package com.example.myapplication.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.myapplication.R;

public class LoadingPopup {

    public static Dialog loadingDialog(Activity activity){
        Dialog congratDiag = new Dialog(activity);
        congratDiag.setContentView(R.layout.popup_loading);
        congratDiag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        congratDiag.setCancelable(false);
        return congratDiag;
    }
}
