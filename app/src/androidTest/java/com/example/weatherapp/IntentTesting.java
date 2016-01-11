package com.example.weatherapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.core.deps.dagger.Module;
import android.support.test.espresso.core.deps.dagger.Provides;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.app.Instrumentation.ActivityResult;

import com.example.weatherapp.activities.DetailsActivity;
import com.example.weatherapp.activities.MainActivity;
import com.example.weatherapp.activities.MainActivity$$InjectAdapter;
import com.example.weatherapp.services.WeatherService;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.util.Log;
import android.content.Intent;

import java.io.File;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;

import javax.inject.Inject;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntentTesting {

    private static final String TAG = "IntentTesting";

    @Provides WeatherAppSharedPrefs provideSharedPrefs(){
        return new WeatherAppSharedPrefs(mIntentRule.getActivity());
    }

    @Rule
    public IntentsTestRule<MainActivity> mIntentRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp(){

        PreferenceManager.getDefaultSharedPreferences(mIntentRule.getActivity()).edit().clear().commit();
        //Clear shared prefs
        //sharedPrefs.getSharedPrefs().edit().clear().commit();
        //sharedPrefs.setLocationPrefs(mIntentRule.getActivity().getString(R.string.preference_zip_default));

    }

    //Task 8
    @Test
    public void whenMapIsCalled_MapIntentIsSent(){

        //get and print the zip to the log, this isn't so relevent when it isn't used in location URI
        String zip = mIntentRule.getActivity().getString(R.string.preference_zip_default);
        Log.d(TAG, "Using Zip " + zip);

        //Copied from MainActivityFragment class with location name updated to match default
        Uri locationUri = Uri.parse("geo:0,0").buildUpon()
                .appendQueryParameter("q", "Toronto,CA")
                .build();

        //expect ACTION_VIEW to be called
        intending(hasAction(Intent.ACTION_VIEW));

        Espresso.openActionBarOverflowOrOptionsMenu(mIntentRule.getActivity());

        onView(withText(R.string.action_map)).perform(click());

        //CHeck that ACTION_VIEW has been called with location data
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData(locationUri)));

    }

    //Task 9

}
