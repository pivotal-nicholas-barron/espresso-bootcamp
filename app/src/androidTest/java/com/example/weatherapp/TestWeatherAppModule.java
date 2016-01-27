package com.example.weatherapp;


import com.example.weatherapp.activities.MainActivityTest;

import dagger.Module;

@Module(includes = WeatherAppModule.class,
        injects = {
                MainActivityTest.class
        })
public class TestWeatherAppModule {

    private final WeatherAppApplication app;

    public TestWeatherAppModule(WeatherAppApplication app) {
        this.app = app;
    }


}
