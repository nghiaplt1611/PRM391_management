package com.example.myapplication.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.model.Question;

import java.util.ArrayList;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

public class ListAdapter extends android.widget.ArrayAdapter<Question>{

    public ListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Question> lecList) {
        super(context, resource, lecList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView ==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.question_list_view,parent,false);
        }
        Question currentItem = getItem(position);
        ImageView imageView = listItemView.findViewById(R.id.img_list_item);
        Picasso.get().load(currentItem.getImageURL()).into(imageView);
        TextView answer = listItemView.findViewById(R.id.quest_answer);
        answer.setText(currentItem.getAnswer());
        TextView level = listItemView.findViewById(R.id.quest_level);
        level.setText(currentItem.getLevel());
        return listItemView;
    }
}
