package com.example.weatherapp;

import android.util.Log;

import dagger.ObjectGraph;

public class TestWeatherAppApplication extends WeatherAppApplication {

    @Override
    protected Object[] getModules() {
        Object[] modules = {new WeatherAppModule(this), new TestWeatherAppModule(this)};
        return modules;
    }
}
