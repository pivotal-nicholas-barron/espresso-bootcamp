package com.example.weatherapp;


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
import com.example.weatherapp.testUtils.IdlingWeatherResource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module(injects = {
            MainActivityFragment.class,
            WeatherAppApplication.class,
            PreferenceFragment.class,
            ForecastAdapter.class,
            DetailsActivityFragment.class,
            MainActivity.class,
            WeatherService.class,
            WeatherProvider.class,
            IdlingWeatherResource.class,
            DetailsActivityTest.class,
            MainActivityTest.class
        })
public class TestWeatherAppModule {

    private final WeatherAppApplication app;

    public TestWeatherAppModule(WeatherAppApplication app) {
        this.app = app;
    }

    //TODO find better workaround for overriding dagger dependencies, remove section below and update getModules() in app class

    @Provides
    @Singleton
    IdlingWeatherResource provideIdlingWeatherResource(){
        return new IdlingWeatherResource("Retrofit Resource", app.getApplicationContext());
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(this.provideIdlingWeatherResource())
                .build();
    }

    @Provides
    @Singleton
    ForecastRetrofitService provideForecastService(Retrofit retrofit) {
        return retrofit.create(ForecastRetrofitService.class);
    }

    @Provides
    @Singleton
    WeatherAppSharedPrefs providePreferenceManager() {
        return new WeatherAppSharedPrefs(app.getApplicationContext());
    }

    @Provides
    @Singleton
    WeatherDBHelper provideWeatherDBHelper() {
        return new WeatherDBHelper(app.getApplicationContext());
    }
}
