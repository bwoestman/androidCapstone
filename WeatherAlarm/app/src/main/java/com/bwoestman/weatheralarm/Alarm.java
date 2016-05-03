package com.bwoestman.weatheralarm;

import java.util.Calendar;

/**
 * Created by Brian Woestman on 2/28/16.
 * Android Programming
 * We 5:30p - 9:20p
 */

/**
 * this class is a pojo that is used to hold all the alarm data
 */
public class Alarm
{
    private Long _id;
    private Integer hour;
    private Integer minute;
    private Calendar calendar;
    private Integer rain;
    private Integer adjustment;
    private Integer enabled = 0;

    /**
     * Instantiates a new Alarm.
     */
    public Alarm(){}

    /**
     * Instantiates a new Alarm.
     *
     * @param _id        the id
     * @param hour       the hour
     * @param minute     the minute
     * @param rain       the rain
     * @param adjustment the adjustment
     * @param enabled    the enabled
     */
    public Alarm (Long _id, Integer hour, Integer minute, Integer rain, Integer
            adjustment, Integer enabled)
    {
        this();
        this._id = _id;
        this.hour = hour;
        this.minute = minute;
        this.rain = rain;
        this.adjustment = adjustment;
        this.enabled = enabled;
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
     * Gets calendar.
     *
     * @return the calendar
     */
    public Calendar getCalendar()
    {
        return calendar;
    }


    /**
     * Sets calendar.
     *
     * @param calendar the calendar
     */

    public void setCalendar(Calendar calendar)
    {
        this.calendar = calendar;
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
     * Gets enabled.
     *
     * @return the enabled
     */
    public Integer getEnabled()
    {
        return enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }

    /**
     * this method outputs the alarm properties as a string
     * @return String
     */
    public String toString()
    {
        return "_id:" + _id + "hour: " + hour + ", minute: " + minute + ", adjustment: " +
                adjustment + ", rain: " + rain + ", " + enabled;
    }
}
