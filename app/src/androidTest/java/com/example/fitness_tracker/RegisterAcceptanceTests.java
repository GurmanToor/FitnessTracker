package com.example.fitness_tracker;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.example.fitness_tracker.presentation.LoginActivity;

import java.util.EnumSet;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * // https://developer.android.com/training/testing/espresso/cheat-sheet
 */
@RunWith(AndroidJUnit4.class)
public class RegisterAcceptanceTests {
    // Launch this before every test
    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.fitness_tracker", appContext.getPackageName());
    }

    public void toRegister() {
        // register button
        onView(withId(R.id.textView6)).perform(click());
    }

    @Test
    public void createAccountFail() {
        toRegister();
        // Type in name
        onView(withId(R.id.editText_name)).perform(typeText("z"));
        closeSoftKeyboard();

        // Type in username
        onView(withId(R.id.editText)).perform(typeText("z"));
        closeSoftKeyboard();

        // Type in password
        onView(withId(R.id.editText2)).perform(typeText("z"));
        closeSoftKeyboard();

        // Type in confirm password
        onView(withId(R.id.editText3)).perform(typeText("z"));
        closeSoftKeyboard();

        // Click the sign up button
        onView(withId(R.id.button)).perform(click());
    }

    @Test
    public void createAccountEmpty() {
        toRegister();
        // Click the sign up button
        onView(withId(R.id.button)).perform(click());
    }

    @Test
    public void createAccountSuccess() {
        toRegister();
        // Type in name
        onView(withId(R.id.editText_name)).perform(typeText("z z"));
        closeSoftKeyboard();

        // Type in username
        onView(withId(R.id.editText)).perform(typeText("z"));
        closeSoftKeyboard();

        // Type in password
        onView(withId(R.id.editText2)).perform(typeText("z"));
        closeSoftKeyboard();

        // Type in confirm password
        onView(withId(R.id.editText3)).perform(typeText("z"));
        closeSoftKeyboard();

        // Click the sign up button
        onView(withId(R.id.button)).perform(click());
    }

    @Test
    public void backToLogin() {
        toRegister();
        // Click the log in button
        onView(withId(R.id.textView)).perform(click());
    }
}
