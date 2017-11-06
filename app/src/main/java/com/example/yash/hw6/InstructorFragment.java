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

public class InstructorFragment extends Fragment implements InstructorDescriptionAdaptor.IdeletedInstructor {

    Realm realm;
    RecyclerView instructorViewList ;
    InstructorDescriptionAdaptor instructorDescriptionAdaptor;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Instructor> instructors = new ArrayList<>();
    public InstructorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        realm = Realm.getDefaultInstance();
        final RealmResults<Instructor> instructorList = realm.where(Instructor.class).findAll();
        if(instructorList.size()==0){
            Toast.makeText(getActivity(),"No Instructors found in Database, Please add Instructor",Toast.LENGTH_LONG).show();
        }
        else {
            instructors.addAll(realm.copyFromRealm(instructorList));
            Log.d("instructordesc","size: "+instructors.size());
            instructorViewList = (RecyclerView) getView().findViewById(R.id.instdesc);
            mLayoutManager = new LinearLayoutManager(getActivity());
            instructorViewList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            instructorDescriptionAdaptor = new InstructorDescriptionAdaptor(instructors, InstructorFragment.this, InstructorFragment.this);
            instructorViewList.setAdapter(instructorDescriptionAdaptor);
            instructorDescriptionAdaptor.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor, container, false);
    }

    @Override
    public void deletedInstructorRefresh(ArrayList<Instructor> instructors) {
        instructorDescriptionAdaptor = new InstructorDescriptionAdaptor(instructors,InstructorFragment.this,InstructorFragment.this);
        instructorViewList.setAdapter(instructorDescriptionAdaptor);
        instructorDescriptionAdaptor.notifyDataSetChanged();
    }
}
