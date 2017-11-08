package com.example.yash.hw6;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddInstructorFragment extends Fragment {
    EditText fName, emailName, websiteName;
    Realm realm;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    ImageView image;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        image = (ImageView) getView().findViewById(R.id.instructorImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });
        getView().findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Do you really want to clear all inputs?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((EditText) getView().findViewById(R.id.ifname)).setText("");
                                ((EditText) getView().findViewById(R.id.email)).setText("");
                                ((EditText) getView().findViewById(R.id.website)).setText("");
                                image.setImageResource(R.mipmap.ic_launcher);
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
        getView().findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName = (EditText) getView().findViewById(R.id.ifname);
                emailName = (EditText) getView().findViewById(R.id.email);
                websiteName = (EditText) getView().findViewById(R.id.website);
                String emailText = emailName.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!fName.getText().toString().equals("") && !emailName.getText().toString().equals("") && !websiteName.getText().toString().equals("") && emailText.matches(emailPattern)) {
                    try {
                        realm = Realm.getDefaultInstance();
                        //RealmConfiguration config = new RealmConfiguration.Builder().name("instructor.realm").build();
                        //Realm.setDefaultConfiguration(config);
                        //final RealmResults<Instructor> Instructor = realm.where(Instructor.class).equalTo("fname", ((EditText) getView().findViewById(R.id.ifname)).getText().toString()).findAll();
                        //if (Instructor.size() == 0) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                byte[] bArray = new byte[0];
                                Instructor newInst = realm.createObject(Instructor.class);
                                newInst.setFname(((EditText) getView().findViewById(R.id.ifname)).getText().toString());
                                newInst.setEmail(((EditText) getView().findViewById(R.id.email)).getText().toString());
                                newInst.setWebsite(((EditText) getView().findViewById(R.id.website)).getText().toString());
                                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                                if (image != null) {
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                                    bArray = bos.toByteArray();
                                    newInst.setPic(bArray);
                                } else {
                                    newInst.setPic(bArray);
                                }
                            }
                        });
                        Toast.makeText(getActivity(), "Instructor has been added successfully", Toast.LENGTH_LONG).show();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, new CourseFragment(), "courses")
                                .commit();
                    /*}
                    else{
                        Toast.makeText(getActivity(),"Instructor name already exists",Toast.LENGTH_LONG).show();
                    }*/
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    } finally {
                        realm.close();
                    }
                }else {
                    Toast.makeText(getActivity(), "Please make sure to enter valid email and all the details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public AddInstructorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_instructor, container, false);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data.getExtras() != null) {
                    Bitmap pic = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(pic);
                } else {
                    image.setImageResource(R.mipmap.ic_launcher);
                }
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getActivity(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getActivity(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}
