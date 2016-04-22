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
        Calendar cal;
        int adjSec;
        Long calTimeMs;
        Long dif;
        int difSec;
        int adjDif;
        double currentPrecip;

        cal = alarm.getCalendar();
        adjSec = alarm.getAdjustment() * 60;
        calTimeMs = cal.getTimeInMillis();
        //difference between current time and alarm time
        dif = calTimeMs - System.currentTimeMillis();
        difSec = (int) (dif / 1000);
        //difference between adjustment minutes and time between now and when the alarm
        // is set for
        adjDif = difSec - adjSec;

        //if the alarm with adjustment is set for the future
        if (adjDif > 0)
        {
            Intent intent = new Intent(context, AlarmService.class);

            pendingIntent = PendingIntent.getService(context, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context
                    .ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            calendar.add(Calendar.SECOND, difSec);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            Log.d(TAG, "createTimedTask: " + difSec);
        }
        else if (difSec < 0)
        {

        }
        else
        {
            DBHandler db = new DBHandler(context, null, null, 1);
            db.deleteAlarm(alarm);
        }
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
