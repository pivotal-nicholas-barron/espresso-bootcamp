package com.example.weatherapp;

import dagger.ObjectGraph;

public class TestWeatherAppApplication extends WeatherAppApplication {

    protected ObjectGraph objectGraph;
    static WeatherAppApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        objectGraph = ObjectGraph.create(getModules());
        inject(this);
    }

    @Override
    protected Object[] getModules() {
        final Object[] modules = new Object[2];
        modules[0] = new WeatherAppModule(this);
        modules[1] = new TestWeatherAppModule(this);
        return modules;
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    public static WeatherAppApplication getInstance() {
        return application;
    }
}
