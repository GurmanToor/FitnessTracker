package com.example.fitness_tracker;

/*
FakeDatabase.java

PURPOSE: A fake implementation for a database that contains tables for all domain-specific objects
 */

import com.example.fitness_tracker.business.AccessExerciseFake;
import com.example.fitness_tracker.business.AccessExerciseWorkoutFake;
import com.example.fitness_tracker.business.AccessFoodFake;
import com.example.fitness_tracker.business.AccessFoodMealFake;
import com.example.fitness_tracker.business.AccessMealFake;
import com.example.fitness_tracker.business.AccessUserFake;
import com.example.fitness_tracker.business.AccessUserMealFake;
import com.example.fitness_tracker.business.AccessUserWeightFake;
import com.example.fitness_tracker.business.AccessUserWorkoutFake;
import com.example.fitness_tracker.business.AccessWeightFake;
import com.example.fitness_tracker.business.AccessWorkoutFake;
import com.example.fitness_tracker.domain_specific_objects.IDatabase;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.Food;
import com.example.fitness_tracker.domain_specific_objects.FoodMeal;
import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.domain_specific_objects.Strength;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserMeal;
import com.example.fitness_tracker.domain_specific_objects.UserWeight;
import com.example.fitness_tracker.domain_specific_objects.UserWorkout;
import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.domain_specific_objects.Workout;

import java.math.BigDecimal;
import java.util.List;

public class FakeDatabase implements IDatabase {

    private final AccessUserFake userTable;
    private final AccessWeightFake weightTable;
    private final AccessExerciseFake exerciseTable;
    private final AccessWorkoutFake workoutTable;
    private final AccessUserWeightFake userWeightTable;
    private final AccessUserWorkoutFake userWorkoutTable;
    private final AccessExerciseWorkoutFake exerciseWorkoutTable;
    private final AccessFoodFake foodTable;
    private final AccessMealFake mealTable;
    private final AccessFoodMealFake foodMealTable;
    private final AccessUserMealFake userMealTable;
    private User currentUser; //gives the current user that is pointed to by the FakeDatabase
    /**
     * The current instance!
     */
    private static FakeDatabase fakeDatabaseInstance;

    /**
     * Creates the instance if there is none, gives the instance if there is one
     *
     * @return FakeDatabase instance
     */
    public static FakeDatabase getDatabaseInstance() {
        if (fakeDatabaseInstance == null)
            fakeDatabaseInstance = new FakeDatabase();
        return fakeDatabaseInstance;
    }


    /**
     * The Fake Database
     */
    public FakeDatabase() {
        userTable = new AccessUserFake();
        weightTable = new AccessWeightFake();
        exerciseTable = new AccessExerciseFake();
        workoutTable = new AccessWorkoutFake();
        userWeightTable = new AccessUserWeightFake();
        userWorkoutTable = new AccessUserWorkoutFake();
        exerciseWorkoutTable = new AccessExerciseWorkoutFake();
        foodTable = new AccessFoodFake();
        mealTable = new AccessMealFake();
        foodMealTable = new AccessFoodMealFake();
        userMealTable = new AccessUserMealFake();

        //load with default values
        User u = new User("janesmith123","password123","Jane","Smith",new BigDecimal("145.0"),"janesmith@yahoo.ca");
        currentUser = u;
        Weight w = new Weight(1,new BigDecimal("155.5"));
//        Workout wo = new Strength("00:30:00","2022/02/25",1,10,3);
//        Exercise ex = new Exercise(1,"Bicep Curls","Bicep","Strength");
        UserWeight uWeight = new UserWeight(u,w,"2022/02/25","09:23");
//        UserWorkout uWorkout = new UserWorkout(u,wo,"2022/02/25","19:30");
//        ExerciseWorkout eWorkout = new ExerciseWorkout(ex,wo);
//        userTable.insertUser(u);
//        weightTable.insertWeight(w);
//        exerciseTable.insertExercise(ex);
//        workoutTable.insertWorkout(wo);
//        userWeightTable.insert(uWeight);
//        userWorkoutTable.insert(uWorkout);
//        exerciseWorkoutTable.insert(eWorkout);
    }

    /********************************************************************************************
     *                               INSERTS OBJECTS INTO THE DB
     * ********************************************************************************************/
    //@RequiresApi(api = Build.VERSION_CODES.N)
    public int insert(User u) {
        int output = -1;
        if (userTable.insertUser(u) == u)
            output = 1;
        return output;
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)

    public int insert(Weight w) {
        int output = -1;
        if (weightTable.insertWeight(w) == w)
            output = 1;
        return output;
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)

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
        return userTable.getUserByID(username);
    }


    public Weight getWeight(int weightID) {
        return weightTable.getWeightByID(weightID);
    }


    public Workout getWorkout(int workoutID) {
        return workoutTable.getWorkoutByID(workoutID);
    }


    public Exercise getExercise(int exerciseID) {
        return exerciseTable.getExerciseByID(exerciseID);
    }

    public Food getFood(int foodID) {
        return foodTable.getFood(foodID);
    }

    public Meal getMeal(int mealID) {
        return mealTable.getMeal(mealID);
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

    public int update(ExerciseWorkout ew) {
        int output = -1;
        if (exerciseWorkoutTable.updateExerciseWorkout(ew) != null)
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


    /********************************************************************************************
     *                               GETS THE SIZES OF THE SPECIFIC TABLES
     * ********************************************************************************************/
    public int userSize() {
        return userTable.userSize();
    }

    public int weightSize() {
        return weightTable.weightSize();
    }

    public int workoutSize() {
        return workoutTable.workoutSize();
    }

    public int exerciseSize() {
        return exerciseTable.exerciseSize();
    }

    public int userWeightSize() {
        return userWeightTable.userWeightSize();
    }

    public int userWorkoutSize() {
        return userWorkoutTable.userWorkoutSize();
    }

    public int exerciseWorkoutSize() {
        return exerciseWorkoutTable.exerciseWorkoutSize();
    }

    public int foodSize() {
        return foodTable.foodSize();
    }

    public int mealSize() {
        return mealTable.mealSize();
    }

    public int foodMealSize() {
        return foodMealTable.foodMealSize();
    }

    public int userMealSize() {
        return userMealTable.userMealSize();
    }

    /********************************************************************************************
     *                               CHECKS IF THE TABLES ARE EMPTY
     * ********************************************************************************************/
    public boolean userIsEmpty() {
        return userTable.isEmpty();
    }

    public boolean weightIsEmpty() {
        return weightTable.isEmpty();
    }

    public boolean workoutIsEmpty() {
        return workoutTable.isEmpty();
    }

    public boolean exerciseIsEmpty() {
        return exerciseTable.isEmpty();
    }

    public boolean userWeightIsEmpty() {
        return userWeightTable.isEmpty();
    }

    public boolean userWorkoutIsEmpty() {
        return userWorkoutTable.isEmpty();
    }

    public boolean exerciseWorkoutIsEmpty() {
        return exerciseWorkoutTable.isEmpty();
    }

    /**
     * GETTER AND SETTER
     *
     * For the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
