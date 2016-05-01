package com.example.bwoestman.weatheralarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Brian Woestman on 4/12/16.
 * Android Programming
 * We 5:30p - 9:20p
 */

/**
 * this class is called when the app wakes up after being called by the alarm manager -
 * its purpose, on start, is to check the alarm that set the intent that called it and
 * then determine if the alarm needs to be set based on the precipitation.
 */

public class AlarmService extends Service implements AppInfo
{
    private SingletonAlarm singletonAlarm = SingletonAlarm.getInstance();

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        Log.d(TAG, "onStart: taskRunning");
        super.onStart(intent, startId);

        long id;
        SingletonAlarm singletonAlarm;
        Alarm alarm;
        ArrayList<Alarm> alarms;
        DBHandler db;
        AlarmController ac;

        id = (long) startId;
        singletonAlarm = SingletonAlarm.getInstance();

        db = new DBHandler(getApplicationContext(), null, null, 1);
        alarm = db.getAlarm(id);

        alarms = (ArrayList<Alarm>) db.getAlarms();

        ac = new AlarmController();

        ac.createAlarmCalendar(alarm);

        singletonAlarm.setServiceAlarm(alarm);
        singletonAlarm.setAlarms(alarms);

        Intent dialogIntent = new Intent(this, MainActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_SHORT).show();

        return super.onUnbind(intent);
    }
}
