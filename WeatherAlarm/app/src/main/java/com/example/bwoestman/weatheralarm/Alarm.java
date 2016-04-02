package com.example.bwoestman.weatheralarm;

import android.app.Activity;
import android.content.Intent;
import android.provider.AlarmClock;

/**
 * Created by Brian Woestman on 2/28/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class Alarm
{
    private Long _id;
    private Integer hour;
    private Integer minute;
    private Integer rain;
    private Integer adjustment;
    private Intent alarmIntent;

    public Alarm(){}

    public Alarm (Long _id, Integer hour, Integer minute, Integer rain, Integer
            adjustment)
    {
        this.Alarm();
        this._id = _id;
        this.hour = hour;
        this.minute = minute;
        this.rain = rain;
        this.adjustment = adjustment;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long get_id()
    {
        return _id;
    }

    /**
     * Sets id.
     *
     * @param _id the id
     */
    public void set_id(Long _id)
    {
        this._id = _id;
    }

    /**
     * Alarm.
     */
    public void Alarm(){}

    /**
     * Gets hour.
     *
     * @return the hour
     */
    public Integer getHour()
    {
        return hour;
    }

    /**
     * Sets hour.
     *
     * @param hour the hour
     */
    public void setHour(Integer hour)
    {
        this.hour = hour;
    }

    /**
     * Gets minute.
     *
     * @return the minute
     */
    public Integer getMinute()
    {
        return minute;
    }

    /**
     * Sets minute.
     *
     * @param minute the minute
     */
    public void setMinute(Integer minute)
    {
        this.minute = minute;
    }

    /**
     * Gets rain.
     *
     * @return the rain
     */
    public Integer getRain()
    {
        return rain;
    }

    /**
     * Sets rain.
     *
     * @param rain the rain
     */
    public void setRain(Integer rain)
    {
        this.rain = rain;
    }

    /**
     * Gets adjustment.
     *
     * @return the adjustment
     */
    public Integer getAdjustment()
    {
        return adjustment;
    }

    /**
     * Sets adjustment.
     *
     * @param adjustment the adjustment
     */
    public void setAdjustment(Integer adjustment)
    {
        this.adjustment = adjustment;
    }

    /**
     * Gets alarm intent.
     *
     * @return the alarm intent
     */
    public Intent getAlarmIntent()
    {
        return alarmIntent;
    }

    /**
     * Sets alarm intent.
     *
     * @param alarmIntent the alarm intent
     */
    public void setAlarmIntent(Intent alarmIntent)
    {
        this.alarmIntent = alarmIntent;
    }

    /**
     * Create alarm.
     *
     * @param activity the activity
     */
    public void createAlarm(Activity activity)
    {
        alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, minute);
        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        activity.startActivity(alarmIntent);
    }

    public String toString()
    {
        return "hour: " + hour + ", minute: " + minute + ", adjustment: " +
                adjustment + ", rain: " + rain;
    }
}
