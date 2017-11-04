package com.example.yash.hw6;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCourseFragment extends Fragment implements InstructorAdapter.IselectedInstructor, AdapterView.OnItemSelectedListener {

    Realm realm;
    Course course;
    static InstructorAdapter recycleAdapter;
    RecyclerView recycleViewList ;
    Spinner daySpinner;
    Spinner timeSpinner;
    Spinner semSpinner;
    String hours;
    String minutes;
    RadioGroup rg;
    String credithours;
    String day;
    String time;
    String sem;
    private RecyclerView.LayoutManager mLayoutManager;
    AlertDialog.Builder builder;
    public CreateCourseFragment() {
        // Required empty public constructor
    }

    public CreateCourseFragment(ArrayList<Instructor> instructors) {
        this.instructors = instructors;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_course, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        realm = Realm.getDefaultInstance();
        builder = new AlertDialog.Builder(getActivity());
        course = new Course();
        ArrayList<Instructor> instructors = new ArrayList<>();
        //RealmConfiguration config = new RealmConfiguration.Builder().name("instructor.realm").build();
        //Realm.setDefaultConfiguration(config);
        final RealmResults<Instructor> Instructor = realm.where(Instructor.class).findAll();
        if (Instructor.size() == 0) {
            ((TextView) getView().findViewById(R.id.noInstructor)).setVisibility(View.VISIBLE);
            ((Button) getView().findViewById(R.id.create)).setEnabled(false);
            ((Button) getView().findViewById(R.id.reset)).setEnabled(false);
        }
        else{
            //populate instructor recycler view
            recycleViewList = (RecyclerView)getView().findViewById(R.id.recyclerView);
            mLayoutManager = new LinearLayoutManager(getActivity());
            recycleViewList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
            instructors.addAll(realm.copyFromRealm(Instructor));
            recycleAdapter = new InstructorAdapter(instructors,getActivity(),CreateCourseFragment.this);
            recycleViewList.setAdapter(recycleAdapter);
            recycleAdapter.notifyDataSetChanged();

            daySpinner = (Spinner)getView().findViewById(R.id.dayspinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
            R.array.days, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            daySpinner.setAdapter(adapter);

            timeSpinner = (Spinner)getView().findViewById(R.id.timespinner);
            adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.time, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timeSpinner.setAdapter(adapter);

            semSpinner = (Spinner)getView().findViewById(R.id.semesterspinner);
            adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.semester, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            semSpinner.setAdapter(adapter);

            hours = ((EditText)getView().findViewById(R.id.hours)).getText().toString();
            minutes = ((EditText)getView().findViewById(R.id.minutes)).getText().toString();
            rg = (RadioGroup)getView().findViewById(R.id.radioGroup);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId == R.id.one) {
                        credithours = "1";
                    } else if(checkedId == R.id.two) {
                        credithours = "2";
                    } else {
                        credithours = "3";
                    }
                }
            });

            getView().findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    course.setTitle(((EditText)getView().findViewById(R.id.editText)).getText().toString());
                    course.setDay(day);
                    course.setHours(hours);
                    course.setMinutes(minutes);
                    course.setCreditHours(credithours);
                    course.setSem(sem);
                    realm.copyToRealmOrUpdate(course);
                }
            });
        }
        getView().findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Do you really want to clear all inputs?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((EditText)getView().findViewById(R.id.hours)).setText("");
                                ((EditText)getView().findViewById(R.id.minutes)).setText("");
                                ((EditText)getView().findViewById(R.id.editText)).setText("");
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("delete","donot delete");
                    }
                }).setCancelable(false);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public void selected(Instructor instructor) {
        course.setInstName(instructor.getFname());
        course.setInstPic(instructor.getPic());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==daySpinner.getId()){
            day = parent.getItemAtPosition(position).toString();
        } else if(parent.getId()==timeSpinner.getId()){
            time = parent.getItemAtPosition(position).toString();
        } else if(parent.getId()==semSpinner.getId()){
            sem = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
