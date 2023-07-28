package com.example.fitness_tracker;

/*
RealDatabase.java

PURPOSE: A real implementation for a database that contains tables for all domain-specific objects
using HSQLDB
 */

import com.example.fitness_tracker.business.AccessExercise;
import com.example.fitness_tracker.business.AccessExerciseWorkout;
import com.example.fitness_tracker.business.AccessFood;
import com.example.fitness_tracker.business.AccessFoodMeal;
import com.example.fitness_tracker.business.AccessMeal;
import com.example.fitness_tracker.business.AccessUser;
import com.example.fitness_tracker.business.AccessUserMeal;
import com.example.fitness_tracker.business.AccessUserWeight;
import com.example.fitness_tracker.business.AccessUserWorkout;
import com.example.fitness_tracker.business.AccessWeight;
import com.example.fitness_tracker.business.AccessWorkout;
import com.example.fitness_tracker.domain_specific_objects.IDatabase;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.Food;
import com.example.fitness_tracker.domain_specific_objects.FoodMeal;
import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserMeal;
import com.example.fitness_tracker.domain_specific_objects.UserWeight;
import com.example.fitness_tracker.domain_specific_objects.UserWorkout;
import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.domain_specific_objects.Workout;

import java.util.List;

public class RealDatabase implements IDatabase {

    private AccessUser userTable;
    private AccessWeight weightTable;
    private AccessExercise exerciseTable;
    private AccessWorkout workoutTable;
    private AccessUserWeight userWeightTable;
    private AccessUserWorkout userWorkoutTable;
    private AccessExerciseWorkout exerciseWorkoutTable;
    private AccessFood foodTable;
    private AccessMeal mealTable;
    private AccessFoodMeal foodMealTable;
    private AccessUserMeal userMealTable;
    private User currentUser; //gives the current user that is pointed to by the FakeDatabase
    /**
     * The current instance!
     */
    private static RealDatabase realDatabaseInstance;

    /**
     * Creates the instance if there is none, gives the instance if there is one
     *
     * @return RealDatabase instance
     */
    public static RealDatabase getDatabaseInstance() {
        if (realDatabaseInstance == null)
            realDatabaseInstance = new RealDatabase();
        return realDatabaseInstance;
    }


    /**
     * The Real Database
     */
    public RealDatabase() {
        userTable = new AccessUser();
        weightTable = new AccessWeight();
        exerciseTable = new AccessExercise();
        workoutTable = new AccessWorkout();
        userWeightTable = new AccessUserWeight();
        userWorkoutTable = new AccessUserWorkout();
        exerciseWorkoutTable = new AccessExerciseWorkout();
        foodTable = new AccessFood();
        mealTable = new AccessMeal();
        foodMealTable = new AccessFoodMeal();
        userMealTable = new AccessUserMeal();

        currentUser = null;
    }

    public RealDatabase(boolean empty) {
        if (empty) {
            userTable = null;
            weightTable = null;
            exerciseTable = null;
            workoutTable = null;
            userWeightTable = null;
            userWorkoutTable = null;
            exerciseWorkoutTable = null;
            foodTable = null;
            mealTable = null;
            foodMealTable = null;
            userMealTable = null;
            currentUser = null;
        }
    }



    //Note, for all methods with int return values: If method returns 1, it it successful, if it
    //returns -1, then it is unsuccessful

    /********************************************************************************************
     *                               INSERTS OBJECTS INTO THE DB
     * ********************************************************************************************/

    public int insert(User u) {
        int output = -1;
        if (userTable.insertUser(u) == u)
            output = 1;
        return output;
    }

    public int insert(Weight w) {
        int output = -1;
        if (weightTable.insertWeight(w) == w)
            output = 1;
        return output;
    }


    public int insert(Exercise e) {
        int output = -1;
        if (exerciseTable.insertExercise(e) == e)
            output = 1;
        return output;
    }

    public int insert(Workout w) {
        int output = -1;
        if (workoutTable.insertWorkout(w) == w)
            output = 1;
        return output;
    }


    public int insert(UserWeight uwe) {
        int output = -1;
        if (userWeightTable.insertUserWeight(uwe) == uwe)
            output = 1;
        return output;
    }

    public int insert(UserWorkout uwo) {
        int output = -1;
        if (userWorkoutTable.insertUserWorkout(uwo) == uwo)
            output = 1;
        return output;
    }

    public int insert(ExerciseWorkout ew) {
        int output = -1;
        if (exerciseWorkoutTable.insertExerciseWorkout(ew) == ew)
            output = 1;
        return output;
    }

    public int insert(Food f) {
        int output = -1;
        if (foodTable.insertFood(f) == f)
            output = 1;
        return output;
    }

    public int insert(Meal m) {
        int output = -1;
        if (mealTable.insertMeal(m) == m)
            output = 1;
        return output;
    }

    public int insert(FoodMeal fm) {
        int output = -1;
        if (foodMealTable.insertFoodMeal(fm) == fm)
            output = 1;
        return output;
    }

    public int insert(UserMeal um) {
        int output = -1;
        if (userMealTable.insertUserMeal(um) == um)
            output = 1;
        return output;
    }

    /********************************************************************************************
     *                               GETS THE OBJECTS OF THE SPECIFIC TABLES
     * ********************************************************************************************/

    public User getUser(String username) {
        return userTable.getRandom(username);
    }


    public Weight getWeight(int weightID) {
        return weightTable.getRandom(weightID);
    }


    public Workout getWorkout(int workoutID) {
        return workoutTable.getRandom(workoutID);
    }


    public Exercise getExercise(int exerciseID) {
        return exerciseTable.getRandom(exerciseID);
    }

    public Food getFood(int foodID) {
        return foodTable.getRandom(foodID);
    }

    public Meal getMeal(int mealID) {
        return mealTable.getRandom(mealID);
    }


    public List<UserWeight> getUserWeight(String username) {
        return userWeightTable.getUserWeight(username);
    }


    public List<UserWeight> getUserWeight(int weightID) {
        return userWeightTable.getWeightUser(weightID);
    }


    public List<UserWorkout> getUserWorkout(String username) {
        return userWorkoutTable.getUserWorkout(username);
    }


    public List<UserWorkout> getUserWorkout(int workoutID) {
        return userWorkoutTable.getWorkoutUser(workoutID);
    }


    public List<ExerciseWorkout> getExerciseWorkoutByExercise(int exerciseID) {
        return exerciseWorkoutTable.getExerciseWorkout(exerciseID);
    }


    public List<ExerciseWorkout> getExerciseWorkoutByWorkout(int workoutID) {
        return exerciseWorkoutTable.getWorkoutExercise(workoutID);
    }

    public List<FoodMeal> getFoodMealByFood(int foodID) {
        return foodMealTable.getFoodMeal(foodID);
    }

    public List<FoodMeal> getFoodMealByMeal(int mealID) {
        return foodMealTable.getMealFood(mealID);
    }

    public List<UserMeal> getUserMealByUser(String username) {
        return userMealTable.getUserMeal(username);
    }

    public List<UserMeal> getUserMealByMeal(int mealID) {
        return userMealTable.getMealUser(mealID);
    }


    /********************************************************************************************
     *                               DELETES THE GIVEN OBJECTS FROM THE DB
     * ********************************************************************************************/
    public int delete(User w) {
        userTable.deleteUser(w);
        return 1;
    }

    public int delete(Weight w) {
        weightTable.deleteWeight(w);
        return 1;
    }

    public int delete(Exercise e) {
        exerciseTable.deleteExercise(e);
        return 1;
    }

    public int delete(Workout w) {
        workoutTable.deleteWorkout(w);
        return 1;
    }

    public int delete(UserWeight uwe) {
        userWeightTable.deleteUserWeight(uwe);
        return 1;
    }

    public int delete(UserWorkout uwo) {
        userWorkoutTable.deleteUserWorkout(uwo);
        return 1;
    }

    public int delete(ExerciseWorkout ew) {
        exerciseWorkoutTable.deleteExerciseWorkout(ew);
        return 1;
    }

    public int delete(Food f) {
        foodTable.deleteFood(f);
        return 1;
    }

    public int delete(Meal m) {
        mealTable.deleteMeal(m);
        return 1;
    }

    public int delete(FoodMeal fm) {
        foodMealTable.deleteFoodMeal(fm);
        return 1;
    }

    public int delete(UserMeal um) {
        userMealTable.deleteUserMeal(um);
        return 1;
    }

    /********************************************************************************************
     *                               THE UPDATES FOR THE DB
     * ********************************************************************************************/
    //Note: ExerciseWorkout and FoodMeal do not need update methods as there are no values to update(except primary key)

    public int update(User w) {
        int output = -1;
        if (userTable.updateUser(w) != null)
            output = 1;
        return output;
    }

    public int update(Weight w) {
        int output = -1;
        if (weightTable.updateWeight(w) != null)
            output = 1;
        return output;
    }

    public int update(Exercise e) {
        int output = -1;
        if (exerciseTable.updateExercise(e) != null)
            output = 1;
        return output;
    }

    public int update(Workout w) {
        int output = -1;
        if (workoutTable.updateWorkout(w) != null)
            output = 1;
        return output;
    }

    public int update(UserWeight uwe) {
        int output = -1;
        if (userWeightTable.updateUserWeight(uwe) != null)
            output = 1;
        return output;
    }

    public int update(UserWorkout uwo) {
        int output = -1;
        if (userWorkoutTable.updateUserWorkout(uwo) != null)
            output = 1;
        return output;
    }

    public int update(Food f) {
        int output = -1;
        if (foodTable.updateFood(f) != null)
            output = 1;
        return output;
    }

    public int update(Meal m) {
        int output = -1;
        if (mealTable.updateMeal(m) != null)
            output = 1;
        return output;
    }

    public int update(UserMeal um) {
        int output = -1;
        if (userMealTable.updateUserMeal(um) != null)
            output = 1;
        return output;
    }

//    public int update(ExerciseWorkout ew) {
//        int output = -1;
//        if (userMealTable.updateUserMeal(ew) != null)
//            output = 1;
//        return output;
//    }
//

    /**
     * Author: Hao Qin
     * update foodMeal to the table
     * @param fm
     * @return
     */
    public int update(FoodMeal fm) {
        int output = -1;
        if (foodMealTable.insertFoodMeal(fm) != null)
            output = 1;
        return output;
    }

    public int exerciseSize() {
        return exerciseTable.exerciseSize();
    }

    public int exerciseWorkoutSize() {
        return exerciseWorkoutTable.exerciseWorkoutSize();
    }

    public int workoutSize() {
        return workoutTable.workoutSize();
    }

    public int userSize() {
        return userTable.userSize();
    }

    public int userMealSize() {
        return userMealTable.userMealSize();
    }

    public int mealSize() {
        return mealTable.mealSize();
    }

    public int foodSize() {
        return foodTable.foodSize();
    }

    public int foodMealSize() {
        return foodMealTable.foodMealSize();
    }

    public int weightSize() {
        return weightTable.weightSize();
    }

    public int userWeightSize() {
        return userWeightTable.userWeightSize();
    }

    public int userWorkoutSize() {
        return userWorkoutTable.userWorkoutSize();
    }

    /**
     * GETTER AND SETTER
     *
     * For the current user
     */
    public User getCurrentUser() {
        if(currentUser == null) {
            currentUser = getUser("janesmith123"); // DEFAULT USER
        }

        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void resetCurrentUser() {
        this.currentUser = null;
    }


    public void setUserTable(AccessUser userTable) {
        this.userTable = userTable;
    }

    public void setWeightTable(AccessWeight weightTable) {
        this.weightTable = weightTable;
    }

    public void setExerciseTable(AccessExercise exerciseTable) {
        this.exerciseTable = exerciseTable;
    }

    public void setWorkoutTable(AccessWorkout workoutTable) {
        this.workoutTable = workoutTable;
    }

    public void setUserWeightTable(AccessUserWeight userWeightTable) {
        this.userWeightTable = userWeightTable;
    }

    public void setUserWorkoutTable(AccessUserWorkout userWorkoutTable) {
        this.userWorkoutTable = userWorkoutTable;
    }

    public void setExerciseWorkoutTable(AccessExerciseWorkout exerciseWorkoutTable) {
        this.exerciseWorkoutTable = exerciseWorkoutTable;
    }

    public void setFoodTable(AccessFood foodTable) {
        this.foodTable = foodTable;
    }

    public void setMealTable(AccessMeal mealTable) {
        this.mealTable = mealTable;
    }

    public void setFoodMealTable(AccessFoodMeal foodMealTable) {
        this.foodMealTable = foodMealTable;
    }

    public void setUserMealTable(AccessUserMeal userMealTable) {
        this.userMealTable = userMealTable;
    }
}