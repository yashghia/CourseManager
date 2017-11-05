package com.example.yash.hw6;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends Fragment {
    RecyclerView courseViewList ;
    static CourseAdapter courseAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Course> courses = new ArrayList<>();
    Realm realm;
    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        realm = Realm.getDefaultInstance();
        final RealmResults<Course> courseList = realm.where(Course.class).findAll();
        if(courseList.size()==0){
            Toast.makeText(getActivity(),"No Courses found in Database, Please add Course",Toast.LENGTH_LONG).show();
        }
        else {
            courses.addAll(realm.copyFromRealm(courseList));
            Log.d("coursedesc","size: "+courses.size());
            courseViewList = (RecyclerView) getView().findViewById(R.id.coursedesc);
            mLayoutManager = new LinearLayoutManager(getActivity());
            courseViewList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            courseAdapter = new CourseAdapter(courses, CourseFragment.this);
            courseViewList.setAdapter(courseAdapter);
            courseAdapter.notifyDataSetChanged();

            courseViewList.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });
        }

        getView().findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new CreateCourseFragment(), "createcourse")
                        .commit();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }
}
