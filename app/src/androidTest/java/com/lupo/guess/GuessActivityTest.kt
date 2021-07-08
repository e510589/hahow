package com.lupo.guess

import android.content.res.Resources
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GuessActivityTest {


    @get:Rule
    val rule = activityScenarioRule<GuessActivity>()

    @Test
    fun guessWrong(){
        var secret = 0;
        lateinit var resource: Resources
        var scenario = rule.scenario.onActivity { activity ->
            secret = activity.getSecretNumber()
            resource = activity.resources
        }

        for(testNumber in 1..5){
            if (testNumber != secret){
                onView(ViewMatchers.withId(R.id.tie_guess)).perform(ViewActions.clearText())
                onView(ViewMatchers.withId(R.id.tie_guess)).perform(ViewActions.typeText(testNumber.toString()))
                onView(ViewMatchers.withId(R.id.btn_guess)).perform(ViewActions.click())
                val message  = if ( testNumber < secret) resource.getString(R.string.bigger)
                else resource.getString(R.string.smaller)
                onView(ViewMatchers.withText(message)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                onView(ViewMatchers.withText(resource.getString(R.string.OK))).perform(ViewActions.click())
            }
        }
    }

    @Test
    fun repaly(){

        var secret = 0;
        lateinit var resource: Resources
        var scenario = rule.scenario.onActivity { activity ->
            secret = activity.getSecretNumber()
            resource = activity.resources
        }

        for(testNumber in 1..5){
            if (testNumber != secret){
                onView(ViewMatchers.withId(R.id.tie_guess)).perform(ViewActions.clearText())
                onView(ViewMatchers.withId(R.id.tie_guess)).perform(ViewActions.typeText(testNumber.toString()))
                onView(ViewMatchers.withId(R.id.btn_guess)).perform(ViewActions.click())
                val message  = if ( testNumber < secret) resource.getString(R.string.bigger)
                else resource.getString(R.string.smaller)
                onView(ViewMatchers.withText(message)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                onView(ViewMatchers.withText(resource.getString(R.string.OK))).perform(ViewActions.click())
            }
        }

        onView(ViewMatchers.withId(R.id.fab_refresh)).perform(ViewActions.click())
        onView(ViewMatchers.withText(resource.getString(R.string.OK))).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.tv_count)).check(ViewAssertions.matches(ViewMatchers.withText("0")))

    }
}