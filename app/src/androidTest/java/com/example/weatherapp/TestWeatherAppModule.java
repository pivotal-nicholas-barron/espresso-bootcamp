package com.example.weatherapp;


import android.util.Log;

import com.example.weatherapp.activities.DetailsActivityTest;
import com.example.weatherapp.activities.MainActivity;
import com.example.weatherapp.activities.MainActivityTest;
import com.example.weatherapp.adapters.ForecastAdapter;
import com.example.weatherapp.data.WeatherDBHelper;
import com.example.weatherapp.data.WeatherProvider;
import com.example.weatherapp.fragments.DetailsActivityFragment;
import com.example.weatherapp.fragments.MainActivityFragment;
import com.example.weatherapp.fragments.PreferenceFragment;
import com.example.weatherapp.services.ForecastRetrofitService;
import com.example.weatherapp.services.WeatherService;
import com.example.weatherapp.services.WeatherServiceTest;
import com.example.weatherapp.testUtils.IdlingWeatherResource;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module(includes = WeatherAppModule.class,
        overrides = true,
        library = true,
        injects = {
                IdlingWeatherResource.class,
                DetailsActivityTest.class,
                MainActivityTest.class
        })
public class TestWeatherAppModule {

    private final WeatherAppApplication app;

    @Provides
    @Singleton
    public android.app.Service provideWeatherService(){
        return new WeatherServiceTest();
    }

    public TestWeatherAppModule(WeatherAppApplication app) {
        this.app = app;
    }
/*
    @Provides
    @Singleton
    IdlingWeatherResource provideIdlingWeatherResource() {
        return new IdlingWeatherResource("Retrofit Resource", app.getApplicationContext());
    }



    @Provides
    @Singleton
    Retrofit provideRetrofit(IdlingWeatherResource resource) {
        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client((new OkHttpClient()).setDispatcher(new Dispatcher(resource)))
                .build();
    }
*/
}
