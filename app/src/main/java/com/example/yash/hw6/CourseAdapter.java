package com.example.yash.hw6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    ArrayList<Course> courseArrayList;
    Context context;
    public CourseAdapter(ArrayList<Course> courses, Context context) {
        this.courseArrayList = courses;
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
        Course course = courseArrayList.get(position);
        holder.details = course;
        holder.titleTextView.setText(course.getTitle());
        holder.instructorTextView.setText(course.getInstName());
        holder.timeTextView.setText(course.getHours()+":"+course.getMinutes()+" "+course.getTime());
        holder.dayTextView.setText(course.getDay());
        holder.instructorImageView.setImageBitmap(BitmapFactory.decodeByteArray(course.getInstPic(), 0, course.getInstPic().length));
    }
    @Override
    public int getItemCount() {
        return courseArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView, instructorTextView,dayTextView,timeTextView;
        ImageView instructorImageView;
        Course details;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            instructorTextView = (TextView) itemView.findViewById(R.id.inst);
            dayTextView = (TextView) itemView.findViewById(R.id.day);
            timeTextView = (TextView) itemView.findViewById(R.id.time);
            instructorImageView = (ImageView) itemView.findViewById(R.id.instructorImage);
        }
    }
}
