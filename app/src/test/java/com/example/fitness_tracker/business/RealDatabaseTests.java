package com.example.fitness_tracker.business;

import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.Cardio;
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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class RealDatabaseTests {
    private RealDatabase database;
    private File tempDB;


    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final IUserPersistence userPersistence = new UserPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IUserWorkoutPersistence userWorkoutPersistence = new UserWorkoutPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IUserWeightPersistence userWeightPersistence = new UserWeightPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IUserMealPersistence userMealPersistence = new UserMealPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IExercisePersistence exercisePersistence = new ExercisePersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IExerciseWorkoutPersistence exerciseWorkoutPersistence = new ExerciseWorkoutPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IWorkoutPersistence workoutPersistence = new WorkoutPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IFoodMealPersistence foodMealPersistence = new FoodMealPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IWeightPersistence weightPersistence = new WeightPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IMealPersistence mealPersistence = new MealPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IFoodPersistence foodPersistence = new FoodPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        database = new RealDatabase(true);
        database.setUserTable(new AccessUser(userPersistence));
        database.setUserWorkoutTable(new AccessUserWorkout(userWorkoutPersistence));
        database.setUserWeightTable(new AccessUserWeight(userWeightPersistence));
        database.setUserMealTable(new AccessUserMeal(userMealPersistence));
        database.setExerciseTable(new AccessExercise(exercisePersistence));
        database.setExerciseWorkoutTable(new AccessExerciseWorkout(exerciseWorkoutPersistence));
        database.setWorkoutTable(new AccessWorkout(workoutPersistence));
        database.setFoodMealTable(new AccessFoodMeal(foodMealPersistence));
        database.setWeightTable(new AccessWeight(weightPersistence));
        database.setMealTable(new AccessMeal(mealPersistence));
        database.setFoodTable(new AccessFood(foodPersistence));
    }

    @Test
    public void getUser() {
        assertEquals("janesmith123",database.getUser("janesmith123").getUsername());
    }

    @Test
    public void insertUser() {
        assertEquals(1,database.insert(new User("joejoe123")));
    }

    @Test
    public void updateUser() {
        assertEquals(1, database.update(new User("janesmith123","password123","Joe","Smith",new BigDecimal("155.0"),"toorg1@myumanitoba.ca")));
        assertEquals("Joe",database.getUser("janesmith123").getFirstName());
    }

    @Test
    public void deleteUser() {
        assertEquals(1,database.delete(new User("joejoe123")));
        assertNull(database.getUser("joejoe123"));
    }

    @Test
    public void userSize() {
        assertEquals(3,database.userSize());
    }

    @Test
    public void getExercise() {
        assertEquals("Bicep Curls",database.getExercise(1).getName());
    }

    @Test
    public void updateExercise() {
        insertExercise();
        assertEquals(1,database.update(new Cardio(4,"Biking","All","Cardio",3,"km","00:03:11")));
    }

    @Test
    public void insertExercise() {
        assertEquals(1,database.insert(new Cardio(4,"Biking","Heart","Cardio",3,"km","00:03:11")));
    }

    @Test
    public void deleteExercise() {
        assertEquals(1,database.delete(new Cardio(4)));
    }

    @Test
    public void exerciseSize() {
        assertEquals(3,database.exerciseSize());
    }

    @Test
    public void getExerciseWorkout() {
        assertEquals(1,database.getExerciseWorkoutByExercise(1).get(0).getExercise().getExerciseID());
    }

    @Test
    public void insertExerciseWorkout() {
        assertEquals(1,database.insert(new ExerciseWorkout(new Cardio(2),new Workout(2))));
    }

    @Test
    public void deleteExerciseWorkout() {
        assertEquals(1, database.delete(new ExerciseWorkout(new Cardio(2),new Workout(2))));
    }

    @Test
    public void getFoodMeal() {
        assertEquals(1,database.getFoodMealByFood(1).get(0).getFood().getId());
    }

    @Test
    public void insertFoodMeal() {
        assertEquals(1,database.insert(new FoodMeal(new Food(2),new Meal(2))));
    }

    @Test
    public void deleteFoodMeal() {
        assertEquals(1,database.delete(new FoodMeal(new Food(2),new Meal(2))));
    }

    @Test
    public void foodMealSize() {
        assertEquals(4,database.foodMealSize());
    }

    @Test
    public void getFood() {
        assertEquals("Apple",database.getFood(1).getName());
    }

    @Test
    public void insertFood() {
        assertEquals(1, database.insert(new Food(6,"Jam",100)));
    }

    @Test
    public void updateFood() {
        insertFood();
        assertEquals(1,database.update(new Food(6,"Jam",90)));
        assertEquals(90,database.getFood(6).getCalories());
    }

    @Test
    public void deleteFood() {
        assertEquals(1,database.delete(new Food(6)));
    }

    @Test
    public void foodSize() {
        assertEquals(3,database.foodSize());
    }

    @Test
    public void getMeal() {
        assertEquals("Breakfast",database.getMeal(1).getName());
    }

    @Test
    public void insertMeal() {
        assertEquals(1,database.insert(new Meal(5,"Cheat")));
    }

    @Test
    public void updateMeal() {
        insertMeal();
        assertEquals(1, database.update(new Meal(5,"Cheat2")));
        assertEquals("Cheat2",database.getMeal(5).getName());
    }

    @Test
    public void deleteMeal() {
        assertEquals(1, database.delete(new Meal(5)));
    }

    @Test
    public void mealSize() {
        assertEquals(3,database.mealSize());
    }

    @Test
    public void getUserMeal() {
        assertEquals(1,database.getUserMealByMeal(1).get(0).getMeal().getId());
    }

    @Test
    public void insertUserMeal() {
        assertEquals(1,database.insert(new UserMeal(database.getUser("janesmith123"),database.getMeal(2),"2022/03/01","12:15")));
    }

    @Test
    public void updateUserMeal() {
        insertUserMeal();
        assertEquals(1,database.update(new UserMeal(database.getUser("janesmith123"),database.getMeal(2),"2022/03/02","12:15")));
        assertEquals("2022/03/02",database.getUserMealByUser("janesmith123").get(1).getDate());
    }

    @Test
    public void deleteUserMeal() {
        assertEquals(1, database.delete(new UserMeal(database.getUser("janesmith123"),database.getMeal(2),"2022/03/02","12:15")));
    }

    @Test
    public void userMealSize() {
        assertEquals(3,database.userMealSize());
    }

    @Test
    public void getUserWeight() {
        assertEquals("janesmith123",database.getUserMealByUser("janesmith123").get(0).getUser().getUsername());
    }

    @Test
    public void insertUserWeight() {
        assertEquals(1,database.insert(new UserWeight(database.getUser("janesmith123"),database.getWeight(3),"2022/11/13","20:00")));
    }

    @Test
    public void updateUserWeight() {
        insertUserWeight();
        assertEquals(1,database.update(new UserWeight(database.getUser("janesmith123"),database.getWeight(3),"2022/11/11","20:00")));
        assertEquals("2022/11/11",database.getUserWeight("janesmith123").get(1).getDate());
    }

    @Test
    public void deleteUserWeight() {
        assertEquals(1,database.delete(new UserWeight(database.getUser("janesmith123"),database.getWeight(3),"2022/11/11","20:00")));
    }

    @Test
    public void userWeightSize() {
        assertEquals(3,database.userWeightSize());
    }

    @Test
    public void getUserWorkout() {
        assertEquals("janesmith123",database.getUserWorkout("janesmith123").get(0).getUser().getUsername());
    }

    @Test
    public void insertUserWorkout() {
        assertEquals(1, database.insert(new UserWorkout(database.getUser("janesmith123"),database.getWorkout(1),"2022/11/13","20:00")));
    }

    @Test
    public void updateUserWorkout() {
        insertUserWorkout();
        assertEquals(1, database.update(new UserWorkout(database.getUser("janesmith123"),database.getWorkout(1),"2022/11/12","20:00")));
        assertEquals("2022/11/12",database.getUserWorkout("janesmith123").get(0).getDate());
    }

    @Test
    public void deleteUserWorkout() {
        assertEquals(1, database.delete(new UserWorkout(database.getUser("janesmith123"),database.getWorkout(1),"2022/11/12","20:00")));
    }

    @Test
    public void userWorkoutSize() {
        assertEquals(3,database.userWorkoutSize());
    }

    @Test
    public void getWeight() {
        assertEquals(1,database.getWeight(1).getWeightID());
    }

    @Test
    public void insertWeight() {
        assertEquals(1, database.insert(new Weight(5,new BigDecimal("155.5"))));
    }

    @Test
    public void updateWeight() {
        insertWeight();
        assertEquals(1, database.update(new Weight(5,new BigDecimal("135.5"))));
        assertEquals(new BigDecimal("135.5"),database.getWeight(5).getWeight());
    }

    @Test
    public void deleteWeight() {
        assertEquals(1,database.delete(new Weight(5,new BigDecimal("135.5"))));
    }

    @Test
    public void weightSize() {
        assertEquals(3,database.weightSize());
    }

    @Test
    public void getWorkout() {
        assertEquals(1,database.getWorkout(1).getWorkoutID());
    }

    @Test
    public void insertWorkout() {
        assertEquals(1,database.insert(new Workout(5,"Chest","00:24:22")));
    }

    @Test
    public void updateWorkout() {
        insertWorkout();
        assertEquals(1, database.update(new Workout(5,"Pull","00:24:22")));
        assertEquals("Pull",database.getWorkout(5).getName());
    }

    @Test
    public void deleteWorkout() {
        assertEquals(1,database.delete(new Workout(5,"Pull","00:24:22")));
    }

    @Test
    public void workoutSize() {
        assertEquals(3,database.workoutSize());
    }

}
