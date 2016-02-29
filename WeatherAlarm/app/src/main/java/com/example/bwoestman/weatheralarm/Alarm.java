package com.example.bwoestman.weatheralarm;

import android.app.AlarmManager;

import java.util.Calendar;

/**
 * Created by Brian Woestman on 2/28/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class Alarm
{
    private Calendar calendar;
    private Integer rain;
    private Integer adjustment;

    public void Alarm(){}

    public void Alarm(Calendar calendar, Integer rain, Integer adjustment)
    {
        this.calendar = calendar;
        this.rain = rain;
        this.adjustment = adjustment;
    }


    public Calendar getCalendar()
    {
        return calendar;
    }

    public void setCalendar(Calendar calendar)
    {
        this.calendar = calendar;
    }

    public Integer getRain()
    {
        return rain;
    }

    public void setRain(Integer rain)
    {
        this.rain = rain;
    }

    public Integer getAdjustment()
    {
        return adjustment;
    }

    public void setAdjustment(Integer adjustment)
    {
        this.adjustment = adjustment;
    }
}
