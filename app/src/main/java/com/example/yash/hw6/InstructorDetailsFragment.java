package com.example.yash.hw6;


import android.annotation.SuppressLint;
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
public class InstructorDetailsFragment extends Fragment {

    Instructor instructor;
    public InstructorDetailsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InstructorDetailsFragment(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((TextView)getView().findViewById(R.id.insName)).setText(instructor.getFname());
        ((TextView)getView().findViewById(R.id.insEmail)).setText(instructor.getEmail());
        ((TextView)getView().findViewById(R.id.insWebsite)).setText(instructor.getWebsite());
        ((ImageView)getView().findViewById(R.id.insPic))
                .setImageBitmap(BitmapFactory
                        .decodeByteArray(instructor.getPic(),0,instructor.getPic().length));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor_details, container, false);
    }

}
