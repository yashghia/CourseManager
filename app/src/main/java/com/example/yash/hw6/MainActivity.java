package com.example.yash.hw6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        getFragmentManager().beginTransaction().add(R.id.container,new LoginFragment(),"login").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                getFragmentManager().beginTransaction().add(R.id.container,new LoginFragment(),"login").commit();
                return true;
            case R.id.instructors:
                getFragmentManager().beginTransaction().add(R.id.container,new CourseFragment(),"coursedata").commit();
                return true;
            case R.id.addInstructors:
                getFragmentManager().beginTransaction().add(R.id.container,new CreateCourseFragment(),"createcourse").commit();
                return true;
            case R.id.logout:
                getFragmentManager().beginTransaction().add(R.id.container,new LoginFragment(),"login").commit();
                return true;
            case R.id.exit:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
