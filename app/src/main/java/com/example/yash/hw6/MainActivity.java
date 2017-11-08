package com.example.yash.hw6;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "preferenceName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        getFragmentManager().beginTransaction().add(R.id.container,new LoginFragment(),"default").commit();
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
                getFragmentManager().beginTransaction().replace(R.id.container,new CourseFragment(),"course").commit();
                return true;
            case R.id.instructors:
                getFragmentManager().beginTransaction().replace(R.id.container,new InstructorFragment(),"instructor").commit();
                return true;
            case R.id.addInstructors:
                getFragmentManager().beginTransaction().replace(R.id.container,new AddInstructorFragment(),"addinst").commit();
                return true;
            case R.id.logout:
                getFragmentManager().beginTransaction().replace(R.id.container,new LoginFragment(),"login").commit();
                return true;
            case R.id.exit:
                finish();
                moveTaskToBack(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public static void setPreference(Context context, String key, Boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }

}
