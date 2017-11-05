package com.example.yash.hw6;


import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseDetailsFragment extends Fragment implements CourseAdapter.ICourseDetails{

    Course course = new Course();

    public CourseDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((TextView)getView().findViewById(R.id.title)).setText(course.getTitle());
        ((TextView)getView().findViewById(R.id.instructorName)).setText(course.getInstName());
        ((TextView)getView().findViewById(R.id.day)).setText(course.getDay());
        ((TextView)getView().findViewById(R.id.time)).setText(course.getHours()+":"+course.getMinutes()+" "+course.getTime());
        ((TextView)getView().findViewById(R.id.semester)).setText(course.getSem());
        ((TextView)getView().findViewById(R.id.credit)).setText(course.getCreditHours());
        ((ImageView)getView().findViewById(R.id.instructorPic))
                .setImageBitmap(BitmapFactory
                        .decodeByteArray(course.getInstPic(),0,course.getInstPic().length));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_details, container, false);
    }

    @Override
    public void details(Course course) {
        this.course = course;
    }
}
