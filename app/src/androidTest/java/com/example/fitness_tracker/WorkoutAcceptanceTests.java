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
public class WorkoutAcceptanceTests {
    // Launch this before every test
    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.fitness_tracker", appContext.getPackageName());
    }

    public void startNewWorkout(){
        // Type in username
        onView(withId(R.id.editText6)).perform(typeText("janesmith123"));
        closeSoftKeyboard();
        // Type in password
        onView(withId(R.id.editText7)).perform(typeText("password123"));
        // Was getting error, had to make sure to close the keyboard so it could click
        // https://stackoverflow.com/questions/29786413/performexception-error-performing-single-click
        closeSoftKeyboard();

        // Click the login button
        onView(withId(R.id.button2)).perform(click());

        // Go to the workout home page
        onView(withId(R.id.workout_home)).perform(click());
        // Press create new workout button
        onView(withId(R.id.new_workout_button)).perform(click());
    }

    public void createNewStrengthExercise(){
        onView(withId(R.id.new_strength_exe_btn)).perform(click());

        // Select the body part from the spinner
        // Source: https://stackoverflow.com/questions/34619624/android-espresso-how-to-click-on-spinner-item-with-text
        onView(withId(R.id.body_part_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Abs"))).perform(click());
        onView(withId(R.id.body_part_spinner)).check(matches(withSpinnerText(containsString("Abs"))));

        // Type in the exercise name
        onView(withId(R.id.edit_exercise_name)).perform(typeText("Crunches"));

        // Type in exercise length (clear first, then hh:mm:ss)
        onView(withId(R.id.editTextTime)).perform(clearText()).perform(typeText("01:11:11"));

        // Type in sets
        onView(withId(R.id.number_of_sets)).perform(typeText("4"));

        // Type in reps
        onView(withId(R.id.number_of_reps)).perform(typeText("10"));
        closeSoftKeyboard();

        // Click submit
        onView(withId(R.id.button)).perform(click());
    }

    public void createNewCardioExercise(){

        onView(withId(R.id.new_cardio_exe_btn)).perform(click());

        // Select Walk as the cardio exercise
        onView(withId(R.id.buttonWalk)).perform(click());

        // Set the time worked out, need to clear the default first
        onView(withId(R.id.editTextTime)).perform(clearText()).perform(typeText("01:11:11"));

        // Set distance
        onView(withId(R.id.editTextDistance)).perform(typeText("20"));
        closeSoftKeyboard();

        // Click submit
        onView(withId(R.id.button)).perform(click());
    }

    @Test
    public void createCardioExercise(){
        startNewWorkout();
        createNewCardioExercise();
    }

    @Test
    public void createStrengthExercise(){
        startNewWorkout();

        createNewStrengthExercise();

    }

    @Test
    public void editCardioExercise(){
        startNewWorkout();
        createNewCardioExercise();

        // Edit cardio exercise
        onView(withId(R.id.edit_exercise_button)).perform(click());

        // Switch to run as the cardio exercise
        onView(withId(R.id.buttonRun)).perform(click());

        // Clear the time and put new one
        onView(withId(R.id.editTextTime)).perform(clearText()).perform(typeText("01:01:01"));
        closeSoftKeyboard();
        // Click submit
        onView(withId(R.id.button)).perform(click());
    }

    @Test
    public void editStrengthExercise(){
        startNewWorkout();
        createNewStrengthExercise();

        // Click edit
        onView(withId(R.id.edit_exercise_button)).perform(click());

        onView(withId(R.id.body_part_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Calves"))).perform(click());
        onView(withId(R.id.body_part_spinner)).check(matches(withSpinnerText(containsString("Calves"))));

        // Type in sets
        onView(withId(R.id.number_of_sets)).perform(clearText()).perform(typeText("3"));

        closeSoftKeyboard();

        // Click submit
        onView(withId(R.id.button)).perform(click());
    }

    @Test
    public void deleteCardioExercise(){
        startNewWorkout();

        createNewCardioExercise();

        // Delete cardio exercise
        onView(withId(R.id.delete_button)).perform(click());

    }

    @Test
    public void deleteStrengthExercise(){
        startNewWorkout();

        createNewStrengthExercise();

        // Click edit
        onView(withId(R.id.delete_button)).perform(click());
    }

    @Test
    public void createCompleteMultiExerciseWorkout(){
        createCardioExercise();
        createNewStrengthExercise();

        onView(withId(R.id.finish_workout_button)).perform(click());

    }
}