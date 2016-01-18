package com.example.weatherapp.testUtils;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

public class CustomViewMatchers {
    //Custom matcher class that checks if a view contains a specified number of items
    public static Matcher<View> RecyclerViewItemTotalMatches(final int numExpected) {
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
}
