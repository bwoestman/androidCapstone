package com.example.bwoestman.weatheralarm;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.*;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AppInfo
{
    private ArrayList<Alarm> alarms;
    private PendingIntent pendingIntent;
    private AlarmController ac;
    private SingletonAlarm singletonAlarm = SingletonAlarm.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        DBHandler db = new DBHandler(this, null, null, 1);
        db.deleteAllAlarms();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlarmWeather weather = new AlarmWeather();
        weather.getCurrentWeatherForecast();

        if (findViewById(R.id.fragment_container) != null)
        {
            if (savedInstanceState == null)
            {
                AlarmListFragment tempFragment = new AlarmListFragment();
                tempFragment.setArguments(getIntent().getExtras());

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, tempFragment)
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.add_alarm:
                SingletonAlarm.getInstance().setClickedAlarm(null);
                goToEditView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToEditView()
    {
        AlarmEditFragment aef = new AlarmEditFragment();

        android.support.v4.app.FragmentTransaction transaction = this
                .getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, aef)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
//
//        alarms = singletonAlarm.getAlarms();
//
//        if (alarms != null)
//        {
//            for (Alarm a : alarms)
//            {
//                ac = new AlarmController();
//                ac.createAlarmCalendar(a);
//                ac.createTimedTask(this, a.getCalendar());
//            }
//        }
    }
}