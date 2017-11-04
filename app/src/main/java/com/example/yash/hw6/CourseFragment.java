package com.example.yash.hw6;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    ArrayList<Instructor> instructors = new ArrayList<>();

    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //instructors =  ArrayList(Realm.where(Instructor.class).findAll());
        courseViewList = (RecyclerView)getView().findViewById(R.id.coursedesc);
        //MainActivity.trackResultsList.add(1);
        mLayoutManager = new LinearLayoutManager(getActivity());
        //recycleViewList.setLayoutManager(mLayoutManager);
        courseViewList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //recycleViewList.setHasFixedSize(false);
        courseAdapter = new CourseAdapter(instructors,getActivity());
        courseViewList.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();
    }
    public CourseFragment(ArrayList<Instructor> instructors) {
        this.instructors = instructors;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

}
