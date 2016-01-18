package com.example.weatherapp;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.weatherapp.activities.MainActivity;
import com.example.weatherapp.data.WeatherContract;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.weatherapp.testUtils.CustomViewMatchers.RecyclerViewItemTotalMatches;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityTesting {

    private static final String TAG = "ActivityTesting";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    //Task 0
    @Ignore
    public void whenAppStarts_SleepForTwentySeconds() {

        Log.d(TAG, "Sleeping...");
        SystemClock.sleep(20000);
    }

    //Task 1
    @Test
    public void whenSettingsMenuIsOpened_ForecastPreferenceTitleIsDisplayed() {

        openSettingsLinkInActionBar();
        onView(withText(R.string.preference_title)).check(matches(isDisplayed()));
    }

    //Task 2
    @Test
    public void whenLocationNameIsChangedInSettings_ForecastPreferencesDisplayNewLocationName() {

        String alternativeLocation = "Montreal,CA";

        openSettingsLinkInActionBar();
        onView(withText(R.string.preference_zip_title)).perform(click());
        onView(withId(android.R.id.edit)).perform(clearText(), typeText(alternativeLocation));
        onView(withText("OK")).perform(click());
        onView(allOf(withId(android.R.id.summary), withText(alternativeLocation)))
                .check(matches(isDisplayed()));
    }

    //Task 3
    @Test
    public void whenPullingUpOnMainScreen_SnackbarContainingLocationNameIsDisplayed() {

        String alternativeLocation = "Montreal,CA";

        onView(withId(R.id.refresh_layout)).perform(swipeDown());
        onView(allOf(withId(R.id.snackbar_text), withText(alternativeLocation)))
                .check(matches(isDisplayed()));
    }

    //Task 4
    @Test
    public void whenTemperatureUnitsAreChangedToImperialOnSettingsScreen_UnitsAreDisplayedAsImperialInTheSettingsMenu() {

        String imperialUnitsLabel = mActivityRule.getActivity().getString(R.string.units_imperial);

        openSettingsLinkInActionBar();
        onView(withText("Temperature Units")).perform(click());
        onData(Matchers.hasToString(imperialUnitsLabel)).perform(click());
        onView(allOf(hasSibling(withText("Temperature Units")), withText(imperialUnitsLabel)))
                .check(matches(isDisplayed()));
    }

    //Task 5
    @Ignore
    public void whenTheCurrentDateIsSelectedOnTheMainPageRecyclerView_TheExpandedViewForTodaysForecastIsDisplayed() {

        DateTime currentDateTime = new DateTime();

        onData(withRowLong(WeatherContract.WeatherEntry.COLUMN_DATE, currentDateTime.getMillis()))
                .perform(click());
        onView(allOf(withParent(withId(R.id.action_bar_container)), withText(currentDateTime.getDayOfWeek())))
                .check(matches(isDisplayed()));
    }

    //Task 6
    @Test
    public void whenTheLastItemInTheMainRecyclerViewIsSelected_TheExpandedViewForThatDaysForecastIsDisplayed() {

        String yesterdaysDayOfWeek = DateTime.now().minusDays(1).dayOfWeek().getAsText();

        onView(withId(R.id.recyclerview_forecast))
                .perform(actionOnItem(allOf(hasDescendant(withText(yesterdaysDayOfWeek)), withId(R.id.card_view)), click()));
        onView(allOf(withParent(withId(R.id.action_bar_container)), withText(yesterdaysDayOfWeek)))
                .check(matches(isDisplayed()));
    }

    //Task 7
    @Test
    public void whenCountingNumberOfItemsInMainPageRecyclerView_TotalIsSeven() {

        onView(withId(R.id.recyclerview_forecast)).check(matches(RecyclerViewItemTotalMatches(7)));
    }


    //Helper method to select overflow menu and open Settings
    private static void openSettingsLinkInActionBar() {

        openActionBarOverflowOrOptionsMenu(getContext());
        onView(withText(R.string.action_settings)).perform(click());
    }

}