/*

 */
package com.example.weatherapp.testUtils;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

import com.example.weatherapp.services.WeatherService;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IdlingWeatherResource implements IdlingResource {

    private String name;
    private ResourceCallback callback;
    private Context context;
//    private BroadcastReceiver intentListener;

    public IdlingWeatherResource(String name, Context context) {
        //super(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() + 1, 100000, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
        this.name = name;
        this.context = context;
        /*
        intentListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                callback.onTransitionToIdle();
                idle = true;
            }
        };
        context.registerReceiver(intentListener, new IntentFilter(WeatherService.SERVICE_RESULT));
        */
    }

    /*
        @Override
        protected void beforeExecute(Thread t, Runnable r) {

            super.beforeExecute(t, r);
            idle = false;
        }
    */
    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public boolean isIdleNow() {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo currentService : manager.getRunningServices(30)) {
            if (currentService.service.getClassName().equals(WeatherService.class.getName())) {
                return false;
            }
        }

        if (callback != null) {
            callback.onTransitionToIdle();
        }
        return true;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {

        this.callback = callback;
    }
}
