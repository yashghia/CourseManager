package com.example.yash.hw6;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    Realm realm;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,new RegisterFragment(),"register")
                        .addToBackStack(null)
                        .commit();
            }
        });

        getView().findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                try{
                    realm = Realm.getDefaultInstance();
                    final String username = ((EditText)getView().findViewById(R.id.username)).getText().toString();
                    final String password = ((EditText)getView().findViewById(R.id.password)).getText().toString();
                    if(username==null||password==null||password.isEmpty()||username.isEmpty())
                    {
                        Toast.makeText(getActivity(),"Please enter your credentials",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.d("password ", password);
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                final User user = realm.where(User.class).equalTo("userName", username).findFirst();
                                if (user != null && user.getPassword().equals(password)) {
                                    Log.d("user", "fname " + user.getFirstName() + " lname " + user.getLastName() + " uname " + user.getUserName() + " password " + user.getPassword());
                                    ArrayList<Instructor> instructors =  new ArrayList(Realm.where(Instructor.class).findAll());
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.container, new CourseFragment(instructors), "courses")
                                            .commit();
                                    Toast.makeText(getActivity(), "Login Successful, Welcome " + user.getFirstName(), Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getActivity(), "Invalid Credentials, Please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),"Error :"+e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("login error",e.getMessage());
                }
                finally {
                    realm.close();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

}
