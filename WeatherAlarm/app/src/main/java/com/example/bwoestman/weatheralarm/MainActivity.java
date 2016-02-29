package com.example.bwoestman.weatheralarm;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AppInfo
{
    private ArrayList<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null)
        {
            if (savedInstanceState == null)
            {
                AlarmEditFragment alarmEditFragment = new AlarmEditFragment();
                alarmEditFragment.setArguments(getIntent().getExtras());

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, alarmEditFragment)
                        .commit();
            }
        }
    }
}