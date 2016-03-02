package com.example.bwoestman.weatheralarm;

/**
 * Created by Brian Woestman on 2/28/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class Alarm
{
    private Integer hour;
    private Integer minute;
    private Integer rain;
    private Integer adjustment;

    public void Alarm(){}

    public Integer getHour()
    {
        return hour;
    }

    public void setHour(Integer hour)
    {
        this.hour = hour;
    }

    public Integer getMinute()
    {
        return minute;
    }

    public void setMinute(Integer minute)
    {
        this.minute = minute;
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

    public String toString()
    {
        String string = "hour " + hour + ", minute " + minute + ", adjustment " +
                adjustment + ", rain " + rain;
        return string;
    }
}
