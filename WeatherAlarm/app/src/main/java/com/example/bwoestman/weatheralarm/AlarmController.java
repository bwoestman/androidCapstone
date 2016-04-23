package com.example.bwoestman.weatheralarm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.security.KeyStore;
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
        int hour;
        int min;

        hour = alarm.getHour();
        min = alarm.getMinute();

        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);

        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, min);
        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        activity.startActivity(alarmIntent);

        Log.d(TAG, "createAlarm: alarmSet" + hour + ":" + min);
    }

    public void createAlarmCalendar(Alarm alarm)
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

        alarm.setCalendar(calendar);
    }

    public void createTimedTask(Context context, Alarm alarm)
    {
        Calendar cal = alarm.getCalendar();
        int _id = alarm.get_id().intValue();

        //todo remove this string
        String intentAlert = _id + " intent was saved";

        Intent intent = new Intent(context, AlarmService.class);

        pendingIntent = PendingIntent.getService(context, _id, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context
                .ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        Log.d(TAG, "createTimedTask: " + intentAlert);
    }

    /**
     * this function checks that the alarm is set for the future and adds 24 hours
     * if the time has already passed
     *
     * @param alarm
     */

    public void validateAlarmTime(Alarm alarm)
    {
        Calendar alarmCal;

        Long calTimeMs;
        Long dif;

        alarmCal = alarm.getCalendar();

        //calendar time in ms
        calTimeMs = alarmCal.getTimeInMillis();

        //difference between alarm and current time
        dif = calTimeMs - System.currentTimeMillis();

        //check if alarm is set for the future and push up 24 hours if it has
        //passed already
        if (dif < 0)
        {
            alarmCal.add(Calendar.HOUR, 24);
        }
    }

    public boolean validateAlarmAdj(Alarm alarm)
    {
        Calendar alarmCal;

        long calTimeMs;
        long adjInMs;
        long currentTimeInMs;
        long dif;
        long adjDiff;

        alarmCal = alarm.getCalendar();
        adjInMs = (long) alarm.getAdjustment() * 60 * 1000;
        calTimeMs = alarmCal.getTimeInMillis();
        currentTimeInMs = System.currentTimeMillis();

        dif = calTimeMs - currentTimeInMs;
        adjDiff = dif - adjInMs;

        return (adjDiff * 1000 * 60) > 0;
    }

    public boolean setAlarm(Alarm alarm)
    {
        long adjMs;
        long calTimeMs;
        long currTime;
        long adjustedCalTimeMs;
        long decideTime;

        calTimeMs = alarm.getCalendar().getTimeInMillis();
        adjMs = alarm.getAdjustment() * 60 * 1000;
        currTime = System.currentTimeMillis();

        adjustedCalTimeMs = calTimeMs - adjMs;

        decideTime = adjustedCalTimeMs - currTime;

        return decideTime < 600000;
    }

    public void checkPrecipThreshold(Alarm alarm)
    {
        AlarmWeather aw = new AlarmWeather();
        double rain;
        double currentPrecip;

        rain = alarm.getRain() / 100;
        aw.getCurrentWeatherForecast();
        currentPrecip = aw.getCurrentPrecip();

        if (rain < currentPrecip)
        {
            Log.d(TAG, "timeToSetAlarm: " + rain);
        }
    }
}
