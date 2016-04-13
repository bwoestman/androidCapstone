package com.example.bwoestman.weatheralarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Brian Woestman on 4/12/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class AlarmReceiver extends BroadcastReceiver implements AppInfo
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "onReceive", Toast.LENGTH_SHORT).show();
    }
}
