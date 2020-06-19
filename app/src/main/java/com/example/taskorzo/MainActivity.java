package com.example.taskorzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    ChipNavigationBar bottomNavigationView;
    FloatingActionButton floatingActionButton;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Here for app to go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);




        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.addTaskButton);

        if (savedInstanceState == null) {
            bottomNavigationView.setItemSelected(R.id.nav_task, true);
            fragmentManager = getSupportFragmentManager();
            TaskFragment taskFragment = new TaskFragment();
            fragmentManager.beginTransaction().replace(R.id.main_frame, taskFragment).commit();
            floatingActionButton.show();
        }


        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;

                switch (id) {
                    case R.id.nav_task:
                        fragment = new TaskFragment();
                        floatingActionButton.show();
                        break;
                    case R.id.nav_habit:
                        fragment = new HabitFragment();
                        floatingActionButton.hide();
                        break;
                    case R.id.nav_skill:
                        fragment = new SkillFragment();
                        floatingActionButton.hide();
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


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTask.class);
                startActivity(intent);
            }
        });



    }

}