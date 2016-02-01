/*

 */
package com.example.weatherapp.testUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IdlingWeatherResource extends ThreadPoolExecutor implements IdlingResource {

    private String name;
    private ResourceCallback callback;
    private BroadcastReceiver intentListener;
    private boolean idle;

    public IdlingWeatherResource(String name, Context context){

        super(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() + 1, 100000, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
        this.name = name;
        intentListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                callback.onTransitionToIdle();
                idle = true;
            }
        };
        context.registerReceiver(intentListener, new IntentFilter("SERVICE_RESULT"));
        idle = true;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {

        super.beforeExecute(t, r);
        idle = false;
    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public boolean isIdleNow() {

        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {

        this.callback = callback;
    }
}
