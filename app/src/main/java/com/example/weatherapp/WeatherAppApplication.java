package com.example.weatherapp;

import android.app.Application;
import android.util.Log;

import dagger.ObjectGraph;

public class WeatherAppApplication extends Application {
    private ObjectGraph objectGraph;
    private static WeatherAppApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        objectGraph = ObjectGraph.create(getModules());
    }

    protected Object[] getModules() {
        Object[] modules = {new WeatherAppModule(this)};
        return modules;
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    public static WeatherAppApplication getInstance() {
        return application;
    }
}
