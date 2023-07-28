package com.example.fitness_tracker.domain_specific_objects;

import java.util.List;

/**
 * DATABASE INTERFACE
 */
public interface IDatabase {

    //get rows in the database
    public User getUser(String username);
    public Weight getWeight(int weightID);
    public Workout getWorkout(int workoutID);
    public Exercise getExercise(int exerciseID);
    public Food getFood(int foodID);
    public Meal getMeal(int mealID);
    public List<UserWeight> getUserWeight(String username);
    public List<UserWeight> getUserWeight(int weightID);
    public List<FoodMeal> getFoodMealByFood(int foodID);
    public List<FoodMeal> getFoodMealByMeal(int mealID);
    public List<UserMeal> getUserMealByUser(String username);
    public List<UserMeal> getUserMealByMeal(int mealID);
    public List<UserWorkout> getUserWorkout(String username);
    public List<UserWorkout> getUserWorkout(int workoutID);
    public List<ExerciseWorkout> getExerciseWorkoutByExercise(int exerciseID);
    public List<ExerciseWorkout> getExerciseWorkoutByWorkout(int workoutID);
    //insert rows in the database
    public int insert(User u);
    public int insert(Weight w);
    public int insert(Exercise e);
    public int insert(Workout w);
    public int insert(UserWeight uwe);
    public int insert(UserWorkout uwo);
    public int insert(ExerciseWorkout ew);
    public int insert(Meal m);
    public int insert(Food f);
    public int insert(FoodMeal fm);
    public int insert(UserMeal um);
    //delete rows in the database
    public int delete(User w);
    public int delete(Weight w);
    public int delete(Exercise e);
    public int delete(Workout w);
    public int delete(UserWeight uwe);
    public int delete(UserWorkout uwo);
    public int delete(ExerciseWorkout ew);
    public int delete(Food f);
    public int delete(Meal m);
    public int delete(FoodMeal fm);
    public int delete(UserMeal um);
    //update rows in the database
    public int update(User w);
    public int update(Weight w);
    public int update(Exercise e);
    public int update(Workout w);
    public int update(UserWeight uwe);
    public int update(UserWorkout uwo);
    public int update(Food f);
    public int update(Meal m);
    public int update(UserMeal um);
    //get size of tables in the database
    public int exerciseSize();
    public int workoutSize();
    public int exerciseWorkoutSize();
    public int userSize();
    public int userWorkoutSize();
    public int userMealSize();
    public int mealSize();
    public int foodSize();
    public int foodMealSize();
    public int weightSize();
    public int userWeightSize();
}
