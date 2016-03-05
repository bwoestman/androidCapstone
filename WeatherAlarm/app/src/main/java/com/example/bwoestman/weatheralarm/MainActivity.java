package com.example.bwoestman.weatheralarm;

import android.annotation.TargetApi;
import android.os.Build;
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

public class MainActivity extends AppCompatActivity implements AppInfo
{
    private ArrayList<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ForecastApi.create(API_KEY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Log.d(TAG, "success: " + weatherResponse.getCurrently().getPrecipProbability());
            }

            @Override
            public void failure(RetrofitError error)
            {
                Log.d(TAG, "failure: Oh, shit!" + error);
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