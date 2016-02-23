package com.example.weatherapp;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

public class TestWeatherAppTestRule extends ActivityTestRule{

    @Inject
    TestWeatherAppServer server;

    public TestWeatherAppTestRule(Class activityClass) {
        this(activityClass, true, true);
    }

    public TestWeatherAppTestRule(Class activityClass, boolean initialTouchMode, boolean launchActivity) {

        super(activityClass, initialTouchMode, launchActivity);
        TestWeatherAppApplication.getInstance().inject(this);
    }

    @Override
    protected void beforeActivityLaunched() {

        Log.d("READ THIS", " Instance created");
        try {
            server.getMockWebServer().start(8080);
            Log.d("READ THIS", " Server started");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("READ THIS", " Could not start server");
        }
        super.beforeActivityLaunched();
    }

    @Override
    protected void afterActivityFinished() {

        try {
            server.getMockWebServer().shutdown();
            Log.d("READ THIS", " Server stopped");
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.afterActivityFinished();
    }
}
