package com.example.fitness_tracker.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.User;
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

import java.io.File;
import java.io.IOException;

public class AccountTests {

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
    public void testUser() {
        User user = database.getUser("janesmith123");
        assertNotNull(user);
        user = null;
        user = database.getUser("mikejames123");
        assertNotNull(user);
    }

    @Test
    public void currentUserIsNull() {
        assertNotNull(database.getCurrentUser());
    }

    @Test
    public void currentUserChange() {
        User user = database.getUser("mikejames123");
        User jane = database.getUser("janesmith123");
        database.setCurrentUser(user);
        assertNotNull(database.getCurrentUser());
        assertNotEquals(jane, database.getCurrentUser());
        assertEquals(user, database.getCurrentUser());
    }

    @Test
    public void userInfo() {
        User user = database.getCurrentUser();
        assertNotNull(user);
        assertNotNull(user.getUsername());
        assertNotEquals(user.getUsername(), "");
        assertNotNull(user.getFirstName());
        assertNotEquals(user.getFirstName(), "");
        assertNotNull(user.getLastName());
        assertNotEquals(user.getLastName(), "");
        assertNotNull(user.getPassword());
        assertNotEquals(user.getPassword(), "");
        assertNotNull(user.getGoalWeight());
    }

    @Test
    public void userInfoChange() {
        User user = database.getCurrentUser();
        String username = user.getUsername();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userToChange = "zachrocks";
        String firstToChange = "zach";
        String secondToChange = "lebron";
        assertNotNull(username);
        assertNotNull(firstName);
        assertNotNull(lastName);

        user.setUsername(userToChange);
        user.setFirstName(firstToChange);
        user.setLastName(secondToChange);

        assertEquals(user.getUsername(), userToChange);
        assertEquals(user.getFirstName(), firstToChange);
        assertEquals(user.getLastName(), secondToChange);
    }

    @Test
    public void userInfoOnChange() {
        User user = database.getCurrentUser();
        User mike = database.getUser("mikejames123");
        String username = user.getUsername();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userToChange = "zachrocks";
        String firstToChange = "zach";
        String secondToChange = "lebron";
        user.setUsername(userToChange);
        user.setFirstName(firstToChange);
        user.setLastName(secondToChange);
        assertNotNull(username);
        assertNotNull(firstName);
        assertNotNull(lastName);
        assertNotNull(mike);


        database.setCurrentUser(mike);
        user = database.getCurrentUser();
        assertNotEquals(user.getUsername(), userToChange);
        assertNotEquals(user.getFirstName(), firstToChange);
        assertNotEquals(user.getLastName(), secondToChange);
    }

}
