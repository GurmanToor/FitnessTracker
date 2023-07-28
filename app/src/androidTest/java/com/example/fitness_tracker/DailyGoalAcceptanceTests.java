package com.example.fitness_tracker;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
public class DailyGoalAcceptanceTests {
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
    public void addMeal()
    {
        // Type in username
        onView(withId(R.id.editText6)).perform(typeText("janesmith123"));
        closeSoftKeyboard();
        // Type in password
        onView(withId(R.id.editText7)).perform(typeText("password123"));

        closeSoftKeyboard();
        // Click the login button
        onView(withId(R.id.button2)).perform(click());

        // click on the progress bar
        onView(withId(R.id.daily_goal_progressBar)).perform(click());

        //verify that breakfast fragment is in view
        onView(withId(R.id.meal_view_pager)).check(matches(isDisplayed()));

        //verify that breakfast fragment is in view
        onView(withId(R.id.meal_view_pager)).check(matches(isDisplayed()));

        // Type in the snack calories
        onView(withId(R.id.snack_calories)).perform(typeText("120"));
        closeSoftKeyboard();

        // click on the add button
        onView(withId(R.id.add_snack_calories)).perform(click());

        // click on the add water
        onView(withId(R.id.add_water)).perform(click());

        // click on the add water
        onView(withId(R.id.add_water)).perform(click());

        // click on the add water
        onView(withId(R.id.remove_water)).perform(click());


        //check if the pager is displayed
        onView(withId(R.id.meal_view_pager)).check(matches(isDisplayed()));

        //swipe right to lunch
        onView(withId(R.id.meal_view_pager)).perform(swipeLeft());

        // click on all the section in of tabs
        onView(withId(R.id.meal_view_pager))
                .perform(swipeLeft());
        onView(withText("Lunch")).perform(click());
        onView(withText("Dinner")).perform(click());
        onView(withText("Breakfast")).perform(click());

        // click on the back button
        onView(withId(R.id.back_button)).perform(click());

        // clear the progress bar
        // click on the progress bar
        onView(withId(R.id.daily_goal_progressBar)).perform(longClick());

    }

}
