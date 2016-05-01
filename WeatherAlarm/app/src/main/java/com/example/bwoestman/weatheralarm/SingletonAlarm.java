package com.example.bwoestman.weatheralarm;

import java.util.ArrayList;

/**
 * Created by Brian Woestman on 3/1/16.
 * Android Programming
 * We 5:30p - 9:20p
 */

/**
 * this class is used to store data while the application is running
 */
public class SingletonAlarm
{
    private static SingletonAlarm ourInstance = new SingletonAlarm();
    private ArrayList<Alarm> alarms;
    private Alarm clickedAlarm;
    private Alarm serviceAlarm;
    private Integer clickedAlarmPosition = null;
    private double currPrecip;
    private boolean isSinglePane;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SingletonAlarm getInstance()
    {
        return ourInstance;
    }

    private SingletonAlarm()
    {
    }

    /**
     * Gets alarms.
     *
     * @return the alarms
     */
    public ArrayList<Alarm> getAlarms()
    {
        return alarms;
    }

    /**
     * Sets alarms.
     *
     * @param alarms the alarms
     */
    public void setAlarms(ArrayList<Alarm> alarms)
    {
        this.alarms = alarms;
    }

    /**
     * Gets clicked alarm position.
     *
     * @return the clicked alarm position
     */
    public Integer getClickedAlarmPosition()
    {
        return clickedAlarmPosition;
    }

    /**
     * Gets clicked alarm.
     *
     * @return the clicked alarm
     */
    public Alarm getClickedAlarm()
    {
        return clickedAlarm;
    }

    /**
     * Sets clicked alarm.
     *
     * @param clickedAlarm the clicked alarm
     */
    public void setClickedAlarm(Alarm clickedAlarm)
    {
        this.clickedAlarm = clickedAlarm;
    }

    /**
     * Gets curr precip.
     *
     * @return the curr precip
     */
    public double getCurrPrecip()
    {
        return currPrecip;
    }

    /**
     * Sets curr precip.
     *
     * @param currPrecip the curr precip
     */
    public void setCurrPrecip(double currPrecip)
    {
        this.currPrecip = currPrecip;
    }

    /**
     * Gets service alarm.
     *
     * @return the service alarm
     */
    public Alarm getServiceAlarm()
    {
        return serviceAlarm;
    }

    /**
     * Sets service alarm.
     *
     * @param serviceAlarm the service alarm
     */
    public void setServiceAlarm(Alarm serviceAlarm)
    {
        this.serviceAlarm = serviceAlarm;
    }

    /**
     * Gets screen size.
     *
     * @return the screen size
     */
    public boolean getIsSinglePane()
    {
        return isSinglePane;
    }

    /**
     * Sets screen size.
     *
     * @param isSinglePane the screen size
     */
    public void setIsSinglePane(boolean isSinglePane)
    {
        this.isSinglePane = isSinglePane;
    }

}
