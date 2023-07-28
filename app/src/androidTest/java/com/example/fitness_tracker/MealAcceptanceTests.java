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
public class MealAcceptanceTests {
    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.fitness_tracker", appContext.getPackageName());
    }

    @Test
    public void addFoodMeal() {
        // Type in username
        onView(withId(R.id.editText6)).

                perform(typeText("janesmith123"));

        closeSoftKeyboard();

        // Type in password
        onView(withId(R.id.editText7)).

                perform(typeText("password123"));

        closeSoftKeyboard();

        // Click the login button
        onView(withId(R.id.button2)).

                perform(click());

        // Click the login button
        onView(withId(R.id.activity_home)).

                perform(click());

        // Type in the meal name
        onView(withId(R.id.meal_name)).

                perform(typeText("myfirstmeal"));

        closeSoftKeyboard();

        // Type in the food name
        onView(withId(R.id.food_name)).

                perform(typeText("foodIhave"));

        closeSoftKeyboard();


        // Type in the food calories
        onView(withId(R.id.food_calories)).

                perform(typeText("120"));

        closeSoftKeyboard();


        // click on the save button
        onView(withId(R.id.save_button)).perform(click());

    }


}
