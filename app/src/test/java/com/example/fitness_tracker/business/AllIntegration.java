package com.example.fitness_tracker.business;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        AccountTests.class,
        IntegratedWeightLoggerTest.class,
        WorkoutDatabaseHandlerTest.class
})

public class AllIntegration {
}