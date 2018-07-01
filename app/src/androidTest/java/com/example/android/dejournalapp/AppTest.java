package com.example.android.dejournalapp;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by DELL PC on 7/1/2018.
 */


    @RunWith(AndroidJUnit4.class)
    @LargeTest
    public class AppTest extends ActivityInstrumentationTestCase2<MainActivity> {

        public AppTest() {
            super(MainActivity.class);
        }

        @Before
        public void setUp() throws Exception {
            super.setUp();
            injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        }

        @Test
        public void test1ChatId() {
            getActivity();
            onView(withId(R.id.main_activity)).check(matches(isDisplayed()));
        }

        @After
        public void tearDown() throws Exception {
            super.tearDown();
        }
    }

