package com.example.yash.hw6;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by teja on 11/3/17.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    static Realm realm;
    static ArrayList<Course> courseArrayList;
    static Fragment fragment;
    static AlertDialog.Builder builder;
    static ICourseDetails courseDetails;
    public CourseAdapter(ArrayList<Course> courses, Fragment fragment) {
        this.courseArrayList = courses;
        this.fragment = fragment;
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
        static Course details;
        public ViewHolder(View itemView) {
            super(itemView);
            builder = new AlertDialog.Builder(itemView.getContext());
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            instructorTextView = (TextView) itemView.findViewById(R.id.inst);
            dayTextView = (TextView) itemView.findViewById(R.id.day);
            timeTextView = (TextView) itemView.findViewById(R.id.time);
            instructorImageView = (ImageView) itemView.findViewById(R.id.instructorImage);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    builder.setTitle("Do you really want to clear all inputs?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    realm = Realm.getDefaultInstance();
                                    courseArrayList.remove(details);
                                    final RealmResults<Course> courses = realm.where(Course.class).findAll();
                                    Course course = courses.where().equalTo("title",details.getTitle()).findFirst();

                                    if(course!=null){
                                        if (!realm.isInTransaction()) {
                                            realm.beginTransaction();
                                        }
                                        course.deleteFromRealm();
                                        realm.commitTransaction();
                                    }
                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("delete","donot delete");
                        }
                    }).setCancelable(false);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.getFragmentManager().beginTransaction()
                            .replace(R.id.container,new CourseDetailsFragment(),"coursedetails")
                            .commit();
                    courseDetails.details(details);
                }
            });
        }
    }

    interface ICourseDetails{
        void details(Course course);
    }
}
