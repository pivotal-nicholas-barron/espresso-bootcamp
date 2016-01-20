package com.example.weatherapp;

import com.example.weatherapp.activities.DetailsActivityTest;
import com.example.weatherapp.activities.MainActivityTest;

import dagger.Module;

@Module(injects = {
        MainActivityTest.class,
        DetailsActivityTest.class,
        TestWeatherAppApplication.class,
        WeatherAppModule.class
})
public class TestWeatherAppModule {

    public TestWeatherAppModule(TestWeatherAppApplication app){

    }
}
