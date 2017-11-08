package com.example.yash.hw6;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    Realm realm;
    EditText firstName, lastName, userName, password;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    ImageView image;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        image = (ImageView) getView().findViewById(R.id.imageView);
        realm = Realm.getDefaultInstance();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });
        getView().findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = (EditText) getView().findViewById(R.id.fname);
                lastName = (EditText) getView().findViewById(R.id.lname);
                userName = (EditText) getView().findViewById(R.id.uname);
                password = (EditText) getView().findViewById(R.id.pword);
                if (!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") && !userName.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    try {
                        //realm = Realm.getDefaultInstance();
                        //RealmConfiguration config = new RealmConfiguration.Builder().name("login.realm").build();
                        //Realm.setDefaultConfiguration(config);
                        final RealmResults<User> users = realm.where(User.class).equalTo("userName", ((EditText) getView().findViewById(R.id.uname)).getText().toString()).findAll();
                        if (users.size() == 0) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    byte[] bArray = new byte[0];
                                    User newUser = realm.createObject(User.class);
                                    newUser.setFirstName(firstName.getText().toString());
                                    newUser.setLastName(lastName.getText().toString());
                                    newUser.setUserName(userName.getText().toString());
                                    newUser.setPassword(password.getText().toString());

                                    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                                    if (image != null) {
                                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                                        bArray = bos.toByteArray();
                                        newUser.setPic(bArray);
                                    } else {
                                        newUser.setPic(bArray);
                                    }
                                }
                            });
                            Toast.makeText(getActivity(), "You have been registered succesfully", Toast.LENGTH_LONG).show();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, new CourseFragment(), "courses")
                                    .commit();
                            realm.close();
                        } else {
                            Toast.makeText(getActivity(), "Username Already exists", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    } finally {

                    }
                }else{
                    Toast.makeText(getActivity(), "Please enter all the details",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
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
