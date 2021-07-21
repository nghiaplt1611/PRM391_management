package com.example.myapplication.utilities;

public class UploadImage {
    private String mName;
    private String mImageUri;

    public UploadImage() {
    }

    public UploadImage(String mName, String mImageUri) {
        if (mName.trim().equals("")){
            mName = "NO NAME";
        }
        this.mName = mName;
        this.mImageUri = mImageUri;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }
}
