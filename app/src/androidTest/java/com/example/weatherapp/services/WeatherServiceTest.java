package com.example.weatherapp.services;


import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.util.Log;

import javax.inject.Inject;

public class WeatherServiceTest extends WeatherService{
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w("yolo", "i am in");
        super.onHandleIntent(intent);
    }
}
