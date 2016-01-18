package com.example.weatherapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.weatherapp.WeatherAppSharedPrefs;
import com.example.weatherapp.activities.DetailsActivity;
import com.example.weatherapp.data.WeatherContract;
import com.example.weatherapp.services.WeatherService;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {

    @Rule
    public ActivityTestRule<DetailsActivity> mActivityRule = new ActivityTestRule(DetailsActivity.class, false, false);

    @Inject
    WeatherAppSharedPrefs sharedPrefs = new WeatherAppSharedPrefs(getTargetContext());

    @Before
    public void setUp() {

        getTargetContext().getSharedPreferences("com.example.weatherapp_preferences", 0).edit().clear().commit();
        sharedPrefs.setLocationPrefs("Toronto,CA");
    }

    @Test
    public void whenForecastDetailsPageIsLaunched_ExtendedForecastDetailsAreShown(){

        DateTime sampleDate = new DateTime();
        mActivityRule.launchActivity(generateActivityDetailsIntent(sampleDate));

        Intent weatherServiceIntent = new Intent(getTargetContext(), WeatherService.class);
        weatherServiceIntent.putExtra(WeatherService.LOCATION_SETTING_EXTRA, sharedPrefs.getLocationPrefs());
        getTargetContext().startService(weatherServiceIntent);

        //temporary sleep to let service task finish before IdlingResource task is completed
        SystemClock.sleep(10000);

        //day of week or today, tomorrow
        onView(withText(sampleDate.toString("MMM DD, YYYY"))).check(matches(isDisplayed()));
        //high - by id
        //low - by id
        //humidity: [1-100]%
        //wind: <speed> km/h <direction>
        //pressure: <number> hPa
    }

    private Intent generateActivityDetailsIntent(DateTime date){

        Intent activityDetailsIntent = new Intent(getTargetContext(), DetailsActivity.class);
        long dateAsLong = date.withZone(DateTimeZone.getDefault()).withTimeAtStartOfDay().getMillis();
        Uri weatherUri = WeatherContract.buildWeatherLocationWithDate(sharedPrefs.getLocationPrefs(), dateAsLong);
        activityDetailsIntent.setData(weatherUri);
        activityDetailsIntent.putExtra(WeatherService.LOCATION_SETTING_EXTRA, sharedPrefs.getLocationPrefs());
        return activityDetailsIntent;
    }
}