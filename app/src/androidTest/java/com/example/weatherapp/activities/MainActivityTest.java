package com.example.weatherapp.activities;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.example.weatherapp.R;
import com.example.weatherapp.WeatherAppApplication;
import com.example.weatherapp.WeatherAppSharedPrefs;
import com.example.weatherapp.data.WeatherContract;
import com.example.weatherapp.testUtils.TestConstants;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.CursorMatchers.withRowLong;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.weatherapp.testUtils.CustomViewMatchers.RecyclerViewItemTotalMatches;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private static final String TAG = "ActivityTesting";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(com.example.weatherapp.activities.MainActivity.class);;

    @Inject
    WeatherAppSharedPrefs sharedPrefs;

    @Before
    public void setUp(){

        getTargetContext()
                .getSharedPreferences("com.example.weatherapp_preferences", 0).edit().clear().commit();
        WeatherAppApplication.getInstance().inject(this);
        sharedPrefs.setLocationPrefs(TestConstants.DEFAULT_LOCATION);
    }

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

        openSettingsLinkInActionBar();
        onView(withText(R.string.preference_zip_title)).perform(click());
        onView(withId(android.R.id.edit)).perform(clearText(), typeText(TestConstants.ALTERNATIVE_LOCATION));
        onView(withText("OK")).perform(click());
        onView(withText(TestConstants.ALTERNATIVE_LOCATION))
                .check(matches(isDisplayed()));
    }

    //Task 3
    @Test
    public void whenPullingUpOnMainScreen_SnackbarContainingLocationNameIsDisplayed() {

        onView(withId(R.id.refresh_layout)).perform(swipeDown());
        onView(allOf(withId(R.id.snackbar_text), withText(TestConstants.ALTERNATIVE_LOCATION)))
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
        onView(allOf(withParent(withId(R.id.action_bar)), withText(yesterdaysDayOfWeek)))
                .check(matches(isDisplayed()));
    }

    //Task 7
    @Test
    public void whenCountingNumberOfItemsInMainPageRecyclerView_TotalIsSeven() {

        onView(withId(R.id.recyclerview_forecast)).check(matches(RecyclerViewItemTotalMatches(7)));
    }

    //Task 8
    @Test
    public void whenMapIsCalled_MapIntentIsSent(){

        Uri locationUri = Uri.parse("geo:0,0").buildUpon()
                .appendQueryParameter("q", TestConstants.DEFAULT_LOCATION)
                .build();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, new Intent());

        Intents.init();
        intending(hasAction(Intent.ACTION_VIEW)).respondWith(result);
        openActionBarOverflowOrOptionsMenu(getTargetContext());
        onView(withText(R.string.action_map)).perform(click());
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData(locationUri)));
        Intents.release();
    }

    //Helper method to select overflow menu and open Settings
    private static void openSettingsLinkInActionBar() {

        openActionBarOverflowOrOptionsMenu(getTargetContext());
        onView(withText(R.string.action_settings)).perform(click());
    }
}
