package com.example.bwoestman.weatheralarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        // TODO: 3/5/16 Alarm testing this needs to be moved but is working
//        Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);
//        alarm.putExtra(AlarmClock.EXTRA_HOUR, 12);
//        alarm.putExtra(AlarmClock.EXTRA_MINUTES, 00);
//        alarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//        startActivity(alarm);

        // TODO: 3/5/16 Weather testing needs to be moved but is working
        ForecastApi.create(API_KEY);

        RequestBuilder weather = new RequestBuilder();

        final Request request = new Request();
        request.setLat("43.06");
        request.setLng("89.40");
        request.setUnits(Request.Units.US);
        request.setLanguage(Request.Language.ENGLISH);

        weather.getWeather(request, new Callback<WeatherResponse>()
        {
            @Override
            public void success(WeatherResponse weatherResponse, Response response)
            {
                Log.d(TAG, "success: Precip = " + weatherResponse.getCurrently()
                        .getPrecipProbability());
            }

            @Override
            public void failure(RetrofitError error)
            {
                Log.d(TAG, "failure: Oh, no!" + error);
            }
        });

        if (findViewById(R.id.fragment_container) != null)
        {
            if (savedInstanceState == null)
            {
                AlarmEditFragment alarmEditFragment = new AlarmEditFragment();
                alarmEditFragment.setArguments(getIntent().getExtras());

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, alarmEditFragment)
                        .commit();
            }
        }
    }
}