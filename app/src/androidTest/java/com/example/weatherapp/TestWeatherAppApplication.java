package com.example.weatherapp;

public class TestWeatherAppApplication extends WeatherAppApplication {

    @Override
    protected Object[] getModules() {
        Object[] modules = {new WeatherAppModule(this), new TestWeatherAppModule(this)};
        return modules;
    }
}
