package com.example.bwoestman.weatheralarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Brian Woestman on 4/12/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class AlarmService extends Service implements AppInfo
{
    private SingletonAlarm singletonAlarm = SingletonAlarm.getInstance();
    @Override
    public void onCreate()
    {
        super.onCreate();

        SingletonAlarm singletonAlarm = SingletonAlarm.getInstance();
        ArrayList<Alarm> alarms = singletonAlarm.getAlarms();
        AlarmController ac = new AlarmController();

        if (alarms != null)
        {

        }

        Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_SHORT).show();
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
        super.onStart(intent, startId);
        Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_SHORT).show();

        return super.onUnbind(intent);
    }
}
