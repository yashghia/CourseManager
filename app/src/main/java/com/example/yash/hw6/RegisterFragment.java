package com.example.yash.hw6;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    Realm realm;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    realm = Realm.getDefaultInstance();
                    final RealmResults<User> users = realm.where(User.class).equalTo("userName", ((EditText) getView().findViewById(R.id.uname)).getText().toString()).findAll();
                    if (users.size() == 0) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                byte[] bArray = new byte[0];
                                User newUser = realm.createObject(User.class);
                                newUser.setFirstName(((EditText) getView().findViewById(R.id.fname)).getText().toString());
                                newUser.setLastName(((EditText) getView().findViewById(R.id.lname)).getText().toString());
                                newUser.setUserName(((EditText) getView().findViewById(R.id.uname)).getText().toString());
                                newUser.setPassword(((EditText) getView().findViewById(R.id.pword)).getText().toString());
                                ImageView image = (ImageView) getView().findViewById(R.id.imageView);
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
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, new CourseFragment(), "courses")
                                .commit();
                    }
                    else{
                        Toast.makeText(getActivity(),"Username Already exists",Toast.LENGTH_LONG).show();
                    }
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

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

}
