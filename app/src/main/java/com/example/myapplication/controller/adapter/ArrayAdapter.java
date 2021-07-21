package com.example.myapplication.controller.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Question;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//Dinh Cong Toai CE140341
public class ArrayAdapter extends android.widget.ArrayAdapter<Question> {

    public ArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Question> lecList) {
        super(context, resource, lecList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView ==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview,parent,false);
        }
        Question currentItem = getItem(position);
        ImageView imageView = listItemView.findViewById(R.id.img_question);
        Picasso.get().load(currentItem.getImageURL()).into(imageView);
        TextView answer = listItemView.findViewById(R.id.txt_answer);
        answer.setText(currentItem.getAnswer());
        TextView level = listItemView.findViewById(R.id.txt_level);
        level.setText("Level: " + currentItem.getLevel());

        return listItemView;
    }
}
