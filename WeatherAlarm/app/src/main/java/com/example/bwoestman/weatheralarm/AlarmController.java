package com.example.bwoestman.weatheralarm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Brian Woestman on 4/12/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class AlarmController implements AppInfo
{
    private PendingIntent pendingIntent;
    /**
     * Create alarm.
     *
     * @param activity the activity
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void createAlarm(Activity activity, Alarm alarm)
    {
        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);

        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, alarm.getHour());
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, alarm.getMinute());
        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        activity.startActivity(alarmIntent);
    }
}
