package com.example.fitness_tracker.business;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        RealDatabaseTests.class,
        FakeDatabaseTests.class,
        ValidateCardioUserInputTest.class,
        ValidateDateInputTest.class,
        ValidateDistanceTest.class,
        ValidateStrengthUserInputTest.class,
        ValidateTimeLengthTest.class,
        WeightLoggerTest_Junit4.class
})

public class AllUnitTests {
}
