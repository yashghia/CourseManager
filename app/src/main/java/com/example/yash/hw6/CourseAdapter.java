package com.example.yash.hw6;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by teja on 11/3/17.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    ArrayList<Instructor> instructorArrayList;
    Context context;
    public CourseAdapter(ArrayList<Instructor> instructors, Context context) {
        this.instructorArrayList = instructors;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coursedescription, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Instructor instructorData = instructorArrayList.get(position);
        holder.details = instructorData;
        holder.titleTextView.setText(instructorData.getWebsite());
        holder.instructorTextView.setText(instructorData.getFname());
        holder.dayTextView.setText(instructorData.getEmail());
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView, instructorTextView,dayTextView,timeTextView;
        ImageView recipeImageView;
        Instructor details;

        Instructor instructorAdapter;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            instructorTextView = (TextView) itemView.findViewById(R.id.inst);
            dayTextView = (TextView) itemView.findViewById(R.id.day);
            timeTextView = (TextView) itemView.findViewById(R.id.time);
            recipeImageView = (ImageView) itemView.findViewById(R.id.instructorImage);

        }
    }
}
