package com.example.weatherapp.testUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.test.espresso.IdlingResource;

public class IdlingWeatherResource implements IdlingResource {

    private String name;
    private ResourceCallback callback;
    private BroadcastReceiver intentListener;

    public IdlingWeatherResource(String name, Context context){

        this.name = name;

        intentListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                callback.onTransitionToIdle();
            }
        };
        register(context);
    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public boolean isIdleNow() {

        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {

        this.callback = callback;
    }

    public void register(Context context){

        context.registerReceiver(intentListener, new IntentFilter("SERVICE_RESULT"));
    }

    //SERVICE_RESULT -> success or failure for WeatherService
}
