package com.example.fitness_tracker.application;

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

public class Services {
    private static IUserPersistence userPersistence = null;
    private static IWeightPersistence weightPersistence = null;
    private static IWorkoutPersistence workoutPersistence = null;
    private static IExercisePersistence exercisePersistence = null;
    private static IUserWeightPersistence userWeightPersistence = null;
    private static IUserWorkoutPersistence userWorkoutPersistence = null;
    private static IExerciseWorkoutPersistence exerciseWorkoutPersistence = null;
    private static IFoodPersistence foodPersistence = null;
    private static IMealPersistence mealPersistence = null;
    private static IFoodMealPersistence foodMealPersistence = null;
    private static IUserMealPersistence userMealPersistence = null;


    public static synchronized IUserPersistence getUserPersistence()
    {
        if (userPersistence == null)
        {
            userPersistence = new UserPersistenceHSQLDB(Main.getDBPathName());
        }
        return userPersistence;
    }

    public static synchronized IWeightPersistence getWeightPersistence()
    {
        if (weightPersistence == null)
        {
            weightPersistence = new WeightPersistenceHSQLDB(Main.getDBPathName());
        }
        return weightPersistence;
    }

    public static synchronized IWorkoutPersistence getWorkoutPersistence()
    {
        if (workoutPersistence == null)
        {
            workoutPersistence = new WorkoutPersistenceHSQLDB(Main.getDBPathName());
        }
        return workoutPersistence;
    }

    public static synchronized IExercisePersistence getExercisePersistence()
    {
        if (exercisePersistence == null)
        {
            exercisePersistence = new ExercisePersistenceHSQLDB(Main.getDBPathName());
        }
        return exercisePersistence;
    }

    public static synchronized IUserWeightPersistence getUserWeightPersistence() {

        if (userWeightPersistence == null) {
            userWeightPersistence = new UserWeightPersistenceHSQLDB(Main.getDBPathName());
        }
        return userWeightPersistence;
    }

    public static synchronized IUserWorkoutPersistence getUserWorkoutPersistence() {

        if (userWorkoutPersistence == null) {
            userWorkoutPersistence = new UserWorkoutPersistenceHSQLDB(Main.getDBPathName());
        }
        return userWorkoutPersistence;
    }

    public static synchronized IExerciseWorkoutPersistence getExerciseWorkoutPersistence() {

        if (exerciseWorkoutPersistence == null) {
            exerciseWorkoutPersistence = new ExerciseWorkoutPersistenceHSQLDB(Main.getDBPathName());
        }
        return exerciseWorkoutPersistence;
    }

    public static synchronized IFoodPersistence getFoodPersistence() {

        if (foodPersistence == null) {
            foodPersistence = new FoodPersistenceHSQLDB(Main.getDBPathName());
        }
        return foodPersistence;
    }

    public static synchronized IMealPersistence getMealPersistence() {

        if (mealPersistence == null) {
            mealPersistence = new MealPersistenceHSQLDB(Main.getDBPathName());
        }
        return mealPersistence;
    }

    public static synchronized IFoodMealPersistence getFoodMealPersistence() {

        if (foodMealPersistence == null) {
            foodMealPersistence = new FoodMealPersistenceHSQLDB(Main.getDBPathName());
        }
        return foodMealPersistence;
    }

    public static synchronized IUserMealPersistence getUserMealPersistence() {

        if (userMealPersistence == null) {
            userMealPersistence = new UserMealPersistenceHSQLDB(Main.getDBPathName());
        }
        return userMealPersistence;
    }


}
