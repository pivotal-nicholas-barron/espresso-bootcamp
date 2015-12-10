package com.example.weatherapp;

import org.junit.*;
import org.junit.runner.*;

import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import com.example.weatherapp.activities.MainActivity;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HelloWorldEspressoTest {

    private static final String TAG = "HelloWorldEspressoTest";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void sleepForTwenty() {
        Log.d(TAG, "Sleeping...");
        SystemClock.sleep(20000);
    }
}