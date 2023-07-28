package com.example.fitness_tracker;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.example.fitness_tracker.presentation.LoginActivity;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * // https://developer.android.com/training/testing/espresso/cheat-sheet
 *
 *
 * NOTE: the back button built into the actual toolbar on the top does not
 * associate with the current activity, will be tested in the other tests
 * of the associated activities
 */
@RunWith(AndroidJUnit4.class)
public class HomeScreenAcceptanceTests {
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
    public void toSettings() {
        login();
        onSettings();
    }

    @Test
    public void toWorkout() {
        login();
        onWorkout();
    }

    @Test
    public void toWeightLog() {
        login();
        onWeightLog();
    }

    @Test
    public void toNotes() {
        login();
        onAddNote();
    }

    @Test
    public void toCalories() {
        login();
        onCalories();
    }

    public void login() {
        // Type in username
        onView(withId(R.id.editText6)).perform(typeText("janesmith123"));
        closeSoftKeyboard();
        // Type in password
        onView(withId(R.id.editText7)).perform(typeText("password123"));

        closeSoftKeyboard();
        // Click the login button
        onView(withId(R.id.button2)).perform(click());
    }

    public void onSettings() {
        // click the settings button
        onView(withId(R.id.settings)).perform(click());

        // click back button
       // onView(withId(R.id.home)).perform(click());
    }

    public void onWorkout() {
        // click the workout button
        onView(withId(R.id.workout_home)).perform(click());

        // click back button
//         onView(withId(R.id.go_back)).perform(click());
    }

    public void onWeightLog() {
        // click weight log button
        onView(withId(R.id.weight_log_home)).perform(click());

        // click back button
        // onView(withId(R.id.home)).perform(click());
    }

    public void onAddNote() {
        // click add note button
        onView(withId(R.id.floatingActionButton)).perform(click());

        // click back button
        // onView(withId(R.id.home)).perform(click());
    }

    public void onCalories() {
        // click caloric button
        onView(withId(R.id.daily_goal_progressBar)).perform(click());

        // click back button
        onView(withId(R.id.back_button)).perform(click());
    }
}
