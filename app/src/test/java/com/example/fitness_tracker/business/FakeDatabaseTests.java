package com.example.fitness_tracker.business;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.example.fitness_tracker.application.Main;
import com.example.fitness_tracker.domain_specific_objects.Cardio;
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
import com.example.fitness_tracker.FakeDatabase;



import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;


public class FakeDatabaseTests {

    @Test
    public void insertUser() {
        FakeDatabase db = new FakeDatabase();
        assertEquals(db.insert(new User("kimboslice")),1);
    }

    @Test
    public void deleteUser() {
        FakeDatabase db = new FakeDatabase();
        db.delete(new User("janesmith123"));
        assertEquals(db.userSize(),2);
    }

    @Test
    public void updateUser() {
        FakeDatabase db = new FakeDatabase();
        db.update(new User("janesmith123","password355"
                ,"Jane","Smith",new BigDecimal("145.0"),"toorg1@myumanitoba.ca"));
        assertEquals("password355",db.getUser("janesmith123").getPassword());
    }

    @Test
    public void getUser() {
        FakeDatabase db = new FakeDatabase();
        User generic = db.getUser("janesmith123");
        assertEquals("password123",generic.getPassword());
    }

    @Test
    public void insertWeight() {
        FakeDatabase db = new FakeDatabase();
        Weight w = new Weight(4,new BigDecimal("155"));
        assertEquals(db.insert(w),1);
        assertEquals(4,db.weightSize());
    }

    @Test
    public void deleteWeight() {
        FakeDatabase db = new FakeDatabase();
        db.delete(new Weight(1));
        assertEquals(db.weightSize(),2);
    }

    @Test
    public void updateWeight() {
        FakeDatabase db = new FakeDatabase();
        db.update(new Weight(1,new BigDecimal("155.0")));
        assertEquals(new BigDecimal("155.0"),db.getWeight(1).getWeight());
    }

    @Test
    public void getWeight() {
        FakeDatabase db = new FakeDatabase();
        Weight w = db.getWeight(1);
        assertEquals(new BigDecimal("150.0"),w.getWeight());
    }

    @Test
    public void insertWorkout() {
        FakeDatabase db = new FakeDatabase();
        Workout w = new Workout(4,"2022/03/11","00:12:22");
        assertEquals(db.insert(w),1);
        assertEquals(4,db.workoutSize());
    }

    @Test
    public void deleteWorkout() {
        FakeDatabase db = new FakeDatabase();
        db.delete(new Workout(1));
        assertEquals(db.workoutSize(),2);
    }

    @Test
    public void updateWorkout() {
        FakeDatabase db = new FakeDatabase();
        Workout orig = db.getWorkout(1);
        db.update(new Workout(1,"2022/02/28","00:22:25"));
        assertNotEquals("00:22:25",orig.getLength());
        assertEquals("00:22:25",db.getWorkout(1).getLength());
    }

    @Test
    public void getWorkout() {
        FakeDatabase db = new FakeDatabase();
        assertEquals("00:34:27",db.getWorkout(1).getLength());
    }

    @Test
    public void insertExercise() {
        FakeDatabase db = new FakeDatabase();
        Exercise ex = new Cardio(4,"Running","Heart","Cardio",4,"km","00:35:33");
        assertEquals(db.insert(ex),1);
        assertEquals(4,db.exerciseSize());
    }

    @Test
    public void deleteExercise() {
        FakeDatabase db = new FakeDatabase();
        db.delete(new Cardio(1));
        assertEquals(db.exerciseSize(),2);
    }

    @Test
    public void updateExercise() {
        FakeDatabase db = new FakeDatabase();
        db.update(new Strength(1,"Shoulder Raises","Shoulder","Strength",8,4,"00:08:42"));
        assertEquals("Shoulder Raises",db.getExercise(1).getName());
    }

    @Test
    public void getExercise() {
        FakeDatabase db = new FakeDatabase();
        assertEquals("Bicep Curls",db.getExercise(1).getName());
    }

    @Test
    public void insertUserWeight() {
        FakeDatabase db = new FakeDatabase();
        User u = new User("johnappleseed");
        Weight w = new Weight(2,new BigDecimal("160.0"));
        assertEquals(db.insert(new UserWeight(u,w,"2022-02-28","09:45")),1);
        assertEquals(4,db.userWeightSize());
    }

    @Test
    public void deleteUserWeight() {
        FakeDatabase db = new FakeDatabase();
        User u = new User("janesmith123");
        Weight w = new Weight(2);
        UserWeight uw = new UserWeight(u,w,"2021/12/28","18:23");
        db.delete(uw);
        assertEquals(db.userWeightSize(),2);
    }

    @Test
    public void updateUserWeight() {
        FakeDatabase db = new FakeDatabase();
        String uw1 = db.getUserWeight(1).get(0).getDate();
        System.out.println(uw1);
        User u = new User("janesmith123");
        Weight w = new Weight(1);
        UserWeight uw = new UserWeight(u,w,"2022-02-23","09:23");
        db.update(uw);
        System.out.println();
        assertEquals("2021/12/25",db.getUserWeight(1).get(0).getDate());
    }

    @Test
    public void getUserWeight() {
        FakeDatabase db = new FakeDatabase();
        String uw1 = db.getUserWeight("janesmith123").get(0).getDate();
        assertEquals("2021/12/28",uw1);
    }

    @Test
    public void insertUserWorkout() {
        FakeDatabase db = new FakeDatabase();
        User u = new User("gurman");
        Workout w = new Workout(2);
        UserWorkout uw = new UserWorkout(u,w,"2022/04/02","21:00");
        db.insert(uw);
        assertEquals(4,db.userWorkoutSize());
    }

    @Test
    public void deleteUserWorkout() {
        FakeDatabase db = new FakeDatabase();
        UserWorkout uwo = db.getUserWorkout(1).get(0);
        db.delete(uwo);
        assertEquals(db.userWorkoutSize(),2);
    }

    @Test
    public void updateUserWorkout() {
        FakeDatabase db = new FakeDatabase();
        User u = new User("janesmith123");
        Workout w = new Workout(2,"2022/01/02","00:45:35");
        UserWorkout uwo = new UserWorkout(u,w,"2022/03/03","20:05");
        db.update(uwo);
        assertEquals("20:05",db.getUserWorkout(2).get(0).getTime());
    }

    @Test
    public void getUserWorkout() {
        FakeDatabase db = new FakeDatabase();
        assertEquals("gurman123",db.getUserWorkout(1).get(0).getUser().getUsername());
    }

    @Test
    public void insertExerciseWorkout() {
        FakeDatabase db = new FakeDatabase();
        Exercise e = new Strength(5,"Leg Press","Hamstrings","Strength",5,5,"00:11:12");
        Workout w = new Workout(5,"2022-02-27","00:22:55");
        ExerciseWorkout ew = new ExerciseWorkout(e,w);
        db.insert(ew);
        assertEquals(6,db.exerciseWorkoutSize());
    }

    @Test
    public void deleteExerciseWorkout() {
        FakeDatabase db = new FakeDatabase();
        Exercise e = new Strength(1,"Bicep Curls","Bicep","Strength",10,3,"00:08:42");
        Workout w = new Workout(1,"2022/01/01","00:34:27");
        ExerciseWorkout ew = new ExerciseWorkout(e,w);
        db.delete(ew);
        assertEquals(db.exerciseWorkoutSize(),4);
    }

    @Test
    public void getExerciseWorkout() {
        FakeDatabase db = new FakeDatabase();
        assertEquals("Push Day",db.getExerciseWorkoutByExercise(1).get(0).getWorkout().getName());
    }

    @Test
    public void insertFood() {
        FakeDatabase db = new FakeDatabase();
        assertEquals(db.insert(new Food(5,"Blueberries",30)),1);
    }

    @Test
    public void updateFood() {
        FakeDatabase db = new FakeDatabase();
        db.update(new Food(1,"Chocolate 1 tbsp",38));
        assertEquals(db.getFood(1).getName(),"Chocolate 1 tbsp");
    }

    @Test
    public void deleteFood() {
        FakeDatabase db = new FakeDatabase();
        db.delete(new Food(1,"Apple",80));
        assertEquals(db.foodSize(),2);
    }

    @Test
    public void getFood() {
        FakeDatabase db = new FakeDatabase();
        assertEquals(db.getFood(1).getCalories(),80);
    }

    @Test
    public void insertMeal() {
        FakeDatabase db = new FakeDatabase();
        assertEquals(db.insert(new Meal(5,"Cheat Day")),1);
    }

    @Test
    public void updateMeal() {
        FakeDatabase db = new FakeDatabase();
        db.update(new Meal(1,"Lunch 12"));
        assertEquals(db.getMeal(1).getName(),"Lunch 12");
    }

    @Test
    public void deleteMeal() {
        FakeDatabase db = new FakeDatabase();
        db.delete(new Meal(1,"Breakfast"));
        assertEquals(db.mealSize(),2);
    }

    @Test
    public void getMeal() {
        FakeDatabase db = new FakeDatabase();
        assertEquals(db.getMeal(1).getName(),"Breakfast");
    }

    @Test
    public void insertFoodMeal() {
        FakeDatabase db = new FakeDatabase();
        Food f = db.getFood(1);
        Meal m = db.getMeal(2);
        FoodMeal fm = new FoodMeal(f,m);
        assertEquals(db.insert(fm),1);
    }

    @Test
    public void deleteFoodMeal() {
        FakeDatabase db = new FakeDatabase();
        FoodMeal fm = db.getFoodMealByFood(1).get(0);
        db.delete(fm);
        assertEquals(db.foodMealSize(),3);
    }

    @Test
    public void getFoodMeal() {
        FakeDatabase db = new FakeDatabase();
        assertEquals(db.getFoodMealByFood(1).get(0).getFood().getName(),"Apple");
    }

    @Test
    public void insertUserMeal() {
        FakeDatabase db = new FakeDatabase();
        User u = db.getUser("janesmith123");
        Meal m = db.getMeal(2);
        UserMeal um = new UserMeal(u,m,"2022/02/11","20:15");
        assertEquals(db.insert(um),1);
    }

    @Test
    public void updateUserMeal() {
        FakeDatabase db = new FakeDatabase();
        User u = db.getUser("janesmith123");
        Meal m = db.getMeal(1);
        UserMeal um = new UserMeal(u,m,"2022/02/15","12:13");
        assertEquals(db.update(um),1);
        assertEquals(db.getUserMealByUser("janesmith123").get(0).getDate(),"2022/02/15");
    }

    @Test
    public void deleteUserMeal() {
        FakeDatabase db = new FakeDatabase();
        UserMeal um = db.getUserMealByUser("gurman123").get(0);
        db.delete(um);
        assertEquals(db.userMealSize(),2);
    }

    @Test
    public void getUserMeal() {
        FakeDatabase db = new FakeDatabase();
        assertEquals(db.getUserMealByUser("gurman123").get(0).getTime(),"14:25");
    }

    @Test
    public void testUserSize() {
        FakeDatabase db = new FakeDatabase();
        assertEquals(3,db.userSize());
        db.insert(new User("gurman"));
        assertEquals(4,db.userSize());
        db.delete(new User("janesmith123"));
        db.delete(new User("gurman"));
        assertEquals(2,db.userSize());
    }

    @Test
    public void testUserWeightSize() {
        FakeDatabase db = new FakeDatabase();
        assertEquals(3,db.userWeightSize());
        db.delete(new UserWeight(new User("janesmith123"),new Weight(2),"2021/12/28","18:23"));
        assertEquals(2,db.userWeightSize());
    }

}