package com.example.bwoestman.weatheralarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AppInfo
{
    private ArrayList<Alarm> alarms;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
//        DBHandler db = new DBHandler(this, null, null, 1);
//        db.deleteAllAlarms();

        createTimedTask();

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
    }

    public void setup()
    {
        Alarm alarm = new Alarm();
        alarm.setHour(23);
        alarm.setMinute(3);

        AlarmController ac = new AlarmController();
        createTimedTask();

        Log.d(TAG, "onDestroy: ");
    }

    public void createTimedTask()
    {
        Intent intent = new Intent(MainActivity.this, AlarmService.class);
        pendingIntent = PendingIntent.getService(MainActivity.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(MainActivity.this, "Start Alarm", Toast.LENGTH_SHORT).show();
    }

    public void createAlarmTask(Activity activity, Alarm alarm)
    {
        int hour = alarm.getHour();
        int min = alarm.getMinute();

        Calendar calendar = Calendar.getInstance();

        if (hour > 12)
        {
            calendar.set(Calendar.AM_PM, Calendar.PM);
            hour = hour - 12;
        }
        else
        {
            calendar.set(Calendar.AM_PM, Calendar.AM);
        }

        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        Long time = calendar.getTimeInMillis();

        Intent intent = new Intent(activity, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getService(activity, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context
                .ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

        Toast.makeText(activity, "Alarm scheduled for: " + time, Toast.LENGTH_LONG).show();
        Log.d(TAG, "createAlarmTask: systemTime " + System.currentTimeMillis());
        Log.d(TAG, "createAlarmTask: " + time);
    }
}