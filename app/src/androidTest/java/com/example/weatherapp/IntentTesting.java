package com.example.weatherapp;

import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.weatherapp.activities.MainActivity;
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


@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntentTesting {

    private static final String TAG = "IntentTesting";


    @Rule
    public IntentsTestRule<MainActivity> mIntentRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Test
    public void checkMapIntentIsSent(){

        //TODO clear value in zip and replace with default
        //


        String zip = mIntentRule.getActivity().getString(R.string.preference_zip_default); //TODO Zip is long form without country code, how to handle this better so it can be used in Uri?

        //print the zip to the log
        Log.d(TAG, "Using Zip " + zip);

        //Copied from MainActivityFragment class
        Uri locationUri = Uri.parse("geo:0,0").buildUpon()
                .appendQueryParameter("q", "Toronto,CA")
                .build();//should be using zip here

        Espresso.openActionBarOverflowOrOptionsMenu(mIntentRule.getActivity());

        File filesDir = mIntentRule.getActivity().getFilesDir();

        onView(withText(R.string.action_map)).perform(click());

        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData(locationUri)));

    }
/*
    private static Matcher<Intent> hasStringAsAttribute(final String zip){
        return new TypeSafeMatcher<Intent>(Intent.class) {
            @Override
            protected boolean matchesSafely(Intent item) {
                item.hasExtra();
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("\nCheck has text " + zip + "\n");
            }
        };
    }
*/
}
