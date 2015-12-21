package com.example.weatherapp;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runner.*;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.InputType;
import android.util.Log;
import com.example.weatherapp.activities.MainActivity;
import com.example.weatherapp.data.WeatherContract;
import com.squareup.okhttp.internal.Platform;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import java.util.Calendar;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.CursorMatchers.withRowInt;
import static android.support.test.espresso.matcher.CursorMatchers.withRowLong;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class HelloWorldEspressoTest {

    private static final String TAG = "HelloWorldEspressoTest", ALTERNATIVE_LOCATION = "Montreal,CA";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);


    //Task 1
    @Ignore
    public void sleepForTwenty() {
        Log.d(TAG, "Sleeping...");
        SystemClock.sleep(20000);
    }


    //Task 2
    @Test
    public void displayValidForecastPreference() {

        openSettings();

        //check that forecast title exists
        onView(withText(R.string.preference_title)).check(matches(isDisplayed()));
    }

    //Task 3
    @Test
    public void changeSettingsLocationAndCheckPersistence(){

        openSettings();

        //select Location setting
        onView(withText(R.string.preference_zip_title))
                .perform(click());

        //select Location setting
        onView (withId(android.R.id.edit))
                .perform(clearText(), typeText(ALTERNATIVE_LOCATION));

        onView (withText("OK"))
                .perform(click());

        //check that new forecast title is displayed
        onView(allOf(withId(android.R.id.summary), withText(ALTERNATIVE_LOCATION)))
                .check(matches(isDisplayed()));
    }

    //Task 4
    @Test
    public void pullUpToRefreshAndVerifySnackbar() {

        //pull down on main activity to refresh
        onView(withId(R.id.refresh_layout))
                .perform(swipeDown());

        //check for snackbar
        onView(allOf(withId(R.id.snackbar_text), withText(ALTERNATIVE_LOCATION)))
            .check(matches(isDisplayed()));

    }

    //Task 5
    @Test
    public void changeTemperatureUnitsToImperial() {

        openSettings();

        //select Location setting
        onView(withText("Temperature Units"))
                .perform(click());

        //string used to represent Imperial units
        String unitsText = mActivityRule.getActivity().getString(R.string.units_imperial);

        //change units to imperial
        onData(Matchers.hasToString(unitsText))
                .perform(click());

        //check that change is made
        onView(allOf(hasSibling(withText("Temperature Units")), withText(unitsText)))
                .check(matches(isDisplayed()));

    }

    //Task 6
    //This is a simulation, does not exist in application
    @Ignore
    public void getForecastCursorAndSelectTodaysForecast() {

        //get current date from Calendar
        DateTime dateTime = new DateTime();

        //select row with current date
        onData(withRowLong(WeatherContract.WeatherEntry.COLUMN_DATE, dateTime.getMillis()))
                .perform(click());

    }

    //Task 7
    @Test
    public void clickOnLastElementInForecastList() {

        //get recycler view for forecasts and perform a ViewAction to select the last item in the list
        onView(allOf(withId(R.id.recyclerview_forecast), is(instanceOf(RecyclerView.class))))
                .perform(actionOnItem(allOf(hasDescendant(withText(DateTime.now().minusDays(1).dayOfWeek().getAsText())), withId(R.id.card_view)), click()));

    }

    //Task
    //TODO Write a test that tests number of items in the recyclerview of the main activity
    @Test
    public void checkNumberOfItemsInForecast(){
    }

    //Task 8, May be written elsewhere
    @Test
    public void createCustomDaggerModule(){

    }

    //Helper method to select overflow menu and open Settings
    private static void openSettings(){
        //open overflow menu
        openActionBarOverflowOrOptionsMenu(getContext());

        //select Settings menu
        onView(withText(R.string.action_settings))
                .perform(click());
    }

}