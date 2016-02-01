package com.example.weatherapp;

public class TestWeatherAppApplication extends WeatherAppApplication {

    @Override
    protected Object[] getModules() {
        Object[] modules = {new TestWeatherAppModule(this)};
        return modules;
    }
}
