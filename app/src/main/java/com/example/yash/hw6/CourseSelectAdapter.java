package com.example.yash.hw6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by teja on 11/3/17.
 */

public class CourseSelectAdapter extends RecyclerView.Adapter<CourseSelectAdapter.ViewHolder> {
    ArrayList<Instructor> instructorArrayList;
    Context context;
    public CourseSelectAdapter(ArrayList<Instructor> instructors, Context context) {
        this.instructorArrayList = instructors;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inst_info_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Instructor instructorData = instructorArrayList.get(position);
        holder.details = instructorData;
        holder.titleTextView.setText(instructorData.getWebsite());
        Bitmap bMap = BitmapFactory.decodeByteArray(instructorData.getPic(), 0, instructorData.getPic().length);
        holder.instImgView.setImageBitmap(bMap);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView instImgView;
        RadioButton radioButton;
        Instructor details;

        Instructor instructorAdapter;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.infoText);
            radioButton = (RadioButton) itemView.findViewById(R.id.selectRadio);
            instImgView = (ImageView) itemView.findViewById(R.id.infoImage);
        }
    }
}
