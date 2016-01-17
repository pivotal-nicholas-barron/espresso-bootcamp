package com.example.weatherapp;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.weatherapp.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.app.Instrumentation.ActivityResult;
import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TempTaskEight {

    private static String DEFAULT_LOCATION = "Montreal,CA";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Inject
    WeatherAppSharedPrefs sharedPrefs = new WeatherAppSharedPrefs(getTargetContext());

    @Before
    public void setUp(){

        getTargetContext()
                .getSharedPreferences("com.example.weatherapp_preferences", 0).edit().clear().commit();
        sharedPrefs.setLocationPrefs(DEFAULT_LOCATION);
    }

    @Test
    public void whenMapIsCalled_MapIntentIsSent(){

        Uri locationUri = Uri.parse("geo:0,0").buildUpon()
                .appendQueryParameter("q", DEFAULT_LOCATION)
                .build();
        ActivityResult result = new ActivityResult(Activity.RESULT_OK, new Intent());

        Intents.init();
        intending(hasAction(Intent.ACTION_VIEW)).respondWith(result);
        openActionBarOverflowOrOptionsMenu(getContext());
        onView(withText(R.string.action_map)).perform(click());
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData(locationUri)));
        Intents.release();
    }

}
