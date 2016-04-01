package com.example.bwoestman.weatheralarm;

import java.util.ArrayList;

/**
 * Created by Brian Woestman on 3/1/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class SingletonAlarm
{
    private static SingletonAlarm ourInstance = new SingletonAlarm();
    private ArrayList<Alarm> alarms;
    private Integer clickedAlarm = null;

    public static SingletonAlarm getInstance()
    {
        return ourInstance;
    }

    private SingletonAlarm()
    {
    }

    public ArrayList<Alarm> getAlarms()
    {
        return alarms;
    }

    public void setAlarms(ArrayList<Alarm> alarms)
    {
        this.alarms = alarms;
    }

    public Integer getClickedAlarm()
    {
        return clickedAlarm;
    }

    public void setClickedAlarm(Integer clickedAlarm)
    {
        this.clickedAlarm = clickedAlarm;
    }

    public void addAlarm(Alarm alarm)
    {
        alarms.add(alarm);
    }

    public void updateAlarms(Integer position, Alarm alarm)
    {
        alarms.set(position, alarm);
    }

    public void deleteAlarm(Integer position)
    {
        alarms.remove(position);
    }
}
