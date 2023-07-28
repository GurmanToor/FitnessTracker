package com.example.fitness_tracker;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.fitness_tracker.presentation.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 */
@RunWith(AndroidJUnit4.class)
public class WeighLogAcceptanceTests {
    // Launch this before every test
    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.fitness_tracker", appContext.getPackageName());
    }

    @Test
    public void analyseWeight(){
        // Type in username
        onView(withId(R.id.editText6)).perform(typeText("janesmith123"));
        closeSoftKeyboard();
        // Type in password
        onView(withId(R.id.editText7)).perform(typeText("password123"));

        closeSoftKeyboard();

        // Click the login button
        onView(withId(R.id.button2)).perform(click());

        // Go to the weight log page
        onView(withId(R.id.weight_log_home)).perform(click());

        // Press the analysis button
        onView(withId(R.id.analysis_button)).perform(click());

        //check if the analysis text is displayed
        onView(withId(R.id.analysis_text)).check(matches(isDisplayed()));
    }

    @Test
    public void addWeight(){
        // Type in username
        onView(withId(R.id.editText6)).perform(typeText("janesmith123"));
        closeSoftKeyboard();
        // Type in password
        onView(withId(R.id.editText7)).perform(typeText("password123"));

        closeSoftKeyboard();

        // Click the login button
        onView(withId(R.id.button2)).perform(click());

        // Go to the weight log page
        onView(withId(R.id.weight_log_home)).perform(click());

        // Press the add button
        onView(withId(R.id.addWeight_button)).perform(click());

        //check if the analysis text is displayed
        //onView(withId(R.id.analysis_text)).check(matches(isDisplayed()));

        // Type in the weight
        onView(withId(R.id.input_weight)).perform(typeText("185"));

        // Type in the date
        onView(withId(R.id.input_weight)).perform(typeText("2022/04/18"));
        closeSoftKeyboard();

        // Press the save button
        onView(withId(R.id.save_button)).perform(click());

//        //check if the analysis text is displayed
//        onView(withId(R.id.input_weight)).check(doesNotExist());

    }

}
