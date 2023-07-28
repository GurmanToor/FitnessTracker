package com.example.fitness_tracker.business;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserWeight;
import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.persistence.IExercisePersistence;
import com.example.fitness_tracker.persistence.IExerciseWorkoutPersistence;
import com.example.fitness_tracker.persistence.IFoodMealPersistence;
import com.example.fitness_tracker.persistence.IFoodPersistence;
import com.example.fitness_tracker.persistence.IMealPersistence;
import com.example.fitness_tracker.persistence.IUserMealPersistence;
import com.example.fitness_tracker.persistence.IUserPersistence;
import com.example.fitness_tracker.persistence.IUserWeightPersistence;
import com.example.fitness_tracker.persistence.IUserWorkoutPersistence;
import com.example.fitness_tracker.persistence.IWeightPersistence;
import com.example.fitness_tracker.persistence.IWorkoutPersistence;
import com.example.fitness_tracker.persistence.hsqldb.ExercisePersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.ExerciseWorkoutPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.FoodMealPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.FoodPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.MealPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.UserMealPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.UserPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.UserWeightPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.UserWorkoutPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.WeightPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.WorkoutPersistenceHSQLDB;
import com.example.fitness_tracker.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public class IntegratedWeightLoggerTest {

    private static RealDatabase db;
    private File tempDB;
    private static User theUser;
    private WeightLogger weightLogger;

    @Before
    public void setUp() throws Exception {
        this.tempDB = TestUtils.copyDB();
        final IUserPersistence userPersistence = new UserPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        //final IUserWorkoutPersistence userWorkoutPersistence = new UserWorkoutPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IUserWeightPersistence userWeightPersistence = new UserWeightPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IUserMealPersistence userMealPersistence = new UserMealPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IExercisePersistence exercisePersistence = new ExercisePersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IExerciseWorkoutPersistence exerciseWorkoutPersistence = new ExerciseWorkoutPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IWorkoutPersistence workoutPersistence = new WorkoutPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IFoodMealPersistence foodMealPersistence = new FoodMealPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IWeightPersistence weightPersistence = new WeightPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IMealPersistence mealPersistence = new MealPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IFoodPersistence foodPersistence = new FoodPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        db = new RealDatabase(true);
        db.setUserTable(new AccessUser(userPersistence));
        //db.setUserWorkoutTable(new AccessUserWorkout(userWorkoutPersistence));
        db.setUserWeightTable(new AccessUserWeight(userWeightPersistence));
        db.setUserMealTable(new AccessUserMeal(userMealPersistence));
        db.setExerciseTable(new AccessExercise(exercisePersistence));
        db.setExerciseWorkoutTable(new AccessExerciseWorkout(exerciseWorkoutPersistence));
        db.setWorkoutTable(new AccessWorkout(workoutPersistence));
        db.setFoodMealTable(new AccessFoodMeal(foodMealPersistence));
        db.setWeightTable(new AccessWeight(weightPersistence));
        db.setMealTable(new AccessMeal(mealPersistence));
        db.setFoodTable(new AccessFood(foodPersistence));
        //dbHandler = new WorkoutDatabaseHandler(db);
        weightLogger = new WeightLogger();
        theUser = db.getUser("janesmith123");
        db.setCurrentUser(theUser);
        //dbHandler.getCurrentUser();
    }

    @Test
    public void logWeightTest() {

        //testing valid inputs

        //Assertions.assertTrue(weightLogger.logWeight("160", "2022/03/21"));

        //testing invalid dates
        Assertions.assertFalse(weightLogger.logWeight("150", "3022/03/21"));
        Assertions.assertFalse(weightLogger.logWeight("150", "2022/XYU/21"));

        //testing invalid weights
        Assertions.assertFalse(weightLogger.logWeight("-150", "2022/03/21"));


        List<UserWeight> history = db.getUserWeight(theUser.getUsername());
        //db.delete(history.get(1));


    }


    @Test
    public void calculateProgress() {
        //add a bunch of weights to the table
        int id;
        id = db.userWeightSize() + 1;
        BigDecimal weightInBigDecimal = new BigDecimal(185);
        Weight newWeight = new Weight(id,weightInBigDecimal);
        db.insert(newWeight);
        UserWeight newUserWeight = new UserWeight(theUser,newWeight,"2022/03/28", ""); //create user weight
        db.insert(newUserWeight);

        assertTrue(weightLogger.calculateProgress(db.getUserWeight(theUser.getUsername())).floatValue() == -4.5);

        List<UserWeight> history = db.getUserWeight(theUser.getUsername());
        db.delete(history.get(1));
        System.out.println(db.getUserWeight(theUser.getUsername()));

    }

    @Test
    public void analyseProgress() {

        assertTrue(weightLogger.analyseProgress().equals("Log your weight for 2 days to get analysis")  );

    }
}