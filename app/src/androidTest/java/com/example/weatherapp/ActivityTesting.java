package com.example.weatherapp;

import android.os.SystemClock;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;

import com.example.weatherapp.activities.MainActivity;
import com.example.weatherapp.data.WeatherContract;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.matcher.CursorMatchers.withRowLong;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityTesting {

    private static final String TAG = "ActivityTesting", ALTERNATIVE_LOCATION = "Montreal,CA";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    //Task 0
    @Ignore
    public void sleepForTwenty() {
        Log.d(TAG, "Sleeping...");
        SystemClock.sleep(20000);
    }


    //Task 1
    @Test
    public void displayValidForecastPreference() {

        openSettings();

        //check that forecast title exists
        onView(withText(R.string.preference_title)).check(matches(isDisplayed()));
    }

    //Task 2
    @Test
    public void changeSettingsLocationAndCheckPersistence() {

        openSettings();

        //select Location setting
        onView(withText(R.string.preference_zip_title))
                .perform(click());

        //select Location setting
        onView(withId(android.R.id.edit))
                .perform(clearText(), typeText(ALTERNATIVE_LOCATION));

        onView(withText("OK"))
                .perform(click());

        //check that new forecast title is displayed
        onView(allOf(withId(android.R.id.summary), withText(ALTERNATIVE_LOCATION)))
                .check(matches(isDisplayed()));
    }

    //Task 3
    @Test
    public void pullUpToRefreshAndVerifySnackbar() {

        //pull down on main activity to refresh
        onView(withId(R.id.refresh_layout))
                .perform(swipeDown());

        //check for snackbar
        onView(allOf(withId(R.id.snackbar_text), withText(ALTERNATIVE_LOCATION)))
                .check(matches(isDisplayed()));

    }

    //Task 4
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

    //Task 5
    //This is a simulation, does not exist in application
    @Ignore
    public void getForecastCursorAndSelectTodaysForecast() {

        //get current date from Calendar
        DateTime dateTime = new DateTime();

        //select row with current date
        onData(withRowLong(WeatherContract.WeatherEntry.COLUMN_DATE, dateTime.getMillis()))
                .perform(click());

    }

    //Task 6
    @Test
    public void clickOnLastElementInForecastList() {

        //get recycler view for forecasts and perform a ViewAction to select the last item in the list
        onView(withId(R.id.recyclerview_forecast))
                .perform(actionOnItem(allOf(hasDescendant(withText(DateTime.now().minusDays(1).dayOfWeek().getAsText())), withId(R.id.card_view)), click()));

    }

    //Task 7
    @Test
    public void checkCountOfItemsInMainRecyclerView() {
        onView(withId(R.id.recyclerview_forecast)).check(matches(withNItems(7)));
    }

    //Custom matcher class that checks if a view contains a specified number of items
    private static Matcher<View> withNItems(final int numExpected) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            int actual = 0;

            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("Check matches length: ");
                description.appendText("\n Expected: " + numExpected);
                description.appendText("\n but got: " + actual);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                actual = item.getAdapter().getItemCount();
                return numExpected == actual;
            }
        };
    }


    //Helper method to select overflow menu and open Settings
    private static void openSettings() {
        //open overflow menu
        openActionBarOverflowOrOptionsMenu(getContext());

        //select Settings menu
        onView(withText(R.string.action_settings))
                .perform(click());
    }

}