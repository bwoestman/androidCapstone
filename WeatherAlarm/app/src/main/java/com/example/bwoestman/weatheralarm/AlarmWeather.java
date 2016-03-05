package com.example.bwoestman.weatheralarm;

import android.util.Log;
import com.johnhiott.darkskyandroidlib.ForecastApi;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Brian Woestman on 3/5/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class AlarmWeather implements AppInfo
{
    /**
     * The Current precip.
     */
    Integer currentPrecip = 1000;

    /**
     * Gets current weather forcast.
     */
    public void getCurrentWeatherForcast()
    {
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
                currentPrecip = Integer.valueOf(weatherResponse.getCurrently()
                        .getPrecipProbability());

                Log.d(TAG, "success: precip " + currentPrecip);
            }

            @Override
            public void failure(RetrofitError error)
            {
                Log.d(TAG, "failure: Oh, no!" + error);
            }
        });
    }

    /**
     * Gets current precip.
     *
     * @return the current precip
     */
    public Integer getCurrentPrecip()
    {
        return currentPrecip;
    }

    /**
     * Sets current precip.
     *
     * @param currentPrecip the current precip
     */
    public void setCurrentPrecip(Integer currentPrecip)
    {
        this.currentPrecip = currentPrecip;
    }
}
