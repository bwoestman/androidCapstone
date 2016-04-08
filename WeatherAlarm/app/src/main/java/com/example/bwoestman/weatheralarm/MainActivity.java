package com.example.bwoestman.weatheralarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.johnhiott.darkskyandroidlib.*;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements AppInfo
{
    private ArrayList<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
//        DBHandler db = new DBHandler(this, null, null, 1);
//        db.deleteAllAlarms();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlarmWeather weather = new AlarmWeather();
        weather.getCurrentWeatherForcast();

        if (findViewById(R.id.fragment_container) != null)
        {
            if (savedInstanceState == null)
            {
                AlarmListFragment tempFragment = new AlarmListFragment();
                tempFragment.setArguments(getIntent().getExtras());

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, tempFragment)
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.add_alarm:
                SingletonAlarm.getInstance().setClickedAlarm(null);
                goToEditView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToEditView()
    {
        AlarmEditFragment aef = new AlarmEditFragment();

        android.support.v4.app.FragmentTransaction transaction = this
                .getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, aef)
                .addToBackStack(null)
                .commit();
    }
}