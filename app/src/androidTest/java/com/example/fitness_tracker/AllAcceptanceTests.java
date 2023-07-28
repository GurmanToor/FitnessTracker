package com.example.fitness_tracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        DailyGoalAcceptanceTests.class,
        HomeScreenAcceptanceTests.class,
        LoginScreenAcceptanceTests.class,
        MealAcceptanceTests.class,
        NotesAcceptanceTests.class,
        RegisterAcceptanceTests.class,
        SettingsActivityAcceptanceTests.class,
        WeighLogAcceptanceTests.class,
        WorkoutAcceptanceTests.class
})

public class AllAcceptanceTests {
}
