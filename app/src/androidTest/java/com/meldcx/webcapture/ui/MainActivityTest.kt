package com.meldcx.webcapture.ui

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.meldcx.webcapture.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    /**
     * checking MainActivity show the right layout
     */
    @Test
    fun isActivityInView() {
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    /**
     * checking the GO button is visible
     */
    @Test
    fun goButtonVisibilityTest() {
        onView(withId(R.id.goButton)).check(matches(isDisplayed()))
    }

    /**
     * checking text on button
     */
    @Test
    fun checkTextOnCaptureButton() {
        onView(withId(R.id.captureButton)).check(matches(withText(R.string.capture)))
    }

    @Test
    fun test_navSecondaryActivity() {
        onView(withId(R.id.historyButton)).perform(click())
        onView(withId(R.id.secondaryMain)).check(matches(isDisplayed()))
    }
}