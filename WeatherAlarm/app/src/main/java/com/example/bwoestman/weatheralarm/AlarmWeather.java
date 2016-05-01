package com.example.bwoestman.weatheralarm;

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
     * this method sends a request to DarkSky and returns the current weather forecast
     * for Madison, WI and saves chance of precipitation to the AlarmSingleton
     */
    public void getCurrentWeatherForecast()
    {
        ForecastApi.create(API_KEY);

        RequestBuilder weather = new RequestBuilder();
        final Request request = new Request();

        request.setLat("43.8014");
        request.setLng("-90.2396");
        request.setUnits(Request.Units.US);
        request.setLanguage(Request.Language.ENGLISH);

        /**
         * anonymous class that gets the weather from DarkSky api and saves the
         * precipitation data in the singleton
         */

        weather.getWeather(request, new Callback<WeatherResponse>()
        {
            @Override
            public void success(WeatherResponse weatherResponse, Response response)
            {
                double currentPrecip = Double.valueOf(weatherResponse.getCurrently()
                        .getPrecipProbability());

                SingletonAlarm singletonAlarm = SingletonAlarm.getInstance();
                singletonAlarm.setCurrPrecip(currentPrecip);
            }

            @Override
            public void failure(RetrofitError error)
            {
            }
        });
    }
}
