package com.example.yash.hw6;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCourseFragment extends Fragment {
    Realm realm;
    Spinner daySpinner,timeSpinner,semesterSpinner;
    RecyclerView selectInstructList ;
    static CourseSelectAdapter selectAdapter;
    ArrayList<Instructor> instructors = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
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
        selectInstructList = (RecyclerView)getView().findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        selectInstructList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //recycleViewList.setHasFixedSize(false);
        selectAdapter = new CourseSelectAdapter(instructors,getActivity());
        selectInstructList.setAdapter(selectAdapter);
        selectAdapter.notifyDataSetChanged();
        getView().findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    realm = realm.getDefaultInstance();
                    RealmConfiguration realmConfig = new RealmConfiguration.Builder().name("coursedetails.realm").build();
                    Realm.setDefaultConfiguration(realmConfig);
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            byte[] bArray = new byte[0];
                            Instructor newInst = realm.createObject(Instructor.class);
                            daySpinner = (Spinner) getView().findViewById(R.id.dayspinner);
                            timeSpinner = (Spinner) getView().findViewById(R.id.timespinner);
                            semesterSpinner = (Spinner) getView().findViewById(R.id.semesterspinner);
                            CourseDetails courseDetails = realm.createObject(CourseDetails.class);
                            courseDetails.setTitle(((EditText) getView().findViewById(R.id.titleTextView)).getText().toString());
                            String daytext = daySpinner.getSelectedItem().toString();
                            String timetext = timeSpinner.getSelectedItem().toString();
                            String semestertext = semesterSpinner.getSelectedItem().toString();
                            courseDetails.setDay(daytext);
                            courseDetails.setTime(timetext);
                            courseDetails.setSemester(semestertext);
//                            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
//                            if (image != null) {
//                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                                bArray = bos.toByteArray();
//                                newInst.setPic(bArray);
//                            } else {
//                                newInst.setPic(bArray);
//                            }
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
                finally {
                    realm.close();
                }
            }
        });

    }
}
