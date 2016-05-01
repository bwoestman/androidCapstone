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

/**
 * this class acts as the controller for all alarm activities including: setting
 * intents, validating alarm times and calculating adjustments and precipitation
 */

public class AlarmController implements AppInfo
{
    SingletonAlarm singletonAlarm = SingletonAlarm.getInstance();
    DBHandler dbHandler;
    /**
     * this method is used to set an AlarmClock to the time specified by the Alarm
     * parameter
     * @param activity
     * @param alarm
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void createAlarm(Activity activity, Alarm alarm)
    {
        if (alarm == null) return;

        int hour;
        int min;
        int merid;
        Calendar calendar;

        calendar = alarm.getCalendar();
        dbHandler = new DBHandler(activity.getApplicationContext(), null, null, 1);

        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);
        merid = calendar.get(Calendar.AM_PM);

        if (merid == 1)
        {
            hour = hour + 12;
        }

        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);

        alarm.setEnabled(1);
        dbHandler.updateAlarm(alarm);

        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, min);
        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        activity.startActivity(alarmIntent);
    }

    /**
     * this method saves a calendar to the Alarm parameter for the specified alarm time
     * @param alarm
     */

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

    /**
     * this method creates a timed task that will wake the application to set the
     * alarms at a future time
     * @param context
     * @param alarm
     */

    public void createTimedTask(Context context, Alarm alarm)
    {
        Calendar cal = alarm.getCalendar();
        int _id = alarm.get_id().intValue();
        dbHandler = new DBHandler(context, null, null, 1);

        alarm.setEnabled(1);

        dbHandler.updateAlarm(alarm);

        cal.add(Calendar.MINUTE, -10);

        Intent intent = new Intent(context, AlarmService.class);

        PendingIntent pendingIntent = PendingIntent.getService(context, _id, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context
                .ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    /**
     * this function checks that the alarm is set for the future and adds 24 hours
     * if the time has already expired
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
        //already expired
        if (dif < 0)
        {
            alarmCal.add(Calendar.HOUR, 24);
        }
    }

    /**
     * this method is used to check if the alarm adjustment will result in a valid
     * alarm time by checking the difference between the alarm time and the minute
     * value of the adjustment
     * @param alarm
     * @return
     */

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

    /**
     * this method checks the alarm time and the adjustment to determine if the alarm
     * should be set immediately
     * @param alarm
     * @return
     */

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

    /**
     * this method determines if the current chance of rain exceeds the Alarm property
     * for chance of rain
     * @param alarm
     * @return
     */

    public boolean exceedsPrecipThreshold(Alarm alarm)
    {
        AlarmWeather aw = new AlarmWeather();

        double rain;
        double currentPrecip;

        rain = alarm.getRain() / 100;
        aw.getCurrentWeatherForecast();
        currentPrecip = singletonAlarm.getCurrPrecip();

        return rain <= currentPrecip;
    }

    public void adjustAlarmCalendar(Alarm alarm)
    {
        int adj;
        Calendar calendar;

        adj = alarm.getAdjustment();
        calendar = alarm.getCalendar();

        calendar.add(Calendar.MINUTE, -adj);
        alarm.setCalendar(calendar);
    }
}
