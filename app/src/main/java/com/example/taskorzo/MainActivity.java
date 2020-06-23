package com.example.taskorzo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity implements AddTaskDialog.AddTaskDialogListner {
    FragmentManager fragmentManager;
    ChipNavigationBar bottomNavigationView;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Here for app to go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);




        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            bottomNavigationView.setItemSelected(R.id.nav_task, true);
            fragmentManager = getSupportFragmentManager();
            TaskFragment taskFragment = new TaskFragment();
            fragmentManager.beginTransaction().replace(R.id.main_frame, taskFragment).commit();
        }


        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;

                switch (id) {
                    case R.id.nav_task:
                        fragment = new TaskFragment();
                        break;
                    case R.id.nav_habit:
                        fragment = new HabitFragment();
                        break;
                    case R.id.nav_skill:
                        fragment = new SkillFragment();
                        break;

                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();

                } else {
                    Log.i("NAVIGATION", "Error creating in Fragment");
                }
            }
        });





    }

    @Override
    public void applyTexts(String title, String description) {
        Log.i("DataMainActivity", title + " " + description);
    }
}